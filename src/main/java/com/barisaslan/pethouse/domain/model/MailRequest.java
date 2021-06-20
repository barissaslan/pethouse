package com.barisaslan.pethouse.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class MailRequest {

    private String subject;
    private String message;
    private List<String> recipients;

}
