package com.github.he305.contentcore.contentaccount.infra.mapper;

import com.github.he305.contentcore.contentaccount.domain.model.ContentAccount;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.model.values.UseCounter;
import com.github.he305.contentcore.contentaccount.infra.data.ContentAccountData;
import org.springframework.stereotype.Component;

@Component
public class ContentAccountDataMapperImpl implements ContentAccountDataMapper {
    @Override
    public ContentAccount toDomain(ContentAccountData data) {
        return new ContentAccount(
                data.getId(),
                new ContentAccountDetails(data.getName(), data.getPlatform()),
                new UseCounter(data.getUseCounter()),
                data.getStatus()
        );
    }

    @Override
    public ContentAccountData toJpa(ContentAccount account) {
        return new ContentAccountData(
                account.getId(),
                account.getDetails().getName(),
                account.getDetails().getPlatform(),
                account.getUseCounter().getCounter(),
                account.getStatus()
        );
    }
}
