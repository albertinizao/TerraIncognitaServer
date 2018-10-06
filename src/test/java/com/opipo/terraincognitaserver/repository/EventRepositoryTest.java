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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.opipo.terraincognitaserver.TerraIncognitaServerApplicationConfig;
import com.opipo.terraincognitaserver.dto.Event;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class EventRepositoryTest {

    @Autowired
    private EventRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Event element1 = null;
    private String name1 = "Event 1";
    private Event element2 = null;
    private String name2 = "Event 2";

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().getCollection("event").drop();

        element1 = new Event();
        element1.setName(name1);
        mongoTemplate.insert(element1);

        element2 = new Event();
        element2.setName(name2);
        mongoTemplate.insert(element2);
    }

    @Test
    public void get() {
        Event actual = repository.findById(name1).get();
        assertNotNull(actual);
        assertEquals(element1, actual);
    }

    @Test
    public void save() {
        Event expected = new Event();
        String name = "Name";
        expected.setName(name);
        Event actual = repository.save(expected);
        assertNotNull(actual);
        assertEquals(name, actual.getName());
    }

    @Test
    public void update() {
        repository.findById(element1.getName()).get();
        element1.setName("previous");
        Event actual = repository.save(element1);
        assertNotNull(actual);
        assertEquals(element1.getName(), actual.getName());
    }

    @Test
    public void list() {
        Collection<Event> actual = repository.findAll();
        assertEquals(2, actual.size());
        List<Event> users = Arrays.asList(element1, element2);
        assertTrue(actual.stream().allMatch(p -> users.contains(p)));

    }

    @Test
    public void delete() {
        assertTrue(repository.existsById(name1));
        repository.deleteById(name1);
        assertFalse(repository.existsById(name1));
    }

    @Test
    public void deleteAll() {
        assertTrue(0L < repository.count());
        repository.deleteAll();
        assertEquals(0L, repository.count());
    }

}
