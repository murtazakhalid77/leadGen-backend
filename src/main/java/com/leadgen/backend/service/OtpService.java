package com.leadgen.backend.service;

import javax.transaction.Transactional;

public interface OtpService {
    String sendOtp(String email);

    @Transactional
    Boolean cratePassword(String password, String number);
}
