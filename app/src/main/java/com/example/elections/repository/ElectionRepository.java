package com.example.elections.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.elections.MyApplication;
import com.example.elections.ResultOfLogin;
import com.example.elections.SingleLiveEvent;
import com.example.elections.Votes;
import com.example.elections.model.Candidates;
import com.example.elections.model.Dayra;
import com.example.elections.model.DayraObjList;
import com.example.elections.model.Qesm;
import com.example.elections.model.School;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ElectionRepository {
    private static ElectionRepository mRepository;
    private FirebaseDatabase db;

    DatabaseReference update_counter_ref, get_candidates_ref, get_candidates_ref2, update_candidatesVotes_ref, add_dayra_ref, get_password_ref, get_changed_Date, add_votesCount;

    final MutableLiveData<Candidates> candidates = new SingleLiveEvent<>();
    final MutableLiveData<List<Candidates>> candidates2 = new SingleLiveEvent<List<Candidates>>();

    final MutableLiveData<ArrayList<DayraObjList>> dayraSingleLiveEvent = new SingleLiveEvent<ArrayList<DayraObjList>>();

    private int governorate_position;
    private int daira;
    private int qesm;
    private int school;
    private int lagna;

    private ElectionRepository() {
        db = MyApplication.getFirebaseInstance();
        SharedPreferences sP = MyApplication.getInstance().getSharedPreferences("mandoop", Context.MODE_PRIVATE);
        SharedPreferences lagnaSP = MyApplication.getInstance().getSharedPreferences("lagna", Context.MODE_PRIVATE);

        governorate_position = sP.getInt("governorate_position", 1);
        daira = sP.getInt("daira_num", 1);
        qesm = sP.getInt("qesm_num", 1);
        school = sP.getInt("school_num", 1);
        lagna = sP.getInt("lagna_num", 1);

        update_counter_ref = db.getReference("vots/" +
                "Governorate/" + governorate_position + "/dayra/" + daira + "/qsms/" + qesm +
                "/scools/" + school + "/lagna/" + lagna);

        get_candidates_ref = db.getReference("vots/Governorate/" + governorate_position + "/dayra/" + daira);
        get_candidates_ref2 = db.getReference("vots/Governorate");


        update_candidatesVotes_ref = db.getReference("vots/Governorate/" + governorate_position +
                "/dayra/" + daira + "/candidates/");

        add_dayra_ref = db.getReference("vots/Governorate");
        get_changed_Date = db.getReference("vots/Governorate/");
        add_votesCount = db.getReference("vots_Cunt/governorates");
    }

    public static synchronized ElectionRepository getInstance() {
        if (mRepository == null) {
            mRepository = new ElectionRepository();
        }
        return mRepository;
    }


    public void updateCounter(int votes) {
        update_counter_ref.child("vots_num").setValue(votes);
    }

    public LiveData<Candidates> getCandidates() {

        get_candidates_ref.child("candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // array hena...

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
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
                Log.d("tttttt", error.getMessage() + "   -*-*-*");
            }
        });
        return candidates;
    }

    public LiveData<List<Candidates>> getCandidates2(int governorate_position, int daira) {

        get_candidates_ref2.child(governorate_position+"/dayra/"+daira+"/candidates/").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // array hena...
                List<Candidates> candi = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        Candidates c =
                                new Candidates(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(),
                                        (Long) dataSnapshot.child("votes_num").getValue());
                        candi.add(c);
                    }
                    //Log.d("^^^^&&&!!!!!!!ttttttt", dataSnapshot.child("votes_num").getValue()+"   "+ dataSnapshot.getKey()+"   ");
                }
                candidates2.setValue(candi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tttttt", error.getMessage() + "   -*-*-*");
            }
        });
        return candidates2;
    }

    public LiveData<ArrayList<DayraObjList>> getDayra(int governorate_position, int daira_number) {

        get_changed_Date.child(governorate_position + "/dayra/" + daira_number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<DayraObjList> dayraObjLists = new ArrayList<>();
                DayraObjList dayra;
                Dayra dayraObj = new Dayra();
                int depth = 0;
                Log.d(TAG, "##!!!!!! The name of the Governorate: " + snapshot.child("name").getValue());
                dayraObj.setDyra_name(snapshot.child("name").getValue().toString());
                ArrayList<Qesm> qesms = new ArrayList<>();
                //   Log.d(TAG, "##!!!!!!getChildren: " + snapshot.child("qsms").child("1").child("scools").getValue());
                //   Log.d(TAG, "##!!!!!!getChildren: " + snapshot.child("qsms").getChildrenCount());

                for (int i = 0; i < snapshot.child("qsms").getChildrenCount(); i++) {
                    Log.d(TAG, "##!!!!!! qesm name: " + snapshot.child("qsms/" + (i + 1) + "/name").getValue());
                    qesms.add(new Qesm(snapshot.child("qsms/" + (i + 1) + "/name").getValue().toString()));
                    ArrayList<School> schools = new ArrayList<>();
                    for (int j = 0; j < snapshot.child("qsms/" + (i + 1) + "/scools").getChildrenCount(); j++) {
                        School school = new School(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue().toString());
                        Log.d(TAG, "##!!!!!! School name: " + snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue());
                        ArrayList<Integer> legan = new ArrayList<>();
                        for (int l = 0; l < snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna").getChildrenCount(); l++) {
                            dayra = new DayraObjList();
                            depth++;
                            legan.add(Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna/" + (l + 1) + "/vots_num").getValue().toString()));
                            Log.d(TAG, "##!!!!!! votes num for lagna " + (l + 1) + ": " + snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna/" + (l + 1) + "/vots_num").getValue());

                            //----------------------------------data
                            dayra.setDayraName(snapshot.child("name").getValue().toString());
                            dayra.setQesmName(snapshot.child("qsms/" + (i + 1) + "/name").getValue().toString());
                            dayra.setLagnaNum(l + 1);
                            dayra.setSchoolName(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue().toString());
                            dayra.setVoteNum(Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna/" + (l + 1) + "/vots_num").getValue().toString()));
                            //-----------------------------------------
                            dayraObjLists.add(dayra);
                        }
                        school.setLegan(legan);
                        schools.add(school);
                    }
                    qesms.get(i).setSchools(schools);
                }

                dayraObj.setDepth(depth);
                dayraObj.setQesms(qesms);
                dayraSingleLiveEvent.setValue(dayraObjLists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dayraSingleLiveEvent;
    }


    public void updateCandidateVotes(String key, int votes) {
        update_candidatesVotes_ref.child(key).child("votes_num").setValue(votes);
    }

    public void updateCandidateVotesSurvey(String key1, String key2) {

        update_candidatesVotes_ref.child(key1).child("votes_num_survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("OOOKKK", snapshot.getValue() + "");
                if (snapshot.getValue() != null)
                    update_candidatesVotes_ref.child(key1).child("votes_num_survey").setValue(Integer.parseInt(snapshot.getValue().toString()) + 1);
                else
                    update_candidatesVotes_ref.child(key1).child("votes_num_survey").setValue(1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("OOOKKK", error.getMessage() + "   -*-*-*");
            }
        });
        update_candidatesVotes_ref.child(key2).child("votes_num_survey").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    update_candidatesVotes_ref.child(key2).child("votes_num_survey").setValue(Integer.parseInt(snapshot.getValue().toString()) + 1);
                else
                    update_candidatesVotes_ref.child(key2).child("votes_num_survey").setValue(1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void updateValidInvalidVotes(int valid, int invalid) {

        get_candidates_ref.child("valid_vots").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    get_candidates_ref.child("valid_vots").setValue(Integer.parseInt(snapshot.getValue().toString()) + valid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        get_candidates_ref.child("invalid_vots").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    get_candidates_ref.child("invalid_vots").setValue(Integer.parseInt(snapshot.getValue().toString()) + invalid);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update_counter_ref.child("valid_vots").setValue(valid);
        update_counter_ref.child("invalid_vots").setValue(invalid);
    }

    //check login for lagna.
    public void checkLogin(int governorate_position, int daira_num, String password, ResultOfLogin result) {
        //admins/1/dayra/1/password
        get_password_ref = db.getReference("admins/" + governorate_position + "/dayra/" + daira_num + "/password");

        get_password_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "!!!onDataChange: " + snapshot.getValue().toString());
                if (snapshot.getValue() != null) {
                    if (password.equals(snapshot.getValue().toString())) {
                        result.changeFlag(true);
                    } else {
                        result.changeFlag(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // this is for testing ..
    public void add_dayra(int governorate_position, String dayra_name, int dayra_number, int seats_number, int qesm_num) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/name").setValue(dayra_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/seats_number").setValue(seats_number);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qesm_num").setValue(qesm_num);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/voters_num").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/valid_vots").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/invalid_vots").setValue(0);
        get_password_ref =  db.getReference("admins/");
        get_password_ref.child(governorate_position + "/dayra/" + dayra_number + "/password").setValue(dayra_number + "entkhabat2020");
    }

    public void addNewQesm(String qesm_name, int governorate_position, int dayra_number, int school_num, int qesm_num) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/name").setValue(qesm_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/schools_num").setValue(school_num);
    }

    public void addNewSchool(String school_name, int governorate_position, int dayra_number, int school_num, int qesm_num, int lagan_num) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/name").setValue(school_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/legan_num").setValue(lagan_num);
        for (int i = 0; i < lagan_num; i++) {
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + (i + 1) + "/vots_num").setValue(0);
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + (i + 1) + "/valid_vots").setValue(0);
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + (i + 1) + "/invalid_vots").setValue(0);
            add_votesCount.child(governorate_position + "/dayra/" + dayra_number + "/" + qesm_num + "-" + school_num + "-" + (i + 1)).setValue(0);
        }
    }

    public void getInValidVotes(int governorate_position, int daira, Votes viewModel) {
        get_changed_Date.child(governorate_position + "/dayra/" + daira + "/invalid_vots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewModel.InValidVotes(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getValidVotes(int governorate_position, int daira, Votes viewModel) {
        get_changed_Date.child(governorate_position + "/dayra/" + daira + "/valid_vots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewModel.ValidVotes(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add_Candidate(int governorate_position, String candidate_name, int dayra_number) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 1;
                if (snapshot.getValue() != null) {
                    i = Integer.parseInt(String.valueOf(snapshot.getChildrenCount())) + 1;
                }
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/name").setValue(candidate_name);
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/votes_num").setValue(0);
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/votes_num_survey").setValue(0);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
