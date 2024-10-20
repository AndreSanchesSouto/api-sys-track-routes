package br.com.api_str_innovation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("helloworld")
public class HeloWorldController {

    @GetMapping
    public String helloHorld() {
        return "hello world";
    }

}
