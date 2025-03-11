package com.example.TimPhongTro.Model.Dto;

public class PostLocationDto {
    private int id;
    private String address;

    public PostLocationDto() {
    }

    public PostLocationDto(int id, String address) {
        this.id = id;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
