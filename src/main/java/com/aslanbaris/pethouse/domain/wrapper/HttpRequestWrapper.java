package com.aslanbaris.pethouse.domain.wrapper;

import com.aslanbaris.pethouse.common.exceptions.HttpRequestFailException;

import java.util.Map;

public interface HttpRequestWrapper {

    void post(String url, String username, String password, Map<String, Object> fields) throws HttpRequestFailException;

}
