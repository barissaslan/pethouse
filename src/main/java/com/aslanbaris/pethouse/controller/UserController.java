package com.aslanbaris.pethouse.controller;

import com.aslanbaris.pethouse.entity.User;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping(value = "/register")
    public void register(@RequestBody User user, HttpServletRequest request) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.save(user);

        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
    }

    @GetMapping(value = "/confirmation")
    public void confirmation(WebRequest request, HttpServletResponse response, @RequestParam("token") String token)
            throws IOException {
//        Locale locale = request.getLocale();
//        VerificationToken verificationToken = userService.getVerificationToken(token);
//        if (verificationToken == null) {
//            // todo
//        }
//        User user = verificationToken.getUser();
//
//        // if token not expired
//
//        user.setEnabled(true);
//        userService.save(user);

        response.sendRedirect("http://localhost:8080/");

    }
}
