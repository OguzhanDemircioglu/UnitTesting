package com.example.unittesting.app.controller;

import com.example.unittesting.app.dto.KisiDto;
import com.example.unittesting.app.service.KisiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kisi")
@RequiredArgsConstructor
public class KisiController {

    private final KisiService kisiService;

    @PostMapping(value = "/kaydet")
    public ResponseEntity<KisiDto> kaydet(@Validated @RequestBody KisiDto kisiDto) throws Exception {
        try {
            return ResponseEntity.ok(kisiService.save(kisiDto));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (Exception e) {
            throw new Exception("işlem geçersiz");
        }
    }

    @GetMapping(value = "/tumunuListele")
    public ResponseEntity<List<KisiDto>> tumunuListele() {
        return ResponseEntity.ok(kisiService.getAll());
    }
}
