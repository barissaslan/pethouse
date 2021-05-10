package com.aslanbaris.pethouse.dao.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static com.aslanbaris.pethouse.config.security.SecurityConstants.EMAIL_EXPIRATION_TIME;

@Data
@Entity
@NoArgsConstructor
public class EmailVerificationToken {

    public EmailVerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date(System.currentTimeMillis() + EMAIL_EXPIRATION_TIME);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    private Date expiryDate;

    public boolean isExpired() {
        return new Date().after(expiryDate);
    }

}
