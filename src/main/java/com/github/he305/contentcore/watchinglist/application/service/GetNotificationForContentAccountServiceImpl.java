package com.github.he305.contentcore.watchinglist.application.service;

import com.github.he305.contentcore.watchinglist.application.dto.query.GetNotificationForContentAccountDto;
import com.github.he305.contentcore.watchinglist.application.dto.query.NotificationDto;
import com.github.he305.contentcore.watchinglist.application.exceptions.WatchingListNotExistsException;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccount;
import com.github.he305.contentcore.watchinglist.application.exchange.WatchingListContentAccountExchangeService;
import com.github.he305.contentcore.watchinglist.application.mapper.query.NotificationDtoMapper;
import com.github.he305.contentcore.watchinglist.application.query.GetNotificationForContentAccountQuery;
import com.github.he305.contentcore.watchinglist.domain.model.WatchingList;
import com.github.he305.contentcore.watchinglist.domain.model.values.NotificationId;
import com.github.he305.contentcore.watchinglist.domain.model.values.WatchingListContentAccountId;
import com.github.he305.contentcore.watchinglist.domain.repository.WatchingListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetNotificationForContentAccountServiceImpl implements GetNotificationForContentAccountService {
    private final WatchingListRepository watchingListRepository;
    private final WatchingListContentAccountExchangeService watchingListContentAccountExchangeService;
    private final NotificationDtoMapper notificationDtoMapper;

    @Override
    public GetNotificationForContentAccountDto execute(GetNotificationForContentAccountQuery query) {
        Optional<WatchingList> optionalWatchingList = watchingListRepository.getWatchingListByMemberId(query.getMemberId());
        if (optionalWatchingList.isEmpty()) {
            throw new WatchingListNotExistsException();
        }

        WatchingListContentAccount watchingListContentAccount = new WatchingListContentAccount(query.getContentAccountName(), query.getPlatform());
        UUID contentAccountId = watchingListContentAccountExchangeService.getContentAccountId(watchingListContentAccount);
        WatchingList watchingList = optionalWatchingList.get();
        Set<NotificationId> set = watchingList.getNotificationsIdForContentAccountId(new WatchingListContentAccountId(contentAccountId));
        watchingListRepository.save(watchingList);

        List<NotificationDto> notificationDtoList = set.stream().map(notificationDtoMapper::toDto).collect(Collectors.toList());
        return new GetNotificationForContentAccountDto(notificationDtoList);
    }
}
