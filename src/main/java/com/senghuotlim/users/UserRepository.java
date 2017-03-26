package com.senghuotlim.users;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for User data implemented using Spring Data.
 */
@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
    List<User> findByLastname(String lastname);

    List<User> findAll();
}
