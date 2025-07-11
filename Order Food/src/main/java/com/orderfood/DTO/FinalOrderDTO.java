package com.orderfood.DTO;
import com.orderfood.model.FinalOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class FinalOrderDTO {

    public Long id;

    private String phoneNumber;

    private Date date;
    private int finalPrice;
    private String address;
    private String status;

    public FinalOrderDTO(FinalOrder finalOrder) {
        this.id = finalOrder.id;
        this.address = finalOrder.getAddress();
        this.phoneNumber = finalOrder.getPhoneNumber();
        this.date = finalOrder.getDate();
        this.finalPrice = finalOrder.getFinalPrice();
        this.status = finalOrder.getStatus();


    }

}
