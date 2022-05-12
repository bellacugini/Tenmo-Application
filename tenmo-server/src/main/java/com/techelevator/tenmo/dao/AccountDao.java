package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {

    List<Account> getAllAccounts();
    Account getBalance(int id);

    void updateBalance(Account account);

    Account getAccountById(Long id);




}
