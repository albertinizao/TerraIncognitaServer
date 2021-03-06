package com.opipo.terraincognitaserver.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("EventInscription autogenerated")
public class EventInscriptionTest {

    private EventInscription eventInscription;

    @BeforeEach
    public void init() {
        eventInscription = new EventInscription();
    }

    @Test
    @DisplayName("The getter and the setter of id work well")
    public void idAttributeTest() {
        EventInscriptionId id = this.buildEventInscriptionId();
        eventInscription.setId(id);
        assertEquals(id, eventInscription.getId());
    }

    private EventInscriptionId buildEventInscriptionId() {
        return new EventInscriptionId();
    }

    @Test
    @DisplayName("The getter and the setter of requestedCharacters work well")
    public void requestedCharactersAttributeTest() {
        List<CharacterRequest> requestedCharacters = new ArrayList<CharacterRequest>();
        eventInscription.setRequestedCharacters(requestedCharacters);
        assertEquals(requestedCharacters, eventInscription.getRequestedCharacters());
    }

    @Test
    @DisplayName("The getter and the setter of paymentNotes work well")
    public void paymentNotesAttributeTest() {
        String paymentNotes = Integer.toString(1);
        eventInscription.setPaymentNotes(paymentNotes);
        assertEquals(paymentNotes, eventInscription.getPaymentNotes());
    }

    @Test
    @DisplayName("The getter and the setter of assignedCharacterGroup work well")
    public void assignedCharacterGroupAttributeTest() {
        String assignedCharacterGroup = "Soldados";
        eventInscription.setAssignedCharacterGroup(assignedCharacterGroup);
        assertEquals(assignedCharacterGroup, eventInscription.getAssignedCharacterGroup());
    }

    @Test
    @DisplayName("The getter and the setter of assignedCharacter work well")
    public void assignedCharacterAttributeTest() {
        String assignedCharacter = "Menganito";
        eventInscription.setAssignedCharacter(assignedCharacter);
        assertEquals(assignedCharacter, eventInscription.getAssignedCharacter());
    }

    @Test
    @DisplayName("The getter and the setter of partner work well")
    public void partnerAttributeTest() {
        Boolean partner = Boolean.valueOf(true);
        eventInscription.setPartner(partner);
        assertEquals(partner, eventInscription.getPartner());
    }

    @Test
    public void givenSameObjReturnThatTheyAreEquals() {
        EventInscription o1 = new EventInscription();
        EventInscription o2 = new EventInscription();
        assertEquals(o1, o2);
    }

    @Test
    public void givenSameObjReturnZero() {
        EventInscription o1 = new EventInscription();
        EventInscription o2 = new EventInscription();
        assertEquals(0, o1.compareTo(o2));
    }

    @Test
    public void givenObjectFromOtherClassReturnThatTheyArentEquals() {
        EventInscription o1 = new EventInscription();
        assertNotEquals(o1, "");
    }

    @Test
    public void givenSameObjReturnSameHashCode() {
        EventInscription o1 = new EventInscription();
        EventInscription o2 = new EventInscription();
        assertEquals(o1.hashCode(), o2.hashCode());
    }

}