package com.tagchat;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private Socket socket;
    private ObjectInputStream ois = null;
    private ObjectOutputStream oos = null;
    private byte sex; // 0 - not set, 1 - male, 2 - female
    private byte interlocutorSex; // 0 - not set, 1 - male, 2 - female
    private ArrayList<String> tagList;
    private boolean findAllTag; //search for all tag matches or search for one of the tags

    public Client(Socket socket, ObjectOutputStream oos, ObjectInputStream ois,byte sex, byte interlocutorSex, ArrayList<String> tagList, boolean findAllTag) {
        this.socket = socket;
        this.oos = oos;
        this.ois = ois;
        this.sex = sex;
        this.interlocutorSex = interlocutorSex;
        this.tagList = tagList;
        this.findAllTag = findAllTag;
    }
}
