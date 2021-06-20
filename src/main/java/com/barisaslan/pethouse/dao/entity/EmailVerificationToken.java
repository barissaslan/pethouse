package com.barisaslan.pethouse.dao.entity;

import com.barisaslan.pethouse.config.security.SecurityConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class EmailVerificationToken {

    public EmailVerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = new Date(System.currentTimeMillis() + SecurityConstants.EMAIL_EXPIRATION_TIME);
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
