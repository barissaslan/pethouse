package com.barisaslan.pethouse.domain.wrapper;

import com.barisaslan.pethouse.common.exceptions.HttpRequestFailException;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class HttpRequestWrapperImpl implements HttpRequestWrapper {

    public void post(String url, String username, String password, Map<String, Object> fields)
            throws HttpRequestFailException {
        try {
            Unirest.post(url).basicAuth(username, password).fields(fields).asJson();
        } catch (UnirestException e) {
            log.error("HttpRequestWrapper::post -> " + e.getMessage());
            throw new HttpRequestFailException();
        }
    }

}
