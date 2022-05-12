package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AccountDAO;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();
    public UserService userService;

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    private HttpEntity<User> makeUserEntity(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth((authToken));
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return entity;
    }

    private HttpEntity<Void> makeAuthEntity(){
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return entity;
    }

    public User[] getAllUsers() {

        System.out.println("DEBUG: " + authToken);

        User[] listOfUsers = null;

//        try {
            listOfUsers = restTemplate.exchange(
                    API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(),
                    User[].class
            ).getBody();

//        } catch (RestClientResponseException | ResourceAccessException e) {
//            System.out.println("ERROR");
//        }

        return listOfUsers;
    }




}
