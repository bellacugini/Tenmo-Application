package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
//@PreAuthorize("isAuthenticated()")

public class UserController {


    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
//    @PreAuthorize("permitAll")
    @GetMapping(path = "users")
    public List<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping(path = "/users/id/{username}")
    public int findIdByUsername(@PathVariable String username) {
        return userDao.findIdByUsername(username);
    }

    @GetMapping(path = "/users/{username}")
    public User findByUsername(@PathVariable String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }

}
