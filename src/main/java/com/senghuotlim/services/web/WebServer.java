package com.senghuotlim.services.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Acts as a Users Microservice facing client request. Instead of exposing the User Instances to the internet,
 * we use Webserver as a gateway to connect to Users Instances via Eureka Discovery Server. The traffic will
 * get spread out via a Ribbon load balancer.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(useDefaultFilters = false)
public class WebServer {

    /**
     * Run the WebServer
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("spring.config.name", "web-server");
        SpringApplication.run(WebServer.class, args);
    }

    /**
     * A customized RestTemplate that has the ribbon load balancer build in.
     *
     * @return
     */
    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * The WebService encapsulates the interaction with the Users clusters.
     *
     * @return A new service instance.
     */
    @Bean
    public WebUsersService usersService() {
        return new WebUsersService("http://users-service");
    }

    /**
     * Defines the controller to accept the traffic and uses WebUsersService to interact
     * with the Users clusters.
     *
     * @return
     */
    @Bean
    public WebUsersController usersController() {return new WebUsersController((usersService()));}
}
