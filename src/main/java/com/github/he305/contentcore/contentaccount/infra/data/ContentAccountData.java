package com.github.he305.contentcore.contentaccount.infra.data;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.contentaccount.domain.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "content_account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContentAccountData {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated
    @Column(name = "platform", nullable = false)
    private Platform platform;

    @Column(name = "use_counter", nullable = false)
    private int useCounter;

    @Enumerated
    @Column(name = "status", nullable = false)
    private Status status;
}
