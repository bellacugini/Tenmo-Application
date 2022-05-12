package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AccountDAO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

public class TransferService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    public AccountService accountService;

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth((this.authToken));
        HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(this.authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    private HttpEntity<TransferDTO> sandTransferEntity(TransferDTO transferDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth((this.authToken));
        System.out.println("Token?");
        System.out.println(this.authToken);
        HttpEntity<TransferDTO> entity = new HttpEntity<>(transferDTO, headers);
        return entity;
    }

    public void sendTransfer(TransferDTO transferDTO) {
        restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, sandTransferEntity(transferDTO), Void.class);
    }

    public Transfer[] getAllTransfers() {

        System.out.println("DEBUG: " + authToken);

        Transfer[] listOfTransfers = null;

//        try {
        listOfTransfers = restTemplate.exchange(
                API_BASE_URL + "/accounts/transfers", HttpMethod.GET, makeAuthEntity(),
                Transfer[].class
        ).getBody();

//        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.println("ERROR");
//        }

        return listOfTransfers;
    }

//    public Transfer[] getPendingTransfers() {
//
//        System.out.println("DEBUG: " + authToken);
//
//        Transfer[] pendingTransfers = null;

//        try {
//        pendingTransfers = restTemplate.exchange(
//                API_BASE_URL + "/accounts/transfers", HttpMethod.GET, makeAuthEntity(),
//                Transfer[].class
//        ).getBody();

//        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.println("ERROR");
//        }
//
//        return pendingTransfers;
//    }

//    public Transfer getTransferById(int transfer_id) {
//
//        System.out.println("DEBUG: " + authToken);
//
//        Transfer output = null;

//        try {
//        output = restTemplate.exchange(
//                API_BASE_URL + "/accounts/transfers" + get, HttpMethod.GET, makeAuthEntity(),
//                Transfer.class
//        ).getBody();

//        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.println("ERROR");
//        }
//
//        return output;
//    }
}
