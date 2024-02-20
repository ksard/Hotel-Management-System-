package com.example.hotelmanagementsystem.dto;

import com.example.hotelmanagementsystem.models.RoomType;
import com.example.hotelmanagementsystem.models.Service;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.misc.Triple;

import java.util.HashMap;
import java.util.List;

public class RoomsResponseDto extends ResponseDto {
    public HashMap<Pair<String, String>, Long> roomsByTypeContent;
    public List<Service> servicesContent;
    public List<RoomType> roomTypesContent;
}
