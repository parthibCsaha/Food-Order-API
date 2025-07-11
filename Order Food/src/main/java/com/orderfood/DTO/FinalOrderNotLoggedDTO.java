package com.orderfood.DTO;

import com.orderfood.model.FinalOrder;
import lombok.Data;

import java.util.Date;

@Data
public class FinalOrderNotLoggedDTO {

    public Long id;

    private String phoneNumber;

    private Date date;
    private int finalPrice;
    private String address;
    private String status;

    public FinalOrderNotLoggedDTO(FinalOrder finalOrder) {
        this.id = finalOrder.getId();
        this.phoneNumber = finalOrder.getPhoneNumber();
        this.finalPrice = finalOrder.getFinalPrice();
        this.date = finalOrder.getDate();
        this.address = finalOrder.getAddress();
        this.status = finalOrder.getStatus();

    }

}
