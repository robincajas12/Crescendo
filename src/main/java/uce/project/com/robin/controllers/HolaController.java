package uce.project.com.robin.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, world";
    }
}
