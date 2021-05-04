package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.model.MailRequest;
import com.mashape.unirest.http.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.aslanbaris.pethouse.constants.Constants.*;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Override
    public boolean sendMail(MailRequest mailRequest) {
        if (checkMailRequest(mailRequest)) {
            Map<String, Object> fields = prepareMailContents(mailRequest);

            try {
                Unirest.post(MAIL_API_URL).basicAuth("api", MAIL_API_KEY).fields(fields).asJson();
                return true;
            } catch (Exception e) {
                log.error("Send Mail Error: " + Arrays.toString(e.getStackTrace()));
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
        fields.put("from", MAIL_SENDER_ADMIN);
        fields.put("to", mailRequest.getRecipients().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")));
        return fields;
    }

}
