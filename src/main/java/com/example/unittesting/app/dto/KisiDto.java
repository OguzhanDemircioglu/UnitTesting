package com.example.unittesting.app.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"id"})
public class KisiDto {

    private Long id;

    @NotNull
    private String adi;

    private String soyadi;

    private List<String> adresler;
}
