package com.israf.makesurvey.Auth;
public class User {

    private String displayName;
    private String email;
    private String uid;
    private String photoUrl;
    private String instanceId;
    private int Birthday,Birthmonth,Birthyear;
    private String PhoneNumber;
    private String Country;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public int getBirthday() {
        return Birthday;
    }

    public void setBirthday(int birthday) {
        Birthday = birthday;
    }

    public int getBirthmonth() {
        return Birthmonth;
    }

    public void setBirthmonth(int birthmonth) {
        Birthmonth = birthmonth;
    }

    public int getBirthyear() {
        return Birthyear;
    }

    public void setBirthyear(int birthyear) {
        Birthyear = birthyear;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public User() {
    }

    public User( String email, String uid) {

        this.email = email;
        this.uid = uid;

    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}