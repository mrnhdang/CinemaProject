package com.spring.cinemaproject.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String rawPass= "Dang@123";
        String encodedPass =encoder.encode(rawPass);

        System.out.print(encodedPass);
    }
}
