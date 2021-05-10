package com.aslanbaris.pethouse.domain.service;

import com.aslanbaris.pethouse.domain.model.MailRequest;

public interface MailService {

    boolean sendMail(MailRequest mailRequest);

}
