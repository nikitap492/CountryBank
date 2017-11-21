package com.cbank.domain.message;

import com.cbank.domain.Persistable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Entity
@Table(name = "feedback")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor(staticName = "of")
public class Feedback extends Persistable {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String body;

}
