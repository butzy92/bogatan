package com.bogatan.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;
import java.net.URISyntaxException;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String getHome(){
        return "index.html";
    }


    @RequestMapping("/home")
    public String getHomeS(){
        return "index.html";
    }


    @RequestMapping("/towers")
    @ResponseBody
    @CrossOrigin("*")
    public String getTowers(){
        return "towerssss";
    }


    @RequestMapping("/login")
    public ResponseEntity login() throws URISyntaxException{
        return ResponseEntity.status(HttpStatus.FOUND).location(new URI("/connect/facebook")).build();
    }
}
