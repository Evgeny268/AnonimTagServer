package com.tagchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4005);
        }catch (IOException e){
            e.printStackTrace();
            System.err.println("Can't create server socket");
            System.exit(-1);
        }
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Can't accept socket");
                continue;
            }
        }
    }
}
