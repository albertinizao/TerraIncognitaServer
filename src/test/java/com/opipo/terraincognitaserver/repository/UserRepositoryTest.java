package com.opipo.terraincognitaserver.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.opipo.terraincognitaserver.TerraIncognitaServerApplicationConfig;
import com.opipo.terraincognitaserver.dto.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {
        TerraIncognitaServerApplicationConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private User user1 = null;
    private String username1 = "Username 1";
    private User user2 = null;
    private String username2 = "Username 2";

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setName(username1);
        mongoTemplate.insert(user1);

        user2 = new User();
        user2.setName(username2);
        mongoTemplate.insert(user2);
    }

    @Test
    public void get() {
        User actual = userRepository.findById(username1).get();
        assertNotNull(actual);
        assertEquals(user1, actual.getName());
    }

    @Test
    public void save() {
        User expected = new User();
        String name = "Name";
        expected.setName(name);
        User actual = userRepository.save(expected);
        assertNotNull(actual);
        assertEquals(name, actual.getName());
    }

    @Test
    public void update() {
        userRepository.findById(user1.getName()).get();
        user1.setName("previous");
        User actual = userRepository.save(user1);
        assertNotNull(actual);
        assertEquals(user1.getName(), actual.getName());
    }

    @Test
    public void list() {
        Collection<User> actual = userRepository.findAll();
        assertEquals(2, actual.size());
        List<User> videogames = Arrays.asList(user1, user2);
        assertTrue(actual.stream().allMatch(p -> videogames.contains(p)));

    }

    @Test
    public void delete() {
        assertTrue(userRepository.existsById(username1));
        userRepository.deleteById(username1);
        assertFalse(userRepository.existsById(username1));
    }

    @Test
    public void deleteAll() {
        assertTrue(0L < userRepository.count());
        userRepository.deleteAll();
        assertEquals(0L, userRepository.count());
    }

}
