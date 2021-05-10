package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(User user) throws EmailUserAlreadyExistException;

    User save(User user);

    String createAndSaveVerificationToken(User user);

    EmailVerificationToken getVerificationToken(String verificationToken);

    String getConfirmationUrl(String token);

}
