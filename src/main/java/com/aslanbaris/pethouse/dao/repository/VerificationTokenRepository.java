package com.aslanbaris.pethouse.dao.repository;

import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);

    EmailVerificationToken findByUser(User user);

}
