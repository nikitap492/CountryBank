package com.cbank.domain.message;

import com.bank.domain.Account;
import com.cbank.domain.Persistable;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "messages")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Message extends Persistable{

    private String to;
    private String title;
    private String body;

    public SimpleMailMessage toMailMessage(String sender) {
        val mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom(sender);
        mailMessage.setText(body);
        mailMessage.setSubject(title);
        return mailMessage;
    }
}
