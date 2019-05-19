package com.nqc.model;

import java.io.Serializable;


public class QuanKaraoke implements Serializable {
    private String ten;
    private int hinh;
    private float vido;
    private float kinhdo;

    public QuanKaraoke(String ten, int hinh, float vido, float kinhdo) {
        this.ten = ten;
        this.hinh = hinh;
        this.vido = vido;
        this.kinhdo = kinhdo;
    }

    public QuanKaraoke() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public float getVido() {
        return vido;
    }

    public void setVido(float vido) {
        this.vido = vido;
    }

    public float getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(float kinhdo) {
        this.kinhdo = kinhdo;
    }
}
