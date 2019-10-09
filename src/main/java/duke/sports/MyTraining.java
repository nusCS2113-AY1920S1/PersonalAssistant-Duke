package duke.Sports;

/**
 * Represents a training programme.
 */
public class MyTraining {

    private String name;
    private int sets;
    private int reps;
    private String intensity;

    public MyTraining(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public String getName() {
        return this.name;
    }

    public int getSets() {
        return this.sets;
    }

    public int getReps() {
        return this.reps;
    }

    public String getIntensity() {
        return this.intensity;
    }

    public void changeName(String newName) {
        this.name = newName;
    }    

    public void changeSets(int newSets) {
        this.sets = newSets;
    }
    
    public void changeReps(int newReps) {
        this.reps = newReps;
    }    
    public void changeIntensity(String newIntensity) {
        this.intensity = newIntensity;
    }    
    
    public String toString() {
        return getName() + ", sets of " + getSets() + " with " + getReps() + "reps each";
    }
    
}
