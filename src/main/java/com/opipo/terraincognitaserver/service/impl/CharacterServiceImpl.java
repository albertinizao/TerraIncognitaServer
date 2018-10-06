package com.opipo.terraincognitaserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.service.CharacterGroupService;
import com.opipo.terraincognitaserver.service.CharacterService;

public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterGroupService characterGroupService;

    private Character persist(CharacterGroup characterGroup, String eventId, String characterId) {
        return characterGroupService.create(characterGroup, eventId).getCharacters().stream()
                .filter(p -> characterId.equalsIgnoreCase(p.getName())).findFirst().get();
    }

    @Override
    public Character create(Character character, String eventId, String characterGroupId) {
        CharacterGroup characterGroup = characterGroupService.get(eventId, characterGroupId);
        characterGroup.removeCharacter(character.getName());
        characterGroup.addCharacter(character);
        return persist(characterGroup, eventId, character.getName());
    }

    @Override
    public Character save(Character character, String eventId, String characterGroupId) {
        CharacterGroup characterGroup = characterGroupService.get(eventId, characterGroupId);
        Character old = characterGroup.getCharacters().stream()
                .filter(p -> character.getName().equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(AbstractServiceDTO.DOESNT_EXISTS));
        old.setCharacterType(character.getCharacterType());
        old.setDescription(character.getDescription());
        characterGroup.removeCharacter(old.getName());
        characterGroup.addCharacter(old);
        return persist(characterGroup, eventId, character.getName());
    }

    @Override
    public Character update(Character character, String eventId, String characterGroupId) {
        CharacterGroup characterGroup = characterGroupService.get(eventId, characterGroupId);
        Character old = characterGroup.getCharacters().stream()
                .filter(p -> character.getName().equalsIgnoreCase(p.getName())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(AbstractServiceDTO.DOESNT_EXISTS));
        old.setCharacterType(
                character.getCharacterType() == null ? old.getCharacterType() : character.getCharacterType());
        old.setDescription(character.getDescription() == null ? old.getDescription() : character.getDescription());
        characterGroup.removeCharacter(old.getName());
        characterGroup.addCharacter(old);
        return persist(characterGroup, eventId, character.getName());
    }

    @Override
    public void delete(String eventId, String characterGroupId, Character character) {
        delete(eventId, characterGroupId, character.getName());
    }

    @Override
    public void delete(String eventId, String characterGroupId, String characterId) {
        CharacterGroup characterGroup = characterGroupService.get(eventId, characterGroupId);
        characterGroup.removeCharacter(characterId);
        persist(characterGroup, eventId, characterId);
    }

}
