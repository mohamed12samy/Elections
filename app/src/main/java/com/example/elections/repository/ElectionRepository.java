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

import java.util.Vector;

import static android.content.ContentValues.TAG;

public class ElectionRepository {


    private static ElectionRepository mRepository;
    private FirebaseDatabase db;
    DatabaseReference update_counter_ref, get_candidates_ref, update_candidatesVotes_ref
            ,add_dayra_ref,get_password_ref,get_dataEntry_password_ref;


    final MutableLiveData<Candidates> candidates = new SingleLiveEvent<>();


    private int governorate_position;
    private int daira;
    private int qesm;
    private int school;
    private int lagna;

    final Vector<Integer> result = new Vector<>();
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

        update_candidatesVotes_ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira+"/candidates/");

        add_dayra_ref = db.getReference("vots/Governorate/");

        /*        //testing... not completed yet
        get_dataEntry_password_ref =  db.getReference("admins/" +
                1+"/dataEntry/password");
*/

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

    public void updateCandidateVotesSurvey(String key1, String key2, int votes1, int votes2){
        update_candidatesVotes_ref.child(key1).child("votes_num_survey").setValue(votes1);
        update_candidatesVotes_ref.child(key2).child("votes_num_survey").setValue(votes2);
    }

    //check login for lagna.
    public Vector<Integer> checkLogin(int governorate_position , int daira_num , String password){
        //admins/1/dayra/1/password
        get_password_ref =  db.getReference("admins/" +governorate_position
                +"/dayra/"+daira_num+"password");

        get_password_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    Log.d(TAG, "!!!onDataChange: " + snapshot.getValue().toString());
                    if (password.equals(snapshot.getValue().toString())) {
                        result.add(7);
                    }
                    else {
                        result.add(5);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return result;
    }

    // this is for testing ..
    public void add_dayra(int governorate_position , String dayra_name,int dayra_number ,int seats_number,int qesm_num)
    {
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/name").setValue(dayra_name);
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/seats_number").setValue(seats_number);
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qesm_num").setValue(qesm_num);
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/voters_num").setValue(0);
    }
    public void addNewQesm(String qesm_name,int governorate_position , int dayra_number ,int school_num,int qesm_num) {
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/name").setValue(qesm_name);
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/schools_num").setValue(school_num);
    }
    public void addNewSchool(String school_name,int governorate_position , int dayra_number ,int school_num,int qesm_num,int lagan_num)
    {
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/scools/"+school_num+"/name").setValue(school_name);
        add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/scools/"+school_num+"/legan_num").setValue(lagan_num);
        for(int i =0;i<lagan_num;i++)
        {
            add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/scools/"+school_num+"/lagna/"+(i+1)+"/vots_num").setValue(0);
            add_dayra_ref.child(governorate_position+"/dayra/"+dayra_number+"/qsms/"+qesm_num+"/scools/"+school_num+"/lagna/"+(i+1)+"/valid_vots").setValue(0);
        }
    }
}
