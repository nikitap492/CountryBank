package com.cbank.domain.message;

import com.cbank.domain.Persistable;
import lombok.*;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Message extends Persistable{

    private String to;
    private String title;

    @Column(length = Integer.MAX_VALUE)
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
