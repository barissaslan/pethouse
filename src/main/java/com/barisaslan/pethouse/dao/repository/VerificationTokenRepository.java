package com.barisaslan.pethouse.dao.repository;

import com.barisaslan.pethouse.dao.entity.EmailVerificationToken;
import com.barisaslan.pethouse.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    EmailVerificationToken findByToken(String token);

    EmailVerificationToken findByUser(User user);

}
