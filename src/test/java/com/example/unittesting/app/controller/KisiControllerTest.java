package com.example.unittesting.app.controller;

import com.example.unittesting.app.dto.KisiDto;
import com.example.unittesting.app.model.Calculator;
import com.example.unittesting.app.model.Student;
import com.example.unittesting.app.service.KisiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = KisiController.class)
class KisiControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    KisiService kisiService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        KisiDto kisi = KisiDto.builder()
                .adi("ogz")
                .soyadi("ddd")
                .build();
        //when
        ResultActions actions = mockMvc.perform(post("/kisi/kaydet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kisi)));
        //then
        ArgumentCaptor<KisiDto> captor = ArgumentCaptor.forClass(KisiDto.class);
        verify(kisiService, times(1)).save(captor.capture());

        assertThat(captor.getValue().getAdi()).isEqualTo("ogz");
        assertThat(captor.getValue().getSoyadi()).isEqualTo("ddd");
        actions.andExpect(status().isOk());
    }

    @Test
    void whenValidInput_thenReturns400() throws Exception {
        ResultActions actions = mockMvc.perform(post("/kisi/kaydet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("kisi")));
        actions.andExpect(status().isBadRequest());
    }

    @Test
    void whenInvalidMethodType() throws Exception {
        KisiDto kisi = KisiDto.builder()
                .soyadi("ddd")
                .build();

        ResultActions actions = mockMvc.perform(get("/kisi/kaydet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(kisi)));

        actions.andExpect(status().isMethodNotAllowed());
    }

    @Test
    void whenCallTumunuListele_thenReturns200() throws Exception {
        KisiDto kisi = KisiDto.builder()
                .adi("123")
                .soyadi("9999")
                .build();
        when(kisiService.getAll()).thenReturn(Collections.singletonList(kisi));

        MvcResult mvcResult = mockMvc.perform(get("/kisi/tumunuListele")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(kisiService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Collections.singletonList(kisi)))
                .isEqualToIgnoringWhitespace(responseBody);
    }

    @Test
    void whenCallTumunuListele_thenReturnsNoData() throws Exception {
        when(kisiService.getAll()).thenReturn(Collections.emptyList());

        MvcResult mvcResult = mockMvc.perform(get("/kisi/tumunuListele")
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        verify(kisiService, times(1)).getAll();
        assertThat(objectMapper.writeValueAsString(Collections.emptyList()))
                .isEqualToIgnoringWhitespace(responseBody);
    }
}
