package com.nqc.model;

import java.io.Serializable;


public class QuanKaraoke implements Serializable {
    private String ten;
    private int hinh;
    private String gioMoCua;
    private String diaChi;
    private double vido;
    private double kinhdo;

    public QuanKaraoke(String ten, int hinh, String gioMoCua, String diaChi, double vido, double kinhdo) {
        this.ten = ten;
        this.hinh = hinh;
        this.gioMoCua = gioMoCua;
        this.diaChi = diaChi;
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
}
