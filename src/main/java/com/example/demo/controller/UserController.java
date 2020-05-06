package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDaoService userDaoService;

    @GetMapping("/users")
    public List<User> getUserList(){
        List<User> list = userDaoService.getUserList();
        //로고출력
//        for (User user: list) {
////            System.out.println(user);
//            // log. error, warn, info, debug
//            log.info(user.toString());
//        }
        return  list;
    }

    // /users/1
    @GetMapping("/users/{id}")
    public  User getUser(@PathVariable Integer id){ // = @PathVariable(value = "id") int user_id
        User user = userDaoService.getUser(id);
        //예외처리(exception) - UserNotFoundException
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        return  user;
    }

    @GetMapping("/admin/users/{id}")
    public MappingJacksonValue getUserByAdmin(@PathVariable Integer id){
        //포함을 하고 싶은 객체를 filter에 선언한다
        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","localDate","ssn");

        User user = userDaoService.getUser(id);
        //예외처리(exception) - UserNotFoundException
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }

        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return  mapping;

    }
}
