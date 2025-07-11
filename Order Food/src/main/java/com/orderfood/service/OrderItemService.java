package com.orderfood.service;

import com.orderfood.DTO.ItemFromCartDTO;
import com.orderfood.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderItemService {

    OrderItem save(OrderItem orderItem);
    List<OrderItem> findAll();
    List<OrderItem> getOrderItemsByFinalOrderId(Long finalOrderId);
    List<ItemFromCartDTO> getItemFromCartByFinalOrderId(Long finalOrderId);

}
