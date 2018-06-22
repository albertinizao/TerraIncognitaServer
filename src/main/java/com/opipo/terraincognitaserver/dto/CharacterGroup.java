package com.opipo.terraincognitaserver.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CharacterGroup implements Comparable<CharacterGroup> {
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
        Collection<Character> charactersCollection = this.characters == null ? new HashSet<>() : getCharacters();
        boolean response = charactersCollection.add(character);
        setCharacters(charactersCollection);
        return response;
    }

    public boolean removeCharacter(String character) {
        Collection<Character> charactersCollection = this.characters == null ? new HashSet<>() : getCharacters();
        this.setCharacters(charactersCollection.stream().filter(p -> !character.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList()));
        return true;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        hcb.append(getDescription());
        hcb.append(getCharacters());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CharacterGroup)) {
            return false;
        }
        final CharacterGroup other = (CharacterGroup) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getDescription(), other.getDescription());
        if (this.getCharacters() == null || other.getCharacters() == null) {
            eqb.append(this.getCharacters(), other.getCharacters());
        } else {
            eqb.append(true, this.getCharacters().containsAll(other.getCharacters()));
            eqb.append(true, other.getCharacters().containsAll(this.getCharacters()));
        }
        return eqb.isEquals();
    }

    @Override
    public int compareTo(CharacterGroup other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getDescription(), other.getDescription());
        if (this.getCharacters() == null || other.getCharacters() == null) {
            ctb.append(this.getCharacters(), other.getCharacters());
        } else {
            ctb.append(this.getCharacters().size(), other.getCharacters().size());
            ctb.append(true, this.getCharacters().containsAll(other.getCharacters()));
            ctb.append(true, other.getCharacters().containsAll(this.getCharacters()));
        }
        return ctb.toComparison();
    }

}
