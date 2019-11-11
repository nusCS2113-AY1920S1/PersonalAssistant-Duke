package eggventory.model;

import eggventory.commons.enums.PersonProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.loans.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

//@@author cyanoei
public class PersonListTest {

    private String matricNo = "A1";
    private String name = "John Doe";

    @BeforeEach
    void resetPersonList() throws BadInputException {
        if (PersonList.getSize() == 0) {
            return;
        }

        ArrayList<Person> persons = PersonList.getPersonList();
        ArrayList<String> matrics = new ArrayList<>();

        for (Person person : persons) {
            matrics.add(person.getMatricNo());
        }

        for (String matric : matrics) {
            PersonList.delete(matric);
        }
    }

    @Test
    void addPerson_NewPerson_Success() {
        Assertions.assertDoesNotThrow(() -> PersonList.add("A2", "Jane"));
    }

    @Test
    void addPerson_RepeatedPerson_ThrowsBadInputException() throws BadInputException {
        PersonList.add(matricNo, name);
        Assertions.assertThrows(BadInputException.class, () -> PersonList.add(matricNo, name));
    }

    @Test
    void deletePerson_PersonFound_Success() throws BadInputException {
        PersonList.add(matricNo, name);
        Assertions.assertDoesNotThrow(() -> PersonList.delete(matricNo));
    }

    @Test
    void deletePerson_PersonFound_ReturnsPerson() throws BadInputException {
        PersonList.add(matricNo, name);
        Person person = PersonList.delete(matricNo);
        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getName(), name);
        Assertions.assertEquals(person.getMatricNo(), matricNo);
    }

    @Test
    void deletePerson_PersonNotFound_ThrowsBadInputException() {
        Assertions.assertThrows(BadInputException.class, () -> PersonList.delete("not"));
    }

    @Test
    void editPerson_EditName_Success() throws BadInputException {
        PersonList.add(matricNo, name);
        Assertions.assertDoesNotThrow(() -> PersonList.edit(matricNo, PersonProperty.NAME, "newName"));
    }

    @Test
    void editPerson_EditMatric_ReturnsNewMatric() throws BadInputException {
        PersonList.add(matricNo, name);
        Person person = PersonList.edit(matricNo, PersonProperty.MATRICNO, "newMatric");
        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getMatricNo(), "newMatric");
    }

    @Test
    void editPerson_EditName_ReturnsNewName() throws BadInputException {
        PersonList.add(matricNo, name);
        Person person = PersonList.edit(matricNo, PersonProperty.NAME, "newName");
        Assertions.assertNotNull(person);
        Assertions.assertEquals(person.getName(), "newName");
    }

    @Test
    void editPerson_PersonNotFound_ThrowsBadInputException() {
        Assertions.assertThrows(BadInputException.class, () -> PersonList.delete("not"));
    }

    @Test
    void findPerson_PersonFound_ReturnsCorrectIndex() throws BadInputException {
        PersonList.add(matricNo, name);
        Assertions.assertEquals(PersonList.findPerson(matricNo), 0);
    }

    @Test
    void findPerson_PersonNotFound_ReturnsMinusOne() {
        Assertions.assertEquals(PersonList.findPerson(matricNo), -1);
    }

    

}
