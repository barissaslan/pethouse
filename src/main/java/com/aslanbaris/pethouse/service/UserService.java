package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.entity.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String verificationToken);

}
