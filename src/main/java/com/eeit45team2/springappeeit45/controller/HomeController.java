package com.eeit45team2.springappeeit45.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    public HomeController(){
    }

    @GetMapping("/")
    public @ResponseBody String home(){
        return "Hello_World";
    }
}
