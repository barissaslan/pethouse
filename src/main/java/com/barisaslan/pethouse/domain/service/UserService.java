package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.api.request.RegisterRequest;
import com.barisaslan.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.barisaslan.pethouse.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(String email, String password) throws EmailUserAlreadyExistException;

    User save(User user);

    String createAndSaveVerificationToken(User user);

    void publishRegistrationCompleteEvent(User user, String verificationToken);

    boolean isVerificationTokenAvailable(String verificationToken);

    void verifyEmail(String verificationToken);

    void handleRegistration(RegisterRequest registerRequest) throws EmailUserAlreadyExistException;

}
