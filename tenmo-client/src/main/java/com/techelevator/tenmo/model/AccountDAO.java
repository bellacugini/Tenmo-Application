package com.techelevator.tenmo.model;

import java.util.List;

public interface AccountDAO {

    List<Account> getAllAccounts();
    Account getBalance(int id);
}
