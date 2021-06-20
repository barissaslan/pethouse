package com.barisaslan.pethouse.config.security;

import com.barisaslan.pethouse.common.exceptions.UserAuthenticationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
 class JWTAuthenticationFilterTest {

    @InjectMocks
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    AuthenticationManager authenticationManager;

    @Test
    void attemptAuthenticationShouldThrowException() {
       var request = new MockHttpServletRequest();

       UserAuthenticationException thrown = assertThrows(
               UserAuthenticationException.class,
               () -> jwtAuthenticationFilter.attemptAuthentication(request, null)
       );
    }

}
