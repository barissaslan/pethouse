package com.aslanbaris.pethouse.common.events;

import com.aslanbaris.pethouse.dao.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private User user;
    private Locale locale;
    private String confirmationUrl;

    public OnRegistrationCompleteEvent(User user, Locale locale, String confirmationUrl) {
        super(user);

        this.user = user;
        this.locale = locale;
        this.confirmationUrl = confirmationUrl;
    }

}
