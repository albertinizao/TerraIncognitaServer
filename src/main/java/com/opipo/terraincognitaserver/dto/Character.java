package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Character implements Comparable<Character> {
    @NotNull
    private String name;
    private String description;
    private CharacterType characterType;

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

    public CharacterType getCharacterType() {
        return characterType;
    }

    public void setCharacterType(CharacterType characterType) {
        this.characterType = characterType;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        hcb.append(getDescription());
        hcb.append(getCharacterType());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Character)) {
            return false;
        }
        final Character other = (Character) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getDescription(), other.getDescription());
        eqb.append(this.getCharacterType(), other.getCharacterType());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Character other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getDescription(), other.getDescription());
        ctb.append(this.getCharacterType(), other.getCharacterType());
        return ctb.toComparison();
    }

}
