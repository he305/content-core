package com.github.he305.contentcore.infra.jpa;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
@Getter
public class BaseJpaEntity {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private final UUID id = UUID.randomUUID();
}
