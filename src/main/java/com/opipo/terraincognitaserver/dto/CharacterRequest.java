package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CharacterRequest implements Comparable<CharacterRequest> {
    @NotNull
    @Min(0)
    private Integer order;
    @NotEmpty
    private String characterGroup;
    @NotEmpty
    private String character;
    private String description;
    private Boolean assigned;
    private Boolean npcPreference;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getCharacterGroup() {
        return characterGroup;
    }

    public void setCharacterGroup(String characterGroup) {
        this.characterGroup = characterGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAssigned() {
        return assigned;
    }

    public Boolean isAssigned() {
        return getAssigned();
    }

    public void setAssigned(Boolean assigned) {
        this.assigned = assigned;
    }

    public Boolean getNpcPreference() {
        return npcPreference;
    }

    public Boolean isNpcPreference() {
        return getNpcPreference();
    }

    public void setNpcPreference(Boolean npcPreference) {
        this.npcPreference = npcPreference;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getOrder());
        hcb.append(getCharacterGroup());
        hcb.append(getCharacter());
        hcb.append(getDescription());
        hcb.append(isAssigned());
        hcb.append(isNpcPreference());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CharacterRequest)) {
            return false;
        }
        final CharacterRequest other = (CharacterRequest) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getOrder(), other.getOrder());
        eqb.append(this.getCharacterGroup(), other.getCharacterGroup());
        eqb.append(this.getCharacter(), other.getCharacter());
        eqb.append(this.getDescription(), other.getDescription());
        eqb.append(this.isAssigned(), other.isAssigned());
        eqb.append(this.isNpcPreference(), other.isNpcPreference());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(CharacterRequest other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.isAssigned(), other.isAssigned());
        ctb.append(this.getOrder(), other.getOrder());
        ctb.append(this.getCharacterGroup(), other.getCharacterGroup());
        ctb.append(this.getCharacter(), other.getCharacter());
        ctb.append(this.getDescription(), other.getDescription());
        ctb.append(this.isNpcPreference(), other.isNpcPreference());
        return ctb.toComparison();
    }
}
