package com.opipo.terraincognitaserver.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CharacterRequest autogenerated")
public class CharacterRequestTest {

    private CharacterRequest characterRequest;

    @BeforeEach
    public void init() {
        characterRequest = new CharacterRequest();
    }

    @Test
    @DisplayName("The getter and the setter of order work well")
    public void orderAttributeTest() {
        Integer order = Integer.valueOf(1);
        characterRequest.setOrder(order);
        assertEquals(order, characterRequest.getOrder());
    }

    @Test
    @DisplayName("The getter and the setter of characterGroup work well")
    public void characterGroupAttributeTest() {
        String characterGroup = Integer.toString(42);
        characterRequest.setCharacterGroup(characterGroup);
        assertEquals(characterGroup, characterRequest.getCharacterGroup());
    }

    @Test
    @DisplayName("The getter and the setter of character work well")
    public void characterAttributeTest() {
        String character = Integer.toString(2);
        characterRequest.setCharacter(character);
        assertEquals(character, characterRequest.getCharacter());
    }


    @Test
    @DisplayName("The getter and the setter of description work well")
    public void descriptionAttributeTest() {
        String description = Integer.toString(3);
        characterRequest.setDescription(description);
        assertEquals(description, characterRequest.getDescription());
    }

    @Test
    @DisplayName("The getter and the setter of assigned work well")
    public void assignedAttributeTest() {
        Boolean assigned = Boolean.valueOf(true);
        characterRequest.setAssigned(assigned);
        assertEquals(assigned, characterRequest.getAssigned());
    }

    @Test
    @DisplayName("The getter and the setter of npcPreference work well")
    public void npcPreferenceAttributeTest() {
        Boolean npcPreference = Boolean.valueOf(true);
        characterRequest.setNpcPreference(npcPreference);
        assertEquals(npcPreference, characterRequest.getNpcPreference());
    }

    @Test
    public void givenSameObjReturnThatTheyAreEquals() {
        CharacterRequest o1 = new CharacterRequest();
        CharacterRequest o2 = new CharacterRequest();
        assertEquals(o1, o2);
    }

    @Test
    public void givenSameObjReturnZero() {
        CharacterRequest o1 = new CharacterRequest();
        CharacterRequest o2 = new CharacterRequest();
        assertEquals(0, o1.compareTo(o2));
    }

    @Test
    public void givenObjectFromOtherClassReturnThatTheyArentEquals() {
        CharacterRequest o1 = new CharacterRequest();
        assertNotEquals(o1, new String());
    }

    @Test
    public void givenSameObjReturnSameHashCode() {
        CharacterRequest o1 = new CharacterRequest();
        CharacterRequest o2 = new CharacterRequest();
        assertEquals(o1.hashCode(), o2.hashCode());
    }

}