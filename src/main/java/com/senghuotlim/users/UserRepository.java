package com.senghuotlim.users;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by senghuot on 3/16/17.
 */
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByLastName(String lastName);
}
