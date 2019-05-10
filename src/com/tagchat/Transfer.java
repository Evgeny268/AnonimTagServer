package com.tagchat;

import java.util.ArrayList;

public class Transfer {
    private String text = null;
    private byte sex = 0; // 0 - not set, 1 - male, 2 - female
    private ArrayList<String> tagList = null;
    private boolean findAllTag = false; //search for all tag matches or search for one of the tags
    private boolean adult = false;

    public Transfer(String text) {
        this.text = text;
    }

    public Transfer(byte sex, ArrayList<String> tagList, boolean findAllTag) {
        this.sex = sex;
        this.tagList = tagList;
        this.findAllTag = findAllTag;
    }

    public Transfer(boolean adult) {
        this.adult = adult;
    }

    public String getText() {
        return text;
    }

    public byte getSex() {
        return sex;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public boolean isFindAllTag() {
        return findAllTag;
    }

    public boolean isAdult() {
        return adult;
    }
}
