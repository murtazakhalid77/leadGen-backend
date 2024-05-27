package com.leadgen.backend.security.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class CustomAesPasswordEncoder implements PasswordEncoder {

    private final SecretKey secretKey;

    public CustomAesPasswordEncoder(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(rawPassword.toString().getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during password encryption", e);
        }
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        try {
            String encryptedPassword = encode(rawPassword);
            return encryptedPassword.equals(encodedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error occurred during password matching", e);
        }
    }
}