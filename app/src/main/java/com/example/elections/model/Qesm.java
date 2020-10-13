package com.example.elections.model;

import java.util.ArrayList;

public class Qesm {
    private String name;
    private ArrayList<School> schools;

    public ArrayList<School> getSchools() {
        return schools;
    }

    public Qesm(String name) {
        this.name = name;
    }

    public Qesm(String name, ArrayList<School> schools) {
        this.name = name;
        this.schools = schools;
    }

    public void setSchools(ArrayList<School> schools) {
        this.schools = schools;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
