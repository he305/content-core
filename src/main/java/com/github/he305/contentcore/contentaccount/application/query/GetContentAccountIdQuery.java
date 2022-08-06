package com.github.he305.contentcore.contentaccount.application.query;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class GetContentAccountIdQuery {
    private String name;
    private Platform platform;
}
