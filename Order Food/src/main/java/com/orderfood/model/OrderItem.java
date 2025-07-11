package com.orderfood.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Meal meal;

    private String mealName;
    private int mealPrice;
    private String mealDescription;

    @Lob
    private String mealImage;
    private String mealImageName;
    private String mealTypeName;

    @ManyToOne
    private FinalOrder finalOrder;

    //private Date orderDate;
    private int quantity;

}
