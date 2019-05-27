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
        while (!isInterrupted()){
            try {
                Transfer tIn = (Transfer) objectInputStream.readObject();
                objectOutputStream.writeObject(tIn);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
