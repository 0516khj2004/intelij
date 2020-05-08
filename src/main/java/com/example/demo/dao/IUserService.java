package com.example.demo.dao;

import com.example.demo.entity.User;

import java.util.List;

public interface IUserService {
    List<User> getUserList();
    User getUser(Integer id);
    User save(User newUser);
    User update(User modifyUser);
    User delete(Integer id);
}
