package com.github.he305.contentcore.stream.infra.data;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "stream")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class StreamJpa {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "max_viewers")
    private int maxViewers;

    @Column(name = "started_at", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startedAt;

    @Column(name = "ended_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime endedAt;

    @Column(name = "is_live")
    private boolean isLive;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stream_channel_id", referencedColumnName = "id", nullable = false)
    private StreamChannelJpa streamChannel;

    @OneToMany(targetEntity = StreamDataJpa.class, cascade = CascadeType.ALL, mappedBy = "stream", orphanRemoval = true)
    private List<StreamDataJpa> streamDatas;

    public void setStreamDatas(List<StreamDataJpa> streamDatas) {
        this.streamDatas = streamDatas;
    }
}
