package com.opipo.terraincognitaserver.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.dto.EventInscription;
import com.opipo.terraincognitaserver.dto.EventInscriptionId;
import com.opipo.terraincognitaserver.exception.EmptyResourceException;
import com.opipo.terraincognitaserver.exception.ResourceNotFoundException;
import com.opipo.terraincognitaserver.service.CharacterGroupService;
import com.opipo.terraincognitaserver.service.CharacterService;
import com.opipo.terraincognitaserver.service.EventInscriptionService;
import com.opipo.terraincognitaserver.service.EventService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

public class EventControllerTest extends AbstractCRUDControllerTest<Event, String> {

    @InjectMocks
    private EventController controller;

    @Mock
    private EventService service;

    @Mock
    private CharacterGroupService characterGroupService;

    @Mock
    private CharacterService characterService;

    @Mock
    private EventInscriptionService eventInscriptionService;

    @Override
    protected AbstractCRUDController<Event, String> getController() {
        return controller;
    }

    @Override
    protected ServiceDTOInterface<Event, String> getService() {
        return service;
    }

    @Override
    protected String getCorrectID() {
        return "correctId";
    }

    @Override
    protected String getIncorrectID() {
        return "fakeId";
    }

    @Override
    protected Event buildElement(String id) {
        Event element = new Event();
        element.setName(id);
        return element;
    }

    @Test
    @DisplayName("Character-group list")
    public void givenEventIdThenGetCharacterGroupList() {
        String id = getCorrectID();
        Event event = Mockito.mock(Event.class);
        Mockito.when(getService().find(id)).thenReturn(event);
        Collection<CharacterGroup> expected = new ArrayList<>();
        expected.add(Mockito.mock(CharacterGroup.class));
        Mockito.when(event.getCharacterGroups()).thenReturn(expected);
        assertNotNull(checkResponse(controller.listCharacterGroups(id), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("Character-group list with bad eventId")
    public void givenIncorrectEventIdThenGetCharacterGroupListWithError() {
        String id = getCorrectID();
        Event event = Mockito.mock(Event.class);
        Mockito.when(getService().find(id)).thenReturn(null);
        Collection<CharacterGroup> expected = new ArrayList<>();
        expected.add(Mockito.mock(CharacterGroup.class));
        Mockito.when(event.getCharacterGroups()).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, () -> controller.listCharacterGroups(id));
    }

    @Test
    @DisplayName("Character-group get by id")
    public void givenEventIdAndCharacterGroupIdThenGetIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = mockCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, mockCharacterGroup("fake" + characterGroupId), expected,
                mockCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertNotNull(checkResponse(controller.getCharacterGroups(eventId, characterGroupId), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("Character-group get by wrong id")
    public void givenEventIdAndWrongCharacterGroupIdThenGetNull() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        Event event = mockEvent(eventId, mockCharacterGroup("fake" + characterGroupId),
                mockCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertThrows(EmptyResourceException.class, () -> controller.getCharacterGroups(eventId, characterGroupId));
    }

    @Test
    @DisplayName("Create new inexistant character-group")
    public void givenEventIdAndCharacterGroupThenCreateIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId),
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.create(expected, eventId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.createCharacterGroup(eventId, characterGroupId, expected), expected,
                HttpStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Create new inexistant character-group with none previous")
    public void givenEventIdAndCharacterGroupThenCreateIt2() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId);
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.create(expected, eventId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.createCharacterGroup(eventId, characterGroupId, expected), expected,
                HttpStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Create new existant character-group")
    public void givenEventIdAndCharacterGroupThatExistsThenErrorInCreation() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), expected,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertThrows(IllegalArgumentException.class,
                () -> controller.createCharacterGroup(eventId, characterGroupId, expected));
    }

    @Test
    @DisplayName("Update existant character-group")
    public void givenEventIdAndCharacterGroupThatExistsThenUpdateIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), expected,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.update(expected, eventId)).thenReturn(expected);
        assertEquals(expected, controller.updateCharacterGroup(eventId, characterGroupId, expected).getBody());
    }

    @Test
    @DisplayName("Update character-group with other id")
    public void givenEventIdAndTwoDiferentCharacterGroupThenWhenTryToUpdateGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), expected,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.update(expected, eventId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class,
                () -> controller.updateCharacterGroup(eventId, characterGroupId + "other", expected));
    }

    @Test
    @DisplayName("Update character-group with no character-group")
    public void givenEventIdAndCharacterGroupIdButNoCharacterGroupThenWhenTryToUpdateGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), expected,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.update(expected, eventId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class,
                () -> controller.updateCharacterGroup(eventId, characterGroupId, null));
    }

    @Test
    @DisplayName("Update character-group with no id")
    public void givenEventIdAndCharacterGroupButNoTCharacterGroupIdhenWhenTryToUpdateGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        CharacterGroup expected = newCharacterGroup(characterGroupId);
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), expected,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterGroupService.update(expected, eventId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class, () -> controller.updateCharacterGroup(eventId, null, expected));
    }

    @Test
    @DisplayName("Delete character-group")
    public void givenEventIdAndCharacterGroupIdThenDeleteIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        assertEquals(characterGroupId, controller.deleteCharacterGroup(eventId, characterGroupId).getBody());
        Mockito.verify(characterGroupService).delete(eventId, characterGroupId);
    }

    @Test
    @DisplayName("List characters")
    public void givenEventIdAndCharacterGroupIdThenListAllCharacters() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        Character character = newCharacter("char1");
        Character character2 = newCharacter("char2");
        Collection<Character> expected = new ArrayList<>();
        expected.add(character);
        expected.add(character2);
        CharacterGroup cg = mockCharacterGroup(characterGroupId, character, character2);
        Event event = mockEvent(eventId, newCharacterGroup("id1"), cg, newCharacterGroup("id2"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertNotNull(checkResponse(controller.listCharacters(eventId, characterGroupId), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("List characters wrong eventId")
    public void givenWrongEventIdAndCharacterGroupIdThenGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        Mockito.when(getService().find(eventId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> controller.listCharacters(eventId, characterGroupId));
    }

    @Test
    @DisplayName("List characters wrong character-group")
    public void givenEventIdAndWrongCharacterGroupIdThenGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        Event event = mockEvent(eventId, newCharacterGroup("id1"), newCharacterGroup("id2"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertThrows(ResourceNotFoundException.class, () -> controller.listCharacters(eventId, characterGroupId));
    }

    @Test
    @DisplayName("Get character")
    public void givenEventIdAndCharacterGroupIdAndCharacterIdThenGetCharacter() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharacterID";
        Character character = newCharacter("char1");
        Character character2 = newCharacter("char2");
        Character expected = newCharacter(characterId);
        CharacterGroup cg = mockCharacterGroup(characterGroupId, character, expected, character2);
        Event event = mockEvent(eventId, newCharacterGroup("id1"), cg, newCharacterGroup("id2"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertNotNull(checkResponse(controller.getCharacter(eventId, characterGroupId, characterId), expected,
                HttpStatus.OK));
    }

    @Test
    @DisplayName("Get character wrong eventId")
    public void givenWrongEventIdAndCharacterGroupIdAndCharacterIdThenGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharacterID";
        Mockito.when(getService().find(eventId)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class,
                () -> controller.getCharacter(eventId, characterGroupId, characterId));
    }

    @Test
    @DisplayName("Get character in event without character groups")
    public void givenEventIdAndBadCharacterGroupIdAndCharacterIdThenGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharacterID";
        Event event = mockEvent(eventId, newCharacterGroup("id1"), newCharacterGroup("id2"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertThrows(ResourceNotFoundException.class,
                () -> controller.getCharacter(eventId, characterGroupId, characterId));
    }

    @Test
    @DisplayName("Get character wrong character-group id")
    public void givenEventIdAndWrongCharacterGroupIdAndCharacterIdThenGetError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharacterID";
        Character character = newCharacter("char1");
        Character character2 = newCharacter("char2");
        CharacterGroup cg = mockCharacterGroup(characterGroupId, character, character2);
        Event event = mockEvent(eventId, newCharacterGroup("id1"), cg, newCharacterGroup("id2"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        assertThrows(ResourceNotFoundException.class,
                () -> controller.getCharacter(eventId, characterGroupId, characterId));
    }

    @Test
    @DisplayName("Create new inexistant character")
    public void givenEventIdAndCharacterGroupAndCharacterThenCreateIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup(characterGroupId, newCharacter("f1"), newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.create(expected, eventId, characterGroupId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.createCharacter(eventId, characterGroupId, characterId, expected),
                expected, HttpStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Create new inexistant character in bad charactergroup")
    public void givenEventIdAndNadCharacterGroupAndCharacterThenCreateItWithError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup("NO", newCharacter("f1"), newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.create(expected, eventId, characterGroupId)).thenReturn(expected);
        assertThrows(ResourceNotFoundException.class,
                () -> controller.createCharacter(eventId, characterGroupId, characterId, expected));
    }

    @Test
    @DisplayName("Create new existant character")
    public void givenEventIdAndCharacterGroupAndExistantCharacterThenCreateItWithError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup(characterGroupId, newCharacter("f1"), expected, newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.create(expected, eventId, characterGroupId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class,
                () -> controller.createCharacter(eventId, characterGroupId, characterId, expected));
    }

    @Test
    @DisplayName("Update inexistant character")
    public void givenEventIdAndCharacterGroupAndCharacterThenUpdateIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup(characterGroupId, newCharacter("f1"), newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.update(expected, eventId, characterGroupId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class,
                () -> controller.updateCharacter(eventId, characterGroupId, characterId, expected));
    }

    @Test
    @DisplayName("Update existant character in bad charactergroup")
    public void givenEventIdAndNadCharacterGroupAndCharacterThenUpdateItWithError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup("NO", newCharacter("f1"), newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.update(expected, eventId, characterGroupId)).thenReturn(expected);
        assertThrows(ResourceNotFoundException.class,
                () -> controller.updateCharacter(eventId, characterGroupId, characterId, expected));
    }

    @Test
    @DisplayName("Update existant character")
    public void givenEventIdAndCharacterGroupAndExistantCharacterThenUpdateItWithError() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        Character expected = newCharacter(characterId);
        CharacterGroup gc = mockCharacterGroup(characterGroupId, newCharacter("f1"), expected, newCharacter("f2"));
        Event event = mockEvent(eventId, newCharacterGroup("fake" + characterGroupId), gc,
                newCharacterGroup(characterGroupId + "fake"));
        Mockito.when(getService().find(eventId)).thenReturn(event);
        Mockito.when(characterService.update(expected, eventId, characterGroupId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.updateCharacter(eventId, characterGroupId, characterId, expected),
                expected, HttpStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Delete existant character")
    public void givenEventIdAndCharacterGroupAndExistantCharacterThenDeleteIt() {
        String characterGroupId = "GroupID";
        String eventId = "EventID";
        String characterId = "CharID";
        assertNotNull(checkResponse(controller.deleteCharacter(eventId, characterGroupId, characterId), characterId,
                HttpStatus.OK));
        Mockito.verify(characterService).delete(eventId, characterGroupId, characterId);
    }

    @Test
    @DisplayName("List inscriptions")
    public void givenEventIdThenListInscriptions() {
        String eventId = "eventID";
        List<EventInscription> expected = Arrays.asList(
                new EventInscription[] {Mockito.mock(EventInscription.class), Mockito.mock(EventInscription.class)});
        Mockito.when(eventInscriptionService.findByEventId(eventId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.listInscriptions(eventId), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("Get one inscription")
    public void givenEventIdAndUserIdThenGetInscription() {
        String eventId = "eventID";
        String userId = "userID";
        EventInscription expected = new EventInscription();
        EventInscriptionId eventInscriptionId = buildInscriptionId(eventId, userId);
        Mockito.when(eventInscriptionService.find(eventInscriptionId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.getInscription(eventId, userId), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("Get own inscription")
    public void givenEventThenGetOwnInscription() {
        String eventId = "eventID";
        String userId = "userID";
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(userId);
        EventInscription expected = new EventInscription();
        EventInscriptionId eventInscriptionId = buildInscriptionId(eventId, userId);
        Mockito.when(eventInscriptionService.find(eventInscriptionId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.getInscription(auth, eventId), expected, HttpStatus.OK));
    }

    @Test
    @DisplayName("Join in event")
    public void givenEventAndUserThenJoinEvent() {
        String eventId = "eventID";
        String userId = "userID";
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(userId);
        EventInscription expected = new EventInscription();
        EventInscriptionId eventInscriptionId = buildInscriptionId(eventId, userId);
        Mockito.when(eventInscriptionService.exists(eventInscriptionId)).thenReturn(false);
        Mockito.when(service.exists(eventId)).thenReturn(true);
        Mockito.when(eventInscriptionService.create(eventInscriptionId)).thenReturn(expected);
        assertNotNull(checkResponse(controller.join(auth, eventId, expected), expected, HttpStatus.ACCEPTED));
    }

    @Test
    @DisplayName("Join in event inscribed previously")
    public void givenEventAndUserInscribedThenJoinEvent() {
        String eventId = "eventID";
        String userId = "userID";
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(userId);
        EventInscription expected = new EventInscription();
        EventInscriptionId eventInscriptionId = buildInscriptionId(eventId, userId);
        Mockito.when(eventInscriptionService.exists(eventInscriptionId)).thenReturn(true);
        Mockito.when(service.exists(eventId)).thenReturn(true);
        Mockito.when(eventInscriptionService.create(eventInscriptionId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class, () -> controller.join(auth, eventId, expected));
    }

    @Test
    @DisplayName("Join inexistant event")
    public void givenInexistantEventAndUserThenJoinEvent() {
        String eventId = "eventID";
        String userId = "userID";
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(auth.getPrincipal()).thenReturn(userId);
        EventInscription expected = new EventInscription();
        EventInscriptionId eventInscriptionId = buildInscriptionId(eventId, userId);
        Mockito.when(eventInscriptionService.exists(eventInscriptionId)).thenReturn(false);
        Mockito.when(service.exists(eventId)).thenReturn(false);
        Mockito.when(eventInscriptionService.create(eventInscriptionId)).thenReturn(expected);
        assertThrows(IllegalArgumentException.class, () -> controller.join(auth, eventId, expected));
    }

    private EventInscriptionId buildInscriptionId(String eventId, String userId) {
        EventInscriptionId id = new EventInscriptionId();
        id.setEvent(eventId);
        id.setUsername(userId);
        return id;
    }

    @Test
    @DisplayName("Check id correct")
    public void givenIdAndSupplierThenCheckIsCorrect() {
        String id1 = "id1";
        String id2 = id1;
        controller.checkIdFromGeneric(id1, () -> id2);
    }

    @Test
    @DisplayName("Check id with nullpointer")
    public void givenIdAndSupplierThenCheckNullpointer() {
        String id1 = "id1";
        CharacterGroup cg = null;
        assertThrows(IllegalArgumentException.class, () -> controller.checkIdFromGeneric(id1, () -> cg.getName()));
    }

    @Test
    @DisplayName("Check id different")
    public void givenIdAndSupplierThenCheckAreDifferent() {
        String id1 = "id1";
        String id2 = "id2";
        assertThrows(IllegalArgumentException.class, () -> controller.checkIdFromGeneric(id1, () -> id2));
    }

    @Test
    @DisplayName("Check id one null")
    public void givenNullIdAndSupplierThenCheckIsCorrect() {
        String id1 = null;
        String id2 = "id2";
        assertThrows(IllegalArgumentException.class, () -> controller.checkIdFromGeneric(id1, () -> id2));
    }

    @Test
    @DisplayName("Check id one null 2")
    public void givenIdAndNullSupplierThenCheckIsCorrect() {
        String id1 = "id1";
        String id2 = null;
        assertThrows(IllegalArgumentException.class, () -> controller.checkIdFromGeneric(id1, () -> id2));
    }

    private Character newCharacter(String name) {
        Character character = new Character();
        character.setName(name);
        return character;
    }

    private CharacterGroup newCharacterGroup(String nombre) {
        CharacterGroup cg = new CharacterGroup();
        cg.setName(nombre);
        return cg;
    }

    private CharacterGroup mockCharacterGroup(Character... characters) {
        CharacterGroup characterGroup = Mockito.mock(CharacterGroup.class);
        Mockito.when(characterGroup.getCharacters()).thenReturn(Arrays.asList(characters));
        return characterGroup;
    }

    private CharacterGroup mockCharacterGroup(String id, Character... characters) {
        CharacterGroup characterGroup = mockCharacterGroup(characters);
        Mockito.when(characterGroup.getName()).thenReturn(id);
        return characterGroup;
    }

    private Event mockEvent(CharacterGroup... characterGroups) {
        Event event = Mockito.mock(Event.class);
        Mockito.when(event.getCharacterGroups()).thenReturn(Arrays.asList(characterGroups));
        return event;
    }

    private Event mockEvent(String eventId, CharacterGroup... characterGroups) {
        Event event = mockEvent(characterGroups);
        Mockito.when(event.getName()).thenReturn(eventId);
        return event;
    }

}
