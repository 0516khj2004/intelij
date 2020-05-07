package com.example.demo.controller;

import com.example.demo.dao.UserDaoService;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
//
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
//import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Slf4j
//@RequestMapping("/user")
public class UserController {

    @Autowired
    UserDaoService userDaoService;

    @GetMapping("/users")
    public MappingJacksonValue getUserList(){
        List<User> list = userDaoService.getUserList();
        //로고출력
//        for (User user: list) {
////            System.out.println(user);
//            // log. error, warn, info, debug
//            log.info(user.toString());
//        }
        List<EntityModel<User>> result = new ArrayList<>();
        list.forEach(user -> {
            EntityModel<User> model = new EntityModel<>(user);
            WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUser(user.getId()));
            model.add(linkTo.withRel("users-detail"));
            result.add(model);
        });

        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","localDate","ssn");

        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(result);
        mapping.setFilters(provider);

        return  mapping;
    }

    // /users/1
    @GetMapping("/users/{id}")
    public  MappingJacksonValue getUser(@PathVariable Integer id){ // = @PathVariable(value = "id") int user_id
        User user = userDaoService.getUser(id);
        //예외처리(exception) - UserNotFoundException
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","localDate");

        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return  mapping;
    }

    @GetMapping("/admin/users/{id}")
    public MappingJacksonValue getUserByAdmin(@PathVariable Integer id){


        User user = userDaoService.getUser(id);
        //예외처리(exception) - UserNotFoundException
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        //포함을 하고 싶은 객체를 filter에 선언한다
        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","localDate","ssn");

        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(provider);

        return  mapping;

    }

    @GetMapping("/hateoas/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id){
        User user = userDaoService.getUser(id);
        //예외처리(exception) - UserNotFoundException
        if(user == null) {
            throw new UserNotFoundException("id-" + id);
        }
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getUserList());
        model.add(linkTo.withRel("all-users"));

        SimpleBeanPropertyFilter filter =SimpleBeanPropertyFilter.filterOutAllExcept("id", "name","localDate","ssn");

        FilterProvider provider = new SimpleFilterProvider().addFilter("UserInfo", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(model);
        mapping.setFilters(provider);


        return mapping;
    }
}
