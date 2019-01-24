package com.opipo.terraincognitaserver.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event implements Comparable<Event> {
    @Id
    @NotNull
    private String name;
    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;
    @NotNull
    private Long openDate;
    @NotNull
    private Long closeDate;
    @NotNull
    private Price price;
    @NotNull
    private Integer paymentPartition = 1;
    private Location location;
    private String image;
    @NotNull
    private Boolean secretNPC;
    private Collection<@Valid CharacterGroup> characterGroups;

    public Collection<@Valid CharacterGroup> getCharacterGroups() {
        return characterGroups == null ? null : new ArrayList<>(characterGroups);
    }

    public void setCharacterGroups(Collection<CharacterGroup> characterGroups) {
        this.characterGroups = characterGroups == null ? null : new ArrayList<>(characterGroups);
    }

    public boolean addCharacterGroup(CharacterGroup characterGroup) {
        Collection<CharacterGroup> characterGroups = this.characterGroups == null ? new ArrayList<>()
                : getCharacterGroups();
        boolean response = characterGroups.add(characterGroup);
        setCharacterGroups(characterGroups);
        return response;
    }

    public boolean removeCharacterGroup(String characterGroup) {
        this.setCharacterGroups(this.characterGroups.stream().filter(p -> !characterGroup.equalsIgnoreCase(p.getName()))
                .collect(Collectors.toList()));
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Long openDate) {
        this.openDate = openDate;
    }

    public Long getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Long closeDate) {
        this.closeDate = closeDate;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Integer getPaymentPartition() {
        return paymentPartition;
    }

    public void setPaymentPartition(Integer paymentPartition) {
        this.paymentPartition = paymentPartition;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isSecretNPC() {
        return secretNPC;
    }

    public void setSecretNPC(Boolean secretNPC) {
        this.secretNPC = secretNPC;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        hcb.append(getStartDate());
        hcb.append(getEndDate());
        hcb.append(getOpenDate());
        hcb.append(getCloseDate());
        hcb.append(getPrice());
        hcb.append(getPaymentPartition());
        hcb.append(getLocation());
        hcb.append(getImage());
        hcb.append(isSecretNPC());
        hcb.append(getCharacterGroups());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Event)) {
            return false;
        }
        final Event other = (Event) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getStartDate(), other.getStartDate());
        eqb.append(this.getEndDate(), other.getEndDate());
        eqb.append(this.getOpenDate(), other.getOpenDate());
        eqb.append(this.getCloseDate(), other.getCloseDate());
        eqb.append(this.getPrice(), other.getPrice());
        eqb.append(this.getPaymentPartition(), other.getPaymentPartition());
        eqb.append(this.getLocation(), other.getLocation());
        eqb.append(this.getImage(), other.getImage());
        eqb.append(this.isSecretNPC(), other.isSecretNPC());
        if (this.getCharacterGroups() == null || other.getCharacterGroups() == null) {
            eqb.append(this.getCharacterGroups(), other.getCharacterGroups());
        } else {
            eqb.append(true, this.getCharacterGroups().containsAll(other.getCharacterGroups()));
            eqb.append(true, other.getCharacterGroups().containsAll(this.getCharacterGroups()));
        }
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Event other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getStartDate(), other.getStartDate());
        ctb.append(this.getEndDate(), other.getEndDate());
        ctb.append(this.getOpenDate(), other.getOpenDate());
        ctb.append(this.getCloseDate(), other.getCloseDate());
        ctb.append(this.getPrice(), other.getPrice());
        ctb.append(this.getPaymentPartition(), other.getPaymentPartition());
        ctb.append(this.getLocation(), other.getLocation());
        ctb.append(this.getImage(), other.getImage());
        ctb.append(this.isSecretNPC(), other.isSecretNPC());
        if (this.getCharacterGroups() == null || other.getCharacterGroups() == null) {
            ctb.append(this.getCharacterGroups(), other.getCharacterGroups());
        } else {
            ctb.append(this.getCharacterGroups().size(), other.getCharacterGroups().size());
            ctb.append(true, this.getCharacterGroups().containsAll(other.getCharacterGroups()));
            ctb.append(true, other.getCharacterGroups().containsAll(this.getCharacterGroups()));
        }
        return ctb.toComparison();
    }
}
