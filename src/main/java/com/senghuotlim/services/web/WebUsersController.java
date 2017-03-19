package com.senghuotlim.services.web;

import com.senghuotlim.exceptions.UserNotFoundException;
import com.senghuotlim.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Define a gateway to fetch Users from WebUserService
 */
@RestController
public class WebUsersController {

    @Autowired
    protected WebUsersService usersService;

    public WebUsersController(WebUsersService usersService) {
        this.usersService = usersService;
    }

    @RequestMapping(value = "/users", headers = "Accept=application/json")
    public @ResponseBody List<User> getAll() {
        List<User> users;

        try {
            users = usersService.getAll();
        } catch (RuntimeException ex) {
            throw new UserNotFoundException("Please insert some users into AWS DynamoDB");
        }

        return users;
    }

    @RequestMapping(value = "/users/lastname/{lastname}", headers = "Accept=application/json")
    public @ResponseBody List<User> findByLastname(@PathVariable("lastname") String lastname) {
        List<User> users;

        try {
            users = usersService.byLastname(lastname);
        } catch (RuntimeException ex) {
            throw new UserNotFoundException(lastname);
        }

        return users;
    }
}
