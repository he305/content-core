package com.github.he305.contentcore.watchinglist.infra.data;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "content_account_entry")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
public class ContentAccountEntryData {

    @Id
    @Column(name = "id")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "content_account_id")
    @Type(type = "uuid-char")
    private UUID contentAccountId;

    @ElementCollection
    @CollectionTable(name = "content_account_notification")
    private Set<NotificationIdData> notificationIds;

    @ManyToOne
    @JoinColumn(name = "watching_list_entry_id", referencedColumnName = "id", nullable = false)
    private WatchingListEntryData watchingListEntry;
}
