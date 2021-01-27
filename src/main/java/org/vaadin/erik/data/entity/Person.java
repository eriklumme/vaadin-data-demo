package org.vaadin.erik.data.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import org.vaadin.erik.data.AbstractEntity;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Person extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;

}
