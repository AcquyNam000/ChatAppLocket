package com.example.chatapplocket;

public class hinhanh {
    private int id;
    private byte[] hinh;
    private String email;

    public hinhanh(int id, byte[] hinh, String email) {
        this.id = id;
        this.hinh = hinh;
        this.email = email;
    }

    public hinhanh() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getHinh() {
        return hinh;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

