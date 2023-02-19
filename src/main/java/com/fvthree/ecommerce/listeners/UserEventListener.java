package com.fvthree.ecommerce.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.fvthree.ecommerce.util.Utils;

import events.UserEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEventListener {
    @EventListener
    public void onApplicationEvent(UserEvent userEvent) {
        log.info("Received UserEvent Event : " + userEvent.getEventType());
        log.info("Received User From UserEvent :" + Utils.asJsonString(userEvent.getUser()));
    }
}
