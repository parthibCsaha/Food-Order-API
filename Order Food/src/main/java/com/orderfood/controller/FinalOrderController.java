package com.orderfood.controller;

import com.orderfood.DTO.FinalOrderDTO;
import com.orderfood.DTO.FinalOrderIdAndStatusDTO;
import com.orderfood.DTO.ItemFromCartDTO;
import com.orderfood.DTO.OrderItemDTO;
import com.orderfood.model.FinalOrder;
import com.orderfood.model.User;
import com.orderfood.service.FinalOrderService;
import com.orderfood.service.OrderItemService;
import com.orderfood.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/findOrder")
public class FinalOrderController {

    @Autowired
    private FinalOrderService finalOrderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @RequestMapping(value ="/createFinalOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> saveFinalOrderUser(@RequestBody OrderItemDTO orderItemDTO){
        Long responseToClient = finalOrderService.makeFinalOrder(orderItemDTO);
        return new ResponseEntity<>(responseToClient, HttpStatus.OK);
    }

    @RequestMapping(value = "/getFinalOrderById/{id}", method = RequestMethod.GET)
    public ResponseEntity<FinalOrder> getFinalOrderById(@PathVariable Long id){
        FinalOrder finalOrder;
        try {
            finalOrder = finalOrderService.findOne(id);
        }
        catch (Exception e){
            finalOrder = new FinalOrder();
        }
        return new ResponseEntity<>(finalOrder, HttpStatus.OK);
    }

    @RequestMapping(value ="/getAllDeliveredFinalOrders", method = RequestMethod.GET)
    public ResponseEntity<List<FinalOrderDTO>> getAllDeliveredFinalOrders(){
        List<FinalOrderDTO> allDeliveredFinalOrders;
        allDeliveredFinalOrders = finalOrderService.getAllDeliveredFinalOrders();
        return new ResponseEntity<>(allDeliveredFinalOrders, HttpStatus.OK);
    }

    @RequestMapping(value ="/getAllActiveFinalOrders", method = RequestMethod.GET)
    public ResponseEntity<List<FinalOrderDTO>> getAllActiveFinalOrders(){
        List<FinalOrderDTO> allActiveFinalOrders;
        allActiveFinalOrders = finalOrderService.getAllActiveFinalOrders();
        return new ResponseEntity<>(allActiveFinalOrders, HttpStatus.OK);
    }

    @RequestMapping(value ="/getAllMyActiveFinalOrders", method = RequestMethod.GET)
    public ResponseEntity<List<FinalOrderDTO>> getAllMyActiveFinalOrders(){
        List<FinalOrderDTO> allMyActiveFinalOrders = new ArrayList<>();
        User currentUser;
        try {
            currentUser = userService.getCurrentUser();
        }
        catch (Exception e) {
            return new ResponseEntity<>(allMyActiveFinalOrders, HttpStatus.NOT_FOUND);
        }
        allMyActiveFinalOrders = finalOrderService.getAllMyActiveFinalOrders(currentUser.getId());
        return new ResponseEntity<>(allMyActiveFinalOrders, HttpStatus.OK);
    }

    @RequestMapping(value ="/getAllMyDeliveredFinalOrders", method = RequestMethod.GET)
    public ResponseEntity<List<FinalOrderDTO>> getAllMyDeliveredFinalOrders(){
        List<FinalOrderDTO> allMyDeliveredFinalOrders = new ArrayList<FinalOrderDTO>();
        User currentUser;
        try {
            currentUser = userService.getCurrentUser();
        } catch (Exception e) {
            return new ResponseEntity<>(allMyDeliveredFinalOrders, HttpStatus.NOT_FOUND);
        }

        allMyDeliveredFinalOrders = finalOrderService.getAllMyDeliveredFinalOrders(currentUser.getId());
        return new ResponseEntity<>(allMyDeliveredFinalOrders, HttpStatus.OK);
    }

    @RequestMapping(value = "/getOrderItemsByFinalOrderId/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ItemFromCartDTO>> getOrderItemsByFinalOrderId(@PathVariable Long id){
        List<ItemFromCartDTO> itemsFromCartByFinalOrderId;
        itemsFromCartByFinalOrderId = orderItemService.getItemFromCartByFinalOrderId(id);
        return new ResponseEntity<>(itemsFromCartByFinalOrderId, HttpStatus.OK);
    }

    @RequestMapping(value = "/setFinalOrderToDelivered/{finalOrderId}", method = RequestMethod.PUT)
    public ResponseEntity<String> setFinalOrderToDelivered(@PathVariable Long finalOrderId){
        String response = finalOrderService.setFinalOrderToDelivered(finalOrderId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/changeStatus", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editStatus(@RequestBody FinalOrderIdAndStatusDTO foIdStatus){
        String response = finalOrderService.changeFinalOrderStatus(foIdStatus);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFinalOrder/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        String responseToClient = finalOrderService.delete(id);;
        return new ResponseEntity<>(responseToClient, HttpStatus.OK);
    }

}
