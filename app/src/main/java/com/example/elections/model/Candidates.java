package com.example.elections.model;

import java.util.HashMap;

public class Candidates {


    private String name;
    private long votes;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Candidates(String key, String name, long votes) {
        this.key = key;
        this.name = name;
        this.votes = votes;
    }


}
