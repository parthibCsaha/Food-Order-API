package com.orderfood.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FinalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    private User users;

    @JsonIgnore
    @OneToMany(mappedBy="finalOrder" ,fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private List<OrderItem> orders = new ArrayList<>();

    private Date date;
    private int finalPrice;
    private String address;
    private String phoneNumber;
    private String status;

}
