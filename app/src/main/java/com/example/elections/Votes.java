package com.example.elections;

public interface Votes {
     void ValidVotes(String validVotes);
    void InValidVotes(String inValidVotes);
    void TotalVotes(String votes);

    void getSeats(int governorate_position, int daira);
    void setSeats(int s);



}
