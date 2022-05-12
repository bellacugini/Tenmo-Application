package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@PreAuthorize("isAuthenticated()")

@RestController
public class AccountController {
    @Autowired
private AccountDao accountDao;
    @Autowired
UserDao userDao;


    @GetMapping(path="/balance")
    public Account getBalance(Principal principal){
    int userId = userDao.findIdByUsername(principal.getName());
        // 1. use the principal to get the username
    return accountDao.getBalance(userId);


    }

}
