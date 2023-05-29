package ru.liftcom.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liftcom.database.entity.CustomOrder;
import ru.liftcom.database.repository.OrderRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public synchronized CustomOrder getOrder(){
        List<CustomOrder> customOrders = orderRepository.findByStatusOrderByOrderDate(0);
        if(customOrders.size() == 0){
            return null;
        }
        CustomOrder customOrder = customOrders.get(0);
        customOrder.setStatus(1);
        orderRepository.save(customOrder);

        return customOrder;
    }

    public int amount(){
        return orderRepository.findAllByStatus(0).size();
    }

    public boolean addOrder(String phone, String name){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        CustomOrder customOrder = orderRepository.findByPhoneAndStatus(phone, 0);

        if(customOrder != null){
            customOrder.setOrderDate(timestamp);
            customOrder.setStatus(0);
            orderRepository.save(customOrder);
            return true;
        }

        customOrder = new CustomOrder();
        customOrder.setPhone(phone);
        customOrder.setName(name);
        customOrder.setOrderDate(timestamp);
        customOrder.setStatus(0);

        orderRepository.save(customOrder);

        return true;
    }
}
