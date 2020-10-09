package com.example.elections.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.elections.MyApplication;
import com.example.elections.SingleLiveEvent;
import com.example.elections.model.Candidates;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ElectionRepository {


    private static ElectionRepository mRepository;
    private FirebaseDatabase db;

    DatabaseReference update_counter_ref, get_candidates_ref, update_candidatesVotes_ref;


    final MutableLiveData<List<Candidates>> candidates = new SingleLiveEvent<>();


    private int governorate_position;
    private int daira;
    private int qesm;
    private int school;
    private int lagna;

    private ElectionRepository() {
        db = MyApplication.getFirebaseInstance();
        SharedPreferences sP = MyApplication.getInstance().getSharedPreferences("mandoop", Context.MODE_PRIVATE);

        governorate_position = sP.getInt("governorate_position",1);
        daira = sP.getInt("daira_num",1);
        qesm = sP.getInt("qesm_num",1);
        school = sP.getInt("school_num",1);
        lagna = sP.getInt("lagna_num",1);

        update_counter_ref = db.getReference("vots/" +
                "Governorate/"+governorate_position+"/dayra/"+daira+ "/qsms/"+qesm+
                "/scools/"+school+"/lagna/"+lagna+"/vots_num");

        get_candidates_ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira+"/candidates");

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

    public LiveData<List<Candidates>> getCandidates() {

        get_candidates_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ttttttt",snapshot.getValue() + "");
                candidates.setValue((List<Candidates>) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tttttt",error.getMessage() + "   -*-*-*");
            }
        });

        return candidates;
    }
    public void updateCandidateVotes(int id){
        update_candidatesVotes_ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira+"/candidates/"+id+"");


        //update_candidatesVotes_ref.updateChildren();

    }
}
