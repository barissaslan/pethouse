package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(User user);

    String createAndSaveVerificationToken(User user);

    VerificationToken getVerificationToken(String verificationToken);

    String getConfirmationUrl(String token);

}
