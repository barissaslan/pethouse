package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.common.exceptions.HttpRequestFailException;
import com.barisaslan.pethouse.domain.wrapper.HttpRequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.barisaslan.pethouse.TestHelper.getDummyMailRequest;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @InjectMocks
    private MailServiceImpl mailService;

    @Mock
    private HttpRequestWrapper httpRequestWrapper;

    @Test
    void sendMailShouldReturnTrue() throws HttpRequestFailException {
        doNothing().when(httpRequestWrapper).post(any(), any(), any(), anyMap());

        boolean result = mailService.sendMail(getDummyMailRequest());

        verify(httpRequestWrapper).post(any(), any(), any(), anyMap());
        verifyNoMoreInteractions(httpRequestWrapper);
        assertTrue(result);
    }

    @Test
    void sendMailShouldReturnFalse() throws HttpRequestFailException {
        doThrow(new HttpRequestFailException())
                .when(httpRequestWrapper)
                .post(any(), any(), any(), anyMap());

        boolean result = mailService.sendMail(getDummyMailRequest());

        verify(httpRequestWrapper).post(any(), any(), any(), anyMap());
        verifyNoMoreInteractions(httpRequestWrapper);
        assertFalse(result);
    }

}
