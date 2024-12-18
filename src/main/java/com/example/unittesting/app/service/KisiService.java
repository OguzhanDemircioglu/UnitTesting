package com.example.unittesting.app.service;

import com.example.unittesting.app.dto.KisiDto;

import java.util.List;

public interface KisiService {
    KisiDto save(KisiDto kisiDto);

    List<KisiDto> getAll();
}
