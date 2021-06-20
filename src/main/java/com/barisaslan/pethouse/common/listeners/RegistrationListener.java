package com.barisaslan.pethouse.common.listeners;

import com.barisaslan.pethouse.common.events.OnRegistrationCompleteEvent;
import com.barisaslan.pethouse.common.properties.UserMessageProperties;
import com.barisaslan.pethouse.dao.entity.User;
import com.barisaslan.pethouse.domain.model.MailRequest;
import com.barisaslan.pethouse.domain.service.MailService;
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
    private final UserMessageProperties userMessageProperties;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        log.debug("onApplicationEvent");
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String message = userMessageProperties.getConfirmMessage();

        MailRequest mailRequest = new MailRequest();
        mailRequest.setRecipients(Arrays.asList(user.getEmail()));
        mailRequest.setSubject("Email Confirmation");
        mailRequest.setMessage(message + "\n" + event.getConfirmationUrl());

        mailService.sendMail(mailRequest);
    }

}
