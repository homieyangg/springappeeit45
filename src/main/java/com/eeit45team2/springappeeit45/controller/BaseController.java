package com.eeit45team2.springappeeit45.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BaseController {

    @GetMapping("/hello")
    public String home(
            @RequestParam(value = "name", required = false) String visitor,
            @RequestParam(value = "score", required = false, defaultValue = "-1") Integer score,
            Model model) {
        String message = visitor != null ? visitor + ", 您好" : ", 訪客您好";
        model.addAttribute("helloMessage", message);
        model.addAttribute("score", score);
        return "greeting";

    }
}
