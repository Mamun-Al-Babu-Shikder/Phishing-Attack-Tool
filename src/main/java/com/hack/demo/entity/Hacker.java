package com.hack.demo.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Hacker {

    @Transient
    public static final String ROLE_HACKER = "ROLE_HACKER",
            ROLE_HACKER_ADMIN = "ROLE_HACKER_ADMIN";

    @Id
    @Column(length = 100)
    @Email
    private String email;
    private String id;
    @Length(min = 3, max = 30, message = "name length must between 3 and 30")
    private String name;
    @Length(min = 6, max = 30, message = "password length must be 6 to 30")
    private String password;
    private String role;
    private boolean enable;
}
