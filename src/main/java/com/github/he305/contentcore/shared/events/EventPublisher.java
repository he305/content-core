package com.github.he305.contentcore.shared.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class EventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEvent(Collection<Object> events) {
        events.forEach(publisher::publishEvent);
    }

}
