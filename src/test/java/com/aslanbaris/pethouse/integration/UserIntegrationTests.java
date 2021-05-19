package com.aslanbaris.pethouse.integration;

import com.aslanbaris.pethouse.api.controller.UserController;
import com.aslanbaris.pethouse.api.request.RegisterRequest;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.repository.UserRepository;
import com.aslanbaris.pethouse.dao.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.aslanbaris.pethouse.TestHelper.asJsonString;
import static com.aslanbaris.pethouse.common.constants.Constants.USER_CONTROLLER_BASE_PATH;
import static com.aslanbaris.pethouse.common.constants.Constants.USER_CONTROLLER_REGISTER_PATH;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserIntegrationTests {

    private MockMvc mvc;

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerShouldCreateUserWithToken() throws Exception {
        String URL = "/" + USER_CONTROLLER_BASE_PATH + "/" + USER_CONTROLLER_REGISTER_PATH;

        mvc.perform(post(URL)
                .content(asJsonString(new RegisterRequest("test1@barisaslan.com", "password")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        Optional<User> user = userRepository.findUserByEmail("test1@barisaslan.com");
        assertTrue(user.isPresent());
        assertTrue(user.get().getId() > 0);

        EmailVerificationToken token = tokenRepository.findByUser(user.get());
        assertNotNull(token);
        assertTrue(token.getId() > 0);
        assertTrue(token.getToken().length() > 0);
    }

}
