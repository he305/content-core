package com.github.he305.contentcore.domain.model;

import com.github.he305.contentcore.domain.model.entities.BaseEntity;
import com.github.he305.contentcore.domain.model.entities.ContentAccount;
import com.github.he305.contentcore.shared.validators.StringValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Getter
public class ContentCreator extends BaseEntity {
    private final String name;
    private final List<ContentAccount> contentAccountList;

    public ContentCreator(UUID id, String name, List<ContentAccount> contentAccountList) {
        this(name, contentAccountList);
        this.id = id;
    }

    public ContentCreator(String name, List<ContentAccount> contentAccountList) {
        validateFields(name, contentAccountList);
        this.name = name;
        this.contentAccountList = contentAccountList;
    }

    public void addContentEntity(ContentAccount entity) {
        if (contentAccountList.contains(entity)) {
            throw new IllegalArgumentException("Can't add existing content entity");
        }

        this.contentAccountList.add(entity);
    }

    public void removeContentEntity(ContentAccount entity) {
        if (contentAccountList.size() == 1) {
            throw new IllegalStateException("Can't delete last content entity");
        }

        contentAccountList.remove(entity);
    }


    public void validateFields(String name, List<ContentAccount> contentAccountList) {
        StringValidator.isNullOrEmpty(name);
        if (contentAccountList == null) {
            throw new IllegalArgumentException("Content creator must have name and content entity list");
        }

        if (contentAccountList.isEmpty()) {
            throw new IllegalArgumentException("Content creator must contain at list one content entity");
        }
    }
}
