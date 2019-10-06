package seedu.duke.game.achievement;

import seedu.duke.game.achievement.Achievements;

public class DoneTask extends Achievements {


    /**
     * Number of tasks completed.
     */
    private int taskCompleted = 0;

    /**
     * Increase the count of task being completed.
     * @return count of task completed.
     */
    private int increment() {
        this.taskCompleted ++;
        return taskCompleted;
    }

    /**
     *
     */


}
