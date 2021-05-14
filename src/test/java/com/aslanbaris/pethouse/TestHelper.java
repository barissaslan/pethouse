package com.aslanbaris.pethouse;

import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.Pet;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.domain.model.MailRequest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class TestHelper {

    public static User getDummyUser() {
        User user = new User();

        user.setEmail("test@barisaslan.com");
        user.setPassword("123");
        user.setFirstName("Barış");
        user.setLastName("Aslan");

        return user;
    }

    public static EmailVerificationToken getDummyVerificationToken() {
        EmailVerificationToken token = new EmailVerificationToken();

        token.setToken("token");
        token.setExpiryDate(new Date());

        return token;
    }

    public static MailRequest getDummyMailRequest() {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setSubject("subject");
        mailRequest.setMessage("message");
        mailRequest.setRecipients(Arrays.asList("test@barisaslan.com", "test2@barisaslan.com"));

        return mailRequest;
    }

    public static List<Pet> getDummyPetList() {
        Pet p1 = new Pet();
        p1.setName("pet1");

        Pet p2 = new Pet();
        p2.setName("pet2");

        return Arrays.asList(p1, p2);
    }

    public static Pet getDummyPet() {
        Pet p1 = new Pet();
        p1.setName("pet1");
        return p1;
    }

}
