package com.orderfood.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private MealType mealType;

    private String name;
    private int price;

    @Lob
    private String image;

    private String imageName;
    private String description;
    private boolean isDeleted;

}
