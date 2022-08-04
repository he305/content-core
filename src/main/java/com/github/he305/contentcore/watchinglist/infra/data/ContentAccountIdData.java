package com.github.he305.contentcore.watchinglist.infra.data;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.util.UUID;

@Embeddable
@Table(name = "content_account_id")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContentAccountIdData {
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;
}
