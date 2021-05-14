package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.aslanbaris.pethouse.common.exceptions.InvalidEmailException;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User createUser(String email, String password) throws EmailUserAlreadyExistException, InvalidEmailException;

    User save(User user);

    String createAndSaveVerificationToken(User user);

    EmailVerificationToken getVerificationToken(String verificationToken);

    String getConfirmationUrl(String token);

}
