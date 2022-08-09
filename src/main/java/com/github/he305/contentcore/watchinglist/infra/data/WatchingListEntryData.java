package com.github.he305.contentcore.watchinglist.infra.data;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "watching_list_entry")
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@Getter
public class WatchingListEntryData {
    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(targetEntity = ContentAccountEntryData.class, cascade = CascadeType.ALL, mappedBy = "watchingListEntry", orphanRemoval = true)
    private List<ContentAccountEntryData> contentAccountEntryDataSet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "watching_list_id", referencedColumnName = "id", nullable = false)
    private WatchingListData watchingList;

    public void setContentAccountEntryDataList(List<ContentAccountEntryData> list) {
        this.contentAccountEntryDataSet = new ArrayList<>(list);
    }
}
