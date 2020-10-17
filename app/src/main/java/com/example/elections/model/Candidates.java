package com.example.elections.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


public class Candidates implements Parcelable {


    private String name;
    private long votes;
    private long votes_survay;
    private String key;
    private double percentage;

    protected Candidates(Parcel in) {
        name = in.readString();
        votes = in.readLong();
        votes_survay = in.readLong();
        key = in.readString();
        percentage = in.readDouble();
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(int votes, int pos) {

        this.percentage = pos == 0 ?(this.votes*100)/(votes == 0 ?1:votes) : (this.votes_survay*100)/(votes == 0 ?1:votes);
    }

    public static final Creator<Candidates> CREATOR = new Creator<Candidates>() {
        @Override
        public Candidates createFromParcel(Parcel in) {
            return new Candidates(in);
        }

        @Override
        public Candidates[] newArray(int size) {
            return new Candidates[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public Candidates( String key , String name, long votes, long votes_survay) {
        this.name = name;
        this.votes = votes;
        this.votes_survay = votes_survay;
        this.key = key;
        this.percentage = 1.0;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public long getVotes_survay() {
        return votes_survay;
    }

    public void setVotes_survay(long votes_survay) {
        this.votes_survay = votes_survay;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeLong(votes);
        parcel.writeLong(votes_survay);
        parcel.writeString(key);
        parcel.writeDouble(percentage);
    }
}
