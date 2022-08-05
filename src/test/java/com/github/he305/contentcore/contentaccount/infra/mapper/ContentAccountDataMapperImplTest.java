package com.github.he305.contentcore.contentaccount.infra.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Status;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.model.values.UseCounter;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContentAccountDataMapperImplTest {

    private ContentAccountDataMapperImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new ContentAccountDataMapperImpl();
    }

    @Test
    void toDomain() {
        UUID id = UUID.randomUUID();
        ContentAccountData contentAccountData = new ContentAccountData(id, "name", Platform.TWITCH, 255, Status.ACTIVE);
        ContentAccount expected = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(255), Status.ACTIVE);

        ContentAccount actual = underTest.toDomain(contentAccountData);
        assertEquals(expected, actual);
    }

    @Test
    void toJpa() {
        UUID id = UUID.randomUUID();
        ContentAccount contentAccount = new ContentAccount(id, new ContentAccountDetails("name", Platform.TWITCH), new UseCounter(255), Status.ACTIVE);
        ContentAccountData expected = new ContentAccountData(id, "name", Platform.TWITCH, 255, Status.ACTIVE);

        ContentAccountData actual = underTest.toJpa(contentAccount);
        assertEquals(expected, actual);
    }
}