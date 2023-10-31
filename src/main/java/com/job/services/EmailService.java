package com.job.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.job.entities.Job;
import com.job.entities.Organization;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendRegistrationEmail(String to, String username,String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome "+username+" to our login process");
        message.setText("You OTP for login is "+otp);

        javaMailSender.send(message);
    }
    
    public void sendPostJobEmail(String to, String username,String formData) {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String subject = "Last Step..";
        String body = formData;

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Setting the second argument to true indicates HTML content

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
        }
    }
    
    public void sendJobSuccessEmail(String to, String username,Job job) {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String subject = "HashJob (Apply successfully in "+ job.getOrganization().getName() +" )";
        String body = "<h1>Hello "+username+" thank you for response !</h1>"
        		+ "<p>Your application has been successfully applied for "+job.getHeadline()+"</p>";

        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Setting the second argument to true indicates HTML content

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
        }
    }
    
    public void applicantDetailsEmail(Organization organization,Job job,String candidateName,String candidateEmail,String candidatePhoneNumber) {
    	MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String subject = "HashJob (Candidate found for "+ job.getHeadline() +" )";
        String body = "<h1>"+candidateName+" applied for "+job.getHeadline()+".</h1>"
        		+ "<p>Candidate Name : "+candidateName+"</p>"
        		+"<p>Eamil : "+candidateEmail+" </p>"
        		+ "<p>PhoneNumber : "+candidatePhoneNumber+"</p>"
        		+"<p>more details go to your organization page</p>";

        try {
            helper.setTo(organization.getEmail());
            helper.setSubject(subject);
            helper.setText(body, true); // Setting the second argument to true indicates HTML content

            javaMailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
        }
    }
    
}
