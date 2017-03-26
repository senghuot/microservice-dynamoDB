package com.senghuotlim.users;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import com.senghuotlim.services.users.UsersServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by senghuot on 3/21/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = UsersServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
// TODO: replace endpoint, accesskey, secretkey with actual real values
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://dynamodb.us-west-2.amazonaws.com",
        "amazon.aws.accesskey=AKIAJJ3MBP4V62L73TIQ",
        "amazon.aws.secretkey=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",
        "eureka.client.enabled=false"
})
public class UserRepositoryIntegrationTest {

    private Logger logger;

    private List<User> existingUsers;

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate restTemplate;

    /**
     * Setup User table on AWS DynamoDB for testing
     *
     * @throws Exception if the User table is already existed
     */
    @Before
    //@Ignore // Remove after you've hooked up DynamoDB Endpoint, AccessKey, SecretKey
    public void setup() throws Exception {
        logger = Logger.getLogger(UsersController.class.getName());

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(UserTest.class);

        tableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));//

        try {
            amazonDynamoDB.createTable(tableRequest);
        } catch (ResourceInUseException e) {
            logger.info("Table already existed. " + e);
        }

        existingUsers = userRepository.findAll();
        dynamoDBMapper.batchDelete(existingUsers);
    }

    /**
     * Reload the data back to User table after testing
     */
    @After
    //@Ignore // Remove after you've hooked up DynamoDB Endpoint, AccessKey, SecretKey
    public void reinsertExistingUsers() {
        if (existingUsers == null || existingUsers.isEmpty())
            return;

        userRepository.save(existingUsers);
    }

    /**
     * Positive test for find users by lastname
     */
    @Test
    public void positivefindByLastname() {
        final String id = "TEST_ID";
        final String firstname = "TEST_FIRSTNAME";
        final String lastname = "TEST_LASTNAME";
        final User user = new User(id, firstname, lastname);

        userRepository.save(user);

        List<User> actualUsers = userRepository.findByLastname(lastname);

        Assert.assertTrue("User gets uploaded to the Database", actualUsers.size() == 1);
        Assert.assertTrue("The same user is returned", actualUsers.get(0).getId().equals(id));

        userRepository.delete(user);
    }

    /**
     * Negative test for find users by lastname
     */
    @Test
    public void negativefindByLastname() {
        final String lastname = "TEST_LASTNAME";

        List<User> actualUsers = userRepository.findByLastname(lastname);

        Assert.assertTrue("No user should be found", actualUsers.isEmpty());
    }

    /**
     * General test for find all users
     */
    @Test
    public void getAllUsers() {
        final String id = "TEST_ID";
        final String firstname = "TEST_FIRSTNAME";
        final String lastname = "TEST_LASTNAME";

        final String id2 = "TEST_ID_2";
        final String firstname2 = "TEST_FIRSTNAME_2";
        final String lastname2 = "TEST_LASTNAME_2";

        User user = new User(id, firstname, lastname);
        User user2 = new User(id2, firstname2, lastname2);

        List<User> users = new ArrayList<User>() {{
                add(user);
                add(user2);
        }};

        userRepository.save(users);

        List<User> actualUsers = userRepository.findAll();

        Assert.assertTrue("Same amount of users should be found", users.size() == actualUsers.size());
        Assert.assertTrue("Same users should be found", actualUsers.contains(user));
        Assert.assertTrue("Same users should be found", actualUsers.contains(user2));

        userRepository.delete(users);
    }
}
