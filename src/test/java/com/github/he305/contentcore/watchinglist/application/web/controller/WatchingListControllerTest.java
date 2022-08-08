package com.github.he305.contentcore.watchinglist.application.web.controller;

import com.github.he305.contentcore.watchinglist.application.dto.AddWatchingEntryDto;
import com.github.he305.contentcore.watchinglist.application.dto.CreateWatchingListDto;
import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.dto.UpdateWatchingEntryDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;
import com.github.he305.contentcore.watchinglist.application.service.AddWatchingEntryService;
import com.github.he305.contentcore.watchinglist.application.service.CreateWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.GetWatchingListService;
import com.github.he305.contentcore.watchinglist.application.service.UpdateWatchingEntryService;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class WatchingListControllerTest {

    @Mock
    private CreateWatchingListService createWatchingListService;
    @Mock
    private GetWatchingListService getWatchingListService;
    @Mock
    private AddWatchingEntryService addWatchingEntryService;
    @Mock
    private UpdateWatchingEntryService updateWatchingEntryService;

    @InjectMocks
    private WatchingListController underTest;

    private UUID setSecurityContext() {
        UUID id = UUID.randomUUID();
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(id.toString());
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        return id;
    }

    @Test
    void createWatchingList() {
        setSecurityContext();
        CreateWatchingListDto dto = new CreateWatchingListDto();
        ResponseEntity<Void> expected = ResponseEntity.ok().build();

        ResponseEntity<Void> actual = underTest.createWatchingList(dto);
        assertEquals(expected, actual);
    }

    @Test
    void addWatchingEntry_valid() {
        setSecurityContext();
        AddWatchingEntryDto dto = new AddWatchingEntryDto();
        ResponseEntity<Void> expected = ResponseEntity.ok().build();

        ResponseEntity<Void> actual = underTest.addWatchingEntry(dto);
        assertEquals(expected, actual);
    }

    @Test
    void addWatchingEntry_exception() {
        setSecurityContext();
        AddWatchingEntryDto dto = new AddWatchingEntryDto();
        ResponseEntity<Void> expected = ResponseEntity.badRequest().build();

        doThrow(IllegalArgumentException.class).when(addWatchingEntryService).addWatchingEntry(Mockito.any());

        ResponseEntity<Void> actual = underTest.addWatchingEntry(dto);
        assertEquals(expected, actual);
    }

    @Test
    void getWatchingList() {
        UUID id = setSecurityContext();
        MemberIdDto dto = new MemberIdDto(id);
        Mockito.when(getWatchingListService.getWatchingList(dto)).thenReturn(new GetWatchingListQuery());
        ResponseEntity<GetWatchingListQuery> expected = ResponseEntity.ok(new GetWatchingListQuery());

        ResponseEntity<GetWatchingListQuery> actual = underTest.getWatchingList();
        assertEquals(expected, actual);
    }

    @Test
    void updateWatchingListEntry() {
        setSecurityContext();
        UpdateWatchingEntryDto dto = new UpdateWatchingEntryDto();
        ResponseEntity<Void> expected = ResponseEntity.ok().build();

        ResponseEntity<Void> actual = underTest.updateWatchingListEntry(dto);
        assertEquals(expected, actual);
    }
}