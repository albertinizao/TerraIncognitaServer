package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Location implements Comparable<Location> {
    @Id
    @NotEmpty
    private String name;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        hcb.append(getLatitude());
        hcb.append(getLongitude());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Location)) {
            return false;
        }
        final Location other = (Location) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getLatitude(), other.getLatitude());
        eqb.append(this.getLongitude(), other.getLongitude());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Location other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getLatitude(), other.getLatitude());
        ctb.append(this.getLongitude(), other.getLongitude());
        return ctb.toComparison();
    }
}
