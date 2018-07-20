package com.opipo.terraincognitaserver.dto;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Embeddable
public class EventInscriptionId implements Comparable<EventInscriptionId>, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6495375032740680487L;

    @NotEmpty
    private String username;
    @NotEmpty
    private String event;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getUsername());
        hcb.append(getEvent());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof EventInscriptionId)) {
            return false;
        }
        final EventInscriptionId other = (EventInscriptionId) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getUsername(), other.getUsername());
        eqb.append(this.getEvent(), other.getEvent());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(EventInscriptionId other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getUsername(), other.getUsername());
        ctb.append(this.getEvent(), other.getEvent());
        return ctb.toComparison();
    }
}
