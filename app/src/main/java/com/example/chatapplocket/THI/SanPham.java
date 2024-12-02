package com.example.chatapplocket.THI;

public class SanPham {
    private String id;
    private String tenSanPham;
    private int soLuong;
    private double gia;

    public SanPham() {
    }

    public SanPham(String id, String tenSanPham, int soLuong, double gia) {
        this.id = id;
        this.tenSanPham = tenSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public String getId() {
        return id;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getGia() {
        return gia;
    }
}
