package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.mapper.query.NotificationDtoMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccount;
import com.github.he305.contentcore.watchinglist.domain.model.values.ContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import com.github.he305.contentcore.watchinglist.domain.service.ContentAccountExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetNotificationForContentAccountServiceImpl implements GetNotificationForContentAccountService {
    private final WatchingListRepository watchingListRepository;
    private final ContentAccountExchangeService contentAccountExchangeService;
    private final NotificationDtoMapper notificationDtoMapper;

    @Override
    public GetNotificationForContentAccountDto execute(GetNotificationForContentAccountQuery query) {
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(query.getMemberId());
        if (optionalWatchingList.isEmpty()) {
            throw new IllegalArgumentException();
        }

        ContentAccount contentAccount = new ContentAccount(query.getContentAccountName(), query.getPlatform());
        ContentAccountId contentAccountId = contentAccountExchangeService.getContentAccountId(contentAccount);
        WatchingList watchingList = optionalWatchingList.get();
        Set<NotificationId> set = watchingList.getNotificationsIdForContentAccountId(contentAccountId);
        watchingListRepository.save(watchingList);

        List<NotificationDto> notificationDtoList = set.stream().map(notificationDtoMapper::toDto).collect(Collectors.toList());
        return new GetNotificationForContentAccountDto(notificationDtoList);
    }
}
