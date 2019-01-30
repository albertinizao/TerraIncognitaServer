package com.opipo.terraincognitaserver.rest.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.opipo.terraincognitaserver.MockitoExtension;
import com.opipo.terraincognitaserver.service.ServiceDTOInterface;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractCRUDControllerTest<T, ID extends Serializable> {

    protected abstract AbstractCRUDController<T, ID> getController();

    protected abstract ServiceDTOInterface<T, ID> getService();

    protected abstract ID getCorrectID();

    protected abstract ID getIncorrectID();

    protected abstract T buildElement(ID id);

    private boolean validateResponseEntity(ResponseEntity<?> response, HttpStatus statusExpected,
            Object valueExpected) {
        assertNotNull(response);
        assertEquals(statusExpected, response.getStatusCode());
        assertEquals(valueExpected, response.getBody());
        return true;
    }

    @Test
    public void whenInvokeGetAllThenGetAll() {
        Collection<T> expected = new ArrayList<>();
        expected.add(buildElement(getCorrectID()));
        Mockito.when(getService().findAll()).thenReturn(expected);
        ResponseEntity<Collection<T>> actual = getController().list();
        validateResponseEntity(actual, HttpStatus.OK, expected);
    }

    @Test
    public void whenCreateElementThenCreateAndGetIt() {
        T expected = buildElement(getCorrectID());
        Mockito.when(getService().create()).thenReturn(expected);
        ResponseEntity<T> actual = getController().create();
        validateResponseEntity(actual, HttpStatus.ACCEPTED, expected);
    }

    @Test
    public void givenIdThenGetElement() {
        ID id = getCorrectID();
        T expected = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(expected);
        ResponseEntity<T> actual = getController().get(id);
        validateResponseEntity(actual, HttpStatus.OK, expected);
    }

    @Test
    public void givenIdAndOtherIdInElementAndTryToSaveThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(getIncorrectID());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().save(id, element));
        assertTrue(exception.getMessage().contains("The id is not the expected"));
    }

    @Test
    public void givenIdAndElementWhoDoesntExistAndTryToSaveThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(id);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().save(id, element));
        assertTrue(exception.getMessage().contains("The element doesn't exist"));
    }

    @Test
    public void givenIdAndElementWhoExistsAndTryToSaveThenGetSavedElement() {
        ID id = getCorrectID();
        T expected = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(expected);
        Mockito.when(getService().save(expected)).thenReturn(expected);
        ResponseEntity<T> actual = getController().save(id, expected);
        assertNotNull(validateResponseEntity(actual, HttpStatus.ACCEPTED, expected));
    }

    @Test
    public void givenIdAndOtherIdInElementAndTryToPatchThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(getIncorrectID());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().partialUpdate(id, element));
        assertTrue(exception.getMessage().contains("The id is wrong"));
    }

    @Test
    public void givenIdAndElementWhoDoesntExistAndTryToPatchThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(id);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().partialUpdate(id, element));
        assertTrue(exception.getMessage().contains("The element doesn't exist"));
    }

    @Test
    public void givenIdAndElementWhoExistsAndTryToPatchThenGetSavedElement() {
        ID id = getCorrectID();
        T expected = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(expected);
        Mockito.when(getService().update(id, expected)).thenReturn(expected);
        ResponseEntity<T> actual = getController().partialUpdate(id, expected);
        validateResponseEntity(actual, HttpStatus.ACCEPTED, expected);
    }

    @Test
    public void givenIdAndElementWhoExistsButNoWithIdAndTryToPatchThenGetSavedElement() {
        ID id = getCorrectID();
        T expected = buildElement(null);
        Mockito.when(getService().find(id)).thenReturn(expected);
        Mockito.when(getService().update(id, expected)).thenReturn(expected);
        ResponseEntity<T> actual = getController().partialUpdate(id, expected);
        validateResponseEntity(actual, HttpStatus.ACCEPTED, expected);
    }

    @Test
    public void givenIdAndOtherIdInElementAndTryToCreateThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(getIncorrectID());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().create(id, element));
        assertTrue(exception.getMessage().contains("The id is not the expected"));
    }

    @Test
    public void givenIdAndElementWhoExistsAndTryToCreateThenGetException() {
        ID id = getCorrectID();
        T element = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(element);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> getController().create(id, element));
        assertTrue(exception.getMessage().contains("The element exists now"));
    }

    @Test
    public void givenIdWhoDoesntExistAndTryToCreateThenGetCreatedElement() {
        ID id = getCorrectID();
        T expected = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(null);
        Mockito.when(getService().create(id)).thenReturn(expected);
        Mockito.when(getService().save(expected)).thenReturn(expected);
        ResponseEntity<T> actual = getController().create(id, null);
        validateResponseEntity(actual, HttpStatus.ACCEPTED, expected);
    }

    @Test
    public void givenIdAndElementWhoDoesntExistAndTryToCreateThenGetCreatedElement() {
        ID id = getCorrectID();
        T expected = buildElement(id);
        Mockito.when(getService().find(id)).thenReturn(null);
        Mockito.when(getService().save(expected)).thenReturn(expected);
        ResponseEntity<T> actual = getController().create(id, expected);
        validateResponseEntity(actual, HttpStatus.ACCEPTED, expected);
    }

    @Test
    public void givenIdThenDeleteElement() {
        ID id = getCorrectID();
        ResponseEntity<ID> actual = getController().delete(id);
        validateResponseEntity(actual, HttpStatus.OK, id);
        Mockito.verify(getService()).delete(id);
    }

    @Test
    public void givenNoIdAndNoElementThenReturnFalse() {
        assertTrue(getController().checkIdFromElement(null, null));
    }

    @Test
    public void givenNoIdAndNoIdInElementThenReturnFalse() {
        assertTrue(getController().checkIdFromElement(null, buildElement(null)));
    }

    @Test
    public void givenNoIdAndElementThenReturnFalse() {
        assertFalse(getController().checkIdFromElement(null, buildElement(getCorrectID())));
    }

    @Test
    public void givenIdAndNoElementThenReturnFalse() {
        assertFalse(getController().checkIdFromElement(getCorrectID(), null));
    }

    @Test
    public void givenIdAndNoIdInElementThenReturnFalse() {
        assertFalse(getController().checkIdFromElement(getCorrectID(), buildElement(null)));
    }

    @Test
    public void givenIdAndDistinctIdElementThenReturnFalse() {
        assertFalse(getController().checkIdFromElement(getCorrectID(), buildElement(getIncorrectID())));
    }

    @Test
    public void givenIdAndSameIdElementThenReturnTrue() {
        assertTrue(getController().checkIdFromElement(getCorrectID(), buildElement(getCorrectID())));
    }

    protected <T> T checkResponse(ResponseEntity<T> response, T expected, HttpStatus httpStatus) {
        T actual = response.getBody();
        assertEquals(httpStatus, response.getStatusCode());
        if (expected != null && actual != null && Collection.class.isAssignableFrom(actual.getClass())) {
            Collection actualCollection = (Collection) actual;
            Collection expectedCollection = (Collection) expected;
            assertEquals(expectedCollection.size(), actualCollection.size());
            assertTrue(expectedCollection.containsAll(actualCollection));
            assertTrue(actualCollection.containsAll(expectedCollection));
        } else {
            assertEquals(expected, actual);
        }
        return actual;
    }
}