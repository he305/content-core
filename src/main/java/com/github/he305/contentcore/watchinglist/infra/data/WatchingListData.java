package com.github.he305.contentcore.watchinglist.infra.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "watching_list")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchingListData {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "member_id", unique = true, nullable = false)
    @Type(type = "uuid-char")
    private UUID memberId;

    @OneToMany(orphanRemoval = true, targetEntity = WatchingListEntryData.class, cascade = CascadeType.ALL, mappedBy = "watchingList")
    private List<WatchingListEntryData> watchingListEntryDataList;
}
