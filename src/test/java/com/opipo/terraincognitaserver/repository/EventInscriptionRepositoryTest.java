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
import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class EventInscriptionRepositoryTest {

    @Autowired
    private EventInscriptionRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private EventInscription element1 = null;
    private EventInscriptionId id1 = buildId("1");
    private EventInscription element2 = null;
    private EventInscriptionId id2 = buildId("2");

    private static EventInscriptionId buildId(String number) {
        EventInscriptionId id = new EventInscriptionId();
        id.setEvent("event " + number);
        id.setUsername("username " + number);
        return id;
    }

    @Before
    public void setUp() throws Exception {

        mongoTemplate.getDb().getCollection("eventInscription").drop();

        element1 = new EventInscription();
        element1.setId(id1);
        mongoTemplate.insert(element1);

        element2 = new EventInscription();
        element2.setId(id2);
        mongoTemplate.insert(element2);
    }

    @Test
    public void get() {
        EventInscription actual = repository.findById(id1).get();
        assertNotNull(actual);
        assertEquals(element1, actual);
    }

    @Test
    public void save() {
        EventInscription expected = new EventInscription();
        EventInscriptionId id = new EventInscriptionId();
        expected.setId(id);
        EventInscription actual = repository.save(expected);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
    }

    @Test
    public void update() {
        repository.findById(element1.getId()).get();
        element1.setId(new EventInscriptionId());
        EventInscription actual = repository.save(element1);
        assertNotNull(actual);
        assertEquals(element1.getId(), actual.getId());
    }

    @Test
    public void list() {
        Collection<EventInscription> actual = repository.findAll();
        assertEquals(2, actual.size());
        List<EventInscription> users = Arrays.asList(element1, element2);
        assertTrue(actual.stream().allMatch(p -> users.contains(p)));
    }

    @Test
    public void getByEventId() {
        String eventId = id1.getEvent();
        Collection<EventInscription> actual = repository.findByEvent(eventId);
        assertEquals(1, actual.size());
        List<EventInscription> users = Arrays.asList(element1);
        assertTrue(actual.stream().allMatch(p -> users.contains(p)));
    }

    @Test
    public void getByEventIdFake() {
        String eventId = id2.getEvent();
        Collection<EventInscription> actual = repository.findByEvent(eventId);
        assertEquals(1, actual.size());
        List<EventInscription> users = Arrays.asList(element1);
        assertTrue(actual.stream().noneMatch(p -> users.contains(p)));
    }

    @Test
    public void delete() {
        assertTrue(repository.existsById(id1));
        repository.deleteById(id1);
        assertFalse(repository.existsById(id1));
    }

    @Test
    public void deleteAll() {
        assertTrue(0L < repository.count());
        repository.deleteAll();
        assertEquals(0L, repository.count());
    }

}
