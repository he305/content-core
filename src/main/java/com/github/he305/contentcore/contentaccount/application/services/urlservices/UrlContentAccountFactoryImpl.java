package com.github.he305.contentcore.contentaccount.application.services.urlservices;

import com.github.he305.contentcore.contentaccount.domain.model.enums.Platform;
import com.github.he305.contentcore.shared.exceptions.ContentCoreArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UrlContentAccountFactoryImpl implements UrlContentAccountFactory {
    private static final Map<Platform, UrlContentAccountService> serviceMap = new EnumMap<>(Platform.class);
    private final List<UrlContentAccountService> services;

    @PostConstruct
    public void initServiceMap() {
        services.forEach(s -> serviceMap.put(s.getType(), s));
    }

    @Override
    public UrlContentAccountService getUrlContentAccountService(Platform platform) {
        UrlContentAccountService service = serviceMap.get(platform);
        if (service == null)
            throw new ContentCoreArgumentException("Cannot create url provider for platform: " + platform.name());
        return service;
    }
}
