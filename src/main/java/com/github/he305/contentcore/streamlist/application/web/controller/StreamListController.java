package com.github.he305.contentcore.streamlist.application.web.controller;

import com.github.he305.contentcore.shared.exceptions.ContentCoreException;
import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.query.GetStreamListQuery;
import com.github.he305.contentcore.streamlist.application.services.GetStreamListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/streamList")
@RequiredArgsConstructor
public class StreamListController {
    private final GetStreamListService getStreamListService;

    @GetMapping
    public ResponseEntity<StreamListDto> getStreamList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID id = UUID.fromString(authentication.getName());

        GetStreamListQuery query = new GetStreamListQuery(id);
        try {
            return ResponseEntity.ok(getStreamListService.execute(query));
        } catch (ContentCoreException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
