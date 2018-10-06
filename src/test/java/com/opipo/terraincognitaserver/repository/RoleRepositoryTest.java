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
import com.opipo.terraincognitaserver.dto.Role;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private Role role1 = null;
    private String name1 = "Username 1";
    private Role role2 = null;
    private String name2 = "Username 2";

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().getCollection("role").drop();

        role1 = new Role();
        role1.setName(name1);
        mongoTemplate.insert(role1);

        role2 = new Role();
        role2.setName(name2);
        mongoTemplate.insert(role2);
    }

    @Test
    public void get() {
        Role actual = roleRepository.findById(name1).get();
        assertNotNull(actual);
        assertEquals(role1, actual);
    }

    @Test
    public void save() {
        Role expected = new Role();
        String name = "Name";
        expected.setName(name);
        Role actual = roleRepository.save(expected);
        assertNotNull(actual);
        assertEquals(name, actual.getName());
    }

    @Test
    public void update() {
        roleRepository.findById(role1.getName()).get();
        role1.setName("previous");
        Role actual = roleRepository.save(role1);
        assertNotNull(actual);
        assertEquals(role1.getName(), actual.getName());
    }

    @Test
    public void list() {
        Collection<Role> actual = roleRepository.findAll();
        assertEquals(2, actual.size());
        List<Role> users = Arrays.asList(role1, role2);
        assertTrue(actual.stream().allMatch(p -> users.contains(p)));

    }

    @Test
    public void delete() {
        assertTrue(roleRepository.existsById(name1));
        roleRepository.deleteById(name1);
        assertFalse(roleRepository.existsById(name1));
    }

    @Test
    public void deleteAll() {
        assertTrue(0L < roleRepository.count());
        roleRepository.deleteAll();
        assertEquals(0L, roleRepository.count());
    }

}
