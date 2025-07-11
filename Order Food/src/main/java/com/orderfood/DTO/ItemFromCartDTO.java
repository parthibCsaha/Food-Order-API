package com.orderfood.DTO;

import com.orderfood.model.OrderItem;
import lombok.Data;

@Data
public class ItemFromCartDTO {

    private Long mealId;
    private String mealName;
    private String mealTypeName;
    private String mealDescription;
    private String mealImage;
    private String mealImageName;
    private int mealPrice;

    private int quantity;

    public ItemFromCartDTO(OrderItem orderItem) {
        this.mealId = orderItem.getMeal().getId();
        this.mealName = orderItem.getMealName();
        this.mealTypeName = orderItem.getMealTypeName();
        this.mealDescription = orderItem.getMealDescription();
        this.mealImage = orderItem.getMeal().getImage();
        this.mealImageName = orderItem.getMealImageName();
        this.mealPrice = orderItem.getMealPrice();

        this.quantity = orderItem.getQuantity();
    }

}
