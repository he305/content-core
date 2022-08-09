package com.github.he305.contentcore.watchinglist.application.mapper.query;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetContentAccountDto;
import com.github.he305.contentcore.watchinglist.domain.model.entities.ContentAccountEntry;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetContentAccountDtoMapperImpl implements GetContentAccountDtoMapper {

    private final ContentAccountExchangeService contentAccountExchangeService;

    @Override
    public GetContentAccountDto toDto(ContentAccountEntry entry) {
        ContentAccount contentAccount = contentAccountExchangeService.getContentAccount(entry.getContentAccountId());
        return new GetContentAccountDto(
                contentAccount.getName(),
                contentAccount.getPlatform(),
                entry.getNotificationSize()
        );
    }
}
