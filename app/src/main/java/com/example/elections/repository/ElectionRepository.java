package com.example.elections.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.elections.ICandidateBaseViewModel;
import com.example.elections.MyApplication;
import com.example.elections.ResultOfLogin;
import com.example.elections.SingleLiveEvent;
import com.example.elections.Votes;
import com.example.elections.model.Candidates;
import com.example.elections.model.Dayra;
import com.example.elections.model.DayraObjList;
import com.example.elections.model.Qesm;
import com.example.elections.model.School;
import com.example.elections.viewModel.SortingAdminViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ElectionRepository {
    private static ElectionRepository mRepository;
    private FirebaseDatabase db;

    DatabaseReference update_counter_ref, get_candidates_ref, get_candidates_ref2, update_candidatesVotes_ref, add_dayra_ref, get_password_ref, get_changed_Date, add_votesCount;

    final MutableLiveData<List<Candidates>> candidates = new SingleLiveEvent<>();
    final MutableLiveData<List<Candidates>> candidates2 = new SingleLiveEvent<List<Candidates>>();
    final MutableLiveData<List<Candidates>> candidates3 = new SingleLiveEvent<List<Candidates>>();
    SharedPreferences sP;

    final MutableLiveData<ArrayList<DayraObjList>> dayraSingleLiveEvent = new SingleLiveEvent<ArrayList<DayraObjList>>();

    private int governorate_position;
    private int daira;
    private int qesm;
    private int school;
    private int lagna;

    private ElectionRepository() {
        db = MyApplication.getFirebaseInstance();
        //getDateFromSharedPreference();
        //conntectWithDataBase();
    }

    public void getDateFromSharedPreference(String nameOfSharedPreference) {
        sP = MyApplication.getInstance().getSharedPreferences(nameOfSharedPreference, Context.MODE_PRIVATE);
        governorate_position = sP.getInt("governorate_position", 0);
        daira = sP.getInt("daira_num", 0);
        qesm = sP.getInt("qesm_num", 0);
        school = sP.getInt("school_num", 0);
        lagna = sP.getInt("lagna_num", 0);
    }

    public void conntectWithDataBase() {
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
        get_candidates_ref.child("votes_num").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    get_candidates_ref.child("votes_num").setValue(Integer.parseInt(snapshot.getValue().toString()) + 1);
                } else {
                    get_candidates_ref.child("votes_num").setValue(1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    MutableLiveData<Integer> totalVotes = new SingleLiveEvent<>();

    public LiveData<Integer> getTotalVotes() {
        get_candidates_ref.child("votes_num").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null)
                    totalVotes.setValue(Integer.parseInt(snapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return totalVotes;

    }
    public LiveData<List<Candidates>> getCandidates() {

        get_candidates_ref.child("candidates").addListenerForSingleValueEvent(new ValueEventListener() {
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
                    //Log.d("ttttttt", dataSnapshot.child("votes_num").getValue()+"   "+ dataSnapshot.getKey()+"   ");
                }
                Collections.reverse(candi);
                candidates.setValue(candi);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("tttttt", error.getMessage() + "   -*-*-*");
            }
        });
        return candidates;
    }

    public LiveData<List<Candidates>> getCandidates2(int governorate_position, int daira, int flag) {

        get_candidates_ref2.child(governorate_position + "/dayra/" + daira + "/candidates/").orderByChild("votes_num" )
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // array hena...
                        List<Candidates> candi = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot != null) {
                                Candidates c =
                                        new Candidates(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(),
                                                (Long) dataSnapshot.child("votes_num").getValue(), (Long) dataSnapshot.child("votes_num_survey").getValue());
                                candi.add(c);
                            }
                        }
//                        Log.d("!!!!", "-*-*-*" + candi.get(0).getName() + " " + candi.get(0).getKey());
                        Collections.reverse(candi);
                        candidates2.setValue(candi);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("tttttt", error.getMessage() + "   -*-*-*");
                    }
                });
        return candidates2;
    }
    public LiveData<List<Candidates>> getCandidatesSurvey(int governorate_position, int daira) {

        get_candidates_ref2.child(governorate_position + "/dayra/" + daira + "/candidates/").orderByChild("votes_num_survey")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // array hena...
                        List<Candidates> candi = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot != null) {
                                Candidates c =
                                        new Candidates(dataSnapshot.getKey(), dataSnapshot.child("name").getValue().toString(),
                                                (Long) dataSnapshot.child("votes_num").getValue(), (Long) dataSnapshot.child("votes_num_survey").getValue());
                                candi.add(c);
                            }
                        }
//                        Log.d("!!!!", "-*-*-*" + candi.get(0).getName() + " " + candi.get(0).getKey());
                        Collections.reverse(candi);
                        candidates3.setValue(candi);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("tttttt", error.getMessage() + "   -*-*-*");
                    }
                });
        return candidates3;
    }

    public LiveData<ArrayList<DayraObjList>> getDayra(int governorate_position, int daira_number) {

        get_changed_Date.child(governorate_position + "/dayra/" + daira_number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {

                    ArrayList<DayraObjList> dayraObjLists = new ArrayList<>();
                    DayraObjList dayra;
                    Dayra dayraObj = new Dayra();
                    int depth = 0;
                    dayraObj.setDyra_name(snapshot.child("name").getValue().toString());
                    ArrayList<Qesm> qesms = new ArrayList<>();

                    for (int i = 0; i < snapshot.child("qsms").getChildrenCount(); i++) {
                        qesms.add(new Qesm(snapshot.child("qsms/" + (i + 1) + "/name").getValue().toString()));
                        ArrayList<School> schools = new ArrayList<>();
                        for (int j = 0; j < snapshot.child("qsms/" + (i + 1) + "/scools").getChildrenCount(); j++) {

                            School school = new School(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue().toString());
                            ArrayList<Integer> legan = new ArrayList<>();
                            int l = Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/from").getValue().toString());
                            int e = Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/to").getValue().toString());
                            for (; l <= e; l++) {
                                dayra = new DayraObjList();
                                depth++;
                                legan.add(Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna/" + l + "/vots_num").getValue().toString()));
                                //----------------------------------data
                                dayra.setDayraName(snapshot.child("name").getValue().toString());
                                dayra.setQesmName(snapshot.child("qsms/" + (i + 1) + "/name").getValue().toString());
                                dayra.setLagnaNum(l);
                                dayra.setSchoolName(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue().toString());
                                dayra.setVoteNum(Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/lagna/" + l + "/vots_num").getValue().toString()));
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return dayraSingleLiveEvent;
    }


    public void updateCandidateVotes(String key, int votes) {
        update_candidatesVotes_ref.child(key).child("votes_num").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(votes);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + votes);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void updateCandidateVotesSurvey(String key1, String key2) {

        update_candidatesVotes_ref.child(key1).child("votes_num_survey").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });

        update_candidatesVotes_ref.child(key2).child("votes_num_survey").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
        get_candidates_ref.child("total_votes_survey").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + 1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });

    }

    public void updateValidInvalidVotes(int valid, int invalid, int total_votes) {

        get_candidates_ref.child("total_votes_sorting").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(total_votes);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + total_votes);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
        get_candidates_ref.child("valid_vots").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(valid);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + valid);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });

        get_candidates_ref.child("invalid_vots").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(invalid);
                    return Transaction.success(currentData);
                }else{
                    currentData.setValue(Integer.parseInt(currentData.getValue().toString()) + invalid);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });

        update_counter_ref.child("valid_vots").setValue(valid);
        update_counter_ref.child("invalid_vots").setValue(invalid);
    }

    //check login for lagna.
    public void checkLoginLagna(int g, String daira, String qesm, String school, String
            lagna, String mandoop_pass, ResultOfLogin result) {

        get_password_ref = db.getReference("vots/Governorate/" + g + "/dayra/" + daira +
                "/qsms/" + qesm + "/scools/" + school + "/lagna/" + lagna+
                "/password");
        DatabaseReference ref = db.getReference("vots/Governorate/" + g + "/dayra/" + daira +
                "/qsms/" + qesm + "/scools/" + school + "/lagna/" + lagna);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() != null){

                    get_password_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (mandoop_pass.equals(snapshot.getValue().toString())) {
                                result.changeFlag(true);
                            } else
                                result.changeFlag(false);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else Toast.makeText(MyApplication.getInstance(),"البيانات خطأ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //check login for lagna.
    public void checkLoginServey(int g, String daira, String qesm, String school, String surveyPass, ResultOfLogin result) {

        get_password_ref = db.getReference("vots/Governorate/"+g+"/dayra/"+daira+
                "/survey_pass");

        DatabaseReference ref = db.getReference("vots/Governorate/" + g + "/dayra/" + daira +
                "/qsms/" + qesm + "/scools/" + school );
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    get_password_ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null) {
                                if (surveyPass.equals(snapshot.getValue().toString())) {
                                    result.changeFlag(true);
                                } else
                                    result.changeFlag(false);
                            } else
                                Toast.makeText(MyApplication.getInstance(), "كلمة السر خطأ", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else Toast.makeText(MyApplication.getInstance(), "البيانات غير صحيحةأ", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void checkLogin(int governorate_position, int daira_num, String
            password, ResultOfLogin result) {

        //get_password_ref = db.getReference("admins/" + governorate_position + "/dayra/" + daira_num + "/password");
        get_password_ref = db.getReference("vots/Governorate/"+governorate_position+"/dayra/"+daira_num+
                "/controller_pass");
        get_password_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
    public void add_dayra(int governorate_position, String dayra_name, int dayra_number,
                          int seats_number, int qesm_num, String survey_pass, String mandoop_pass, String controller_pass) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/name").setValue(dayra_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/votes_num").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/seats_num").setValue(seats_number);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qesm_num").setValue(qesm_num);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/voters_num").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/total_votes_sorting").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/total_votes_survey").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/valid_vots").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/invalid_vots").setValue(0);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/mandoop_pass").setValue(mandoop_pass);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/survey_pass").setValue(survey_pass);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/controller_pass").setValue(controller_pass);

        get_password_ref = db.getReference("admins/");
        get_password_ref.child(governorate_position + "/dayra/" + dayra_number + "/password").setValue(dayra_number + "entkhabat2020");
    }

    public void addNewQesm(String qesm_name, int governorate_position, int dayra_number,
                           int school_num, int qesm_num) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/name").setValue(qesm_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/schools_num").setValue(school_num);
    }

    public void addNewSchool(String school_name, int governorate_position, int dayra_number,
                             int school_num, int qesm_num, int lagan_num,int from ,int to) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/name").setValue(school_name);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/legan_num").setValue(lagan_num);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/from").setValue(from);
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/to").setValue(to);
        for (int i = from; i <= to; i++) {
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + i + "/vots_num").setValue(0);
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + i + "/valid_vots").setValue(0);
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + i + "/invalid_vots").setValue(0);
            add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/qsms/" + qesm_num + "/scools/" + school_num + "/lagna/" + i + "/password").setValue("");
            add_votesCount.child(governorate_position + "/dayra/" + dayra_number + "/" + qesm_num + "-" + school_num + "-" + (i + 1)).setValue(0);
        }
    }

    public void getInValidVotes(int governorate_position, int daira, Votes viewModel) {
        get_changed_Date.child(governorate_position + "/dayra/" + daira + "/invalid_vots").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null) {
                    viewModel.InValidVotes(snapshot.getValue().toString());
                }
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
                if(snapshot.getValue() != null){
                    viewModel.ValidVotes(snapshot.getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSeats(int governorate_position, int daira, Votes viewModel) {
        get_changed_Date.child(governorate_position + "/dayra/" + daira + "/seats_num").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
//                Log.d("sEATS", snapshot.getValue().toString());
                    viewModel.setSeats(Integer.parseInt(snapshot.getValue().toString()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add_Candidate(int governorate_position, String candidate_name, int dayra_number, String candidate_pass) {
        add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 1;
                if (snapshot.getValue() != null) {
                    i = Integer.parseInt(String.valueOf(snapshot.getChildrenCount(  ))) + 1;
                }
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/name").setValue(candidate_name);
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/votes_num").setValue(0);
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/votes_num_survey").setValue(0);
                add_dayra_ref.child(governorate_position + "/dayra/" + dayra_number + "/candidates/" + i + "/password").setValue(candidate_pass);
                //https://election-82a55.firebaseio.com/vots/Governorate/1/dayra/1/candidates/1/votes_num_survey
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reformateDayra(int governorate_position, int daira) {

        add_dayra_ref.child(governorate_position + "/dayra/" + daira).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot != null) {
                        add_dayra(governorate_position, snapshot.child("name").getValue().toString(),
                                daira, Integer.parseInt(snapshot.child("seats_num").getValue().toString()),
                                Integer.parseInt(snapshot.child("qesm_num").getValue().toString()),
                                snapshot.child("survey_pass").getValue().toString(),
                                snapshot.child("mandoop_pass").getValue().toString(),
                                snapshot.child("controller_pass").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reformateQesm(int governorate_position, int daira) {
        add_dayra_ref.child(governorate_position + "/dayra/" + daira).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int qsm = 0;
                for (int i = 0; i < snapshot.child("qsms").getChildrenCount(); i++) { // qsms
                    for (int j = 0; j < snapshot.child("qsms/" + (i + 1) + "/scools").getChildrenCount(); j++) { //schools
                        addNewSchool(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/name").getValue().toString(),
                                governorate_position, daira, j + 1, i + 1
                                , Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/legan_num").getValue().toString())
                        ,Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/from").getValue().toString())
                        ,Integer.parseInt(snapshot.child("qsms/" + (i + 1) + "/scools/" + (j + 1) + "/to").getValue().toString()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void reformateCandidates(int governorate_position, int daira, ArrayList<String> c) {
        add_dayra_ref.child(governorate_position + "/dayra/" + daira + "/candidates").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot != null) {

                            //if(c.contains(dataSnapshot.getKey())) {
//                                Log.d(TAG, "###--candidate: k " + dataSnapshot.getKey());
                                add_dayra_ref.child(governorate_position+"/dayra/"+daira+"/candidates/"+dataSnapshot.getKey()+"/votes_num").setValue(0);
                                add_dayra_ref.child(governorate_position+"/dayra/"+daira+"/candidates/"+dataSnapshot.getKey()+"/votes_num_survey").setValue(0);
                            /*}else{
                                add_dayra_ref.child(governorate_position+"/dayra/"+daira+"/candidates/"+dataSnapshot.getKey()).removeValue();
//                                Log.d(TAG, "###--candidate:  DDD " + dataSnapshot.getKey());
                            }*/
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void clearDB(int governorate_position, int daira, String controller_pass,ArrayList<String> c) {
        add_dayra_ref = db.getReference("vots/Governorate");
        add_dayra_ref.child(governorate_position + "/dayra/" + daira +"/controller_pass").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue() != null){

                    if(controller_pass.equals(snapshot.getValue().toString())){

                        reformateDayra(governorate_position,daira);
                        reformateQesm(governorate_position, daira);
                        reformateCandidates(governorate_position, daira, c);
                        Toast.makeText(MyApplication.getInstance(), "تم التعديل", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(MyApplication.getInstance(), "كلمة السر خطأ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void checkLogin(int governorate_position, int daira_num, String password, int can_num, ICandidateBaseViewModel candidateBase) {
        DatabaseReference ref = db.getReference("vots/Governorate/"+governorate_position+
                "/dayra/"+daira_num+"/candidates/"+can_num);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue() != null) {
                    if (password.equals(snapshot.child("password").getValue().toString())) {

                        Log.d("SERT", snapshot.child("password").getValue().toString());
                        candidateBase.changeFlag(true);
                    } else {
                        candidateBase.changeFlag(false);
                    }
                }else{
                    candidateBase.changeFlag(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void getTotalVotesSort(int governorate_position, int daira, Votes sortingAdminViewModel) {

        get_changed_Date.child(governorate_position + "/dayra/" + daira + "/total_votes_sorting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    sortingAdminViewModel.TotalVotes(snapshot.getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private MutableLiveData<String> survey_votes = new SingleLiveEvent<>();
    public LiveData<String> getSurveyVotes() {
        get_candidates_ref.child("total_votes_survey").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    survey_votes.setValue(snapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return survey_votes;
    }

    public void editPasswords(int governorate_position, int daira_num, String survey_pass, String mandoop_pass, String controller_pass) {
        DatabaseReference ref = db.getReference("vots/Governorate/"+governorate_position+"/dayra/"+daira_num);

        ref.child("controller_pass").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("passs", snapshot.getValue().toString());

                if(snapshot.getValue() != null){

                    if(controller_pass.equals(snapshot.getValue().toString())){
                        Log.d("passs", snapshot.getValue().toString());
                        ref.child("survey_pass").setValue(survey_pass);
                        ref.child("mandoop_pass").setValue(mandoop_pass);

                    }else
                        Toast.makeText(MyApplication.getInstance(),"كلمة سر المتحكم خطأ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
