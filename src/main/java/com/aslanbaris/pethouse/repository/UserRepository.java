package com.aslanbaris.pethouse.repository;

import com.aslanbaris.pethouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
