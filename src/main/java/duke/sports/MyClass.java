package duke.sports;

import duke.task.Item;

import java.util.ArrayList;

/**
 * Create a class for all the sports classes.
 */
public class MyClass extends Item {

    /**
     * The day of the week it will be held. (i.e. Monday, Tuesday)
     */
    private String day;

    /**
     * A list of students attending this sports class.
     */
    private ArrayList<MyStudent> students = new ArrayList<>();

    /**
     * A list of training programmes for this sports class.
     */
    private ArrayList<MyTraining> trainings = new ArrayList<>();

    /**
     * Constructor method for MyClass.
     *
     * @param info   This parameter is the name of the sports class.
     * @param status This shows whether the sports class has been held this week
     *               or not.
     * @param today  When it will be held. (i.e. Monday, Tuesday)
     */
    public MyClass(final String info, final Boolean status,
                   final String today) {
        super(info, status);
        super.setType("C");     //Has its own item type "C"
        this.day = today;
    }

    /**
     * Get the day of the week this sports class is held.
     *
     * @return The day of the week this sports class is held.
     */
    public String getDay() {
        return this.day;
    }

    /**
     * Gets all the info of the sports class.
     *
     * @return A string phrase containing all the details of the sports class.
     */
    @Override
    public String toString() {
        return "[C]" + super.toString() + " (every: " + getDay() + ")";
    }

    /**
     * Get the list of students attending this sports class.
     *
     * @return an arraylist of MyStudent objects
     */
    public ArrayList<MyStudent> getStudents() {
        return this.students;
    }

    /**
     * Get the list of training programmes for this sports class.
     *
     * @return an arraylist of MyTraining objects
     */
    public ArrayList<MyTraining> getTrainings() {
        return this.trainings;
    }

    /**
     * Add a training programme to the sports class.
     *
     * @param training The training programme to add.
     */
    public void addTraining(final MyTraining training) {
        trainings.add(training);
    }

    /**
     * Edit a training programme for a sports class. (The name)
     *
     * @param training The name of the training programme to edit.
     * @param newName  New name to change to.
     */
    public void editTrainingName(final String training, final String newName) {
        for (MyTraining x : trainings) {
            if (x.getName().equals(training)) {
                x.changeName(newName);
            }
        }
    }

    /**
     * Edit a training programme for a sports class. (The number of sets)
     *
     * @param training The name of the training programme to edit.
     * @param newSets  New number of sets to change to.
     */
    public void editTrainingSets(final String training, final int newSets) {
        for (MyTraining x : trainings) {
            if (x.getName().equals(training)) {
                x.changeSets(newSets);
            }
        }
    }

    /**
     * Edit a training programme for a sports class. (The number of reps)
     *
     * @param training The name of the training programme to edit.
     * @param newReps  New number of reps to change to.
     */
    public void editTrainingReps(final String training, final int newReps) {
        for (MyTraining x : trainings) {
            if (x.getName().equals(training)) {
                x.changeReps(newReps);
            }
        }
    }

    /**
     * Edit a training programme for a sports class. (The intensity)
     *
     * @param training     The name of the training programme to edit.
     * @param newIntensity New intensity to change to.
     */
    public void editTrainingIntensity(final String training,
                                      final String newIntensity) {
        for (MyTraining x : trainings) {
            if (x.getName().equals(training)) {
                x.changeIntensity(newIntensity);
            }
        }
    }

}
