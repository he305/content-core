package com.github.he305.contentcore.stream.infra.data;

import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelPlatform;
import com.github.he305.contentcore.stream.domain.model.enums.StreamChannelStatus;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stream_channel")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class StreamChannelJpa {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "content_account_id")
    @Type(type = "uuid-char")
    private UUID contentAccountId;

    @Enumerated
    @Column(name = "platform")
    private StreamChannelPlatform platform;

    @OneToMany(targetEntity = StreamJpa.class, cascade = CascadeType.ALL, mappedBy = "streamChannel", orphanRemoval = true)
    private List<StreamJpa> streams;

    @Enumerated
    @Column(name = "status")
    private StreamChannelStatus status;

    public void setStreams(List<StreamJpa> streams) {
        this.streams = streams;
    }
}
