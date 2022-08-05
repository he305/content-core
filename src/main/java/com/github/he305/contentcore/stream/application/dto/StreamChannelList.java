package com.github.he305.contentcore.stream.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class StreamChannelList {
    private final List<StreamChannelDto> channels;
}
