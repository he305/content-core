package com.github.he305.contentcore.stream.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StreamChannelList {
    private List<StreamChannelDto> channels;
}
