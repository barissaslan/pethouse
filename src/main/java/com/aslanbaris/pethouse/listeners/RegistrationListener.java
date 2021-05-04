package com.aslanbaris.pethouse.listeners;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.model.MailRequest;
import com.aslanbaris.pethouse.service.MailService;
import com.aslanbaris.pethouse.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final UserService userService;
    private final MailService mailService;

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        log.info("onApplicationEvent");
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String confirmationUrl = event.getAppUrl() + "/users/confirmation?token=" + token;
//        String message = messages.getMessage("message.regSucc", null, event.getLocale()); // todo message resource
        String message = "Hello, confirmation.";

        MailRequest mailRequest = new MailRequest();
        mailRequest.setRecipients(Arrays.asList(user.getEmail()));
        mailRequest.setSubject("Email Confirmation");
        mailRequest.setMessage(message + "\r\n" + "http://localhost:8080" + confirmationUrl);

        mailService.sendMail(mailRequest);
    }

}
