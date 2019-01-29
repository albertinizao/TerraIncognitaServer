package com.opipo.terraincognitaserver.rest.api;

import java.util.Collection;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/event")
@Api(value = "REST API to manage event events")
public class EventController extends AbstractCRUDController<Event, String> {

    @Autowired
    private EventService service;

    @Autowired
    private CharacterGroupService characterGroupService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private EventInscriptionService eventInscriptionService;

    @Override
    protected ServiceDTOInterface<Event, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(Event element) {
        return element.getName();
    }

    private Event getEvent(String id) {
        Event event = service.find(id);
        if (event == null) {
            throw new ResourceNotFoundException();
        }
        return event;
    }

    @GetMapping("/{id}/characterGroup")
    @ApiOperation(value = "Get", notes = "Get All the characters group from a event")
    public @ResponseBody ResponseEntity<Collection<CharacterGroup>> listCharacterGroups(
            @ApiParam(value = "The identifier of the element", required = true) @PathVariable("id") String id) {
        return new ResponseEntity<>(getEvent(id).getCharacterGroups(), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/characterGroup/{characterGroupId}")
    public ResponseEntity<CharacterGroup> getCharacterGroups(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId) {
        return new ResponseEntity<>(getEvent(eventId).getCharacterGroups().stream()
                .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new EmptyResourceException()), HttpStatus.OK);
    }

    @PostMapping("/{eventId}/characterGroup/{characterGroupId}")
    public ResponseEntity<CharacterGroup> createCharacterGroup(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId,
            @ApiParam(value = "The character group", required = true) @RequestBody CharacterGroup characterGroup) {
        Assert.isTrue(!checkCharacterGroupIsOkAndExists(eventId, characterGroupId, characterGroup),
                "The element exists now");
        return new ResponseEntity<>(characterGroupService.create(characterGroup, eventId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{eventId}/characterGroup/{characterGroupId}")
    public ResponseEntity<CharacterGroup> updateCharacterGroup(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("eventId") String characterGroupId,
            @ApiParam(value = "The character group", required = true) @RequestBody CharacterGroup characterGroup) {
        Assert.isTrue(checkCharacterGroupIsOkAndExists(eventId, characterGroupId, characterGroup),
                "The element doesn't exist");
        return new ResponseEntity<>(characterGroupService.update(characterGroup, eventId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{eventId}/characterGroup/{characterGroupId}")
    public ResponseEntity<String> deleteCharacterGroup(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroup) {
        characterGroupService.delete(eventId, characterGroup);
        return new ResponseEntity<String>(characterGroup, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/characterGroup/{characterGroupId}/character")
    public ResponseEntity<Collection<Character>> listCharacters(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId) {
        return new ResponseEntity<>(getEvent(eventId).getCharacterGroups().stream()
                .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException()).getCharacters(), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/characterGroup/{characterGroupId}/character/{characterId}")
    public ResponseEntity<Character> getCharacter(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId,
            @ApiParam(value = "The identifier of the character", required = true) @PathVariable("characterId") String characterId) {
        return new ResponseEntity<>(getEvent(eventId).getCharacterGroups().stream()
                .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException()).getCharacters().stream()
                .filter(p2 -> characterId.equalsIgnoreCase(p2.getName())).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException()), HttpStatus.OK);
    }

    @PostMapping("/{eventId}/characterGroup/{characterGroupId}/character/{characterId}")
    public ResponseEntity<Character> createCharacter(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId,
            @ApiParam(value = "The identifier of the character", required = true) @PathVariable("characterId") String characterId,
            @ApiParam(value = "The character", required = true) @RequestBody Character character) {
        Assert.isTrue(!checkCharacterIsOkAndExists(eventId, characterGroupId, characterId, character),
                "The element exists now");
        return new ResponseEntity<>(characterService.create(character, eventId, characterGroupId), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{eventId}/characterGroup/{characterGroupId}/character/{characterId}")
    public ResponseEntity<Character> updateCharacter(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("eventId") String characterGroupId,
            @ApiParam(value = "The identifier of the character", required = true) @PathVariable("characterId") String characterId,
            @ApiParam(value = "The character", required = true) @RequestBody Character character) {
        Assert.isTrue(checkCharacterIsOkAndExists(eventId, characterGroupId, characterId, character),
                "The element doesn't exist");
        return new ResponseEntity<>(characterService.update(character, eventId, characterGroupId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{eventId}/characterGroup/{characterGroupId}/character/{characterId}")
    public ResponseEntity<String> deleteCharacter(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathVariable("characterGroupId") String characterGroupId,
            @ApiParam(value = "The identifier of the character", required = true) @PathVariable("characterGroupId") String character) {
        characterService.delete(eventId, characterGroupId, character);
        return new ResponseEntity<String>(character, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/manage")
    public ResponseEntity<Collection<EventInscription>> listInscriptions(@PathVariable("eventId") String eventId) {
        return new ResponseEntity<>(eventInscriptionService.findByEventId(eventId), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/manage/user/{userId}")
    public ResponseEntity<EventInscription> getInscription(
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @ApiParam(value = "The identifier of the user", required = true) @PathVariable("userId") String userId) {
        EventInscriptionId id = buildEventInscriptionId(eventId, userId);
        return new ResponseEntity<>(eventInscriptionService.find(id), HttpStatus.OK);
    }

    @GetMapping("/{eventId}/manage/join")
    public ResponseEntity<EventInscription> getInscription(Authentication authentication,
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId) {
        return getInscription(eventId, (String) authentication.getPrincipal());
    }

    @PostMapping("/{eventId}/manage/join")
    public ResponseEntity<EventInscription> join(Authentication authentication,
            @ApiParam(value = "The identifier of the event", required = true) @PathVariable("eventId") String eventId,
            @RequestBody EventInscription eventInscription) {
        EventInscriptionId id = buildEventInscriptionId(eventId, authentication);
        checkEventExists(id);
        if (eventInscriptionService.exists(id)) {
            throw new IllegalArgumentException("The user has been joined previoulsy");
        }
        return new ResponseEntity<>(eventInscriptionService.create(id), HttpStatus.ACCEPTED);
    }

    private void checkEventExists(EventInscriptionId eventInscriptionId) {
        Assert.isTrue(service.exists(eventInscriptionId.getEvent()), "The event id is incorrect");
    }

    private EventInscriptionId buildEventInscriptionId(String eventId, Authentication authentication) {
        return buildEventInscriptionId(eventId, (String) authentication.getPrincipal());
    }

    private EventInscriptionId buildEventInscriptionId(String eventId, String user) {
        EventInscriptionId id = new EventInscriptionId();
        id.setEvent(eventId);
        id.setUsername(user);
        return id;
    }

    private Boolean checkCharacterIsOkAndExists(String eventId, String characterGroupId, String characterId,
            Character character) {
        checkIdFromGeneric(characterId, () -> character.getName());
        Collection<CharacterGroup> characterGroups = getEvent(eventId).getCharacterGroups();
        return characterGroups.stream().filter(f -> f.getName().equalsIgnoreCase(characterGroupId)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException()).getCharacters().stream()
                .anyMatch(f -> f.getName().equalsIgnoreCase(characterId));
    }

    protected Boolean checkCharacterGroupIsOkAndExists(String eventId, String characterGroupId,
            CharacterGroup characterGroup) {
        checkIdFromGeneric(characterGroupId, () -> characterGroup.getName());
        Collection<CharacterGroup> characterGroups = getEvent(eventId).getCharacterGroups();
        return characterGroups.stream().anyMatch(f -> f.getName().equalsIgnoreCase(characterGroupId));
    }

    protected void checkIdFromGeneric(String id, Supplier<String> idGetter) {
        try {
            String idGetterString = idGetter.get();
            Assert.isTrue(id != null && idGetterString != null && id.equalsIgnoreCase(idGetterString),
                    "The id is not the expected");
        } catch (NullPointerException npe) {
            throw new IllegalArgumentException();
        }
    }

}
