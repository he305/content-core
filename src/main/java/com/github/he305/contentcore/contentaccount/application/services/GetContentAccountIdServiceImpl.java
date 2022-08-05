package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.service.ContentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetContentAccountIdServiceImpl implements GetContentAccountIdService {
    private final ContentAccountService contentAccountService;

    @Override
    public UUID execute(GetContentAccountIdQuery query) {
        ContentAccountDetails details = new ContentAccountDetails(query.getName(), query.getPlatform());
        return contentAccountService.getContentAccountOrCreate(details).getId();
    }
}
