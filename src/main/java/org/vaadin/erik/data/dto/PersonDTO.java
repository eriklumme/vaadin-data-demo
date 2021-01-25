package org.vaadin.erik.data.dto;

import lombok.Getter;
import lombok.Setter;
import org.vaadin.erik.data.IPerson;

import java.time.LocalDate;

@Getter
@Setter
public class PersonDTO implements IPerson {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;
}
