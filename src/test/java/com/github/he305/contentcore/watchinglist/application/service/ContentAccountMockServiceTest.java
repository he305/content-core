package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContentAccountMockServiceTest {

    private ContentAccountMockService contentAccountMockService;

    @BeforeEach
    void setUp() {
        contentAccountMockService = new ContentAccountMockService();
    }

    @Test
    void getContentAccountId_double() {
        ContentAccount contentAccount = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountId id = contentAccountMockService.getContentAccountId(contentAccount);

        ContentAccountId actual = contentAccountMockService.getContentAccountId(contentAccount);
        assertEquals(id, actual);
    }

    @Test
    void getContentAccount_valid() {
        ContentAccount contentAccount = new ContentAccount("name", ContentAccountPlatform.TWITCH);
        ContentAccountId id = contentAccountMockService.getContentAccountId(contentAccount);

        ContentAccount actual = contentAccountMockService.getContentAccount(id);
        assertEquals(contentAccount, actual);
    }

    @Test
    void getContentAccount_notFound() {
        ContentAccountId id = new ContentAccountId(UUID.randomUUID());
        contentAccountMockService.getContentAccount(new ContentAccountId(UUID.randomUUID()));

        ContentAccount actual = contentAccountMockService.getContentAccount(id);
        assertNull(actual);
    }
}