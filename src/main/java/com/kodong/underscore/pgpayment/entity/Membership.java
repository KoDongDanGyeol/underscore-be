package com.kodong.underscore.pgpayment.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;
    private String name;
    private int duration;
    private int amount;
}
