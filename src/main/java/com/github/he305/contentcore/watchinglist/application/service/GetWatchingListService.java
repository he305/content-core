package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.MemberIdDto;
import com.github.he305.contentcore.watchinglist.application.query.GetWatchingListQuery;

public interface GetWatchingListService {
    GetWatchingListQuery getWatchingList(MemberIdDto dto);
}
