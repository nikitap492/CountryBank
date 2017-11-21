package com.cbank.domain.message;

import com.cbank.domain.Persistable;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.Entity;
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
