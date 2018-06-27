package com.opipo.terraincognitaserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.service.CharacterGroupService;
import com.opipo.terraincognitaserver.service.EventService;

@Service
public class CharacterGroupServiceImpl implements CharacterGroupService {

    @Autowired
    private EventService eventService;

    @Override
    public CharacterGroup get(String eventId, String characterGroupId) {
        return eventService.find(eventId).getCharacterGroups().stream()
                .filter(p -> characterGroupId.equalsIgnoreCase(p.getName())).findFirst().get();
    }

    @Override
    public CharacterGroup create(CharacterGroup characterGroup, String eventId) {
        Event event = eventService.find(eventId);
        event.removeCharacterGroup(characterGroup.getName());
        event.addCharacterGroup(characterGroup);
        return saveAndReturn(event, characterGroup.getName());
    }

    @Override
    public CharacterGroup save(CharacterGroup characterGroup, String eventId) {
        Event event = eventService.find(eventId);
        CharacterGroup oldCharacterGroup = event.getCharacterGroups().stream()
                .filter(p -> characterGroup.getName().equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(AbstractServiceDTO.DOESNT_EXISTS));
        oldCharacterGroup.getCharacters().stream().forEach(characterGroup::addCharacter);
        event.removeCharacterGroup(characterGroup.getName());
        event.addCharacterGroup(characterGroup);
        return saveAndReturn(event, characterGroup.getName());
    }

    @Override
    public CharacterGroup update(CharacterGroup characterGroup, String eventId) {
        Event event = eventService.find(eventId);
        CharacterGroup oldCharacterGroup = event.getCharacterGroups().stream()
                .filter(p -> characterGroup.getName().equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(AbstractServiceDTO.DOESNT_EXISTS));
        oldCharacterGroup.getCharacters().stream().forEach(characterGroup::addCharacter);
        characterGroup.setDescription(characterGroup.getDescription() == null ? oldCharacterGroup.getDescription()
                : characterGroup.getDescription());
        event.removeCharacterGroup(characterGroup.getName());
        event.addCharacterGroup(characterGroup);
        return saveAndReturn(event, characterGroup.getName());
    }

    private CharacterGroup saveAndReturn(Event event, String characterGroupName) {
        return eventService.saveComplete(event).getCharacterGroups().stream()
                .filter(p -> p.getName() != null && p.getName().equalsIgnoreCase(characterGroupName)).findFirst()
                .orElse(null);
    }

    @Override
    public void delete(String eventId, CharacterGroup characterGroup) {
        delete(eventId, characterGroup.getName());
    }

    @Override
    public void delete(String eventId, String characterGroupId) {
        Event event = eventService.find(eventId);
        event.removeCharacterGroup(characterGroupId);
        eventService.saveComplete(event);
    }

}
