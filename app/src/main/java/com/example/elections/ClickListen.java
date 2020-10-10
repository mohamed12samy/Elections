package com.example.elections;

public interface ClickListen {

    void clicklisten(String key, int votes);
    void clickListenSurvey(String key1, int idx);
    void handleKey(int idx);
}
