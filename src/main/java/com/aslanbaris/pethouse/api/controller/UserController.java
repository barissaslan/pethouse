package com.aslanbaris.pethouse.api.controller;

import com.aslanbaris.pethouse.api.request.RegisterRequest;
import com.aslanbaris.pethouse.common.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.aslanbaris.pethouse.common.exceptions.InvalidEmailException;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.aslanbaris.pethouse.common.constants.Constants.USER_BASE_CONTROLLER_PATH;
import static com.aslanbaris.pethouse.common.constants.Constants.USER_CONFIRMATION_CONTROLLER_PATH;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = USER_BASE_CONTROLLER_PATH)
public class UserController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/register")
    public void register(@RequestBody @Valid RegisterRequest registerRequest, HttpServletRequest httpRequest)
            throws EmailUserAlreadyExistException, InvalidEmailException {
        User user = userService.createUser(registerRequest.getEmail(), registerRequest.getPassword());

        String token = userService.createAndSaveVerificationToken(user);
        String confirmationUrl = userService.getConfirmationUrl(token);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, httpRequest.getLocale(), confirmationUrl));
    }

    @GetMapping(value = USER_CONFIRMATION_CONTROLLER_PATH)
    public void confirmation(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        EmailVerificationToken emailVerificationToken = userService.getVerificationToken(token);

        if (emailVerificationToken == null
                || emailVerificationToken.isExpired()
                || emailVerificationToken.getUser().isEmailVerified()) {
            response.sendRedirect("http://localhost:8080/error/baris.html");
            return;
        }

        User user = emailVerificationToken.getUser();

        user.setEmailVerified(true);
        userService.save(user);

        response.sendRedirect("http://localhost:8080/success/baris.html");
    }

}