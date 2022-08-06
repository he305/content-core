package com.github.he305.contentcore.stream.application.services;

import com.github.he305.contentcore.stream.application.commands.EndStreamCommand;

public interface EndStreamService {
    void execute(EndStreamCommand command);
}
