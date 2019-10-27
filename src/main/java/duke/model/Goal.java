package duke.model;

import duke.commons.exceptions.DukeException;

/**
 * Goal is a public class that defines all user set dietary goals.
 */
public class Goal {
    private String endDate;
    private String startDate;
    private double weightTarget;
    private int calorieTarget;
    private int lifestyleTarget;

    public Goal() {
    }

    public Goal(String[] splitLine) throws DukeException {
        startDate = splitLine[0];
        endDate = splitLine[1];
        try {
            weightTarget = Double.parseDouble(splitLine[2]);
            calorieTarget = Integer.parseInt(splitLine[3]);
            lifestyleTarget = Integer.parseInt(splitLine[4]);
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setWeightTarget(double weight) {
        this.weightTarget = weight;
    }

    public void setLifestyleTarget(int level) {
        this.lifestyleTarget = level;
    }

    /**
     * This is a getter for date.
     * @return description of the task
     */
    public String getEndDate() {
        return this.endDate;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public double getWeightTarget() {
        return this.weightTarget;
    }

    public int getCalorieTarget() {
        return this.calorieTarget;
    }

    public int getLifestyleTarget() {
        return this.lifestyleTarget;
    }

    /**
     * This function overrides the toString() function in the object class.
     * @return the status icon and the description of the goal
     */
    @Override
    public String toString() {
        return startDate + "|" + endDate + "|" + weightTarget + "|" + calorieTarget + "|" + lifestyleTarget;
    }

}
