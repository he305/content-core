package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetContentAccountDtoMapperImpl implements GetContentAccountDtoMapper {

    private final WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;

    @Override
    public GetContentAccountDto toDto(ContentAccountEntry entry) {
        ContentAccountDetails details = watchingListContentAccountExchangeService.getContentAccount(entry.getWatchingListContentAccountId().getId());
        ContentAccountPlatform platform = ContentAccountPlatform.valueOf(details.getPlatform().name());
        return new GetContentAccountDto(
                entry.getAlias(),
                details.getName(),
                platform,
                entry.getNotificationSize()
        );
    }
}
