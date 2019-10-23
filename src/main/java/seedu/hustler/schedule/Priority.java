package seedu.hustler.schedule;

import java.time.Duration;
import seedu.hustler.task.variables.Difficulty;
import seedu.hustler.schedule.time.TimeRemaining;

/**
 * A class that assigns a priority score to
 * given task.
 */
public class Priority {
    /**
     * Takes in a task and computes a priority score
     * for the task based on its difficulty and amount of
     * hours available.
     *
     * @param entry the task for which priority is computed
     * @return priority score of the task
     */
    public static double getPriorityScore(ScheduleEntry entry) {
        double priorityScore = 0;

        if (entry.getTask().getDifficulty().toString().equals("[H]")) {
            priorityScore = 3;
        } else if (entry.getTask().getDifficulty().toString().equals("[M]")) {
            priorityScore = 2;
        } else {
            priorityScore = 1;
        }

        Duration timeRemaining = TimeRemaining.left(entry.getTask());
        long secondsRemaining = timeRemaining.getSeconds();

        long timeForScoring = secondsRemaining + entry.getTimeSpent();

        double daysForScoring = timeForScoring / 86400.0;
        priorityScore /= daysForScoring;

        return priorityScore;
    }
}
