package com.aslanbaris.pethouse.service;

import com.aslanbaris.pethouse.model.MailRequest;

public interface MailService {

    boolean sendMail(MailRequest mailRequest);

}
