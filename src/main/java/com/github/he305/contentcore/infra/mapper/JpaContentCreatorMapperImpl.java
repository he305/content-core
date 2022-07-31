package com.github.he305.contentcore.infra.mapper;

import com.github.he305.contentcore.domain.model.ContentCreator;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;
import com.github.he305.contentcore.domain.model.entities.ContentAccountInfo;
import com.github.he305.contentcore.infra.jpa.JpaContentAccount;
import com.github.he305.contentcore.infra.jpa.JpaContentCreator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaContentCreatorMapperImpl implements JpaContentCreatorMapper {
    @Override
    public JpaContentCreator toJpaEntity(ContentCreator contentCreator) {
        JpaContentCreator jpaContentCreator = new JpaContentCreator();

        jpaContentCreator.setName(contentCreator.getName());
        List<JpaContentAccount> accountList = contentCreator.getContentAccountList()
                .stream()
                .map(account -> new JpaContentAccount(account.getContentType(), account.getContentAccountInfo().getName(), jpaContentCreator))
                .collect(Collectors.toList());

        jpaContentCreator.setContentAccounts(accountList);
        return jpaContentCreator;
    }

    @Override
    public ContentCreator toDomain(JpaContentCreator jpaContentCreator) {
        List<ContentAccount> contentAccountList = jpaContentCreator.getContentAccounts()
                .stream()
                .map(jpaContentAccount -> new ContentAccount(jpaContentAccount.getId(), new ContentAccountInfo(jpaContentAccount.getNickname()), jpaContentAccount.getContentType()))
                .collect(Collectors.toList());

        return new ContentCreator(jpaContentCreator.getId(), jpaContentCreator.getName(), contentAccountList);
    }


}
