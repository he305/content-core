package com.github.he305.contentcore.streamlist.domain.repository;

import com.github.he305.contentcore.streamlist.domain.model.StreamList;
import com.github.he305.contentcore.streamlist.domain.model.values.MemberId;

import java.util.Optional;

public interface StreamListRepository {
    void save(StreamList streamList);

    Optional<StreamList> getByMemberId(MemberId memberId);
}
