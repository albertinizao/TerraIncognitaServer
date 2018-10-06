package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document
public class Role implements Comparable<Role>, GrantedAuthority {
    @Id
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

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

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getName());
        hcb.append(getDescription());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Role)) {
            return false;
        }
        final Role other = (Role) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getDescription(), other.getDescription());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Role other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getDescription(), other.getDescription());
        return ctb.toComparison();
    }

    @Override
    public String getAuthority() {
        return getName();
    }

}
