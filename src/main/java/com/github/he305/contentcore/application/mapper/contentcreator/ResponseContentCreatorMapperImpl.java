package com.github.he305.contentcore.application.mapper.contentcreator;

import com.github.he305.contentcore.application.dto.ResponseContentAccount;
import com.github.he305.contentcore.application.dto.ResponseContentCreator;
import com.github.he305.contentcore.domain.model.ContentCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ResponseContentCreatorMapperImpl implements ResponseContentCreatorMapper {
    private final ResponseContentAccountMapper responseContentAccountMapper;

    @Override
    public ResponseContentCreator toDto(ContentCreator contentCreator) {
        List<ResponseContentAccount> responseContentAccounts = contentCreator.getContentAccountList()
                .stream()
                .map(responseContentAccountMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseContentCreator(contentCreator.getName(), responseContentAccounts);
    }
}
