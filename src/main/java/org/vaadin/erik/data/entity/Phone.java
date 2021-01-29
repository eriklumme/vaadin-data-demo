package org.vaadin.erik.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.vaadin.erik.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Phone extends AbstractEntity {

    private String phone;
}
