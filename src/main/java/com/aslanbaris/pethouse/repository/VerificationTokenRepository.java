package com.aslanbaris.pethouse.repository;

import com.aslanbaris.pethouse.entity.User;
import com.aslanbaris.pethouse.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
