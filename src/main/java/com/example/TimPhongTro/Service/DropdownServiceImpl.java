package com.example.TimPhongTro.Service;

import com.example.TimPhongTro.Model.Dto.AreaDto;
import com.example.TimPhongTro.Model.Dto.PostLocationDto;
import com.example.TimPhongTro.Model.Dto.PriceRangeDto;
import com.example.TimPhongTro.Model.Dto.RoomTypeDto;
import com.example.TimPhongTro.Repository.AreaRepository;
import com.example.TimPhongTro.Repository.LocationRepository;
import com.example.TimPhongTro.Repository.PriceRangeRepository;
import com.example.TimPhongTro.Repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DropdownServiceImpl implements DropdownService {
    @Autowired
    private PriceRangeRepository priceRangeRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<PriceRangeDto> getAllPriceRanges() {
        return priceRangeRepository.findAll().stream()
                .map(priceRange -> {
                    PriceRangeDto dto = new PriceRangeDto();
                    dto.setId(priceRange.getId());
                    dto.setRangeName(priceRange.getRangeName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomTypeDto> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream()
                .map(roomType -> {
                    RoomTypeDto dto = new RoomTypeDto();
                    dto.setId(roomType.getId());
                    dto.setTypeName(roomType.getTypeName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PostLocationDto> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> {
                    PostLocationDto dto = new PostLocationDto();
                    dto.setId(location.getId());
                    dto.setAddress(location.getAddress());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AreaDto> getAllAreas() {
        return areaRepository.findAll().stream()
                .map(area -> {
                    AreaDto dto = new AreaDto();
                    dto.setId(area.getId());
                    dto.setSize(area.getSize());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
