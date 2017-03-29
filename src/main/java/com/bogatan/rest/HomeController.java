package com.bogatan.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by mbutan on 3/29/2017.
 */
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
}
