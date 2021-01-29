package org.vaadin.erik.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;
import org.vaadin.erik.data.AbstractEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Person extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private List<Phone> phones = new ArrayList<>();

    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;
    @Version
    private Integer version;
}
