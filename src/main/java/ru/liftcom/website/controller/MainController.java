package ru.liftcom.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.liftcom.database.entity.CustomOrder;
import ru.liftcom.website.service.MainService;

@Controller
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @RequestMapping(value ={"/", "/home"}, method = RequestMethod.GET)
    private String mainPage(){
        return("main");
    }

    @PostMapping("/make_order")
    private String makeOrder(CustomOrder order){
        if(order.getPhone() != null && order.getName() != null)
            mainService.addOrder(order.getPhone(), order.getName());
        return("redirect:/home");
    }


}
