package seedu.hustler.schedule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.hustler.schedule.time.TimeRemainingStub;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Event;
import seedu.hustler.task.Deadline;
import java.time.LocalDateTime;
import java.time.Duration;

public class PriorityTest {

    @Test 
    /**
     * Testing priority for ToDo.
     */
    void getPriorityScoreToDo() {
        Priority priority = new Priority(new TimeRemainingStub());

        ToDo todo = new ToDo("a todo task");

        todo.setDifficulty("H");
        Duration timeRemaining = Duration.ofDays(14);
        double priorityScore = 3 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(todo, 0));

        todo.setDifficulty("M");
        timeRemaining = Duration.ofDays(14);
        priorityScore = 2 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(todo, 0));

        todo.setDifficulty("L");
        timeRemaining = Duration.ofDays(14);
        priorityScore = 1 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(todo, 0));

        todo.setDifficulty("H");
        timeRemaining = Duration.ofDays(14);
        priorityScore = 3 / ((timeRemaining.toSeconds() + 10000) / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(todo, 10000));
    }
    
    @Test
    /**
     * Testing priority for deadline.
     */
    void getPriorityScoreDeadline() {
        Priority priority = new Priority(new TimeRemainingStub());
        Deadline deadline = new Deadline("a deadline task", LocalDateTime.of(2019, 12, 1, 0, 0));
        
        deadline.setDifficulty("H");
        Duration timeRemaining = Duration.between(TimeRemainingStub.startTime, deadline.getDateTime());
        double priorityScore = 3 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(deadline, 0));

        deadline.setDifficulty("M");
        timeRemaining = Duration.between(TimeRemainingStub.startTime, deadline.getDateTime());
        priorityScore = 2 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(deadline, 0));

        deadline.setDifficulty("L");
        timeRemaining = Duration.between(TimeRemainingStub.startTime, deadline.getDateTime());
        priorityScore = 1 / (timeRemaining.toSeconds() / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(deadline, 0));

        deadline.setDifficulty("H");
        timeRemaining = Duration.between(TimeRemainingStub.startTime, deadline.getDateTime());
        priorityScore = 3 / ((timeRemaining.toSeconds() + 10000) / 86400.0);
        assertEquals(priorityScore, priority.getPriorityScore(deadline, 10000));
    }
    
}
