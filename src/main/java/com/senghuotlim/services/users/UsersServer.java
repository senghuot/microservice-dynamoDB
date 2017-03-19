package com.senghuotlim.services.users;

import com.senghuotlim.users.UserRepository;
import com.senghuotlim.users.UsersConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

/**
 * UsersServer is a microservice, it will register itself to Discovery Server (Eureka).
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@Import(UsersConfiguration.class)
public class UsersServer {

    @Autowired
    protected UserRepository userRepository;

    /**
     * Run the User Server
     * @param args
     */
    public static void main(String[] args) {
        // Hooking up the properties to users-server.yml
        System.setProperty("spring.config.name", "users-server");
        SpringApplication.run(UsersServer.class, args);
    }
}
