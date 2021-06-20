package com.barisaslan.pethouse.api.controller;

import com.barisaslan.pethouse.api.request.RegisterRequest;
import com.barisaslan.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.pethouse.common.exceptions.InvalidEmailException;
import com.barisaslan.pethouse.dao.entity.User;
import com.barisaslan.pethouse.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static com.barisaslan.pethouse.common.constants.Constants.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = USER_CONTROLLER_BASE_PATH)
public class UserController {

    private final UserService userService;

    @PostMapping(value = USER_CONTROLLER_REGISTER_PATH)
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest registerRequest)
            throws EmailUserAlreadyExistException, InvalidEmailException {
        User user = userService.createUser(registerRequest.getEmail(), registerRequest.getPassword());
        String token = userService.createAndSaveVerificationToken(user);

        log.info("Test2");

        userService.publishRegistrationCompleteEvent(user, token);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = USER_CONTROLLER_CONFIRMATION_PATH)
    public void confirmation(@RequestParam("token") String token, HttpServletResponse response) throws IOException {
        if (!userService.isVerificationTokenAvailable(token)) {
            response.sendRedirect("http://localhost:8080/error/baris.html");
            return;
        }

        userService.verifyEmail(token);
        response.sendRedirect("http://localhost:8080/success/baris.html");
    }

    @GetMapping(value = "dummy")
    public String  dummy() {
        return "dummy";
    }

}
