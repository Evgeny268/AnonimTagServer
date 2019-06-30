package com.tagchat;

public class Dialog extends Thread{
    private Client client1;
    private Client client2;

    public Dialog(Client client1, Client client2) {
        this.client1 = client1;
        this.client2 = client2;
    }

    @Override
    public void run() {
        MessageListener m1 = new MessageListener(client1.getOis(),client2.getOos());
        MessageListener m2 = new MessageListener(client2.getOis(),client1.getOos());
        m1.start();
        m2.start();
        System.out.println("Создан новый диалог");
        try {
            m1.join();
            m2.join();
        }catch (InterruptedException e){
            e.printStackTrace();
            System.err.println("Ошибка ожидания завершения диалога");
        }finally {
            client1.close();
            client2.close();
            synchronized (ClientCreator.listLock){
                ClientCreator.getClientList().remove(client1);
                ClientCreator.getClientList().remove(client2);
                System.out.println("Удаляю клиентов из листа");
            }
        }
    }
}
