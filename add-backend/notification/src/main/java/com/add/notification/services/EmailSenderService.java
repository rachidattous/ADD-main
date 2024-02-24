package com.add.notification.services;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.add.notification.dto.MailFileDTO;
import com.add.notification.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.mail.internet.MimeMessage;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;

import javax.mail.internet.MimeBodyPart;

import java.util.Objects;
import java.util.Optional;

import javax.activation.DataHandler;
import javax.activation.DataSource;

import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderService {

    @Value("${spring.mail.username}")
    private String from;

    private final Session session;

    // @Async
    public void sendMail(MailFileDTO mail) {

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.setSubject(mail.getSubject());
            Multipart multipart = new MimeMultipart();
            // add the text part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mail.getBody(), "text/html; charset=utf-8");
            multipart.addBodyPart(textBodyPart);

            // add the attachment part

            Optional.ofNullable(mail.getFiles())
                    .ifPresent(e -> e.stream().filter(Objects::nonNull).forEach(file -> sendFile(file, multipart)));
            message.setContent(multipart);
            mail.getTo().stream().forEach(dest -> addRecipients(dest, message, RecipientType.TO));

            Optional.ofNullable(mail.getCopy())
                    .ifPresent(e -> e.stream().forEach(dest -> addRecipients(dest, message, RecipientType.CC)));
            sendMailViaSMTPServer(message);

        } catch (MessagingException e) {
            log.info(e.getMessage());
        }

    }

    public void sendMail(MailDTO mail) {

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.setSubject(mail.getSubject());
            Multipart multipart = new MimeMultipart();
            // add the text part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(mail.getBody(), "text/html; charset=utf-8");
            multipart.addBodyPart(textBodyPart);

            // add the attachment part

            message.setContent(multipart);
            mail.getTo().stream().forEach(dest -> addRecipients(dest, message, RecipientType.TO));

            Optional.ofNullable(mail.getCopy())
                    .ifPresent(e -> e.stream().forEach(dest -> addRecipients(dest, message, RecipientType.CC)));
            sendMailViaSMTPServer(message);

        } catch (MessagingException e) {
            log.info(e.getMessage());
        }

    }

    public void sendFile(MultipartFile file, Multipart multipart) {
        try {
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource ds = new ByteArrayDataSource(file.getBytes(), file.getContentType());

            attachmentBodyPart.setDataHandler(new DataHandler(ds));

            attachmentBodyPart.setFileName(file.getOriginalFilename());
            attachmentBodyPart.setDisposition(Part.ATTACHMENT);
            multipart.addBodyPart(attachmentBodyPart);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    public void addRecipients(String dest, MimeMessage message, RecipientType type) {
        try {
            message.addRecipients(type, dest);

        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    public void sendMailViaSMTPServer(MimeMessage message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            log.info(e.getMessage());
        }

    }

}
