package com.hanait.noninvasiveglucosespring.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class userCaregiver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "caregiverId")
    private Caregiver caregiver;

}
