package com.israf.makesurvey.ui.main;

import java.util.ArrayList;

public class AnswerString {
     String soru,sorutipi;
    ArrayList<String> answer= new ArrayList<String>();
    int Ans1,Ans2,Ans3,Ans4;

    public String getSorutipi() {
        return sorutipi;
    }

    public void setSorutipi(String sorutipi) {
        this.sorutipi = sorutipi;
    }

    public AnswerString(String s) {

        soru=s;
    }

    public ArrayList<String> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<String> answer) {
        this.answer = answer;
    }

    public void setanswer(String s){
        answer.add(s);

    }

    public int getAns1() {
        return Ans1;
    }

    public int getAns2() {
        return Ans2;
    }

    public int getAns3() {
        return Ans3;
    }

    public int getAns4() {
        return Ans4;
    }

    public  void Ans1(){
        Ans1++;
    }public  void Ans2(){
        Ans2++;
    }public  void Ans3(){
        Ans3++;
    }public  void Ans4(){
        Ans4++;
    }

}
