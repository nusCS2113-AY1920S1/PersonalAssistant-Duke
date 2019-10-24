package cube.model;

/**
 * Represents a food's name in the food list.
 * Guarantees: immutable; is valid
 */
public class Name {

    private final String name;

    public Name (String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
               || (other instanceof Name && name.equals(((Name)other).getName()));
    }

}
