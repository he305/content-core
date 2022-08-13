package com.github.he305.contentcore.stream.infra.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stream_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class StreamDataJpa {
    @Id
    @Column(name = "id", nullable = false)
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "viewer_count", nullable = false)
    private int viewerCount;

    @Column(name = "stream_data_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime streamDataTime;

    @ManyToOne(optional = false)
    @JoinColumn(name = "stream_id", referencedColumnName = "id", nullable = false)
    private StreamJpa stream;
}
