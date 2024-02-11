package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.dto.KisiDto;
import com.example.unittesting.app.model.Kisi;
import com.example.unittesting.app.repository.AdresRepository;
import com.example.unittesting.app.repository.KisiRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonSrvImplTest {

    @InjectMocks
    private KisiSrvImpl kisiSrv;

    @Mock
    private KisiRepository kisiRepository;

    @Mock
    private AdresRepository adresRepository;

    @Test
    void testSavePerson() {

        KisiDto kisiDto = KisiDto.builder().adi("oguz").soyadi("dddd").adresler(List.of("girsun","bursa")).build();

        Kisi kisiMock = mock(Kisi.class);
        when(kisiMock.getId()).thenReturn(1L);
        when(kisiRepository.save(ArgumentMatchers.any(Kisi.class))).thenReturn(kisiMock);

        KisiDto newDto = kisiSrv.save(kisiDto);

        Assertions.assertEquals(newDto.getAdi(),kisiDto.getAdi());
        Assertions.assertEquals(newDto.getId(),1L);
    }
}
