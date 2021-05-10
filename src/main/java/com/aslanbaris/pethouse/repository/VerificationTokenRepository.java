package com.aslanbaris.pethouse.repository;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);

    EmailVerificationToken findByUser(User user);

}
