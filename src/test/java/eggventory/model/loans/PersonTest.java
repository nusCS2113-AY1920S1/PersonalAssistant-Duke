package eggventory.model.loans;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {

    private Person person;
    private final String testMatric = "Test Matric";
    private final String testName = "Test Name";

    @BeforeEach
    void resetPerson() {
        person = new Person(testMatric, testName);
    }

    @Test
    void settersGetters_Exists_ReturnsValues() {

        assertEquals(testMatric, person.getMatricNo());
        assertEquals(testName, person.getName());

        final String testMatricSet = "Test Matric Set";
        final String testNameSet = "Test Name Set";

        person.setMatricNo(testMatricSet);
        assertEquals(testMatricSet, person.getMatricNo());

        person.setName(testNameSet);
        assertEquals(testNameSet, person.getName());
    }

    @Test
    void getDataAsArray() {
        ArrayList<String> dataExpected = new ArrayList<>();
        dataExpected.add(testMatric);
        dataExpected.add(testName);

        assertEquals(dataExpected, person.getDataAsArray());
    }

    @Test
    void testToString() {
        final String outputExpected = testMatric + " | " + testName;
        assertEquals(outputExpected, person.toString());
    }

    @Test
    void savedPersonString() {
        final String outputExpected = testMatric + "," + testName;
        assertEquals(outputExpected, person.savedPersonString());
    }
}