package com.github.he305.contentcore.contentaccount.application.services;

import com.github.he305.contentcore.contentaccount.application.query.GetContentAccountDetailsQuery;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;

public interface GetContentAccountDetailsService {
    ContentAccountDetails execute(GetContentAccountDetailsQuery query);
}
