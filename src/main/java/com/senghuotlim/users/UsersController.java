package com.senghuotlim.users;

import com.senghuotlim.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by senghuot on 3/17/17.
 */
@RestController
public class UsersController {

    protected UserRepository userRepository;

    @Autowired
    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/users/{lastanme}")
    public List<User> findByLastname(@PathVariable("lastname") String lastname) {
        List<User> users = userRepository.findByLastName(lastname);

        if (users == null || users.isEmpty())
            throw new UserNotFoundException(lastname);

        return users;
    }
}
