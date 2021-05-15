package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.common.properties.MailSenderProperties;
import com.aslanbaris.pethouse.domain.model.MailRequest;
import com.aslanbaris.pethouse.domain.wrapper.HttpRequestWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final HttpRequestWrapper httpRequestWrapper;
    private final MailSenderProperties sender;

    @Override
    public boolean sendMail(MailRequest mailRequest) {
        if (checkMailRequest(mailRequest)) {
            Map<String, Object> fields = prepareMailContents(mailRequest);

            try {
                httpRequestWrapper.post(sender.getApiUrl(), sender.getApiUsername(), sender.getApiKey(), fields);
                return true;
            } catch (Exception e) {
                log.error("Send Mail Error: " + e.getMessage());
            }
        }

        return false;
    }

    private boolean checkMailRequest(MailRequest mailRequest) {
        return mailRequest != null && mailRequest.getRecipients() != null && mailRequest.getRecipients().size() > 0;
    }

    private Map<String, Object> prepareMailContents(MailRequest mailRequest) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("subject", mailRequest.getSubject());
        fields.put("text", mailRequest.getMessage());
        fields.put("from", sender.getMailSender());
        fields.put("to", mailRequest.getRecipients().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));
        return fields;
    }

}
