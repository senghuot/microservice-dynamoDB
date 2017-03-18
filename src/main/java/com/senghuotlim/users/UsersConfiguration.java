package com.senghuotlim.users;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by senghuot on 3/16/17.
 */
@Configurable
@ComponentScan
@EntityScan("com.senghuotlim.users")
public class UsersConfiguration {
}
