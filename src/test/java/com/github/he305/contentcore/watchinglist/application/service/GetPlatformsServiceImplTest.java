package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.PlatformsDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetPlatformsServiceImplTest {

    private final GetPlatformsServiceImpl underTest = new GetPlatformsServiceImpl();

    @Test
    void execute() {
        List<ContentAccountPlatform> expected = List.of(ContentAccountPlatform.values());
        PlatformsDto dto = underTest.execute();
        assertEquals(expected, dto.getPlatforms());
    }
}