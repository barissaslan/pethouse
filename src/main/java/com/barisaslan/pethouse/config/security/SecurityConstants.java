package com.barisaslan.pethouse.config.security;

public class SecurityConstants {
    public static final String SECRET = "pethouse";
    public static final long AUTH_EXPIRATION_TIME = 423_000_000; // 5 days
    public static final long EMAIL_EXPIRATION_TIME = 84_600_000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/register";
}
