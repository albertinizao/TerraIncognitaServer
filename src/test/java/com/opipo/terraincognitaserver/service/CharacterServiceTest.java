package com.opipo.terraincognitaserver.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.opipo.terraincognitaserver.MockitoExtension;
import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.CharacterType;
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

    @Test
    @DisplayName("Save character")
    public void givenEventIdAndCharacterGroupIdAndCharacterThenSaveIt() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        CharacterType characterType = CharacterType.TWICE;
        Character character = createCharacter(characterName, characterDescription, characterType);
        Character characterOld = createCharacter(characterName, characterDescription + "Old", CharacterType.PNJ);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description",
                createCharacterFake(1), characterOld, createCharacterFake(2));
        CharacterGroup characterGroupNew = createCharacterGroup("name", "description", createCharacterFake(1),
                character, createCharacterFake(2));
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupNew, eventId)).thenReturn(characterGroupNew);
        Character actual = characterService.save(character, eventId, characterGroupId);
        assertEquals(character, actual);
    }

    @Test
    @DisplayName("Save inexistent character")
    public void givenEventIdAndCharacterGroupIdAndInexistentCharacterThenSaveIt() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        CharacterType characterType = CharacterType.TWICE;
        Character character = createCharacter(characterName, characterDescription, characterType);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description", createCharacterFake(1),
                createCharacterFake(2));
        CharacterGroup characterGroupNew = createCharacterGroup("name", "description", createCharacterFake(1),
                character, createCharacterFake(2));
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupNew, eventId)).thenReturn(characterGroupNew);
        assertThrows(IllegalArgumentException.class, () -> characterService.save(character, eventId, characterGroupId));
    }

    @Test
    @DisplayName("Update description from character")
    public void givenEventIdAndCharacterGroupIdAndCharacterThenUUpdateDescription() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        CharacterType characterType = CharacterType.TWICE;
        Character character = createCharacter(characterName, null, characterType);
        Character expected = createCharacter(characterName, characterDescription + "Old", characterType);
        Character characterOld = createCharacter(characterName, characterDescription + "Old", CharacterType.PNJ);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description", createCharacterFake(1),
                characterOld, createCharacterFake(2));
        CharacterGroup characterGroupNew = createCharacterGroup("name", "description", createCharacterFake(1), expected,
                createCharacterFake(2));
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupNew, eventId)).thenReturn(characterGroupNew);
        Character actual = characterService.update(character, eventId, characterGroupId);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update type from character")
    public void givenEventIdAndCharacterGroupIdAndCharacterThenUpdateType() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        Character character = createCharacter(characterName, characterDescription, null);
        Character expected = createCharacter(characterName, characterDescription, CharacterType.PNJ);
        Character characterOld = createCharacter(characterName, characterDescription + "Old", CharacterType.PNJ);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description", createCharacterFake(1),
                characterOld, createCharacterFake(2));
        CharacterGroup characterGroupNew = createCharacterGroup("name", "description", createCharacterFake(1),
                expected, createCharacterFake(2));
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupNew, eventId)).thenReturn(characterGroupNew);
        Character actual = characterService.update(character, eventId, characterGroupId);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Update inexistent character")
    public void givenEventIdAndCharacterGroupIdAndInexistentCharacterThenUpdateIt() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        CharacterType characterType = CharacterType.TWICE;
        Character character = createCharacter(characterName, characterDescription, characterType);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description", createCharacterFake(1),
                createCharacterFake(2));
        CharacterGroup characterGroupNew = createCharacterGroup("name", "description", createCharacterFake(1),
                character, createCharacterFake(2));
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupNew, eventId)).thenReturn(characterGroupNew);
        assertThrows(IllegalArgumentException.class,
                () -> characterService.update(character, eventId, characterGroupId));
    }

    @Test
    @DisplayName("Delete existent character")
    public void givenEventIdAndCharacterGroupIdAndCharacterIdThenDeleteIt() {
        String eventId = "eventId";
        String characterGroupId = "characterGroupId";
        String characterName = "miName";
        String characterDescription = "miDescription";
        CharacterType characterType = CharacterType.TWICE;
        Character character = createCharacter(characterName, characterDescription, characterType);
        Character character1 = createCharacterFake(1);
        Character character2 = createCharacterFake(2);
        CharacterGroup characterGroupPersisted = createCharacterGroup("name", "description", character1, character,
                character2);
        CharacterGroup characterGroupExpected = createCharacterGroup("name", "description", character1, character2);
        Mockito.when(characterGroupService.get(eventId, characterGroupId)).thenReturn(characterGroupPersisted);
        Mockito.when(characterGroupService.create(characterGroupExpected, eventId)).thenReturn(characterGroupExpected);
        characterService.delete(eventId, characterGroupId, characterName);
        assertNotNull(eventId);
    }

    private CharacterGroup createCharacterGroup(String name, String description, Character... characters) {
        CharacterGroup cg = new CharacterGroup();
        cg.setName(name);
        cg.setDescription(description);
        cg.setCharacters(Arrays.asList(characters));
        return cg;
    }

    private Character createCharacter(String name, String description, CharacterType characterType) {
        Character character = new Character();
        character.setCharacterType(characterType);
        character.setDescription(description);
        character.setName(name);
        return character;
    }

    private Character createCharacterFake(int number) {
        return createCharacter("fakeName" + number, "descriptionFake" + number, CharacterType.PJ);
    }
}
