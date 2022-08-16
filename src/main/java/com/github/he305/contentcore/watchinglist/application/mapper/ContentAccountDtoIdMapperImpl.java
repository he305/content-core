package com.github.he305.contentcore.watchinglist.application.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.dto.ContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ContentAccountDtoIdMapperImpl implements ContentAccountDtoIdMapper {
    private final WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;

    @Override
    public ContentAccountEntry toContentAccountEntry(ContentAccountDto dto) {
        WatchingListContentAccount account = new WatchingListContentAccount(dto.getName(), dto.getPlatform());
        UUID id = watchingListContentAccountExchangeService.getContentAccountId(account);
        return new ContentAccountEntry(dto.getAlias(), new WatchingListContentAccountId(id));
    }

    @Override
    public ContentAccountDto toContentAccountDto(ContentAccountEntry entry) {
        ContentAccountDetails account = watchingListContentAccountExchangeService.getContentAccount(entry.getWatchingListContentAccountId().getId());
        ContentAccountPlatform platform = ContentAccountPlatform.valueOf(account.getPlatform().name());
        return new ContentAccountDto(entry.getAlias(), account.getName(), platform);
    }
}
