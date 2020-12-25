package com.israf.makesurvey.ui.main;

public class Surveys {



        private SurveyAnswer s ;
        private  String Name;
        private  String userid;

private int questionnumber;

    public Surveys(String name, int questionnumber,String userid) {
        Name = name;
        this.questionnumber = questionnumber;
        this.userid=userid;
    }

    public SurveyAnswer getS() {
        return s;
    }

    public void setS(SurveyAnswer s) {
        this.s = s;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuestionnumber() {
        return questionnumber;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setQuestionnumber(int questionnumber) {
        this.questionnumber = questionnumber;
    }
}
    /*private void init() {

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds:snapshot.getChildren()){
                    String SurveyName= ds.getKey();
                    int NumberOfQuestions= (int) ds.child("Questions").getChildrenCount();
                    Survey.add(new Surveys(SurveyName,NumberOfQuestions));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/