package com.opipo.terraincognitaserver.it;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.opipo.terraincognitaserver.dto.Character;
import com.opipo.terraincognitaserver.dto.CharacterGroup;
import com.opipo.terraincognitaserver.dto.Event;
import com.opipo.terraincognitaserver.dto.Location;
import com.opipo.terraincognitaserver.dto.Payment;
import com.opipo.terraincognitaserver.dto.Price;
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

    private List<Location> locationsInserted = new ArrayList<>();

    private List<Event> eventsInserted = new ArrayList<>();

    private List<Payment> paymentsInserted = new ArrayList<>();

    private User user;

    private Role role;

    private Location location;

    private Event event;

    private Payment payment;

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

    private Location buildLocation(String name) {
        Location location = new Location();
        location.setName(name);
        location.setLatitude(-5.7440);
        location.setLongitude(-35.2654);
        return location;
    }

    private Event buildEvent(String name) {
        Event element = new Event();
        element.setName(name);
        element.setStartDate(1L);
        element.setEndDate(2L);
        element.setOpenDate(3L);
        element.setCloseDate(4L);
        Price price = new Price();
        price.setInscriptionLastDate(7_289_564_400_000L);
        price.setInscriptionPrice(5.5d);
        price.setNpcDiscount(2.0d);
        price.setPartnerDiscount(3.0d);
        price.setTotalPrice(20d);
        element.setPrice(price);
        element.setSecretNPC(false);
        return element;
    }

    private Payment buildPayment(String id) {
        Payment payment = new Payment();
        payment.setAmount(42d);
        payment.setDescription("Mydescription");
        payment.setEventId("eventId");
        payment.setPaid(false);
        payment.setUserId("userId");
        payment.setLastDate(7_289_564_300_000L);
        payment.setId(Long.valueOf(id));
        return payment;
    }

    private CharacterGroup buildCharacterGroup(String name) {
        CharacterGroup characterGroup = new CharacterGroup();
        characterGroup.setName(name);
        return characterGroup;
    }

    private com.opipo.terraincognitaserver.dto.Character buildCharacter(String name) {
        com.opipo.terraincognitaserver.dto.Character character = new com.opipo.terraincognitaserver.dto.Character();
        character.setName(name);
        return character;
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

    @Given("^location (.*) exists in DB$")
    public void insertLocation(String name) {
        Location location = buildLocation(name);
        mongoTemplate.save(location);
        locationsInserted.add(location);
    }

    @Given("^event (.*) exists in DB$")
    public void insertEvent(String name) {
        Event event = buildEvent(name);
        mongoTemplate.save(event);
        eventsInserted.add(event);
    }

    @Given("^payment (.*) exists in DB$")
    public void insertPayment(String id) {
        Payment payment = buildPayment(id);
        mongoTemplate.save(payment);
        paymentsInserted.add(payment);
    }

    @Given("^event (.*) exists with character-group (.*) in DB$")
    public void insertEvent(String name, String characterGroupName) {
        Event event = buildEvent(name);
        event.addCharacterGroup(buildCharacterGroup("fake"));
        event.addCharacterGroup(buildCharacterGroup(characterGroupName));
        event.addCharacterGroup(buildCharacterGroup("fake2"));
        mongoTemplate.save(event);
        eventsInserted.add(event);
    }

    @Given("^event (.*) exists with character (.*) in group (.*) in DB$")
    public void insertEvent(String name, String character, String characterGroupName) {
        Event event = buildEvent(name);
        event.addCharacterGroup(buildCharacterGroup("fake"));
        CharacterGroup characterGroup = buildCharacterGroup(characterGroupName);
        characterGroup.addCharacter(buildCharacter("fakeCharac"));
        characterGroup.addCharacter(buildCharacter(character));
        characterGroup.addCharacter(buildCharacter("fakeCharac2"));
        event.addCharacterGroup(characterGroup);
        event.addCharacterGroup(buildCharacterGroup("fake2"));
        mongoTemplate.save(event);
        eventsInserted.add(event);
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

    @When("^the client build location (.*)")
    public void buildLocationStep(String id) {
        this.location = buildLocation(id);
    }

    @When("^the client build event (.*)")
    public void buildEventStep(String id) {
        this.event = buildEvent(id);
    }

    @When("^the client build payment (.*)")
    public void buildPaymentStep(String id) {
        this.payment = buildPayment(id);
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

    @When("^the client modify location (.*)")
    public void modifyLocation(String username) {
        this.location = buildLocation(username);
        modifyLocation(this.location);
    }

    @When("^the client modify event (.*)")
    public void modifyEvent(String id) {
        this.event = buildEvent(id);
        modifyEvent(this.event);
    }

    @When("^the client modify payment (.*)")
    public void modifyPayment(String id) {
        this.payment = buildPayment(id);
        modifyPayment(this.payment);
    }

    private void modifyUser(User user) {
        user.setSurname("modified");
    }

    private void modifyRole(Role role) {
        role.setDescription("Modified description " + role.getName());
    }

    private void modifyLocation(Location location) {
        location.setLatitude(41.4869631);
        location.setLongitude(-5.4772884);
    }

    private void modifyEvent(Event element) {
        element.setImage("image");
    }

    private void modifyPayment(Payment element) {
        element.setPaid(true);
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

    @When("^the client calls location (.*)$")
    public void getLocationStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Location.class);
    }

    @When("^the client calls event (.*)$")
    public void getEventStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Event.class);
    }

    @When("^the client calls payment (.*)$")
    public void getPaymentsStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Payment.class);
    }

    @When("^the client calls event-characterGroup (.*)$")
    public void getEventCharacterGroupStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), CharacterGroup.class);
    }

    @When("^the client calls character (.*)$")
    public void getCharacterStep(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Character.class);
    }

    @When("^the client calls role (.*)$")
    public void getRole(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.GET, buildRequest((String) null), Role.class);
    }

    @When("^the client post user (.*)$")
    public void postUser(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(user), User.class);
    }

    @When("^the client post location (.*)$")
    public void postLocation(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(location), Location.class);
    }

    @When("^the client post event (.*)$")
    public void postEvent(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(event), Event.class);
    }

    @When("^the client post payment (.*)$")
    public void postPayment(String endpoint) throws Throwable {
        response = template.postForEntity(endpoint, buildRequest(event), Payment.class);
    }

    @When("^the client put user (.*)$")
    public void putUser(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(user), User.class);
    }

    @When("^the client put location (.*)$")
    public void putLocation(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(location), Location.class);
    }

    @When("^the client put event (.*)$")
    public void putEvent(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(event), Event.class);
    }

    @When("^the client put payment (.*)$")
    public void putPayment(String endpoint) throws Throwable {
        response = template.exchange(endpoint, HttpMethod.PUT, buildRequest(payment), Payment.class);
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

    @When("^the client get location list$")
    public void getLocationList() throws Throwable {
        response = template.exchange("/location", HttpMethod.GET, buildRequest((String) null), Location[].class);
    }

    @When("^the client get event list$")
    public void getEventList() throws Throwable {
        response = template.exchange("/event", HttpMethod.GET, buildRequest((String) null), Event[].class);
    }

    @When("^the client get payment list$")
    public void getPaymentList() throws Throwable {
        response = template.exchange("/payment", HttpMethod.GET, buildRequest((String) null), Payment[].class);
    }

    @When("^the client get event character group list with event id (.*)$")
    public void getEventCharacterGroupList(String eventId) throws Throwable {
        response = template.exchange("/event/" + eventId + "/characterGroup", HttpMethod.GET,
                buildRequest((String) null), CharacterGroup[].class);
    }

    @When("^the client calls list of characters (.*)$")
    public void getEventCharacterGroupCharacterList(String url) throws Throwable {
        response = template.exchange(url, HttpMethod.GET, buildRequest((String) null), Character[].class);
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

    @Then("^the client receives a list with all the inserted payments$")
    public void checkCompletePaymentsList() {
        List<Payment> received = Arrays.asList((Payment[]) response.getBody());
        assertEquals("The response has incorrect size", paymentsInserted.size(), received.size());
        assertTrue("The response hasn't the expected values", paymentsInserted.containsAll(received));
        assertTrue("The response hasn't the expected values", received.containsAll(paymentsInserted));
    }

    @Then("^the client receives a list with all the inserted events$")
    public void checkCompleteEventList() {
        List<Event> received = Arrays.asList((Event[]) response.getBody());
        assertEquals("The response has incorrect size", eventsInserted.size(), received.size());
        assertTrue("The response hasn't the expected values", eventsInserted.containsAll(received));
        assertTrue("The response hasn't the expected values", received.containsAll(eventsInserted));
    }

    @Then("^the client receives a list with all the inserted character groups$")
    public void checkCompleteCharacterGroupList() {
        List<CharacterGroup> received = Arrays.asList((CharacterGroup[]) response.getBody());
        assertEquals("The response has incorrect size", eventsInserted.get(0).getCharacterGroups().size(),
                received.size());
        assertTrue("The response hasn't the expected values",
                eventsInserted.get(0).getCharacterGroups().containsAll(received));
        assertTrue("The response hasn't the expected values",
                received.containsAll(eventsInserted.get(0).getCharacterGroups()));
    }

    @Then("^the client receives a list with all the inserted characters$")
    public void checkCompleteCharacterList() {
        List<Character> received = Arrays.asList((Character[]) response.getBody());
        assertEquals("The response has incorrect size", eventsInserted.get(0).getCharacterGroups().size(),
                received.size());
        Collection<Character> charactesInserted = eventsInserted.get(0).getCharacterGroups().stream()
                .filter(p -> p.getCharacters() != null && !p.getCharacters().isEmpty()).map(f -> f.getCharacters())
                .findFirst().get();
        assertTrue("The response hasn't the expected values", charactesInserted.containsAll(received));
        assertTrue("The response hasn't the expected values", received.containsAll(charactesInserted));
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

    @Then("^the client receives (.*) location$")
    public void checkOneLocation(String name) {
        Location expected = buildLocation(name);
        if (Location.class.isAssignableFrom(response.getBody().getClass())) {
            Location received = (Location) response.getBody();
            assertEquals("The response isn't the expected", expected, received);
        } else {
            List<Location> received = Arrays.asList((Location[]) response.getBody());
            assertTrue("The response isn't the expected", received.contains(expected));
        }
    }

    @Then("^the client receives (.*) event$")
    public void checkOneEvent(String name) {
        Event expected = buildEvent(name);
        if (Event.class.isAssignableFrom(response.getBody().getClass())) {
            Event received = (Event) response.getBody();
            assertEquals("The response isn't the expected", expected, received);
        } else {
            List<Event> received = Arrays.asList((Event[]) response.getBody());
            assertTrue("The response isn't the expected", received.contains(expected));
        }
    }

    @Then("^the client receives (.*) event character group$")
    public void checkOneEventCharacterGroup(String name) {
        CharacterGroup expected = buildCharacterGroup(name);
        if (CharacterGroup.class.isAssignableFrom(response.getBody().getClass())) {
            CharacterGroup received = (CharacterGroup) response.getBody();
            assertEquals("The response isn't the expected", expected, received);
        } else {
            List<CharacterGroup> received = Arrays.asList((CharacterGroup[]) response.getBody());
            assertTrue("The response isn't the expected", received.contains(expected));
        }
    }

    @Then("^the client receives (.*) payment$")
    public void checkOnePayment(String id) {
        Payment expected = buildPayment(id);
        if (Payment.class.isAssignableFrom(response.getBody().getClass())) {
            Payment received = (Payment) response.getBody();
            assertEquals("The response isn't the expected", expected, received);
        } else {
            List<Payment> received = Arrays.asList((Payment[]) response.getBody());
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

    @Then("^the client receives (.*) location modified$")
    public void checkModifiedLocation(String name) {
        Location expected = buildLocation(name);
        modifyLocation(expected);
        Location received = (Location) response.getBody();
        assertEquals("The response isn't the expected", expected, received);
    }

    @Then("^the client receives (.*) event modified$")
    public void checkModifiedEvent(String name) {
        Event expected = buildEvent(name);
        modifyEvent(expected);
        Event received = (Event) response.getBody();
        assertEquals("The response isn't the expected", expected, received);
    }

    @Then("^the client receives (.*) payment modified$")
    public void checkModifiedPayment(String id) {
        Payment expected = buildPayment(id);
        modifyPayment(expected);
        Payment received = (Payment) response.getBody();
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

    @Then("^the client don't receives location$")
    public void checkNoLocation() {
        Location received = (Location) response.getBody();
        assertNull("The response isn't the expected", received);
    }

    @Then("^the client don't receives event$")
    public void checkNoEvent() {
        Event received = (Event) response.getBody();
        assertNull("The response isn't the expected", received);
    }

    @Then("^the client don't receives payment$")
    public void checkNoPayment() {
        Payment received = (Payment) response.getBody();
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

    @Then("^the location (.*) is not persisted")
    public void checkLocationNotPersisted(String name) {
        assertNull(getLocationFromDB(name));
    }

    @Then("^the event (.*) is not persisted")
    public void checkEventNotPersisted(String name) {
        assertNull(getEventFromDB(name));
    }

    @Then("^the payment (.*) is not persisted")
    public void checkPaymentNotPersisted(String id) {
        assertNull(getPaymentFromDB(Long.valueOf(id)));
    }

    @Then("^the user (.*) is in the DB")
    public void checkUserWithDB(String username) {
        assertEquals("The persisted element is not the expected", buildUser(username), getUserFromDB(username));
    }

    @Then("^the role (.*) is in the DB")
    public void checkRoleWithDB(String role) {
        assertEquals("The persisted element is not the expected", buildRole(role), getRoleFromDB(role));
    }

    @Then("^the location (.*) is in the DB")
    public void checkLocationWithDB(String id) {
        assertEquals("The persisted element is not the expected", buildLocation(id), getLocationFromDB(id));
    }

    @Then("^the event (.*) is in the DB")
    public void checkEventWithDB(String id) {
        assertEquals("The persisted element is not the expected", buildEvent(id), getEventFromDB(id));
    }

    @Then("^the received payment is in the DB")
    public void checkPaymentWithDB() {
        assertNotNull("The persisted element is not the expected",
                getPaymentFromDB(((Payment) response.getBody()).getId()));
    }

    @Then("^the payment (.*) is in the DB")
    public void checkPaymentWithDB(String id) {
        assertNotNull("The persisted element is not the expected", getPaymentFromDB(Long.valueOf(id)));
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

    @Then("^the location (.*) is modified in the DB")
    public void checkLocationWithDBModified(String name) {
        Location location = buildLocation(name);
        modifyLocation(location);
        assertEquals("The persisted element is not the expected", location, getLocationFromDB(name));
    }

    @Then("^the event (.*) is modified in the DB")
    public void checkEventWithDBModified(String name) {
        Event element = buildEvent(name);
        modifyEvent(element);
        assertEquals("The persisted element is not the expected", element, getEventFromDB(name));
    }

    @Then("^the payment (.*) is modified in the DB")
    public void checkPaymentWithDBModified(String name) {
        Payment element = buildPayment(name);
        modifyPayment(element);
        assertEquals("The persisted element is not the expected", element, getPaymentFromDB(Long.valueOf(name)));
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

    private Location getLocationFromDB(String name) {
        return mongoTemplate.findById(name, Location.class);
    }

    private Event getEventFromDB(String name) {
        return mongoTemplate.findById(name, Event.class);
    }

    private Payment getPaymentFromDB(Long id) {
        return mongoTemplate.findById(id, Payment.class);
    }

}