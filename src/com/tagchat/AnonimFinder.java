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
                            synchronized (ClientCreator.listLock){
                                ClientCreator.getClientList().remove(client);
                                ClientCreator.getClientList().remove(client2);
                            }
                            Dialog dialog = new Dialog(client,client2);
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
        for (int i = 0; i < clientList.size(); i++) {
            if (client.equals(clientList.get(i))) continue;
            if (client.isFindAllTag()){
                Collections.sort(client.getTagList());
                Collections.sort(clientList.get(i).getTagList());
                if (client.getTagList().equals(clientList.get(i).getTagList())){
                    out = clientList.get(i);
                }
            }else {
                for (int j = 0; j < client.getTagList().size(); j++) {
                    if (clientList.get(i).getTagList().contains(client.getTagList().get(j))){
                        out = clientList.get(i);
                    }
                }
            }
        }
        return out;
    }
}
