package ru.liftcom.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OnePageController {
    @GetMapping(value = "/delivery")
    private String deliveryPage(){
        return("delivery");
    }

    @GetMapping(value = "/serving")
    private String servingPage(){
        return("serving");
    }

    @GetMapping(value = "/repair")
    private String repairPage(){
        return("repair");
    }

    @GetMapping(value = "/building")
    private String buildingPage(){
        return("building");
    }
}
