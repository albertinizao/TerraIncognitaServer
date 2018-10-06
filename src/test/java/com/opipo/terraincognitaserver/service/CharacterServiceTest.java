package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.opipo.terraincognitaserver.MockitoExtension;
import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.service.impl.CharacterServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CharacterServiceTest {

    @InjectMocks
    private CharacterServiceImpl characterService;

    @Mock
    private CharacterGroupService characterGroupService;

    @Test
    @DisplayName("Create Character")
    public void givenCharacterThenAddItToACharacterGroup() {
        String eventId = "eventId";
        Character character = new Character();
        Character old = new Character();
        String characterName = "myName";
        String description = "myDescription";
        character.setName(characterName);
        old.setName(characterName);
        character.setDescription(description);
        CharacterGroup characterGroup = new CharacterGroup();
        CharacterGroup expected = new CharacterGroup();
        String characterGroupId = "characterGroupId";
        characterGroup.addCharacter(old);
        characterGroup.setName(characterGroupId);
        expected.addCharacter(character);
        expected.setName(characterGroupId);
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroup);
        Mockito.when(characterGroupService.create(expected, eventId)).thenReturn(expected);

        Character actual = characterService.create(character, eventId, characterGroupId);

        assertEquals(character, actual);

    }
}
