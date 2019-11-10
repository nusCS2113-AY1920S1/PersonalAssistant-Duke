package eggventory.model;

import eggventory.commons.enums.PersonProperty;
import eggventory.commons.exceptions.BadInputException;
import eggventory.model.loans.Person;
import eggventory.ui.TableStruct;

import java.util.ArrayList;

//@@author Raghav-B
public final class PersonList {

    private static ArrayList<Person> personList = new ArrayList<Person>();


    /**
     * Adds a unique Person to the personList.
     * @param matricNo Uniquely identifies a Person.
     * @param name Name to assign to Person. Not unique.
     * @throws BadInputException If Person already exists.
     */
    public static void add(String matricNo, String name) throws BadInputException {
        matricNo = matricNo.toUpperCase();
        if (findPerson(matricNo) != -1) { // Person exists already
            throw new BadInputException("The Person with the specified matriculation number already exists!\n Did you"
                    + " mean to edit this Person's details instead?");
        }
        personList.add(new Person(matricNo, name));
    }

    /**
     * Deletes a Person based on their matricNo.
     * @param matricNo String which uniquely identifies a Person.
     * @return The unedited Person, for printing purpose.
     * @throws BadInputException If desired Person cannot be found.
     */
    public static Person delete(String matricNo) throws BadInputException {
        matricNo = matricNo.toUpperCase();
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
     * @throws BadInputException If no Person could be found.
     */
    public static Person edit(String matricNo, PersonProperty property, String newValue) throws BadInputException {
        matricNo = matricNo.toUpperCase();
        Person uneditedPerson;
        int editPersonIndex = findPerson(matricNo);
        if (editPersonIndex == -1) {
            throw new BadInputException("Could not find a Person with the specified matriculation number.\n Did you"
                    + " mean to add a new Person instead?");
        }

        uneditedPerson = personList.get(editPersonIndex);
        switch (property) {
        case MATRICNO:
            personList.get(editPersonIndex).setMatricNo(newValue);
            break;
        case NAME:
            personList.get(editPersonIndex).setName(newValue);
            break;
        default:
            // No such property exists.
            break;
        }

        return uneditedPerson;
    }

    /**
     * Returns index of person found.
     * @param matricNo Search query: matricNo.
     * @return index of Person found in PersonList.
     */
    public static int findPerson(String matricNo) {
        matricNo = matricNo.toUpperCase();
        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).getMatricNo().equals(matricNo)) {
                return i;
            }
        }

        return -1;
    }

    //@@author Deculsion

    /**
     * Returns the name of a person given the matric number.
     * @param matricNo Matric No of person to search for
     * @return The name of the person.
     */
    public static String getName(String matricNo) {
        matricNo = matricNo.toUpperCase();
        Person person = personList.get(findPerson(matricNo));

        return person.getName();
    }

    public static int getSize() {
        return personList.size();
    }

    /**
     * Creates a tablestruct of all persons in personlist.
     * @return A tablestruct object.
     */
    public static TableStruct getAllPersonStruct() {
        TableStruct tableStruct = new TableStruct("Person List");
        tableStruct.setTableColumns("Matriculation No.", "Full Name");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (Person person: personList) {
            dataArray.add(person.getDataAsArray());
        }

        tableStruct.setTableData(dataArray);

        return tableStruct;
    }

    /**
     * Converts the person list to a string.
     * @return A string representing all data in personlist.
     */
    public static String listToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("List of people registered\n");
        sb.append("------------------------\n");

        for (int i = 0; i < personList.size(); i++) {
            sb.append(String.format("%d: %s\n",
                    i + 1, personList.get(i).toString())
            );
        }

        return sb.toString();
    }

    public static ArrayList<Person> getPersonList() {
        return personList;
    }


    //@@author patwaririshab
    /**
     * Saves the stocktypes into a String.
     * @return The String will be directly saved into a saved_stocktypes file.
     */
    public String savePersonListString() {
        StringBuilder personString = new StringBuilder();

        for (Person person : personList) {
            personString.append(person.savedPersonString()).append("\n");
        }

        return personString.toString();
    }
    //@@author

}
