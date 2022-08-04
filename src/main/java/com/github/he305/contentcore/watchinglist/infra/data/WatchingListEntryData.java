package com.github.he305.contentcore.watchinglist.infra.data;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "watching_list_entry")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WatchingListEntryData {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    private Set<ContentAccountIdData> contentAccountIdDataSet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "watching_list_id", referencedColumnName = "id", nullable = false)
    private WatchingListData watchingList;
}
