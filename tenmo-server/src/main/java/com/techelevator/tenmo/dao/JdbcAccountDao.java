package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public List<Account> getAllAccounts() {
        String sql = "Select * from account";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql);
        List<Account> accounts = new ArrayList<>();
        while (result.next()) {
            accounts.add(accountObjectMapper(result));
        }
        return accounts;
    }


    @Override
    public Account getBalance(int id) {
        String sql = "Select * from account where user_id= ?";
        SqlRowSet result = this.jdbcTemplate.queryForRowSet(sql, id);
        Account accountBalance = null;
        if (result.next()) {
            accountBalance = accountObjectMapper(result);
        }
        return accountBalance;
    }

    @Override
    public void updateBalance(Account account) {

    }

    @Override
    public Account getAccountById(Long id) {
        return null;
    }


    private Account accountObjectMapper(SqlRowSet result) {
        Account account = new Account();
        account.setAccountId(result.getLong("account_id"));
        account.setUserId(result.getInt("user_id"));
        account.setBalance(result.getDouble("balance"));
        return account;
    }

}
