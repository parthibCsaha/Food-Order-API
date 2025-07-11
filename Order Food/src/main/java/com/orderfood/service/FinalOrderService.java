package com.orderfood.service;

import com.orderfood.DTO.FinalOrderDTO;
import com.orderfood.DTO.FinalOrderIdAndStatusDTO;
import com.orderfood.DTO.OrderItemDTO;
import com.orderfood.model.FinalOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FinalOrderService {

    FinalOrder save(FinalOrder finalOrder);
    Long makeFinalOrder(OrderItemDTO orderItemDTO);
    FinalOrder findOne(Long id);
    List<FinalOrderDTO> getAllActiveFinalOrders();
    List<FinalOrderDTO> getAllDeliveredFinalOrders();
    String setFinalOrderToDelivered(Long finalOrderId);
    List<FinalOrderDTO> getAllMyActiveFinalOrders(Long currentUserId);
    List<FinalOrderDTO> getAllMyDeliveredFinalOrders(Long currentUserId);
    String changeFinalOrderStatus(FinalOrderIdAndStatusDTO foIdStatus);
    String delete(Long finalOrderId);

}
