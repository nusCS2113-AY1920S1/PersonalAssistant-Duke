package gazeeebo.notes;

public class Assessment {
    public String name;
    public int weightage;

    public Assessment(String name, int percentage) {
        this.name = name;
        this.weightage = percentage;
    }

    @Override
    public String toString() {
        return name + " (" + weightage + "%)";
    }
}
