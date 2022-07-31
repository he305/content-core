package com.github.he305.contentcore.infra.jpa;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "content_creator")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class JpaContentCreator extends BaseJpaEntity {
    private String name;

    @OneToMany(targetEntity = JpaContentAccount.class, cascade = CascadeType.ALL, mappedBy = "contentCreator", orphanRemoval = true)
    private List<JpaContentAccount> contentAccounts;
}
