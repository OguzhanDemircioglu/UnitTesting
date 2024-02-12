package com.example.unittesting.app.service.srvImpl;

import com.example.unittesting.app.dto.KisiDto;
import com.example.unittesting.app.model.Adres;
import com.example.unittesting.app.model.Kisi;
import com.example.unittesting.app.repository.AdresRepository;
import com.example.unittesting.app.repository.KisiRepository;
import com.example.unittesting.app.service.KisiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KisiSrvImpl implements KisiService {

    private final KisiRepository kisiRepository;
    private final AdresRepository adresRepository;

    @Override
    public KisiDto save(KisiDto kisiDto) {
        Assert.notNull(kisiDto.getAdi(), "Adi alani zorunludur!");//IllegalArgumentException almak içindir

        Kisi kisi = new Kisi();
        kisi.setAdi(kisiDto.getAdi());
        kisi.setSoyadi(kisiDto.getSoyadi());
        final Kisi kisiDb = kisiRepository.save(kisi);

        List<Adres> liste = new ArrayList<>();
        kisiDto.getAdresler().forEach(item -> {
            Adres adres = new Adres();
            adres.setAdres(item);
            adres.setAdresTip(Adres.AdresTip.DIGER);
            adres.setAktif(true);
            adres.setKisi(kisiDb);
            liste.add(adres);
        });
        adresRepository.saveAll(liste);
        kisiDto.setId(kisiDb.getId());
        return kisiDto;
    }

    @Override
    public List<KisiDto> getAll() {
        List<Kisi> kisiler = kisiRepository.findAll();
        List<KisiDto> kisiDtos = new ArrayList<>();

        kisiler.forEach(it -> {
            KisiDto kisiDto =new KisiDto();
            kisiDto.setId(it.getId());
            kisiDto.setAdi(it.getAdi());
            kisiDto.setSoyadi(it.getSoyadi());
            kisiDto.setAdresler(
                    it.getAdresler() != null ?
                            it.getAdresler().stream().map(Adres::getAdres).collect(Collectors.toList())
                            : null);
            kisiDtos.add(kisiDto);
        });
        return kisiDtos;
    }
}
