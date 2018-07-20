package com.opipo.terraincognitaserver.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.opipo.terraincognitaserver.validation.constraint.DateAfterTodayConstraint;

@Document
public class Payment implements Comparable<Payment> {
    @Id
    @NotNull
    private Long id;
    @NotEmpty
    private String userId;
    private String eventId;
    @NotEmpty
    private String description;
    @NotNull
    private Double amount;
    @NotNull
    @DateAfterTodayConstraint
    private Long lastDate;
    private Boolean paid = false;

    public static final String SEQUENCE = "Payment_seq";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public Boolean isPaid() {
        return this.getPaid();
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
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
        hcb.append(getId());
        hcb.append(getUserId());
        hcb.append(getEventId());
        hcb.append(getDescription());
        hcb.append(getAmount());
        hcb.append(isPaid());
        hcb.append(getLastDate());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Payment)) {
            return false;
        }
        final Payment other = (Payment) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getId(), other.getId());
        eqb.append(this.getUserId(), other.getUserId());
        eqb.append(this.getEventId(), other.getEventId());
        eqb.append(this.getDescription(), other.getDescription());
        eqb.append(this.getAmount(), other.getAmount());
        eqb.append(this.isPaid(), other.isPaid());
        eqb.append(this.getLastDate(), other.getLastDate());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(Payment other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getId(), other.getId());
        ctb.append(this.getUserId(), other.getUserId());
        ctb.append(this.getEventId(), other.getEventId());
        ctb.append(this.getDescription(), other.getDescription());
        ctb.append(this.getAmount(), other.getAmount());
        ctb.append(this.isPaid(), other.isPaid());
        ctb.append(this.getLastDate(), other.getLastDate());
        return ctb.toComparison();
    }
}
