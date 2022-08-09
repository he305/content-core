package com.github.he305.contentcore.integration;


import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.dto.RegisterServiceDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Value("${auth.service-register-key}")
    private String serviceRegisterKey;

    @SneakyThrows
    @Test
    void registerAndAuthTest() {
        String username = "user";
        String password = "password";

        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        String json = objectMapper.writeValueAsString(loginRequestDto);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        MvcResult result = mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JwtResponseDto dto = objectMapper.readValue(content, JwtResponseDto.class);
        assertFalse(dto.getToken().isBlank());
        assertEquals("Bearer", dto.getType());
    }

    @SneakyThrows
    @Test
    void registerServiceTest() {
        String username = "service";
        String password = "pass";

        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        String json = objectMapper.writeValueAsString(loginRequestDto);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        String wrongServiceKey = "123";
        assertNotEquals(serviceRegisterKey, wrongServiceKey);

        RegisterServiceDto dto = new RegisterServiceDto(
                username,
                password,
                wrongServiceKey
        );
        json = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/api/auth/registerService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        dto = new RegisterServiceDto(
                username,
                password,
                serviceRegisterKey
        );
        json = objectMapper.writeValueAsString(dto);

        MvcResult result = mockMvc.perform(post("/api/auth/registerService")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        JwtResponseDto resDto = objectMapper.readValue(content, JwtResponseDto.class);
        assertFalse(resDto.getToken().isBlank());
        assertEquals("Bearer", resDto.getType());
    }
}
