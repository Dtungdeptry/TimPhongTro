package com.example.TimPhongTro.Model.Dto;

public class RoomTypeDto {
    private int id;
    private String typeName;

    public RoomTypeDto() {
    }

    public RoomTypeDto(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
