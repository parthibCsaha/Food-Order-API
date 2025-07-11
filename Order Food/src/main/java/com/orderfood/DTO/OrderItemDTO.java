package com.orderfood.DTO;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemDTO {

    private List<ItemFromCartDTO> itemsFromCart;
    private String address;
    private String phoneNumber;
    private int finalPrice;

}
