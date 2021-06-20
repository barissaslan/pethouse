package com.barisaslan.pethouse.domain.wrapper;

import com.barisaslan.pethouse.common.exceptions.HttpRequestFailException;

import java.util.Map;

public interface HttpRequestWrapper {

    void post(String url, String username, String password, Map<String, Object> fields) throws HttpRequestFailException;

}
