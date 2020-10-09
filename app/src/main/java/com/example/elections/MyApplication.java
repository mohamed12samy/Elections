package com.example.elections;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MyApplication extends Application {

    private static MyApplication mMyApplication;
    private static FirebaseDatabase database;

    public static synchronized MyApplication getInstance(){
        return mMyApplication;
    }

    public static synchronized FirebaseDatabase getFirebaseInstance(){
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        database = FirebaseDatabase.getInstance();
        mMyApplication = this;
    }
}
