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
import com.example.elections.model.CandidatesList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ElectionRepository {


    private static ElectionRepository mRepository;
    private FirebaseDatabase db;

    DatabaseReference update_counter_ref, get_candidates_ref, update_candidatesVotes_ref;


    final MutableLiveData<Candidates> candidates = new SingleLiveEvent<>();


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
                "/scools/"+school+"/lagna/"+lagna);

        get_candidates_ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira+"/candidates");

        update_candidatesVotes_ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira+"/candidates/");

    }

    public static synchronized ElectionRepository getInstance() {
        if (mRepository == null) {
            mRepository = new ElectionRepository();
        }
        return mRepository;
    }


    public void updateCounter(int votes){
        update_counter_ref.child("vots_num").setValue(votes);
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

    public LiveData<Candidates> getCandidates() {

        get_candidates_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot != null ) {
                        Candidates c =
                                new Candidates(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(),
                                        (Long) dataSnapshot.child("votes_num").getValue());
                        candidates.setValue(c);
                    }
                    //Log.d("ttttttt", dataSnapshot.child("votes_num").getValue()+"   "+ dataSnapshot.getKey()+"   ");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tttttt",error.getMessage() + "   -*-*-*");
            }
        });

        return candidates;
    }

    public void updateCandidateVotes(String key, int votes){
        update_candidatesVotes_ref.child(key).child("votes_num").setValue(votes);
    }

    public void updateCandidateVotesSurvey(String key1, String key2){

        update_candidatesVotes_ref.child(key1).child("votes_num_survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("OOOKKK",snapshot.getValue() + "");
                if(snapshot.getValue() != null)
                    update_candidatesVotes_ref.child(key1).child("votes_num_survey").setValue(Integer.parseInt(snapshot.getValue().toString())+1);
                else
                    update_candidatesVotes_ref.child(key1).child("votes_num_survey").setValue(1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("OOOKKK",error.getMessage() + "   -*-*-*");
            }
        });
        update_candidatesVotes_ref.child(key2).child("votes_num_survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null)
                    update_candidatesVotes_ref.child(key2).child("votes_num_survey").setValue(Integer.parseInt(snapshot.getValue().toString())+1);
                else
                    update_candidatesVotes_ref.child(key2).child("votes_num_survey").setValue(1);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateValidInvalidVotes(int valid, int invalid) {
        update_counter_ref.child("valid_vots").setValue(valid);
        update_counter_ref.child("invalid_vots").setValue(invalid);
    }
}
