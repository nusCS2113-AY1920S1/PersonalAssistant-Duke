package eggventory.model.loans;

import java.util.ArrayList;

public class Person {

    private String matricNo;
    private String name;

    public Person(String matricNo, String name) {
        this.matricNo = matricNo;
        this.name = name;
    }

    public String getMatricNo() {
        return matricNo;
    }

    public String getName() {
        return name;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the matricNo and Name as an array.
     * @return An array representing the object's attributes.
     */
    public ArrayList<String> getDataAsArray() {
        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add(matricNo);
        arrayList.add(name);

        return arrayList;
    }

    public String toString() {
        return matricNo + " | " + name;
    }
}
