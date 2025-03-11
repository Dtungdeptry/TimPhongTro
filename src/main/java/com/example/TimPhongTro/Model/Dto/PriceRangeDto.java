package com.example.TimPhongTro.Model.Dto;

public class PriceRangeDto {
    private int id;
    private String rangeName;

    public PriceRangeDto() {
    }

    public PriceRangeDto(int id, String rangeName) {
        this.id = id;
        this.rangeName = rangeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRangeName() {
        return rangeName;
    }

    public void setRangeName(String rangeName) {
        this.rangeName = rangeName;
    }
}
