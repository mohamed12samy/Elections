package com.example.elections.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Candidates {

    private String name;
    private int votes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Candidates(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("votes_num", votes);


        return result;
    }
}
