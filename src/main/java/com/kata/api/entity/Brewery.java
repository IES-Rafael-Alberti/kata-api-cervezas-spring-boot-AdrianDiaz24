package com.kata.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "breweries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brewery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 255)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 100)
    private String country;

    @Column(length = 20)
    private String phone;

    @Column(length = 255)
    private String website;

    @Column(columnDefinition = "TEXT")
    private String description;
}

