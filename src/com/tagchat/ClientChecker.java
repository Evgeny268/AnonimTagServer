package com.tagchat;

public class ClientChecker extends Thread {

    @Override
    public void run() {
        while (!isInterrupted()) {
            System.out.println("Клиентов в массиве: " + ClientCreator.getClientList().size());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
