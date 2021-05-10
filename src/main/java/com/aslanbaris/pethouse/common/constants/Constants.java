package com.aslanbaris.pethouse.common.constants;

public final class Constants {
    public static String MAIL_DOMAIN_NAME = "barisaslan.com";
    public static String MAIL_API_URL = String.format("https://api.eu.mailgun.net/v3/%s/messages", MAIL_DOMAIN_NAME);
    public static String MAIL_API_KEY = "0d48a25424eeea1f1304a560586a5506-4b1aa784-3fd821ee";
    public static String MAIL_SENDER_ADMIN = "Barış Aslan <admin@barisaslan.com>";

    public static final String USER_BASE_CONTROLLER_PATH = "users";
    public static final String USER_CONFIRMATION_CONTROLLER_PATH = "confirmation";
}
