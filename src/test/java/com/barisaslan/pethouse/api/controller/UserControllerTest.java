package com.barisaslan.pethouse.api.controller;

import com.barisaslan.pethouse.TestHelper;
import com.barisaslan.pethouse.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.barisaslan.pethouse.common.constants.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void registerShouldReturnSuccess() throws Exception {
        String registerUrl = "/" + USER_CONTROLLER_BASE_PATH + "/" + USER_CONTROLLER_REGISTER_PATH;

        mvc.perform(post(registerUrl)
                .content(TestHelper.asJsonString(TestHelper.getDummyRegisterRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        verify(userService).createUser(anyString(), anyString());
        verify(userService).createAndSaveVerificationToken(any());
        verify(userService).publishRegistrationCompleteEvent(any(), any());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void confirmationShouldReturnSuccess() throws Exception {
        String controllerUrl = "/" + USER_CONTROLLER_BASE_PATH + "/" + USER_CONTROLLER_CONFIRMATION_PATH;

        when(userService.isVerificationTokenAvailable(anyString())).thenReturn(true);

        mvc.perform(get(controllerUrl + "?token=test"));

        verify(userService).isVerificationTokenAvailable(anyString());
        verify(userService).verifyEmail(anyString());
        verifyNoMoreInteractions(userService);
    }

    @Test
    void confirmationShouldReturnFail() throws Exception {
        String controllerUrl = "/" + USER_CONTROLLER_BASE_PATH + "/" + USER_CONTROLLER_CONFIRMATION_PATH;

        when(userService.isVerificationTokenAvailable(anyString())).thenReturn(false);

        mvc.perform(get(controllerUrl + "?token=test"));

        verify(userService).isVerificationTokenAvailable(anyString());
        verifyNoMoreInteractions(userService);
    }

}
