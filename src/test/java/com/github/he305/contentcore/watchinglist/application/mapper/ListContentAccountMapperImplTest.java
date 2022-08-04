package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ListContentAccountMapperImplTest {

    @Mock
    private ContentAccountDtoIdMapper contentAccountDtoIdMapper;

    @InjectMocks
    private ListContentAccountMapperImpl underTest;

    @Test
    void toContentAccountIdSet() {
        ContentAccountDto data = new ContentAccountDto("name", ContentAccountPlatform.TWITCH);
        ContentAccountId mocked = new ContentAccountId(UUID.randomUUID());
        Set<ContentAccountId> expected = Set.of(mocked);
        Mockito.when(contentAccountDtoIdMapper.toContentAccountId(data)).thenReturn(mocked);

        Set<ContentAccountId> actual = underTest.toContentAccountIdSet(List.of(data));
        assertEquals(expected, actual);
    }

    @Test
    void toContentAccountDtoList() {
        ContentAccountId data = new ContentAccountId(UUID.randomUUID());
        ContentAccountDto mocked = new ContentAccountDto("name", ContentAccountPlatform.TWITCH);
        List<ContentAccountDto> expected = List.of(mocked);
        Mockito.when(contentAccountDtoIdMapper.toContentAccountDto(data)).thenReturn(mocked);

        List<ContentAccountDto> actual = underTest.toContentAccountDtoList(Set.of(data));
        assertEquals(expected, actual);
    }
}