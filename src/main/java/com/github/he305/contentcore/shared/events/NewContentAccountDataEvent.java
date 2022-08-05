package com.github.he305.contentcore.shared.events;

import com.github.he305.contentcore.shared.common.ContentAccountData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class NewContentAccountDataEvent<T extends ContentAccountData> {
    private final T data;
}
