package com.opipo.terraincognitaserver.rest.api;

import java.util.Collection;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.service.EventService;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/event")
@Api(value = "REST API to manage event events")
public class EventController extends AbstractCRUDController<Event, String> {

    @Autowired
    private EventService service;

    @Override
    protected ServiceDTOInterface<Event, String> getService() {
        return service;
    }

    @Override
    protected String getIdFromElement(Event element) {
        return element.getName();
    }

    @GetMapping("{eventId}/characterGroup")
    public ResponseEntity<Collection<CharacterGroup>> listCharacterGroups(
            @ApiParam(value = "The identifier of the event", required = true) @PathParam("eventId") String eventId) {
        return new ResponseEntity<>(service.find(eventId).getCharacterGroups(), HttpStatus.OK);
    }

    @GetMapping("{eventId}/characterGroup/{characterGroupId}")
    public ResponseEntity<CharacterGroup> getCharacterGroups(
            @ApiParam(value = "The identifier of the event", required = true) @PathParam("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathParam("characterGroupId") String characterGroupId) {
        return new ResponseEntity<>(service.find(eventId).getCharacterGroups().stream()
                .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst().get(), HttpStatus.OK);
    }

    @GetMapping("{eventId}/characterGroup/{characterGroupId}/character")
    public ResponseEntity<Collection<Character>> listCharacters(
            @ApiParam(value = "The identifier of the event", required = true) @PathParam("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathParam("characterGroupId") String characterGroupId) {
        return new ResponseEntity<>(
                service.find(eventId).getCharacterGroups().stream()
                        .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst().get().getCharacters(),
                HttpStatus.OK);
    }

    @GetMapping("{eventId}/characterGroup/{characterGroupId}/character/{characterId}")
    public ResponseEntity<Character> getCharacter(
            @ApiParam(value = "The identifier of the event", required = true) @PathParam("eventId") String eventId,
            @ApiParam(value = "The identifier of the character group", required = true) @PathParam("characterGroupId") String characterGroupId,
            @ApiParam(value = "The identifier of the character", required = true) @PathParam("characterId") String characterId) {
        return new ResponseEntity<>(
                service.find(eventId).getCharacterGroups().stream()
                        .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst().get().getCharacters()
                        .stream().filter(p2 -> characterId.equalsIgnoreCase(p2.getName())).findFirst().get(),
                HttpStatus.OK);
    }

}
