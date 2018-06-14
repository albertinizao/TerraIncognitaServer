package com.opipo.terraincognitaserver.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.opipo.terraincognitaserver.dto.User;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStep extends CucumberRoot {

    @Autowired
    private MongoTemplate mongoTemplate;

    private List<User> usersInserted = new ArrayList<>();

    private User user;

    protected ResponseEntity<?> response; // output

    private static User buildUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setName("Name " + username);
        user.setPassword("password");
        user.setSurname("surname");
        user.setDni("20582770R");
        user.setEmail("ender@fi.com");
        user.setPhone("656565656");
        user.setBirthDate(1356048000000L);
        user.setMedicalInformation("medicalInformation");
        return user;
    }

    @Given("^database (.*) is clean$")
    public void cleanDatabase(String database) {
        usersInserted.clear();
        List<String> names = new ArrayList<>();
        mongoTemplate.getDb().listCollectionNames().iterator().forEachRemaining(c -> names.add(c));
        mongoTemplate.getDb().getCollection(database).drop();
    }

    @Given("^user (.*) exists in DB$")
    public void insertUser(String username) {
        User user = buildUser(username);
        mongoTemplate.save(user);
        usersInserted.add(user);
    }

    @When("^the client build user (.*)")
    public void build(String username) {
        this.user = buildUser(username);
    }

    @When("^the client modify user (.*)")
    public void modify(String username) {
        this.user = buildUser(username);
        modifyUser(this.user);
    }

    private void modifyUser(User user) {
        user.setSurname("modified");
    }

    @When("^the client calls (.*)$")
    public void get(String endpoint) throws Throwable {
        response = template.getForEntity(endpoint, String.class);
    }

    @When("^the client post (.*)$")
    public void post(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, user, User.class);
    }

    @When("^the client put (.*)$")
    public void put(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, new HttpEntity<User>(user), User.class);
    }

    @When("^the client delete (.*)$")
    public void delete(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
    }

    @When("^the client get user list$")
    public void getList() throws Throwable {
        response = template.getForEntity("/user", User[].class);
    }

    @When("^the client get (.*) user$")
    public void getUser(String username) throws Throwable {
        response = template.getForEntity("/user/" + username, User.class);
    }

    @Then("^the client receives response status code of (\\d+)$")
    public void checkStatus(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertThat("status code is incorrect : " + response.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("^the client receives a empty list in response$")
    public void checkListIsEmpty() {
        List<User> usersReceived = Arrays.asList((User[]) response.getBody());
        assertThat("The list must be empty: " + usersReceived.size(), usersReceived.isEmpty(), is(Boolean.TRUE));
    }

    @Then("^the client receives a list with all the inserted users$")
    public void checkCompleteList() {
        List<User> usersReceived = Arrays.asList((User[]) response.getBody());
        assertEquals("The response has incorrect size", usersInserted.size(), usersReceived.size());
        assertTrue("The response hasn't the expected values", usersInserted.containsAll(usersReceived));
        assertTrue("The response hasn't the expected values", usersReceived.containsAll(usersInserted));
    }

    @Then("^the client receives (.*) user$")
    public void checkOneUser(String username) {
        User expected = buildUser(username);
        User userReceived = (User) response.getBody();
        assertEquals("The response isn't the expected", expected, userReceived);
    }

    @Then("^the client receives (.*) user modified$")
    public void checkModifiedUser(String username) {
        User expected = buildUser(username);
        modifyUser(expected);
        User userReceived = (User) response.getBody();
        assertEquals("The response isn't the expected", expected, userReceived);
    }

    @Then("^the client don't receives user$")
    public void checkNoUser() {
        User userReceived = (User) response.getBody();
        assertNull("The response isn't the expected", userReceived);
    }

    @Then("^the user (.*) is not persisted")
    public void checkNotPersisted(String username) {
        assertNull(getUserFromDB(username));
    }

    @Then("^the user (.*) is in the DB")
    public void checkWithDB(String username) {
        assertEquals("The persisted element is not the expected", buildUser(username), getUserFromDB(username));
    }

    @Then("^the user (.*) is modified in the DB")
    public void checkWithDBModified(String username) {
        User user = buildUser(username);
        modifyUser(user);
        assertEquals("The persisted element is not the expected", user, getUserFromDB(username));
    }

    private User getUserFromDB(String username) {
        return mongoTemplate.findById(username, User.class);
    }

}