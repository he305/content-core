package com.github.he305.contentcore.shared.events;

import com.github.he305.contentcore.shared.common.ContentAccountData;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Getter
public class NewContentAccountDataEvent<T extends ContentAccountData> {
    private final LocalDateTime time;
    private final T data;

    public NewContentAccountDataEvent(T data) {
        this.data = data;
        this.time = LocalDateTime.now(ZoneOffset.UTC);
    }
}
