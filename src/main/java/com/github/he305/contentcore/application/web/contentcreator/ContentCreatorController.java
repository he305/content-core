package com.github.he305.contentcore.application.web.contentcreator;

import com.github.he305.contentcore.application.dto.AddContentCreator;
import com.github.he305.contentcore.application.dto.ResponseContentCreator;
import com.github.he305.contentcore.application.service.ContentCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContentCreatorController {
    private final ContentCreatorService contentCreatorService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseContentCreator> findById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(contentCreatorService.getContentCreatorById(id));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<UUID> createContentCreator(@RequestBody AddContentCreator dto) {
        try {
            return ResponseEntity.ok(contentCreatorService.saveContentCreator(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
