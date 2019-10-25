package eggventory.items;

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
}
