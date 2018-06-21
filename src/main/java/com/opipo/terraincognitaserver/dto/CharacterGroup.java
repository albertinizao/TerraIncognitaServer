package com.opipo.terraincognitaserver.dto;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CharacterGroup {
    private String name;
    private String description;
    private Set<Character> characters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Character> getCharacters() {
        return characters == null ? null : new TreeSet<>(characters);
    }

    public void setCharacters(Collection<Character> characters) {
        this.characters = characters == null ? null : new TreeSet<>(characters);
    }

    public boolean addCharacter(Character character) {
        return this.characters.add(character);
    }

    public boolean removeCharacter(String character) {
        this.setCharacters(this.characters.stream().filter(p -> !character.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList()));
        return true;
    }

}
