package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AccountDAO;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class AccountService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    public AccountService accountService;

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<AccountDAO> makeAccountEntity(AccountDAO accountDAO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth((this.authToken));
        HttpEntity<AccountDAO> entity = new HttpEntity<>(accountDAO, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    public double getUserBalance(long user_id) {
        Account account = null;
//        try {
            account = restTemplate.exchange(API_BASE_URL + "balance", HttpMethod.GET, makeAuthEntity(), Account.class).getBody();
//        } catch (RestClientResponseException e) {
//            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
//        } catch (ResourceAccessException e) {
//            BasicLogger.log(e.getMessage());
//        }
        return account.getBalance();
    }
}
