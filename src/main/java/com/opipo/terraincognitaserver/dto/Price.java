package com.opipo.terraincognitaserver.dto;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.opipo.terraincognitaserver.validation.constraint.DateAfterTodayConstraint;

public class Price implements Comparable<Price> {
    @NotNull
    private Double totalPrice;
    @NotNull
    private Double partnerDiscount;
    @NotNull
    private Double npcDiscount;
    @NotNull
    private Double inscriptionPrice;
    @NotNull
    @DateAfterTodayConstraint
    private Long inscriptionLastDate;
    private Collection<@Valid InstalmentPrice> instalementPrices;

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getPartnerDiscount() {
        return partnerDiscount;
    }

    public void setPartnerDiscount(Double partnerDiscount) {
        this.partnerDiscount = partnerDiscount;
    }

    public Double getNpcDiscount() {
        return npcDiscount;
    }

    public void setNpcDiscount(Double npcDiscount) {
        this.npcDiscount = npcDiscount;
    }

    public Double getInscriptionPrice() {
        return inscriptionPrice;
    }

    public void setInscriptionPrice(Double inscriptionPrice) {
        this.inscriptionPrice = inscriptionPrice;
    }

    public Long getInscriptionLastDate() {
        return inscriptionLastDate;
    }

    public void setInscriptionLastDate(Long inscriptionLastDate) {
        this.inscriptionLastDate = inscriptionLastDate;
    }

    public Collection<InstalmentPrice> getInstalementPrices() {
        return instalementPrices == null ? null : new ArrayList<>(instalementPrices);
    }

    public void setInstalementPrices(Collection<InstalmentPrice> instalementPrices) {
        this.instalementPrices = instalementPrices == null ? null : new ArrayList<>(instalementPrices);
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getTotalPrice());
        hcb.append(getPartnerDiscount());
        hcb.append(getNpcDiscount());
        hcb.append(getInscriptionPrice());
        hcb.append(getInscriptionLastDate());
        hcb.append(getInstalementPrices());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Price)) {
            return false;
        }
        final Price other = (Price) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getTotalPrice(), other.getTotalPrice());
        eqb.append(this.getPartnerDiscount(), other.getPartnerDiscount());
        eqb.append(this.getNpcDiscount(), other.getNpcDiscount());
        eqb.append(this.getInscriptionPrice(), other.getInscriptionPrice());
        eqb.append(this.getInscriptionLastDate(), other.getInscriptionLastDate());
        if (this.getInstalementPrices() != null && other.getInstalementPrices() != null) {
            eqb.append(this.getInstalementPrices().size(), other.getInstalementPrices().size());
            eqb.append(true, this.getInstalementPrices().containsAll(other.getInstalementPrices()));
            eqb.append(true, other.getInstalementPrices().containsAll(this.getInstalementPrices()));
        } else {
            eqb.append(this.getInstalementPrices() == null, other.getInstalementPrices() == null);
        }
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Price other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getTotalPrice(), other.getTotalPrice());
        ctb.append(this.getPartnerDiscount(), other.getPartnerDiscount());
        ctb.append(this.getNpcDiscount(), other.getNpcDiscount());
        ctb.append(this.getInscriptionPrice(), other.getInscriptionPrice());
        ctb.append(this.getInscriptionLastDate(), other.getInscriptionLastDate());
        ctb.append(this.getInstalementPrices(), other.getInstalementPrices());
        return ctb.toComparison();
    }

}
