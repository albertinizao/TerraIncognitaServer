package com.opipo.terraincognitaserver.dto;

import java.util.List;

import javax.persistence.EmbeddedId;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EventInscription implements Owneable, Comparable<EventInscription> {
    @EmbeddedId
    private EventInscriptionId id;
    @NotEmpty
    private List<@Valid CharacterRequest> requestedCharacters;
    private String paymentNotes;
    private String assignedCharacterGroup;
    private String assignedCharacter;
    private Boolean npc;
    private Boolean partner;

    public EventInscriptionId getId() {
        return id;
    }

    public void setId(EventInscriptionId id) {
        this.id = id;
    }

    public List<CharacterRequest> getRequestedCharacters() {
        return requestedCharacters;
    }

    public void setRequestedCharacters(List<CharacterRequest> requestedCharacters) {
        this.requestedCharacters = requestedCharacters;
    }

    public String getPaymentNotes() {
        return paymentNotes;
    }

    public void setPaymentNotes(String paymentNotes) {
        this.paymentNotes = paymentNotes;
    }

    public String getAssignedCharacterGroup() {
        return assignedCharacterGroup;
    }

    public void setAssignedCharacterGroup(String assignedCharacterGroup) {
        this.assignedCharacterGroup = assignedCharacterGroup;
    }

    public String getAssignedCharacter() {
        return assignedCharacter;
    }

    public void setAssignedCharacter(String assignedCharacter) {
        this.assignedCharacter = assignedCharacter;
    }

    public Boolean getPartner() {
        return partner;
    }

    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    public Boolean getNpc() {
        return npc;
    }

    public Boolean isNpc() {
        return getNpc();
    }

    public void setNpc(Boolean npc) {
        this.npc = npc;
    }

    public Boolean isPartner() {
        return getPartner();
    }

    @Override
    public String getOwner() {
        return id.getUsername();
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getId());
        hcb.append(getRequestedCharacters());
        hcb.append(getPaymentNotes());
        hcb.append(getAssignedCharacter());
        hcb.append(isPartner());
        hcb.append(isNpc());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EventInscription)) {
            return false;
        }
        final EventInscription other = (EventInscription) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getId(), other.getId());
        eqb.append(this.getRequestedCharacters(), other.getRequestedCharacters());
        eqb.append(this.getPaymentNotes(), other.getPaymentNotes());
        eqb.append(this.getAssignedCharacter(), other.getAssignedCharacter());
        eqb.append(this.isPartner(), other.isPartner());
        eqb.append(this.isNpc(), other.isNpc());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(EventInscription other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getId(), other.getId());
        ctb.append(this.getRequestedCharacters(), other.getRequestedCharacters());
        ctb.append(this.getPaymentNotes(), other.getPaymentNotes());
        ctb.append(this.getAssignedCharacter(), other.getAssignedCharacter());
        ctb.append(this.isPartner(), other.isPartner());
        ctb.append(this.isNpc(), other.isNpc());
        return ctb.toComparison();
    }
}
