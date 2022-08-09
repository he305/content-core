package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;

public interface GetNotificationForContentAccountService {
    GetNotificationForContentAccountDto execute(GetNotificationForContentAccountQuery query);
}
