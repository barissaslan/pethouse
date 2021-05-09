package com.aslanbaris.pethouse.controller;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.entity.VerificationToken;
import com.aslanbaris.pethouse.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aslanbaris.pethouse.constants.Constants.USER_BASE_CONTROLLER_PATH;
import static com.aslanbaris.pethouse.constants.Constants.USER_CONFIRMATION_CONTROLLER_PATH;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = USER_BASE_CONTROLLER_PATH)
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/register")
    public void register(@RequestBody User user, HttpServletRequest request) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);

        String token = userService.createAndSaveVerificationToken(user);
        String confirmationUrl = userService.getConfirmationUrl(token);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), confirmationUrl));
    }

    @GetMapping(value = USER_CONFIRMATION_CONTROLLER_PATH)
    public void confirmation(WebRequest request, HttpServletResponse response, @RequestParam("token") String token)
            throws IOException {
        VerificationToken verificationToken = userService.getVerificationToken(token);

        if (verificationToken == null || verificationToken.isExpired()) {
            response.sendRedirect("http://localhost:8080/error/baris.html");
            return;
        }

        User user = verificationToken.getUser();

        user.setEmailVerified(true);
        userService.save(user);

        response.sendRedirect("http://localhost:8080/success/baris.html");

    }
}
