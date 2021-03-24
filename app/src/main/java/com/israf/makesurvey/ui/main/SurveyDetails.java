package com.israf.makesurvey.ui.main;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SurveyDetails {
    private String Surveyname,SurveyDescription,SurveyLife,CreatedDate;
    int targetages;
    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public int getTargetages() {
        return targetages;
    }

    public void setTargetages(int targetages) {
        this.targetages = targetages;
    }

    public SurveyDetails() {

    }



    public SurveyDetails(String surveyname, String surveyDescription, String surveyLife, String createdDate,int age) {
        Surveyname = surveyname;
        SurveyDescription = surveyDescription;
        SurveyLife = surveyLife;
        CreatedDate = createdDate;
        targetages=age;
    }

    public String getSurveyname() {
        return Surveyname;
    }

    public void setSurveyname(String surveyname) {
        Surveyname = surveyname;
    }

    public String getSurveyDescription() {
        return SurveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        SurveyDescription = surveyDescription;
    }

    public String getSurveyLife() {
        return SurveyLife;
    }

    public void setSurveyLife(String surveyLife) {
        SurveyLife = surveyLife;
    }
}
