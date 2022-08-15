package com.github.he305.contentcore.streamlist.application.services;

import com.github.he305.contentcore.shared.events.EventPublisher;
import com.github.he305.contentcore.streamlist.application.dto.StreamListDto;
import com.github.he305.contentcore.streamlist.application.mapper.StreamListDtoMapper;
import com.github.he305.contentcore.streamlist.application.query.GetStreamListQuery;
import com.github.he305.contentcore.streamlist.domain.events.RequestUpdateStreamListEvent;
import com.github.he305.contentcore.streamlist.domain.exceptions.StreamListNotFoundException;
import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;
import com.github.he305.contentcore.streamlist.domain.repository.StreamListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetStreamListServiceImpl implements GetStreamListService {
    private final StreamListRepository streamListRepository;
    private final StreamListDtoMapper streamListDtoMapper;
    private final EventPublisher eventPublisher;

    @Override
    public StreamListDto execute(GetStreamListQuery query) {
        Optional<StreamList> optionalStreamList = streamListRepository.getByMemberId(new MemberId(query.getMemberId()));
        if (optionalStreamList.isEmpty()) {
            eventPublisher.publishEvent(List.of(new RequestUpdateStreamListEvent(query.getMemberId())));
            throw new StreamListNotFoundException("Stream list is not found, updating job is in progress");
        }

        StreamList streamList = optionalStreamList.get();
        return streamListDtoMapper.toDto(streamList);
    }
}
