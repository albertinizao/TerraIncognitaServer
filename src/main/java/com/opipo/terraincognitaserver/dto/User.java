package com.opipo.terraincognitaserver.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opipo.terraincognitaserver.validation.constraint.DateBeforeTodayConstraint;
import com.opipo.terraincognitaserver.validation.constraint.NIFConstraint;

@Document
public class User implements Owneable, Comparable<User> {
    @Id
    @NotEmpty
    private String username;
    @NotEmpty
    @Pattern(regexp = "(^.{4,})")
    private String password;
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NIFConstraint
    @NotEmpty
    private String dni;
    @Email
    @NotEmpty
    private String email;
    @Pattern(regexp = "(^$|[0-9]{9})")
    @NotEmpty
    private String phone;
    @DateBeforeTodayConstraint
    @NotNull
    private Long birthDate;
    private String medicalInformation;
    @JsonIgnore
    private List<Role> roles;

    @Override
    public String getOwner() {
        return getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }

    public String getMedicalInformation() {
        return medicalInformation;
    }

    public void setMedicalInformation(String medicalInformation) {
        this.medicalInformation = medicalInformation;
    }

    public List<Role> getRoles() {
        return this.roles == null ? new ArrayList<>() : new ArrayList<>(this.roles);
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles == null ? null : new ArrayList<>(roles);
    }

    public void addRole(Role role) {
        List<Role> roles = this.getRoles();
        roles.add(role);
        this.setRoles(roles);
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(getUsername());
        hcb.append(getName());
        hcb.append(getSurname());
        hcb.append(getDni());
        hcb.append(getEmail());
        hcb.append(getPhone());
        hcb.append(getBirthDate());
        hcb.append(getMedicalInformation());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        final User other = (User) object;
        final EqualsBuilder eqb = new EqualsBuilder();
        eqb.append(this.getUsername(), other.getUsername());
        eqb.append(this.getName(), other.getName());
        eqb.append(this.getSurname(), other.getSurname());
        eqb.append(this.getDni(), other.getDni());
        eqb.append(this.getEmail(), other.getEmail());
        eqb.append(this.getPhone(), other.getPhone());
        eqb.append(this.getBirthDate(), other.getBirthDate());
        eqb.append(this.getMedicalInformation(), other.getMedicalInformation());
        return eqb.isEquals();
    }

    @Override
    public int compareTo(User other) {
        final CompareToBuilder ctb = new CompareToBuilder();
        ctb.append(this.getUsername(), other.getUsername());
        ctb.append(this.getName(), other.getName());
        ctb.append(this.getSurname(), other.getSurname());
        ctb.append(this.getDni(), other.getDni());
        ctb.append(this.getEmail(), other.getEmail());
        ctb.append(this.getPhone(), other.getPhone());
        ctb.append(this.getBirthDate(), other.getBirthDate());
        ctb.append(this.getMedicalInformation(), other.getMedicalInformation());
        return ctb.toComparison();
    }
}
