package gazeeebo.notes;

/**
 * An assessment object that can be added to a module note.
 */
public class Assessment {
    public String name;

    /** Must be a positive number*/
    public int weightage; //as a percentage

    public Assessment(String name, int percentage) {
        this.name = name;
        this.weightage = percentage;
    }

    @Override
    public String toString() {
        return name + " (" + weightage + "%)";
    }
}
