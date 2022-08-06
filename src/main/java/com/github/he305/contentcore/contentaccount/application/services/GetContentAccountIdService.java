package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountIdQuery;

import java.util.UUID;

public interface GetContentAccountIdService {
    UUID execute(GetContentAccountIdQuery query);
}
