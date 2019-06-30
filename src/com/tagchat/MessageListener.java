package com.tagchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageListener extends Thread{
    private ObjectInputStream objectInputStream = null;
    private ObjectOutputStream objectOutputStream = null;

    public MessageListener(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream) {
        this.objectInputStream = objectInputStream;
        this.objectOutputStream = objectOutputStream;
    }

    @Override
    public void run() {
        Transfer transfer = new Transfer("/Test_message");
        try {
            objectOutputStream.writeObject(transfer);
            objectOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Ошибка во время передачи сообщения 2-ух пользователей");
            return;
        }
        while (!isInterrupted()){
            try {
                Transfer tIn = (Transfer) objectInputStream.readObject();
                objectOutputStream.writeObject(tIn);
                objectOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Ошибка во время передачи сообщения 2-ух пользователей");
                try {
                    Transfer tError = new Transfer("/stop");
                    objectOutputStream.writeObject(tError);
                    objectOutputStream.flush();
                }catch (IOException e1){
                    e.printStackTrace();
                }
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.err.println("Ошибка во время передачи сообщения 2-ух пользователей");
                return;
            }
        }
    }
}
