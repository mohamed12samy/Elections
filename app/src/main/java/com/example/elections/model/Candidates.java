package com.example.elections.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Candidates implements Parcelable {


    private String name;
    private long votes;
    private String key;

    protected Candidates(Parcel in) {
        name = in.readString();
        votes = in.readLong();
        key = in.readString();
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
        parcel.writeString(key);
    }
}
