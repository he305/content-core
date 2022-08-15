package com.github.he305.contentcore.streamlist.application.services;

import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.query.GetStreamListQuery;

public interface GetStreamListService {
     StreamListDto execute(GetStreamListQuery query);
}
