package com.nqc.model;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String email;
    private String phone;
    private String songLike;

    public User(String name, String email, String phone, String songLike) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.songLike = songLike;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSongLike() {
        return songLike;
    }

    public void setSongLike(String songLike) {
        this.songLike = songLike;
    }
}
