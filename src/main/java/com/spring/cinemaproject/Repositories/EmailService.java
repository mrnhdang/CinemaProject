package com.spring.cinemaproject.Repositories;

import com.spring.cinemaproject.Models.Users;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    public String sendVerificationEMail(Users user, String siteURL) throws MessagingException, UnsupportedEncodingException;
}
