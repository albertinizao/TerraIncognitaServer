package com.opipo.terraincognitaserver.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.opipo.terraincognitaserver.TerraIncognitaServerApplicationConfig;
import com.opipo.terraincognitaserver.dto.SequenceId;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TerraIncognitaServerApplicationConfig.class)
@TestPropertySource(locations = "classpath:application.properties")
public class SequenceRepositoryTest {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private String key1 = "key1";
    private Long seqVal1 = 5L;

    private String key2 = "key2";
    private Long seqVal2 = 80L;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.getDb().getCollection("sequence").drop();

        SequenceId seq1 = new SequenceId();
        seq1.setId(key1);
        seq1.setSeq(seqVal1);
        mongoTemplate.insert(seq1);

        SequenceId seq2 = new SequenceId();
        seq2.setId(key2);
        seq2.setSeq(seqVal2);
        mongoTemplate.insert(seq2);
    }

    @Test
    public void givenKey1ThenReturnNextValue() {
        Long expected = seqVal1 + 1;
        Long actual = sequenceRepository.getNextSequenceId(key1);
        assertEquals(expected, actual);
    }

    @Test
    public void givenKey2ThenReturnNextValue() {
        Long expected = seqVal2 + 1;
        Long actual = sequenceRepository.getNextSequenceId(key2);
        assertEquals(expected, actual);
    }

    @Test
    public void givenInexistentKeyTwiceThenReturnZeroAndOne() {
        Long expected1 = 0L;
        Long expected2 = 1L;
        Long actual1 = sequenceRepository.getNextSequenceId("Inexistent");
        Long actual2 = sequenceRepository.getNextSequenceId("Inexistent");
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
