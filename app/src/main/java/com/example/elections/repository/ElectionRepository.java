package com.example.elections.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.elections.MyApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ElectionRepository {


    private static ElectionRepository mRepository;
    private FirebaseDatabase db;

    DatabaseReference update_counter_ref;

    private ElectionRepository() {
        db = MyApplication.getFirebaseInstance();
        SharedPreferences sP = MyApplication.getInstance().getSharedPreferences("mandoop", Context.MODE_PRIVATE);

        update_counter_ref = db.getReference("vots/" +
                "Governorate/"+sP.getInt("governorate_position",1)+"/dayra/"+sP.getInt("daira_num",1)+
                "/qsms/"+sP.getInt("qesm_num",1)+
                "/scools/"+sP.getInt("school_num",1)+"/lagna/"+sP.getInt("lagna_num",1)+"/vots_num");

        /*update_counter_ref = db.getReference("vots/" +
                "Governorate/1/dayra/1/qsms/1/scools/1/lagna/1/vots_num");*/
    }

    public static synchronized ElectionRepository getInstance() {
        if (mRepository == null) {
            mRepository = new ElectionRepository();
        }
        return mRepository;
    }

    public void updateCounter(int votes){
        update_counter_ref.setValue(votes);
        /*
        update_counter_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("OOOKKK",snapshot.getValue() + "");
                update_counter_ref.setValue(Integer.parseInt(snapshot.getValue().toString())+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("OOOKKK",error.getMessage() + "   -*-*-*");
            }
        });*/

    }
}
