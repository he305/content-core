package com.github.he305.contentcore.watchinglist.application.query;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.MemberId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GetNotificationForContentAccountQuery {
    MemberId memberId;
    private String contentAccountName;
    private ContentAccountPlatform platform;
}
