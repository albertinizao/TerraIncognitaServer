package com.opipo.terraincognitaserver.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Location autogenerated")
public class LocationTest {

    private Location location;

    @BeforeEach
    public void init() {
        location = new Location();
    }

    @Test
    @DisplayName("The getter and the setter of name work well")
    public void nameAttributeTest() {
        String name = Integer.toString(1);
        location.setName(name);
        assertEquals(name, location.getName());
    }

    @Test
    @DisplayName("The getter and the setter of latitude work well")
    public void latitudeAttributeTest() {
        Double latitude = Double.valueOf(2D);
        location.setLatitude(latitude);
        assertEquals(latitude, location.getLatitude());
    }

    @Test
    @DisplayName("The getter and the setter of longitude work well")
    public void longitudeAttributeTest() {
        Double longitude = Double.valueOf(3D);
        location.setLongitude(longitude);
        assertEquals(longitude, location.getLongitude());
    }

    @Test
    public void givenSameObjReturnThatTheyAreEquals() {
        Location o1 = new Location();
        Location o2 = new Location();
        assertEquals(o1, o2);
    }

    @Test
    public void givenSameObjReturnZero() {
        Location o1 = new Location();
        Location o2 = new Location();
        assertEquals(0, o1.compareTo(o2));
    }

    @Test
    public void givenObjectFromOtherClassReturnThatTheyArentEquals() {
        Location o1 = new Location();
        assertNotEquals(o1, new String());
    }

    @Test
    public void givenSameObjReturnSameHashCode() {
        Location o1 = new Location();
        Location o2 = new Location();
        assertEquals(o1.hashCode(), o2.hashCode());
    }

}