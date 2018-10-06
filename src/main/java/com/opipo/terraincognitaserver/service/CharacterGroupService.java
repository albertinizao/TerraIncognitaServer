package com.opipo.terraincognitaserver.service;

import com.opipo.terraincognitaserver.dto.CharacterGroup;

public interface CharacterGroupService {
    CharacterGroup get(String eventId, String characterGroupId);

    CharacterGroup create(CharacterGroup characterGroup, String eventId);

    CharacterGroup save(CharacterGroup characterGroup, String eventId);

    CharacterGroup update(CharacterGroup characterGroup, String eventId);

    void delete(String eventId, CharacterGroup characterGroup);

    void delete(String eventId, String characterGroupId);
}
