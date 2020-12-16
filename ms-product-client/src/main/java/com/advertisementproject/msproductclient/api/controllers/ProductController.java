package com.advertisementproject.msproductclient.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ProductController {

    @GetMapping("/all")
    public String helloWorld() {
        return "Hello Microservies";
    }

    @GetMapping("/eli")
    public String eliProduct() {
        return "Man, 30, Söt, Har en hund, Fru, Villa, Volvo, Granne med fisk. IDENTICAL?:)";
    }

    @GetMapping("/github")
    public String githubProduct() {
        return "GitHub, Skit, Jobbigt, Please, Funka, Be, Signed, Please";
    }

}
