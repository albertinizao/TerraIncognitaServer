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
import com.opipo.terraincognitaserver.dto.Payment;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Payment payment1 = null;
    private Long id1 = 1L;
    private Payment payment2 = null;
    private Long id2 = 2L;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().getCollection("payment").drop();

        payment1 = new Payment();
        payment1.setId(id1);
        mongoTemplate.insert(payment1);

        payment2 = new Payment();
        payment2.setId(id2);
        mongoTemplate.insert(payment2);
    }

    @Test
    public void get() {
        Payment actual = repository.findById(id1).get();
        assertNotNull(actual);
        assertEquals(payment1, actual);
    }

    @Test
    public void save() {
        Payment expected = new Payment();
        Long id = 6L;
        expected.setId(id);
        Payment actual = repository.save(expected);
        assertNotNull(actual);
        assertEquals(id, actual.getId());
    }

    @Test
    public void update() {
        repository.findById(payment1.getId()).get();
        payment1.setId(64L);
        Payment actual = repository.save(payment1);
        assertNotNull(actual);
        assertEquals(payment1.getId(), actual.getId());
    }

    @Test
    public void list() {
        Collection<Payment> actual = repository.findAll();
        assertEquals(2, actual.size());
        List<Payment> users = Arrays.asList(payment1, payment2);
        assertTrue(actual.stream().allMatch(p -> users.contains(p)));

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
