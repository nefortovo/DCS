package ru.liftcom.website.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liftcom.bot.component.BotComponent;
import ru.liftcom.database.service.OrderService;

@Service
public class MainService {

    private final BotComponent botComponent;
    private final OrderService orderService;

    @Autowired
    public MainService(BotComponent botComponent,
                       OrderService orderService){
        this.botComponent = botComponent;
        this.orderService = orderService;
    }

    public void addOrder(String phone, String name){
        orderService.addOrder(phone, name);
        newOrder();
    }

    @SneakyThrows
    public void newOrder(){
        botComponent.sendToAll("Поступил новый зказ");
    }
}
