package com.opipo.terraincognitaserver.it;

import static org.junit.Assert.assertEquals;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.opipo.terraincognitaserver.dto.User;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserValidationStep extends CucumberRoot {

    @Autowired
    private Validator validator;

    User user = new User();

    private Boolean result = false;

    private User getUser(String username) {
        if (this.user == null) {
            this.user = new User();
        }
        return this.user;
    }

    @Given("^user (.*) has username (.*)$")
    public void setUsername(String user, String username) {
        getUser(user).setUsername(username);
    }

    @Given("^user (.*) has password (.*)$")
    public void setPassword(String user, String password) {
        getUser(user).setPassword(password);
    }

    @Given("^user (.*) has name (.*)$")
    public void setName(String user, String name) {
        getUser(user).setName(name);
    }

    @Given("^user (.*) has surname (.*)$")
    public void setSurname(String user, String surname) {
        getUser(user).setSurname(surname);
    }

    @Given("^user (.*) has dni (.*)$")
    public void setDNI(String user, String dni) {
        getUser(user).setDni(dni);
    }

    @Given("^user (.*) has email (.*)$")
    public void setEmail(String user, String email) {
        getUser(user).setEmail(email);
    }

    @Given("^user (.*) has phone (.*)$")
    public void setPhone(String user, String phone) {
        getUser(user).setPhone(phone);
    }

    @Given("^user (.*) has birthDate (.*)$")
    public void setBirthDate(String user, String birthDate) {
        getUser(user).setBirthDate(Long.valueOf(birthDate));
    }

    @Given("^user (.*) has medicalInformation (.*)$")
    public void setMedicalInformation(String user, String medicalInformation) {
        getUser(user).setMedicalInformation(medicalInformation);
    }

    @When("^Validate the user (.*)$")
    public void validate(String user) {
        java.util.Set<javax.validation.ConstraintViolation<User>> validation = validator.validate(getUser(user));
        if (null != validation && !validation.isEmpty()) {
            result = false;
        } else {
            result = true;
        }
    }

    @Then("^the validation is (.*)$")
    public void checkResult(String resultExpected) {
        assertEquals("The result is not the expected", Boolean.valueOf(resultExpected), result);
    }

}