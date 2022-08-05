package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.dto.StreamChannelList;
import com.github.he305.contentcore.stream.application.query.GetStreamChannelByStatusQuery;

public interface GetStreamChannelByStatusService {
    StreamChannelList execute(GetStreamChannelByStatusQuery query);
}
