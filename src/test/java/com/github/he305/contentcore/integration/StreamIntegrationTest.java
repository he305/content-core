package com.github.he305.contentcore.integration;

import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.account.application.dto.RegisterServiceDto;
import com.github.he305.contentcore.stream.application.dto.StreamChannelDto;
import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.dto.StreamDataDto;
import com.github.he305.contentcore.stream.application.dto.StreamEndDto;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.CreateWatchingListDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class StreamIntegrationTest extends IntegrationTestBase {
    @Autowired
    private MockMvc mockMvc;

    @Value("${auth.service-register-key}")
    private String serviceRegisterKey;

    @SneakyThrows
    String registerUser() {
        String username = "user" + LocalDateTime.now(ZoneOffset.UTC);
        String password = "pass";

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
        return dto.getToken();
    }

    @SneakyThrows
    String registerService() {
        String username = "service" + LocalDateTime.now(ZoneOffset.UTC);
        String password = "pass";

        LoginRequestDto loginRequestDto = new LoginRequestDto(username, password);
        String json = objectMapper.writeValueAsString(loginRequestDto);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError());

        RegisterServiceDto dto = new RegisterServiceDto(
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
        return resDto.getToken();
    }

    @Test
    @SneakyThrows
    void getListAsSimpleUser_shouldFail() {
        String token = registerUser();

        mockMvc.perform(get("/stream")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    void getListAsService_valid() {
        String token = registerService();

        mockMvc.perform(get("/stream")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @SneakyThrows
    void create_get_addData_endStream_DataCycle() {
        String token = registerService();
        String alias = "test";
        String entryName = "createListAndCompare";
        String accountName = "someAccount" + LocalDateTime.now(ZoneOffset.UTC);
        ContentAccountPlatform platform = ContentAccountPlatform.TWITCH;

        ContentAccountDto contentAccountDto = new ContentAccountDto(alias, accountName, platform);
        WatchingListEntryDto watchingListEntry = new WatchingListEntryDto(entryName, List.of(contentAccountDto));
        CreateWatchingListDto entryDto = new CreateWatchingListDto(
                List.of(watchingListEntry)
        );

        String json = objectMapper.writeValueAsString(entryDto);

        mockMvc.perform(post("/watchingList")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        MvcResult result = mockMvc.perform(get("/stream")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String strResult = result.getResponse().getContentAsString();
        StreamChannelList streamList = objectMapper.readValue(strResult, StreamChannelList.class);
        Optional<StreamChannelDto> optional = streamList.getChannels().stream().filter(streamChannelDto -> streamChannelDto.getChannelName().equals(accountName)).findAny();
        assertTrue(optional.isPresent());
        assertFalse(optional.get().isLive());

        UUID streamChannelId = optional.get().getId();
        StreamDataDto dataDto = new StreamDataDto(
                streamChannelId,
                "something",
                "title",
                322,
                LocalDateTime.now(ZoneOffset.UTC)
        );

        json = objectMapper.writeValueAsString(dataDto);
        mockMvc.perform(post("/stream/data")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        result = mockMvc.perform(get("/stream")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        strResult = result.getResponse().getContentAsString();
        streamList = objectMapper.readValue(strResult, StreamChannelList.class);
        optional = streamList.getChannels().stream().filter(streamChannelDto -> streamChannelDto.getChannelName().equals(accountName)).findAny();
        assertTrue(optional.isPresent());
        assertTrue(optional.get().isLive());

        StreamEndDto streamEndDto = new StreamEndDto(
                streamChannelId,
                LocalDateTime.now(ZoneOffset.UTC)
        );
        json = objectMapper.writeValueAsString(streamEndDto);
        mockMvc.perform(post("/stream/end")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        result = mockMvc.perform(get("/stream")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        strResult = result.getResponse().getContentAsString();
        streamList = objectMapper.readValue(strResult, StreamChannelList.class);
        optional = streamList.getChannels().stream().filter(streamChannelDto -> streamChannelDto.getChannelName().equals(accountName)).findAny();
        assertTrue(optional.isPresent());
        assertFalse(optional.get().isLive());
    }
}
