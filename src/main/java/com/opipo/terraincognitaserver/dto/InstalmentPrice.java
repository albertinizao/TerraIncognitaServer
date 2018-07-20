package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.opipo.terraincognitaserver.validation.constraint.DateAfterTodayConstraint;

public class InstalmentPrice implements Comparable<InstalmentPrice> {
    @Max(100)
    private Double percent;
    @NotNull
    @DateAfterTodayConstraint
    private Long lastDate;

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Long getLastDate() {
        return lastDate;
    }

    public void setLastDate(Long lastDate) {
        this.lastDate = lastDate;
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getPercent());
        hcb.append(getLastDate());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof InstalmentPrice)) {
            return false;
        }
        final InstalmentPrice other = (InstalmentPrice) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getPercent(), other.getPercent());
        eqb.append(this.getLastDate(), other.getLastDate());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(InstalmentPrice other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getLastDate(), other.getLastDate());
        ctb.append(this.getPercent(), other.getPercent());
        return ctb.toComparison();
    }

}
