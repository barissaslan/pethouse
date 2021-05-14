package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.aslanbaris.pethouse.common.exceptions.InvalidEmailException;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.repository.UserRepository;
import com.aslanbaris.pethouse.dao.repository.VerificationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.aslanbaris.pethouse.TestHelper.getDummyUser;
import static com.aslanbaris.pethouse.TestHelper.getDummyVerificationToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImpl(userRepository, tokenRepository, bCryptPasswordEncoder);
    }

    @Test
    void loadUserByUsernameShouldReturnUser() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(getDummyUser()));
        final UserDetails user = userService.loadUserByUsername("test");
        assertNotNull(user);
        assertEquals("test@barisaslan.com", user.getUsername());
    }

    @Test
    void loadUserByUsernameShouldThrowException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException thrown = assertThrows(
                UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("test")
        );

        assertTrue(thrown.getMessage().contains("test"));
    }

    @Test
    void createUserShouldReturnUser() throws EmailUserAlreadyExistException, InvalidEmailException {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.createUser("test@test.com", "password");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        assertEquals("test@test.com", userCaptor.getValue().getEmail());
        assertEquals("encodedPassword", userCaptor.getValue().getPassword());
    }

    @Test
    void createUserShouldThrowInvalidEmailException() {
        InvalidEmailException thrown = assertThrows(
                InvalidEmailException.class,
                () -> userService.createUser("invalid_email", "password")
        );

        assertTrue(thrown.getMessage().contains("invalid_email"));
    }

    @Test
    void createUserShouldThrowEmailUserAlreadyExistException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(getDummyUser()));

        EmailUserAlreadyExistException thrown = assertThrows(
                EmailUserAlreadyExistException.class,
                () -> userService.createUser("test@test.com", "password")
        );

        assertTrue(thrown.getMessage().contains("exists"));
    }

    @Test
    void saveShouldReturnUser() {
        when(userRepository.save(any(User.class))).thenReturn(getDummyUser());

        User user = userService.save(getDummyUser());

        verify(userRepository).save(any(User.class));
        assertEquals("test@barisaslan.com", user.getEmail());
    }

    @Test
    void getVerificationTokenShouldReturnToken() {
        when(tokenRepository.findByToken(anyString())).thenReturn(getDummyVerificationToken());

        EmailVerificationToken result = userService.getVerificationToken("t");

        verify(tokenRepository).findByToken(anyString());
        assertEquals("token", result.getToken());
    }

    @Test
    void createAndSaveVerificationTokenShouldReturnToken() {
        String result = userService.createAndSaveVerificationToken(getDummyUser());

        verify(tokenRepository).save(any(EmailVerificationToken.class));
        assertNotNull(result);
    }

    @Test
    void getConfirmationUrlShouldReturnUrl() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        String result = userService.getConfirmationUrl("token");

        assertTrue(result.contains("token"));
    }

}
