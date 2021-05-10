package com.aslanbaris.pethouse.common.listeners;

import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.common.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.domain.model.MailRequest;
import com.aslanbaris.pethouse.common.properties.UserProperties;
import com.aslanbaris.pethouse.domain.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final MailService mailService;
    private final UserProperties userProperties;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        log.info("onApplicationEvent");
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String message = userProperties.getConfirmMessage();

        MailRequest mailRequest = new MailRequest();
        mailRequest.setRecipients(Arrays.asList(user.getEmail()));
        mailRequest.setSubject("Email Confirmation");
        mailRequest.setMessage(message + "\n" + event.getConfirmationUrl());

        mailService.sendMail(mailRequest);
    }

}
