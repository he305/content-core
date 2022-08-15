package com.github.he305.contentcore.streamlist.application.web.controller;

import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.query.GetStreamListQuery;
import com.github.he305.contentcore.streamlist.application.services.GetStreamListService;
import com.github.he305.contentcore.streamlist.domain.exceptions.StreamListNotFoundException;
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

import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StreamListControllerTest {

    @Mock
    private GetStreamListService getStreamListService;

    @InjectMocks
    private StreamListController underTest;


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
    void getStreamList_notFound() {
        UUID id = setSecurityContext();
        GetStreamListQuery query = new GetStreamListQuery(id);

        Mockito.when(getStreamListService.execute(query)).thenThrow(StreamListNotFoundException.class);

        ResponseEntity<StreamListDto> expected = ResponseEntity.badRequest().build();
        ResponseEntity<StreamListDto> actual = underTest.getStreamList();
        assertEquals(expected, actual);
    }

    @Test
    void getStreamList_valid() {
        UUID id = setSecurityContext();
        GetStreamListQuery query = new GetStreamListQuery(id);

        StreamListDto dto = new StreamListDto(Collections.emptyList());
        Mockito.when(getStreamListService.execute(query)).thenReturn(dto);

        ResponseEntity<StreamListDto> expected = ResponseEntity.ok(dto);
        ResponseEntity<StreamListDto> actual = underTest.getStreamList();
        assertEquals(expected, actual);
    }
}