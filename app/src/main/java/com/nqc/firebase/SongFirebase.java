package com.nqc.firebase;

import java.io.Serializable;

public class SongFirebase implements Serializable {
    private String maBH;
    private String tenBH;
    private String loiBaiHat;
    private String tacGia;
    private int yeuThich;
    private int Duyet;
    private String email;

    public SongFirebase(String maBH, String tenBH, String loiBaiHat, String tacGia, int yeuThich, int duyet, String email) {
        this.maBH = maBH;
        this.tenBH = tenBH;
        this.loiBaiHat = loiBaiHat;
        this.tacGia = tacGia;
        this.yeuThich = yeuThich;
        Duyet = duyet;
        this.email = email;
    }

    public SongFirebase() {
    }

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getLoiBaiHat() {
        return loiBaiHat;
    }

    public void setLoiBaiHat(String loiBaiHat) {
        this.loiBaiHat = loiBaiHat;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getYeuThich() {
        return yeuThich;
    }

    public void setYeuThich(int yeuThich) {
        this.yeuThich = yeuThich;
    }

    public int getDuyet() {
        return Duyet;
    }

    public void setDuyet(int duyet) {
        Duyet = duyet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
