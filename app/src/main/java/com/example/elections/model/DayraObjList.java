package com.example.elections.model;

import androidx.lifecycle.ViewModel;

public class DayraObjList extends ViewModel {
    private String dayraName;
    private String qesmName;
    private String schoolName;
    private int lagnaNum;
    private int voteNum;

    public String getDayraName() {
        return dayraName;
    }

    public void setDayraName(String dayraName) {
        this.dayraName = dayraName;
    }

    public String getQesmName() {
        return qesmName;
    }

    public void setQesmName(String qesmName) {
        this.qesmName = qesmName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public int getLagnaNum() {
        return lagnaNum;
    }

    public void setLagnaNum(int lagnaNum) {
        this.lagnaNum = lagnaNum;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(int voteNum) {
        this.voteNum = voteNum;
    }
}
