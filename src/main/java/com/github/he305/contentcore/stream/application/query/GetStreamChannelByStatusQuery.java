package com.github.he305.contentcore.stream.application.query;

import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetStreamChannelByStatusQuery {
    private final StreamChannelStatus status;
}
