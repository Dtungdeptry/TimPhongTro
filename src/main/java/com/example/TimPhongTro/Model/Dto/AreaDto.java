package com.example.TimPhongTro.Model.Dto;

public class AreaDto {
    private int id;
    private String size;

    public AreaDto() {
    }

    public AreaDto(int id, String size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
