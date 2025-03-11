package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Model.Dto.AreaDto;
import com.example.TimPhongTro.Model.Dto.PostLocationDto;
import com.example.TimPhongTro.Model.Dto.PriceRangeDto;
import com.example.TimPhongTro.Model.Dto.RoomTypeDto;

import java.util.List;

public interface DropdownService {
    List<PriceRangeDto> getAllPriceRanges();

    List<RoomTypeDto> getAllRoomTypes();

    List<PostLocationDto> getAllLocations();

    List<AreaDto> getAllAreas();
}
