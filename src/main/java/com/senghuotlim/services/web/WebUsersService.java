package com.senghuotlim.services.web;

import com.senghuotlim.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Hide the access to the User Microservices inside this local service
 */
@Service
public class WebUsersService {
    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    public WebUsersService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
                : "http://" + serviceUrl;
    }

    public List<User> getAll() {
        return Arrays.asList(restTemplate.getForObject(
                serviceUrl + "/users",
                User[].class)
        );
    }

    public List<User> byLastname(String lastname) {
        return Arrays.asList(restTemplate.getForObject(
                serviceUrl +  "/users/lastname/" + lastname,
                User[].class,
                lastname
        ));
    }
}
