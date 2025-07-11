package com.orderfood.serviceImpl;

import com.orderfood.DTO.FinalOrderDTO;
import com.orderfood.DTO.FinalOrderIdAndStatusDTO;
import com.orderfood.DTO.ItemFromCartDTO;
import com.orderfood.DTO.OrderItemDTO;
import com.orderfood.model.FinalOrder;
import com.orderfood.model.OrderItem;
import com.orderfood.model.User;
import com.orderfood.repository.FinalOrderRepository;
import com.orderfood.repository.OrderItemRepository;
import com.orderfood.service.FinalOrderService;
import com.orderfood.service.MealService;
import com.orderfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FinalOrderServiceImpl implements FinalOrderService {

    @Autowired
    FinalOrderRepository finalOrderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    UserService userService;

    @Autowired
    MealService mealService;

    @Override
    public FinalOrder save(FinalOrder finalOrder) {
        return finalOrderRepository.save(finalOrder);
    }

    @Override
    public Long makeFinalOrder(OrderItemDTO orderItemDTO) {
        Long finalOrderId = 0L;
        try {
            FinalOrder finalOrder = new FinalOrder();
            finalOrder.setDate(new Date());
            finalOrder.setStatus("ORDERED");
            finalOrder.setFinalPrice(orderItemDTO.getFinalPrice());
            if (userService.getCurrentUser() != null) {
                User loggedUser = userService.getCurrentUser();
                finalOrder.setAddress(loggedUser.getAddress());
                finalOrder.setPhoneNumber(loggedUser.getPhoneNumber());
                finalOrder.setUsers(loggedUser);
            }
            else {
                finalOrder.setAddress(orderItemDTO.getAddress());
                finalOrder.setPhoneNumber(orderItemDTO.getPhoneNumber());
            }
            FinalOrder savedFinalOrder = save(finalOrder);
            finalOrderId = savedFinalOrder.getId();
            for (ItemFromCartDTO item : orderItemDTO.getItemsFromCart()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setMeal(mealService.findOne(item.getMealId()));
                orderItem.setMealDescription(item.getMealDescription());
                orderItem.setMealImageName(item.getMealImageName());
                orderItem.setMealName(item.getMealName());
                orderItem.setMealPrice(item.getMealPrice());
                orderItem.setMealTypeName(item.getMealTypeName());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setFinalOrder(savedFinalOrder);
                orderItemRepository.save(orderItem);
            }
        }
        catch (Exception e) {
            finalOrderId = 0L;
        }
        return finalOrderId;
    }

    @Override
    public FinalOrder findOne(Long id) {
        Optional<FinalOrder> finalOrder = finalOrderRepository.findById(id);
        return finalOrder.orElse(null);
    }

    @Override
    public List<FinalOrderDTO> getAllActiveFinalOrders() {
        List<FinalOrder> allFinalOrders = new ArrayList<>();
        List<FinalOrder> allFinalOrdersWithStatusOrdered = new ArrayList<>();
        List<FinalOrderDTO> allFinalOrdersWithStatusOrderedDTO = new ArrayList<>();
        FinalOrderDTO finalOrderDTO = new FinalOrderDTO();
        allFinalOrders = finalOrderRepository.findAll();
        for (FinalOrder finalOrder : allFinalOrders) {
            if (finalOrder.getStatus().equals("ORDERED") || finalOrder.getStatus().equals("PREPARATION")) {
                allFinalOrdersWithStatusOrdered.add(finalOrder);
            }
        }
        for (FinalOrder finalOrder : allFinalOrdersWithStatusOrdered) {
            finalOrderDTO = new FinalOrderDTO(finalOrder);
            allFinalOrdersWithStatusOrderedDTO.add(finalOrderDTO);
        }
        return allFinalOrdersWithStatusOrderedDTO;
    }

    @Override
    public List<FinalOrderDTO> getAllDeliveredFinalOrders() {
        List<FinalOrder> allFinalOrders;
        List<FinalOrder> allFinalOrdersWithStatusDelivered = new ArrayList<FinalOrder>();
        List<FinalOrderDTO> allFinalOrdersWithStatusDeliveredDTO = new ArrayList<FinalOrderDTO>();

        FinalOrderDTO finalOrderDTO = new FinalOrderDTO();

        allFinalOrders = finalOrderRepository.findAll();

        for(FinalOrder fo: allFinalOrders) {
            if(fo.getStatus().equals("DELIVERY")) {
                allFinalOrdersWithStatusDelivered.add(fo);
            }
        }
        for(FinalOrder foStatusOrdered: allFinalOrdersWithStatusDelivered) {
            finalOrderDTO = new FinalOrderDTO(foStatusOrdered);
            allFinalOrdersWithStatusDeliveredDTO.add(finalOrderDTO);
        }
        return allFinalOrdersWithStatusDeliveredDTO;
    }

    @Override
    public String setFinalOrderToDelivered(Long finalOrderId) {
        String responseToClient = "fail";
        try {
            FinalOrder finalOrder = findOne(finalOrderId);
            finalOrder.setStatus("DELIVERED");
            finalOrderRepository.save(finalOrder);
            responseToClient = "success";
        }
        catch (Exception e) {
            return  responseToClient;
        }
        return responseToClient;
    }

    @Override
    public List<FinalOrderDTO> getAllMyActiveFinalOrders(Long currentUserId) {
        List<FinalOrder> allFinalOrders;
        List<FinalOrder> allFinalOrdersWithStatusOrdered = new ArrayList<>();
        List<FinalOrderDTO> allFinalOrdersWithStatusOrderedDTO = new ArrayList<>();
        FinalOrderDTO finalOrderDTO = new FinalOrderDTO();
        allFinalOrders = finalOrderRepository.findAll();
        for (FinalOrder finalOrder : allFinalOrders) {
            try {
                if (finalOrder.getUsers().getId() != null && (finalOrder.getStatus().equals("ORDERED") || finalOrder.getStatus().equals("PREPARATION"))) {
                    if (finalOrder.getUsers().getId().equals(currentUserId)) {
                        allFinalOrdersWithStatusOrdered.add(finalOrder);
                    }
                }
            }
            catch (Exception e) {
            }
        }
        for (FinalOrder finalOrder : allFinalOrdersWithStatusOrdered) {
            finalOrderDTO = new FinalOrderDTO(finalOrder);
            allFinalOrdersWithStatusOrderedDTO.add(finalOrderDTO);
        }
        return allFinalOrdersWithStatusOrderedDTO;
    }

    @Override
    public List<FinalOrderDTO> getAllMyDeliveredFinalOrders(Long currentUserId) {
        List<FinalOrder> allFinalOrders;
        List<FinalOrder> allFinalOrdersWithStatusDelivered = new ArrayList<>();
        List<FinalOrderDTO> allFinalOrdersWithStatusDeliveredDTO = new ArrayList<>();

        FinalOrderDTO finalOrderDTO = new FinalOrderDTO();

        allFinalOrders = finalOrderRepository.findAll();

        for(FinalOrder fo: allFinalOrders) {
            try {
                if(fo.getStatus().equals("DELIVERY") && fo.getUsers().getId() != null) {
                    if(fo.getUsers().getId().equals(currentUserId)){
                        allFinalOrdersWithStatusDelivered.add(fo);
                    }

                }
            } catch (Exception e) {
            }

        }
        for(FinalOrder foStatusOrdered: allFinalOrdersWithStatusDelivered) {
            finalOrderDTO = new FinalOrderDTO(foStatusOrdered);
            allFinalOrdersWithStatusDeliveredDTO.add(finalOrderDTO);
        }
        return allFinalOrdersWithStatusDeliveredDTO;
    }

    @Override
    public String changeFinalOrderStatus(FinalOrderIdAndStatusDTO foIdStatus) {
        String responseToClient = "fail";
        try {
            FinalOrder finalOrder = findOne(foIdStatus.getActiveOrderId());
            finalOrder.setStatus(foIdStatus.getStatus());
            finalOrderRepository.save(finalOrder);
            responseToClient = "success";
        } catch (Exception e) {
            return responseToClient;
        }
        return responseToClient;
    }

    @Override
    public String delete(Long finalOrderId) {
        try {
            FinalOrder finalOrder = finalOrderRepository.findById(finalOrderId).get();
            List<OrderItem> allOrderItems = new ArrayList<>();
            allOrderItems = orderItemRepository.findAll();
            for(OrderItem oi: allOrderItems) {
                if(Objects.equals(oi.getFinalOrder().getId(), finalOrderId)) {
                    orderItemRepository.delete(oi);
                }
            }
            finalOrderRepository.delete(finalOrder);
            return "success";
        }
        catch (Exception e) {
            return "fail";
        }
    }
}
