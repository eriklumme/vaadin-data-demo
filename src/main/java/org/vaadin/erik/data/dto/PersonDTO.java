package org.vaadin.erik.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String occupation;
    private boolean important;
    private Integer version;
    private List<PhoneDTO> phones = new ArrayList<>();

    public void setPhones(List<PhoneDTO> phones) {
        if (phones != null) {
            this.phones = phones;
        }
    }
}
