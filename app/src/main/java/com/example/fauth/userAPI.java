package com.example.fauth;

public class userAPI {
    private String username;
    private String userId;
    private static userAPI instance;

    public static userAPI getInstance(){
        if (instance==null)
            instance = new userAPI();
        return instance;
    }

    public userAPI() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
