package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.common.events.OnRegistrationCompleteEvent;
import com.aslanbaris.pethouse.common.exceptions.EmailUserAlreadyExistException;
import com.aslanbaris.pethouse.common.exceptions.InvalidEmailException;
import com.aslanbaris.pethouse.common.utils.Utils;
import com.aslanbaris.pethouse.dao.entity.EmailVerificationToken;
import com.aslanbaris.pethouse.dao.entity.User;
import com.aslanbaris.pethouse.dao.repository.UserRepository;
import com.aslanbaris.pethouse.dao.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;

import static com.aslanbaris.pethouse.common.constants.Constants.USER_CONTROLLER_BASE_PATH;
import static com.aslanbaris.pethouse.common.constants.Constants.USER_CONTROLLER_CONFIRMATION_PATH;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository tokenRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        return user.get();
    }

    @Override
    public User createUser(String email, String password) throws EmailUserAlreadyExistException, InvalidEmailException {
        if (!Utils.isValidEmailAddress(email)) {
            throw new InvalidEmailException(email);
        }

        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            throw new EmailUserAlreadyExistException();
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(newUser);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public String createAndSaveVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        EmailVerificationToken emailVerificationToken = new EmailVerificationToken(token, user);
        tokenRepository.save(emailVerificationToken);

        return token;
    }

    @Override
    public void publishRegistrationCompleteEvent(User user, String verificationToken) {
        String confirmationUrl = getConfirmationUrl(verificationToken);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(this, user, confirmationUrl));
    }

    @Override
    public boolean isVerificationTokenAvailable(String verificationToken) {
        EmailVerificationToken emailVerificationToken = tokenRepository.findByToken(verificationToken);

        return emailVerificationToken != null
                && !emailVerificationToken.isExpired()
                && !emailVerificationToken.getUser().isEmailVerified();
    }

    @Override
    public void verifyEmail(String verificationToken) {
        EmailVerificationToken emailVerificationToken = tokenRepository.findByToken(verificationToken);

        if (emailVerificationToken != null) {
            User user = emailVerificationToken.getUser();
            user.setEmailVerified(true);
            userRepository.save(user);
        }
    }

    private String getConfirmationUrl(String token) {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return String.format("%s/%s/%s?token=%s",
                baseUrl,
                USER_CONTROLLER_BASE_PATH,
                USER_CONTROLLER_CONFIRMATION_PATH,
                token);
    }

}
