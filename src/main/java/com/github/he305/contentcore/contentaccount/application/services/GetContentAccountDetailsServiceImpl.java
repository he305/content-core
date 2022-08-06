package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.service.ContentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetContentAccountDetailsServiceImpl implements GetContentAccountDetailsService {
    private final ContentAccountService contentAccountService;

    @Override
    public ContentAccountDetails execute(GetContentAccountDetailsQuery query) {
        return contentAccountService.getContentAccountDetailsById(query.getId());
    }
}
