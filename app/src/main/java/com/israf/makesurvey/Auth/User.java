package com.israf.makesurvey.Auth;
public class User {

    private String displayName;
    private String email;
    private String uid;
    private String photoUrl;
    private String instanceId;

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