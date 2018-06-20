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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.opipo.terraincognitaserver.dto.Role;
import com.opipo.terraincognitaserver.dto.User;
import com.opipo.terraincognitaserver.security.Constants;
import com.opipo.terraincognitaserver.security.Usuario;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserStep extends CucumberRoot {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private List<User> usersInserted = new ArrayList<>();

    private List<Role> rolesInserted = new ArrayList<>();

    private User user;

    private Role role;

    protected ResponseEntity<?> response; // output

    private String auth;

    private User buildUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setName("Name " + username);

        user.setPassword(bCryptPasswordEncoder.encode("password"));

        user.setSurname("surname");
        user.setDni("20582770R");
        user.setEmail("ender@fi.com");
        user.setPhone("656565656");
        user.setBirthDate(1356048000000L);
        user.setMedicalInformation("medicalInformation");
        return user;
    }

    private Role buildRole(String name) {
        Role role = new Role();
        role.setName(name);
        role.setDescription("Description " + name);
        return role;
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

    @Given("^role (.*) exists in DB$")
    public void insertRole(String name) {
        Role role = buildRole(name);
        mongoTemplate.save(role);
        rolesInserted.add(role);
    }

    @Given("^user (.*) has role (.*)$")
    public void userWithRol(String username, String rolename) {
        User user = mongoTemplate.findById(username, User.class);
        Role role = mongoTemplate.findById(rolename, Role.class);
        user.addRole(role);
        mongoTemplate.save(user);
    }

    @Given("^client is authenticated with user (.*)$")
    public void loginUser(String username) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword("password");
        ResponseEntity<String> loginResponse = template.postForEntity(Constants.LOGIN_URL, usuario, String.class);
        List<String> auths = loginResponse.getHeaders().get("Authorization");
        auth = auths.isEmpty() ? null : auths.get(0);
    }

    @When("^the client build user (.*)")
    public void buildUserStep(String username) {
        this.user = buildUser(username);
    }

    @When("^the client build role (.*)")
    public void buildRoleStep(String name) {
        this.role = buildRole(name);
    }

    @When("^the client build without pass user (.*)")
    public void buildWithoutPass(String username) {
        this.user = buildUser(username);
        this.user.setPassword("password");
    }

    @When("^the client modify user (.*)")
    public void modifyUser(String username) {
        this.user = buildUser(username);
        modifyUser(this.user);
    }

    @When("^the client modify role (.*)")
    public void modifyRole(String name) {
        this.role = buildRole(name);
        modifyRole(this.role);
    }

    private void modifyUser(User user) {
        user.setSurname("modified");
    }

    private void modifyRole(Role role) {
        role.setDescription("Modified description " + role.getName());
    }

    private <T> HttpEntity<T> buildRequest(T requestValue) {
        HttpHeaders headers = new HttpHeaders();
        if (auth != null) {
            headers.set("Authorization", auth);
        }
        HttpEntity<T> request = new HttpEntity<>(requestValue, headers);
        return request;
    }

    @When("^the client calls user (.*)$")
    public void getUserStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), User.class);
    }

    @When("^the client calls role (.*)$")
    public void getRole(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Role.class);
    }

    @When("^the client post user (.*)$")
    public void postUser(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(user), User.class);
    }

    @When("^the client put user (.*)$")
    public void putUser(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(user), User.class);
    }

    @When("^the client post role (.*)$")
    public void postRole(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(role), Role.class);
    }

    @When("^the client put role (.*)$")
    public void putRole(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(role), Role.class);
    }

    @When("^the client delete (.*)$")
    public void delete(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.DELETE, buildRequest(null), String.class);
    }

    @When("^the client get user list$")
    public void getUserList() throws Throwable {
        response = template.exchange("/user", HttpMethod.GET, buildRequest((String) null), User[].class);
    }

    @When("^the client get role list$")
    public void getRoleList() throws Throwable {
        response = template.exchange("/role", HttpMethod.GET, buildRequest((String) null), Role[].class);
    }

    @When("^the client get (.*) user$")
    public void getUser(String username) throws Throwable {
        response = template.exchange("/user/" + username, HttpMethod.GET, buildRequest((String) null), User.class);
    }

    @When("^the client get role user list from user (.*)$")
    public void getRoleUserList(String user) {
        response = template.exchange("/user/" + user + "/role", HttpMethod.GET, buildRequest((String) null),
                Role[].class);
    }

    @When("^client add role (.*) to user (.*)$")
    public void addRoleToUser(String role, String user) {
        response = template.exchange("/user/" + user + "/role/" + role, HttpMethod.POST, buildRequest((String) null),
                User.class);
    }

    @When("^the client get single role (.*) from user (.*)$")
    public void getRoleUser(String role, String user) {
        response = template.exchange("/user/" + user + "/role/" + role, HttpMethod.GET, buildRequest((String) null),
                Role.class);
    }

    @When("^the client change the password of (.*) to (.*)$")
    public void changePassword(String username, String password) throws Throwable {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(password);
        usuario.setOldPassword("password");
        response = template.exchange("/user/" + username + "/changePassword", HttpMethod.PUT, buildRequest(usuario),
                User.class);
    }

    @Then("^the client receives response status code of (\\d+)$")
    public void checkStatus(int statusCode) throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertThat("status code is incorrect : " + response.getBody(), currentStatusCode.value(), is(statusCode));
    }

    @Then("^the client receives a empty list in response$")
    public void checkListIsEmpty() {
        List<Object> received = Arrays.asList((Object[]) response.getBody());
        assertThat("The list must be empty: " + received.size(), received.isEmpty(), is(Boolean.TRUE));
    }

    @Then("^the client receives a list with all the inserted users$")
    public void checkCompleteUserList() {
        List<User> usersReceived = Arrays.asList((User[]) response.getBody());
        assertEquals("The response has incorrect size", usersInserted.size(), usersReceived.size());
        assertTrue("The response hasn't the expected values", usersInserted.containsAll(usersReceived));
        assertTrue("The response hasn't the expected values", usersReceived.containsAll(usersInserted));
    }

    @Then("^the client receives a list with all the inserted roles$")
    public void checkCompleteRoleList() {
        List<Role> received = Arrays.asList((Role[]) response.getBody());
        assertEquals("The response has incorrect size", rolesInserted.size(), received.size());
        assertTrue("The response hasn't the expected values", rolesInserted.containsAll(received));
        assertTrue("The response hasn't the expected values", received.containsAll(rolesInserted));
    }

    @Then("^the client receives (.*) user$")
    public void checkOneUser(String username) {
        User expected = buildUser(username);
        User userReceived = (User) response.getBody();
        assertEquals("The response isn't the expected", expected, userReceived);
    }

    @Then("^the client receives (.*) role$")
    public void checkOneURole(String name) {
        Role expected = buildRole(name);
        if (Role.class.isAssignableFrom(response.getBody().getClass())) {
            Role received = (Role) response.getBody();
            assertEquals("The response isn't the expected", expected, received);
        } else {
            List<Role> received = Arrays.asList((Role[]) response.getBody());
            assertTrue("The response isn't the expected", received.contains(expected));
        }
    }

    @Then("^the user (.*) has role (.*)$")
    public void checkUserHasRole(String username, String role) {
        Role expected = buildRole(role);
        User user = mongoTemplate.findById(username, User.class);
        assertTrue("The role hasn't been added to user", user.getRoles().contains(expected));
    }

    @Then("^the client receives (.*) user modified$")
    public void checkModifiedUser(String username) {
        User expected = buildUser(username);
        modifyUser(expected);
        User userReceived = (User) response.getBody();
        assertEquals("The response isn't the expected", expected, userReceived);
    }

    @Then("^the client receives (.*) role modified$")
    public void checkModifiedRole(String name) {
        Role expected = buildRole(name);
        modifyRole(expected);
        Role received = (Role) response.getBody();
        assertEquals("The response isn't the expected", expected, received);
    }

    @Then("^the client don't receives user$")
    public void checkNoUser() {
        User userReceived = (User) response.getBody();
        assertNull("The response isn't the expected", userReceived);
    }

    @Then("^the client don't receives role$")
    public void checkNoRole() {
        Role received = (Role) response.getBody();
        assertNull("The response isn't the expected", received);
    }

    @Then("^the user (.*) is not persisted")
    public void checkUserNotPersisted(String username) {
        assertNull(getUserFromDB(username));
    }

    @Then("^the role (.*) is not persisted")
    public void checkRoleNotPersisted(String name) {
        assertNull(getRoleFromDB(name));
    }

    @Then("^the user (.*) is in the DB")
    public void checkUserWithDB(String username) {
        assertEquals("The persisted element is not the expected", buildUser(username), getUserFromDB(username));
    }

    @Then("^the role (.*) is in the DB")
    public void checkRoleWithDB(String role) {
        assertEquals("The persisted element is not the expected", buildRole(role), getRoleFromDB(role));
    }

    @Then("^the user (.*) is modified in the DB")
    public void checkUserWithDBModified(String username) {
        User user = buildUser(username);
        modifyUser(user);
        assertEquals("The persisted element is not the expected", user, getUserFromDB(username));
    }

    @Then("^the role (.*) is modified in the DB")
    public void checkRoleWithDBModified(String name) {
        Role role = buildRole(name);
        modifyRole(role);
        assertEquals("The persisted element is not the expected", role, getRoleFromDB(name));
    }

    @Then("^the password of (.*) is (.*)$")
    public void checkPassword(String username, String password) {
        User user = getUserFromDB(username);
        assertTrue("The password is incorrect", bCryptPasswordEncoder.matches(password, user.getPassword()));
    }

    private User getUserFromDB(String username) {
        return mongoTemplate.findById(username, User.class);
    }

    private Role getRoleFromDB(String name) {
        return mongoTemplate.findById(name, Role.class);
    }

}