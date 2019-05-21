package com.nqc.firebase;

import java.io.Serializable;
import java.io.StringReader;

public class QuanKaraFirebase implements Serializable {
    private String ten;
    String urlHinh;
    private String gioMoCua;
    private String diaChi;
    private double vido;
    private double kinhdo;
    private int tonTai;

    public QuanKaraFirebase(String ten, String urlHinh, String gioMoCua, String diaChi, double vido, double kinhdo, int tonTai) {
        this.ten = ten;
        this.urlHinh = urlHinh;
        this.gioMoCua = gioMoCua;
        this.diaChi = diaChi;
        this.vido = vido;
        this.kinhdo = kinhdo;
        this.tonTai = tonTai;
    }

    public QuanKaraFirebase() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getUrlHinh() {
        return urlHinh;
    }

    public void setUrlHinh(String urlHinh) {
        this.urlHinh = urlHinh;
    }

    public String getGioMoCua() {
        return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {
        this.gioMoCua = gioMoCua;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public double getVido() {
        return vido;
    }

    public void setVido(double vido) {
        this.vido = vido;
    }

    public double getKinhdo() {
        return kinhdo;
    }

    public void setKinhdo(double kinhdo) {
        this.kinhdo = kinhdo;
    }

    public int getTonTai() {
        return tonTai;
    }

    public void setTonTai(int tonTai) {
        this.tonTai = tonTai;
    }
}
