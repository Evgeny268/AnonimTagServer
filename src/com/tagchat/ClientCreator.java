package com.tagchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientCreator extends Thread{
    private Socket socket;
    private static volatile ArrayList<Client> clientList = new ArrayList<>();
    private static final Object listLock = new Object();

    public ClientCreator(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            socket.setSoTimeout(5000);
        } catch (SocketException e) {
            e.printStackTrace();
            System.err.println("Can't set timeOut socket in ClientCreator");
            socketClose();
            return;
        }
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ois = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Can't create OIS in ClientCreator");
            socketClose();
            return;
        }

        try{
            oos = new ObjectOutputStream(socket.getOutputStream());
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Can't create OOS in ClientCreator");
            closeInputStream(ois);
            socketClose();
            return;
        }
        Transfer tIn = null;
        try {
            tIn = (Transfer) ois.readObject();
            System.out.println("Объект получен");
        } catch (IOException e) {
            e.printStackTrace();
            closeInputStream(ois);
            closeOutputStream(oos);
            socketClose();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            closeInputStream(ois);
            closeOutputStream(oos);
            socketClose();
            return;
        }
        if (tIn==null){
            closeInputStream(ois);
            closeOutputStream(oos);
            socketClose();
        }
        if (tIn.getTagList()==null){
            System.out.println("Пустые теги");
            HashMap<String,Integer> map = TopTags.getTopTegs(tIn.isAdult());
            Transfer tOut = new Transfer(map);
            try {
                oos.writeObject(tOut);
                oos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Can't send top tags");
            }finally {
                closeOutputStream(oos);
                closeInputStream(ois);
                socketClose();
            }
        }else {
            closeInputStream(ois);
            closeOutputStream(oos);
            Client client = new Client(socket,tIn.getSex(),tIn.getInterlocutorSex(), tIn.getTagList(),tIn.isFindAllTag());
            addClient(client);
            System.out.println("Клиент добавлен");
        }
    }


    private boolean socketClose(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't close socket in ClientCreator");
            return false;
        }
        return true;
    }

    private boolean closeInputStream(ObjectInputStream is){
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't close OIS in ClientCreator");
            return false;
        }
        return true;
    }

    private boolean closeOutputStream(ObjectOutputStream os){
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Can't close OOS in ClientCreator");
            return false;
        }
        return true;
    }

    private void addClient(Client client){
        synchronized (listLock){
            clientList.add(client);
        }
    }
}
