package duke.sports;

/**
 * Represents a training programme.
 */
public class MyTraining {
    /**
     * Represents the activity name.
     */
    private String name;
    /**
     * Represents the number of sets for the activity.
     */
    private int sets;
    /**
     * Represents the number of repetitions for the activity.
     */
    private int reps;
    /**
     * Represents the intensity of the activity plan.
     */
    private String intensity;

    /**
     * Constructor for MyTraining.
     * @param actName Retrieve the name of the activity
     * @param actSets Retrieve the number of sets of the activity
     * @param actReps Retrieve the number of reps of the activity
     */
    public MyTraining(final String actName, final int actSets,
                      final int actReps) {
        this.name = actName;
        this.sets = actSets;
        this.reps = actReps;
    }

    /**
     * Retrieve the activity name.
     * @return activity name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Retrieve the number of sets.
     * @return number of sets
     */
    public int getSets() {
        return this.sets;
    }

    /**
     * Retrieve the number of repetitions.
     * @return number of repetitions
     */
    public int getReps() {
        return this.reps;
    }

    /**
     * retrieve the intensity level.
     * @return intensity level
     */
    public String getIntensity() {
        return this.intensity;
    }

    /**
     * Change the activity name.
     * @param newName name to be changed to
     */
    public void changeName(final String newName) {
        this.name = newName;
    }

    /**
     * Change the number of sets.
     * @param newSets number of sets to be changed to
     */
    public void changeSets(final int newSets) {
        this.sets = newSets;
    }

    /**
     * Change the number of repetitions.
     * @param newReps number of repetitions to be changed to
     */
    public void changeReps(final int newReps) {
        this.reps = newReps;
    }

    /**
     * Change the intensity level.
     * @param newIntensity intensity level to be changed to
     */
    public void changeIntensity(final String newIntensity) {
        this.intensity = newIntensity;
    }

    /**
     * Convert the values to a string format.
     * @return String of the values in MyTraining
     */
    public String toString() {
        return getName() + ", sets of " + getSets() + " with "
                + getReps() + " reps each";
    }
}
