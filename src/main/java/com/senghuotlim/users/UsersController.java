package com.senghuotlim.users;

import com.senghuotlim.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * A RESTFul controller to access User information stores on AWS DynamoDB
 */
@RestController
public class UsersController {

    protected Logger logger = Logger.getLogger(UsersController.class.getName());

    protected UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/users/lastname/{lastname}")
    public List<User> byLastname(@PathVariable("lastname") String lastname) {
        logger.info("Calling to byLastname() " + lastname);
        List<User> users = userRepository.findByLastname(lastname);

        if (users == null || users.isEmpty())
            throw new UserNotFoundException(lastname);

        return users;
    }

    @RequestMapping(value = "/users")
    public List<User> getAll() {
        List<User> allUsers = userRepository.findAll();
        logger.info("information dump: " + allUsers.size());

        return allUsers;
    }
}