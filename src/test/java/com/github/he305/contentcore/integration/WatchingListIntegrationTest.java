package com.github.he305.contentcore.integration;

import com.github.he305.contentcore.account.application.dto.JwtResponseDto;
import com.github.he305.contentcore.account.application.dto.LoginRequestDto;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.CreateWatchingListDto;
import com.github.he305.contentcore.watchinglist.application.dto.WatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetWatchingListEntryDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WatchingListIntegrationTest extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @SneakyThrows
    String register() {
        String username = "user" + LocalDateTime.now();
        String password = "";

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

    @Test
    @SneakyThrows
    void createListAndCompare() {
        String token = register();
        String entryName = "createListAndCompare";
        String accountName = "accoont";
        ContentAccountPlatform platform = ContentAccountPlatform.TWITCH;

        ContentAccountDto contentAccountDto = new ContentAccountDto(accountName, platform);
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

        MvcResult getResult = mockMvc.perform(get("/watchingList")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String strResult = getResult.getResponse().getContentAsString();
        GetWatchingListQuery actual = objectMapper.readValue(strResult, GetWatchingListQuery.class);

        assertEquals(1, actual.getWatchingList().size());
        assertEquals(entryDto.getWatchingList().size(), actual.getWatchingList().size());
        GetWatchingListEntryDto entryResult = actual.getWatchingList().get(0);
        assertEquals(watchingListEntry.getName(), entryResult.getName());
        assertEquals(watchingListEntry.getAccounts().size(), entryResult.getAccounts().size());
        assertEquals(watchingListEntry.getAccounts().get(0).getName(), entryResult.getAccounts().get(0).getName());
        assertEquals(watchingListEntry.getAccounts().get(0).getPlatform(), entryResult.getAccounts().get(0).getPlatform());
        assertEquals(0, entryResult.getAccounts().get(0).getNotificationSize());
    }
}
