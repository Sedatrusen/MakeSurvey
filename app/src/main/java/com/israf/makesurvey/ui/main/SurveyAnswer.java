package com.israf.makesurvey.ui.main;

public class SurveyAnswer   {


    private int id=0;
    private String Question,Answer1,Answer2,Answer3,Answer4;
    public SurveyAnswer(){};

    public SurveyAnswer( String question, String answer1, String answer2, String answer3, String answer4,int id) {

        Question = question;
        Answer1 = answer1;
        Answer2 = answer2;
        Answer3 = answer3;
        Answer4 = answer4;
        this.id=id;
    }

   public SurveyAnswer(String question,int id) {
        Question = question;
        Answer1 = null;
        Answer2 = null;
        Answer3 = null;
        Answer4 = null;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public void setAnswer1(String answer1) {
        Answer1 = answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public void setAnswer2(String answer2) {
        Answer2 = answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public void setAnswer3(String answer3) {
        Answer3 = answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public void setAnswer4(String answer4) {
        Answer4 = answer4;
    }
}
