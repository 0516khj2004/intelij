package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;


@RestController
public class HelloWorldController {
    //ingection(주입)
    //1. annotation -> @Anotation
    //2. constructor
    //3.setter method ->
//    public void setMessageSource(MessageSource messageSource) {
//        this.messageSource = messageSource;
//    }

    @Autowired
    private MessageSource messageSource;


    @GetMapping(path = "/hello")
    public String helloWorld(){
        return "good morning";
    }


    @GetMapping(path = "/hello1")
    public String helloWorld1(
            @RequestHeader(name ="Accept-language", required = false) Locale locale){
        String msg1 = messageSource.getMessage("greeting.message", null,locale);
        String msg2 = messageSource.getMessage("greeting.message1", null,locale);
        String msg3 = messageSource.getMessage("greeting.message2", null,locale);
        return String.format("%s %s %s", msg1, msg2, msg3);
    }

}
