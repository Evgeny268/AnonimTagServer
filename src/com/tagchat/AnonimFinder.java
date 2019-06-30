package com.tagchat;

import java.util.ArrayList;
import java.util.Collections;

public class AnonimFinder extends Thread {
    @Override
    public void run() {
        while (true){
            if (ClientCreator.getClientList().size()==0){
                synchronized (ClientCreator.listLock) {
                    if (ClientCreator.getClientList().size()!=0) continue;
                    try {
                        ClientCreator.listLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                int i = 0;
                while (i<ClientCreator.getClientList().size()){
                    Client client = null;
                    synchronized (ClientCreator.listLock){
                        if (ClientCreator.getClientList().size()>i){
                            client = ClientCreator.getClientList().get(i);
                        } else continue;
                        Client client2 = findTwo(client);
                        if (client2!=null){
                            System.out.println("Найден 2-ой клиент");
                            synchronized (ClientCreator.listLock){
                                ClientCreator.getClientList().remove(client);
                                ClientCreator.getClientList().remove(client2);
                            }
                            System.out.println("Создаю диалог");
                            Dialog dialog = new Dialog(client,client2);
                            dialog.start();
                        }else continue;
                    }
                 i++;
                }

            }
        }
    }

    private Client findTwo(Client client){
        Client out = null;
        ArrayList<Client> clientList;
        synchronized (ClientCreator.listLock) {
            clientList = new ArrayList<>(ClientCreator.getClientList());
        }
        for (int i = 0; i < clientList.size(); i++) {//Цикл по всем клиентам в массиве
            if (client.equals(clientList.get(i))) continue;
            if (client.isFindAllTag()){ //Если поиск по всем тегам
                Collections.sort(client.getTagList());
                Collections.sort(clientList.get(i).getTagList());
                if (client.getTagList().equals(clientList.get(i).getTagList())){
                    if (client.getSex()==0){//Если пол первого клиента не задан
                        if (clientList.get(i).getInterlocutorSex()==0){//Если второму клиенту не важен пол
                            out = clientList.get(i);
                        } else continue;
                    }else { //Если пол первого клиента задан
                        if (client.getSex()==clientList.get(i).getInterlocutorSex() && client.getInterlocutorSex()==clientList.get(i).getSex()){//Если интересы по полу совпадают у обоих клиентов
                            out = clientList.get(i);
                        }else continue;
                    }
                }
            }else { //Если поиск по одному совпавшему тегу
                for (int j = 0; j < client.getTagList().size(); j++) {//Цикл по массиву тегов первого клиента
                    if (clientList.get(i).getTagList().contains(client.getTagList().get(j))){//Если совпал хоть один общий тег
                        if (client.getSex()==0){//Если пол первого клиента не задан
                            if (clientList.get(i).getInterlocutorSex()==0){//Если второму клиенту не важен пол
                                out = clientList.get(i);
                            } else continue;
                        }else { //Если пол первого клиента задан
                            if (client.getSex()==clientList.get(i).getInterlocutorSex() && client.getInterlocutorSex()==clientList.get(i).getSex()){//Если интересы по полу совпадают у обоих клиентов
                                out = clientList.get(i);
                            }else continue;
                        }
                    }
                }
            }
        }
        return out;
    }
}
