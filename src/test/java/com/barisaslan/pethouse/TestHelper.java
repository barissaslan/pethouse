package com.barisaslan.pethouse;

import com.barisaslan.pethouse.api.request.AddPetRequest;
import com.barisaslan.pethouse.api.request.RegisterRequest;
import com.barisaslan.pethouse.dao.entity.EmailVerificationToken;
import com.barisaslan.pethouse.dao.entity.Pet;
import com.barisaslan.pethouse.dao.entity.User;
import com.barisaslan.pethouse.domain.model.MailRequest;
import com.barisaslan.pethouse.domain.model.PetType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class TestHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static User getDummyUser() {
        User user = new User();

        user.setEmail("test@barisaslan.com");
        user.setPassword("123");
        user.setFirstName("Barış");
        user.setLastName("Aslan");

        return user;
    }

    public static EmailVerificationToken getDummyVerifiedToken() {
        EmailVerificationToken token = new EmailVerificationToken();

        token.setToken("token");
        token.setExpiryDate(new Date());

        User user = new User();
        user.setEmail("test@barisaslan.com");
        user.setEmailVerified(true);
        token.setUser(user);

        return token;
    }

    public static EmailVerificationToken getDummyNotVerifiedToken() {
        EmailVerificationToken token = new EmailVerificationToken();

        token.setToken("token");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2100, Calendar.JANUARY, Calendar.MONDAY);
        token.setExpiryDate(calendar.getTime());

        User user = new User();
        user.setEmail("test@barisaslan.com");
        user.setEmailVerified(false);
        token.setUser(user);

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

    public static RegisterRequest getDummyRegisterRequest() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@barisaslan.com");
        request.setPassword("password");
        return request;
    }

    public static AddPetRequest getDummyAddPetRequest() {
        AddPetRequest request = new AddPetRequest();
        request.setPetName("pet1");
        request.setPetType(PetType.BIRD);
        request.setPetBirthDate(new Date());
        return request;
    }

}
