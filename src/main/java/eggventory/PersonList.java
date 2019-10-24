package eggventory;

import eggventory.enums.PersonProperty;
import eggventory.exceptions.BadInputException;
import eggventory.items.Person;
import java.util.ArrayList;

//@@author Raghav-B
public class PersonList {

    private ArrayList<Person> personList;

    public PersonList() {

    }

    public void add(String matricNo, String name) throws BadInputException {
        if (findPerson(matricNo) != -1) { // Person exists already
            throw new BadInputException("The Person with the specified matriculation number already exists!\n Did you"
                    + " mean to edit this Person's details instead?");
        }
        personList.add(new Person(matricNo, name));
    }

    public Person delete(String matricNo) throws BadInputException {
        int personDeleteIndex = findPerson(matricNo);
        if (personDeleteIndex == -1) {
            throw new BadInputException("Could not find a Person with the specified matriculation number.");
        }

        Person deletedPerson = personList.get(personDeleteIndex);
        personList.remove(personDeleteIndex);
        return deletedPerson;
    }

    /**
     * Updates the values of properties of a Stock.
     * @param matricNo String which uniquely identifies a Person.
     * @param property The attribute of a Person we want to update.
     * @param newValue The new value of the attribute to be updated.
     * @return The unedited Person, for printing purpose.
     */
    public Person edit(String matricNo, PersonProperty property, String newValue) throws BadInputException {
        int editPersonIndex = findPerson(matricNo);
        if (editPersonIndex == -1) {
            throw new BadInputException("Could not find a Person with the specified matriculation number.\n Did you"
                    + " mean to add a new Person instead?");
        }

        switch (property) {
        case:
        }
    }

    /**
     * Returns index of person found.
     * @param matricNo Search query: matricNo.
     * @return index of Person found in PersonList.
     */
    public int findPerson(String matricNo) {
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getMatricNo().equals(matricNo)) {
                return i;
            }
        }

        return -1;
    }

}
