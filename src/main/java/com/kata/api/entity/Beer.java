package com.kata.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "beers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "abv")
    private Double abv;

    @Column(name = "ibu")
    private Double ibu;

    @ManyToOne
    @JoinColumn(name = "brewery_id")
    private Brewery brewery;

    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}

