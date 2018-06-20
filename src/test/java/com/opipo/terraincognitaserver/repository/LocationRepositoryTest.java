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
import com.opipo.terraincognitaserver.dto.Location;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Location location1 = null;
    private String name1 = "Location 1";
    private Location location2 = null;
    private String name2 = "Location 2";

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().getCollection("location").drop();

        location1 = new Location();
        location1.setName(name1);
        mongoTemplate.insert(location1);

        location2 = new Location();
        location2.setName(name2);
        mongoTemplate.insert(location2);
    }

    @Test
    public void get() {
        Location actual = repository.findById(name1).get();
        assertNotNull(actual);
        assertEquals(location1, actual);
    }

    @Test
    public void save() {
        Location expected = new Location();
        String name = "Name";
        expected.setName(name);
        Location actual = repository.save(expected);
        assertNotNull(actual);
        assertEquals(name, actual.getName());
    }

    @Test
    public void update() {
        repository.findById(location1.getName()).get();
        location1.setName("previous");
        Location actual = repository.save(location1);
        assertNotNull(actual);
        assertEquals(location1.getName(), actual.getName());
    }

    @Test
    public void list() {
        Collection<Location> actual = repository.findAll();
        assertEquals(2, actual.size());
        List<Location> users = Arrays.asList(location1, location2);
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
