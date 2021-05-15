package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.common.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.aslanbaris.pethouse.common.exceptions.InvalidEmailException;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.repository.UserRepository;
import com.aslanbaris.pethouse.dao.repository.VerificationTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.aslanbaris.pethouse.TestHelper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ApplicationEventPublisher eventPublisher;

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
    void isVerificationTokenAvailableShouldReturnTrue() {
        when(tokenRepository.findByToken(anyString())).thenReturn(getDummyNotVerifiedToken());

        boolean result = userService.isVerificationTokenAvailable("token");

        verify(tokenRepository).findByToken(anyString());
        assertTrue(result);
    }

    @Test
    void isVerificationTokenAvailableShouldReturnFalse() {
        when(tokenRepository.findByToken(anyString())).thenReturn(getDummyVerifiedToken());

        boolean result = userService.isVerificationTokenAvailable("token");

        verify(tokenRepository).findByToken(anyString());
        assertFalse(result);
    }

    @Test
    void verifyEmailShouldUpdateVerificationFlag() {
        when(tokenRepository.findByToken(anyString())).thenReturn(getDummyNotVerifiedToken());

        userService.verifyEmail("token");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        verifyNoMoreInteractions(userRepository);

        assertTrue(userCaptor.getValue().isEmailVerified());
    }

    @Test
    void verifyEmailShouldNotUpdateVerificationFlag() {
        when(tokenRepository.findByToken(anyString())).thenReturn(null);

        userService.verifyEmail("token");

        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createAndSaveVerificationTokenShouldReturnToken() {
        String result = userService.createAndSaveVerificationToken(getDummyUser());

        verify(tokenRepository).save(any(EmailVerificationToken.class));
        assertNotNull(result);
    }

    @Test
    void publishRegistrationCompleteEventShouldSuccess() {
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);

        userService.publishRegistrationCompleteEvent(getDummyUser(), "token");

        verify(eventPublisher).publishEvent(any(OnRegistrationCompleteEvent.class));
    }

}
