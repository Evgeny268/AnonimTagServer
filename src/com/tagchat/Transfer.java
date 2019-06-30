package com.tagchat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class Transfer implements Serializable {
    private String text = null;
    private byte sex = 0; // 0 - not set, 1 - male, 2 - female
    private byte interlocutorSex = 0; // 0 - not set, 1 - male, 2 - female
    private ArrayList<String> tagList = null;
    private boolean findAllTag = true; //search for all tag matches or search for one of the tags
    private boolean adult = false;
    private Map<String,Integer> topTags = null;

    public Transfer(String text) {
        this.text = text;
    }

    public Transfer(byte sex, byte interlocutorSex, ArrayList<String> tagList, boolean findAllTag) {
        if (sex<0 || sex>2){
            sex = 0;
            interlocutorSex = 0;
        }
        if (interlocutorSex<0 || interlocutorSex>2){
            interlocutorSex = 0;
        }
        this.sex = sex;
        this.interlocutorSex = interlocutorSex;
        this.tagList = tagList;
        this.findAllTag = findAllTag;
        if (sex==0){
            this.interlocutorSex = 0;
        }
    }

    public Transfer(Map<String, Integer> topTags) {
        this.topTags = topTags;
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

    public byte getInterlocutorSex() {
        return interlocutorSex;
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

    public Map<String, Integer> getTopTags() {
        return topTags;
    }
}
