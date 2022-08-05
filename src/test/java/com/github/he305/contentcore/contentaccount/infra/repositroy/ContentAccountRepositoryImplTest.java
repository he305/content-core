package com.github.he305.contentcore.contentaccount.infra.repositroy;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;
import com.github.he305.contentcore.contentaccount.infra.jpa.JpaContentAccountRepository;
import com.github.he305.contentcore.contentaccount.infra.mapper.ContentAccountDataMapper;
import com.github.he305.contentcore.shared.events.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ContentAccountRepositoryImplTest {

    @Mock
    private JpaContentAccountRepository jpaContentAccountRepository;
    @Mock
    private ContentAccountDataMapper contentAccountDataMapper;
    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private ContentAccountRepositoryImpl underTest;

    @Test
    void save() {
        ContentAccount data = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        Mockito.when(contentAccountDataMapper.toJpa(data)).thenReturn(new ContentAccountData());
        assertDoesNotThrow(() -> underTest.save(data));
    }

    @Test
    void getById() {
        UUID id = UUID.randomUUID();
        ContentAccount expected = new ContentAccount(UUID.randomUUID(), new ContentAccountDetails("name", Platform.TWITCH));
        ContentAccountData data = new ContentAccountData();
        Mockito.when(jpaContentAccountRepository.getReferenceById(id)).thenReturn(data);
        Mockito.when(contentAccountDataMapper.toDomain(data)).thenReturn(expected);

        ContentAccount actual = underTest.getById(id);
        assertEquals(expected, actual);
    }

    @Test
    void getByContentAccountDetails_empty() {
        ContentAccountDetails data = new ContentAccountDetails("name", Platform.TWITCH);
        Mockito.when(jpaContentAccountRepository.findByNameAndPlatform("name", Platform.TWITCH)).thenReturn(Optional.empty());

        Optional<ContentAccount> actual = underTest.getByContentAccountDetails(data);
        assertTrue(actual.isEmpty());
    }

    @Test
    void getByContentAccountDetails_found() {
        ContentAccountDetails data = new ContentAccountDetails("name", Platform.TWITCH);
        ContentAccountData contentAccountData = new ContentAccountData();
        ContentAccount expected = new ContentAccount(UUID.randomUUID(), data);
        Mockito.when(jpaContentAccountRepository.findByNameAndPlatform("name", Platform.TWITCH)).thenReturn(Optional.of(contentAccountData));
        Mockito.when(contentAccountDataMapper.toDomain(contentAccountData)).thenReturn(expected);

        Optional<ContentAccount> actual = underTest.getByContentAccountDetails(data);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }
}