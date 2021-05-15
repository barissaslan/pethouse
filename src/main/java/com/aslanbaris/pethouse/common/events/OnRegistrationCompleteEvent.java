package com.aslanbaris.pethouse.common.events;

import com.aslanbaris.pethouse.dao.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final String confirmationUrl;

    public OnRegistrationCompleteEvent(Object source, User user, String confirmationUrl) {
        super(source);

        this.user = user;
        this.confirmationUrl = confirmationUrl;
    }

}
