package com.opipo.terraincognitaserver.dto;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class CharacterRequest implements Comparable<CharacterRequest> {
    private Integer order;
    private String character;
    private String description;
    private Boolean assigned;

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

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getOrder());
        hcb.append(getCharacter());
        hcb.append(getDescription());
        hcb.append(isAssigned());
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
        eqb.append(this.getCharacter(), other.getCharacter());
        eqb.append(this.getDescription(), other.getDescription());
        eqb.append(this.isAssigned(), other.isAssigned());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(CharacterRequest other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.isAssigned(), other.isAssigned());
        ctb.append(this.getOrder(), other.getOrder());
        ctb.append(this.getCharacter(), other.getCharacter());
        ctb.append(this.getDescription(), other.getDescription());
        return ctb.toComparison();
    }
}
