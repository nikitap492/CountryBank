package com.cbank.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author Podshivalov N.A.
 * @since 21.11.2017.
 */
@Data
@ToString(exclude = "password")
public class RegistrationForm {

    private String username;
    private String password;
    private String email;
    private String name;
    private String address;

    public Client toClient(){
        return new Client(name, address, email, username);
    }

}
