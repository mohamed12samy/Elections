package com.example.elections.model;

import java.util.ArrayList;

public class Dayra {
    private String dyra_name;
    private ArrayList<Qesm> qesms;
    private int depth;

    public Dayra() {
        this.depth = 0;
    }

    public String getDyra_name() {
        return dyra_name;
    }

    public void setDyra_name(String dyra_name) {
        this.dyra_name = dyra_name;
    }

    public ArrayList<Qesm> getQesms() {
        return qesms;
    }

    public void setQesms(ArrayList<Qesm> qesms) {
        this.qesms = qesms;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
