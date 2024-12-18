package com.example.unittesting.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Table(name = "kisi_adres")
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString
public class Adres implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_kisi_adres", allocationSize = 1)
    @GeneratedValue(generator = "seq_kisi_adres", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 30, name = "adres")
    private String adres;

    @Enumerated
    private AdresTip adresTip;

    @Column(name = "aktif")
    private Boolean aktif;

    @ManyToOne
    @JoinColumn(name = "kisi_adres_id")
    private Kisi kisi;

    public enum AdresTip {
        EV_ADRESI,
        IS_ADRESI,
        DIGER
    }
}
