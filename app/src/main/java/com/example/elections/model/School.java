package com.example.elections.model;

import java.util.ArrayList;

public class School {
    private String name;
    private ArrayList<Integer> legan;

    public School(String name) {
        this.name = name;
    }

    public School(String name, ArrayList<Integer> legan) {
        this.name = name;
        this.legan = legan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getLegan() {
        return legan;
    }

    public void setLegan(ArrayList<Integer> legan) {
        this.legan = legan;
    }
}
