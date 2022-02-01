package pl.pwr.dissertation.logic.domain;


import lombok.Data;

import java.util.Set;

@Data
public class User {

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;

    private Set<String> roles;

    private String type;

}
