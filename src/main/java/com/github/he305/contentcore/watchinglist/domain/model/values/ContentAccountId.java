package com.github.he305.contentcore.watchinglist.domain.model.values;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import java.util.UUID;

@Value
@EqualsAndHashCode
@Getter
public class ContentAccountId {
    UUID id;
}
