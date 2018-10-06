package com.opipo.terraincognitaserver.service;

import com.opipo.terraincognitaserver.dto.Character;

public interface CharacterService {
    Character create(Character character, String eventId, String characterGroupId);

    Character save(Character character, String eventId, String characterGroupId);

    Character update(Character character, String eventId, String characterGroupId);

    void delete(String eventId, String characterGroupId, Character character);

    void delete(String eventId, String characterGroupId, String characterId);
}
