package com.github.he305.contentcore.infra.jpa;

import com.github.he305.contentcore.domain.model.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "content_account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class JpaContentAccount extends BaseJpaEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ContentType contentType;
    @Column(name = "nickname")
    private String nickname;
    @ManyToOne(optional = false)
    @JoinColumn(name = "content_creator_id", referencedColumnName = "id", nullable = false)
    private JpaContentCreator contentCreator;
}
