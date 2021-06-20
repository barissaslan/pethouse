package com.barisaslan.pethouse.domain.service;

import com.barisaslan.pethouse.domain.model.MailRequest;

public interface MailService {

    boolean sendMail(MailRequest mailRequest);

}
