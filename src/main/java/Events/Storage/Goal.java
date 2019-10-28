package Events.Storage;

public class Goal {

    private String goalDescription;

//    private boolean isAchieved;

    /**
     * Creates a Goal instance with the goal input by user and a boolean to check if goal is achieved.
     *
     * @param description
     */
    public Goal(String description) {
        goalDescription = description;
//        isAchieved = false;  TODO: get view goals to print out whether goal is achieved and if not, check if event is over
    }

    public String getGoal() {
        return goalDescription;
    }

}
