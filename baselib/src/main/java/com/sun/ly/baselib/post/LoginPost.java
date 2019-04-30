package com.sun.ly.baselib.post;

public class LoginPost {
    private String username, password;
    private String source = "Android";
    private String pwd = "zxcmtest";

    public LoginPost() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSource() {
        return source;
    }

    public String getPwd() {
        return pwd;
    }
}
