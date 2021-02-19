package com.hack.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class HackingData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hacking_data_sequence")
    @SequenceGenerator(name = "hacking_data_sequence", sequenceName = "hacking_data_sequence", allocationSize = 1)
    private long id;
    private String hackerEmail;
    private String hackerId;
    private String trackId;
    private String redirectUrl;
    private String attackingUrl;
    private String type;

}
