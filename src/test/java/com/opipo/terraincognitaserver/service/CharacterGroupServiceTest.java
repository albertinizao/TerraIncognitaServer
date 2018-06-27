package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.opipo.terraincognitaserver.MockitoExtension;
import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.service.impl.CharacterGroupServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CharacterGroupServiceTest {

    @InjectMocks
    private CharacterGroupServiceImpl characterGroupService;

    @Mock
    private EventService eventService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(characterGroupService);
    }

    private CharacterGroup createCharacterGroup(String name) {
        CharacterGroup cg = new CharacterGroup();
        cg.setName(name);
        return cg;
    }

    @Test
    @DisplayName("Create Character Group")
    public void givenCharacterGroupAndEventIdThenCreateIt() {
        CharacterGroup characterGroup = createCharacterGroup("myName");
        String eventId = "eventId";
        Event event = Mockito.mock(Event.class);
        Mockito.when(eventService.find(eventId)).thenReturn(event);
        Mockito.when(eventService.saveComplete(event)).thenReturn(event);
        Collection<CharacterGroup> characterGroups = new ArrayList<>();
        characterGroups.add(createCharacterGroup("fake"));
        characterGroups.add(characterGroup);
        characterGroups.add(createCharacterGroup(null));
        Mockito.when(event.getCharacterGroups()).thenReturn(characterGroups);
        CharacterGroup actual = characterGroupService.create(characterGroup, eventId);

        assertEquals(characterGroup, actual);

        Mockito.verify(event).addCharacterGroup(characterGroup);
    }

    @Test
    @DisplayName("Update CharacterGroup in event")
    public void givenCharacterGroupAndEventIdThenUpdateIt() {
        CharacterGroup given = new CharacterGroup();
        String characterGroupName = "cgName";

        given.setName(characterGroupName);
        given.setCharacters(buildCharacters("name1"));

        Collection<CharacterGroup> characterGroups = new ArrayList<>();
        characterGroups.add(new CharacterGroup());
        characterGroups.add(given);

        CharacterGroup old = new CharacterGroup();
        old.setName(characterGroupName);
        old.setDescription("oldDescription");
        old.setCharacters(buildCharacters("nameOld"));

        String eventId = "eventId";
        Event event = new Event();
        event.setName(eventId);
        event.setCharacterGroups(Arrays.asList(old));

        Mockito.when(eventService.find(eventId)).thenReturn(event);

        Mockito.when(eventService.saveComplete(event)).thenReturn(event);

        CharacterGroup actual = characterGroupService.save(given, eventId);

        assertEquals(given.getName(), actual.getName());
        assertNull(actual.getDescription());
        assertTrue(actual.getCharacters().containsAll(given.getCharacters()));
        assertTrue(actual.getCharacters().containsAll(old.getCharacters()));
    }

    @Test
    @DisplayName("Update PATCH CharacterGroup in event")
    public void givenCharacterGroupAndEventIdThenUpdateItOnlyFields() {
        CharacterGroup given = new CharacterGroup();
        String characterGroupName = "cgName";
        String characterGroupDescription = "cgDescription";

        given.setName(characterGroupName);
        given.setCharacters(buildCharacters("name1"));

        Collection<CharacterGroup> characterGroups = new ArrayList<>();
        characterGroups.add(new CharacterGroup());
        characterGroups.add(given);

        CharacterGroup old = new CharacterGroup();
        old.setName(characterGroupName);
        old.setDescription("oldDescription");
        old.setCharacters(buildCharacters("nameOld"));

        String eventId = "eventId";
        Event event = new Event();
        event.setName(eventId);
        event.setCharacterGroups(Arrays.asList(old));

        Mockito.when(eventService.find(eventId)).thenReturn(event);

        Mockito.when(eventService.saveComplete(event)).thenReturn(event);

        CharacterGroup actual = characterGroupService.update(given, eventId);

        assertEquals(given.getName(), actual.getName());
        assertEquals(old.getDescription(), actual.getDescription());
        assertTrue(actual.getCharacters().containsAll(given.getCharacters()));
        assertTrue(actual.getCharacters().containsAll(old.getCharacters()));
    }

    @Test
    @DisplayName("Delete CharacterGroup")
    public void givenCharacterGroupAndEventIdThenDeleteCharacterGroup() {
        String characterGroupName = "myCharacterGroup";
        CharacterGroup characterGroup = createCharacterGroup(characterGroupName);
        String eventId = "eventId";
        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getName()).thenReturn(eventId);
        Mockito.when(event.getCharacterGroups())
                .thenReturn(Arrays.asList(createCharacterGroup("fake1"), characterGroup, createCharacterGroup("fake")));
        Mockito.when(eventService.find(eventId)).thenReturn(event);

        characterGroupService.delete(eventId, characterGroup);

        Mockito.verify(eventService).saveComplete(event);
        Mockito.verify(event).removeCharacterGroup(characterGroupName);
    }

    private Collection<Character> buildCharacters(String... names) {
        Collection<Character> charas = new ArrayList<>();
        for (String name : names) {
            charas.add(createCharacter(name));
        }
        return charas;
    }

    private Character createCharacter(String name) {
        Character character = new Character();
        character.setName(name);
        return character;
    }
}
