package com.github.he305.contentcore.contentaccount.application.query;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class GetContentAccountDetailsQuery {
    private UUID id;
}
