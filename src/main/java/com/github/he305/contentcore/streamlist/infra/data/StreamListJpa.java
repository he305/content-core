package com.github.he305.contentcore.streamlist.infra.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "stream_list")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamListJpa {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "member_id")
    @Type(type = "uuid-char")
    private UUID memberId;

    @ElementCollection
    @CollectionTable(name = "stream_list_stream_channel_id")
    private Set<StreamChannelIdJpa> streamChannelIdJpaSet;
}
