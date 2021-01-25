package org.vaadin.erik.data.entity;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import org.vaadin.erik.data.AbstractEntity;
import org.vaadin.erik.data.IPerson;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Person extends AbstractEntity implements IPerson {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;

}
