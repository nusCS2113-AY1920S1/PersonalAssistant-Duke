//@@author yenpeichih

package mistermusik.commons;

public class Goal {

    /**
     * The string for the goal.
     */
    private String goalDescription;

    /**
     * The boolean for the achieved status of the goal.
     */
    private boolean isAchieved;

    /**
     * Creates a Goal instance with the goal input by user and a boolean to check if goal is achieved.
     */
    public Goal(String description) {
        goalDescription = description;
        isAchieved = false;
    }

    /**
     * Returns the string for the goal.
     */
    public String getGoal() {
        return goalDescription;
    }

    /**
     * Sets whether the goal has been achieved.
     */
    public void setAchieved() {
        isAchieved = true;
    }

    /**
     * Returns the boolean for the achieved status of the goal.
     */
    public boolean getBooleanStatus() {
        return isAchieved;
    }

    /**
     * Gets whether the goal is achieved.
     */
    public String getStatus() {
        if (isAchieved) {
            return "Yes";
        } else {
            return "No";
        }
    }
}
