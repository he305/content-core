package com.github.he305.contentcore.shared.entities;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@Getter
public abstract class BaseEntity {
    @NonNull
    protected UUID id = UUID.randomUUID();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
