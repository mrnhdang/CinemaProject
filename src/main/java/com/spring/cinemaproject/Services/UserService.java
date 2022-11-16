package com.spring.cinemaproject.Services;

import com.spring.cinemaproject.Models.Users;
import com.spring.cinemaproject.Repositories.EmailService;
import com.spring.cinemaproject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


@Service
@Transactional
public class UserService implements EmailService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;

    public String sendVerificationEMail (Users user , String siteURL) throws MessagingException, UnsupportedEncodingException {
        String subject = "Please verify your registration";
        String senderName = "Dang Cinema";
        String mailContent = "<p>Dear" + user.getUserName() +",</p>";
        mailContent += "<p>Please click the link below to verify to your Registration: </p>";

        String verifyURL = siteURL+ "/verify?code="+ user.getVerificationCode() ;
        mailContent +="<h3><a href=\""+ verifyURL +"\">VERIFY</a></h3>";

        mailContent +="<p>Thank you<br>Dang Cinema</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("dangtest896@gmail.com",senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(subject);

        helper.setText(mailContent, true);

        mailSender.send(message);
        return "success";
    }
    public boolean verify(String vertification){
        Users user = userRepository.findVertificationCode(vertification);
        if(user == null || user.isStatus()){
            return false;
        }else
        {
            userRepository.enable(user.getUserID());
            return true;
        }
    }

}
