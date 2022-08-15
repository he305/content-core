package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.PlatformsDto;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlatformsServiceImpl implements GetPlatformsService {
    @Override
    public PlatformsDto execute() {
        return new PlatformsDto(
                List.of(ContentAccountPlatform.values())
        );
    }
}
