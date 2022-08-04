package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class ContentAccountMockService implements ContentAccountService {
    private final Map<ContentAccount, UUID> mockMap = new HashMap<>();

    @Override
    public ContentAccountId getContentAccountId(ContentAccount account) {
        log.info(account.toString());
        if (mockMap.containsKey(account)) {
            return new ContentAccountId(mockMap.get(account));
        }

        UUID id = UUID.randomUUID();
        mockMap.put(account, id);
        log.info("added new entry, id: " + id);
        return new ContentAccountId(id);
    }

    @Override
    public ContentAccount getContentAccount(ContentAccountId id) {
        UUID rawId = id.getId();
        for (Map.Entry<ContentAccount, UUID> entry : mockMap.entrySet()) {
            if (entry.getValue().equals(rawId)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
