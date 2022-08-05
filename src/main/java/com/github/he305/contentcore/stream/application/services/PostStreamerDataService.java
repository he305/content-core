package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.PostStreamerDataCommand;

public interface PostStreamerDataService {
    void execute(PostStreamerDataCommand command);
}
