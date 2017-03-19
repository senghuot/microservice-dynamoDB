package com.senghuotlim.services.registration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * This is a Registration Server, accepting clients such as UsersServer and WebServer.
 */
@SpringBootApplication
@EnableEurekaServer
public class RegistrationServer {

    /**
     * Run the Registration Server
     * @param args
     */
    public static void main(String[] args) {
        // Hooking up the properties to registration-server.yml
        System.setProperty("spring.config.name", "registration-server");
        SpringApplication.run(RegistrationServer.class, args);
    }
}
