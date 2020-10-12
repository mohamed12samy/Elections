package com.example.elections;

public interface Votes {
    public void ValidVotes(String validVotes);
    public void InValidVotes(String inValidVotes);

    void getSeats(int governorate_position, int daira);
    void setSeats(int s);

}
