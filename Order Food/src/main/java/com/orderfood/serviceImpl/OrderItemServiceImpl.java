package com.orderfood.serviceImpl;

import com.orderfood.DTO.ItemFromCartDTO;
import com.orderfood.DTO.OrderItemDTO;
import com.orderfood.model.OrderItem;
import com.orderfood.repository.OrderItemRepository;
import com.orderfood.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    @Override
    public List<OrderItem> getOrderItemsByFinalOrderId(Long finalOrderId) {
        List<OrderItem> allOrderItems = orderItemRepository.findAll();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItem orderItem : allOrderItems) {
            if (orderItem.getFinalOrder().getId().equals(finalOrderId)) {
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    @Override
    public List<ItemFromCartDTO> getItemFromCartByFinalOrderId(Long finalOrderId) {
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<ItemFromCartDTO> itemFromCartDTOs = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getFinalOrder().getId().equals(finalOrderId)) {
                itemFromCartDTOs.add(new ItemFromCartDTO(orderItem));
            }
        }
        return itemFromCartDTOs;
    }
}
