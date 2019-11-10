package seedu.hustler.schedule;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import seedu.hustler.task.ToDo;
import seedu.hustler.task.Deadline;
import seedu.hustler.schedule.time.TimeRemainingStub;
import java.time.LocalDateTime;

public class RecommenderTest {

    @Test

    /**
     * Testing for recommend function of Recommender.
     */
    void recommend() {
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        ArrayList<ScheduleEntry> recommended = new ArrayList<ScheduleEntry>();

        ToDo todo = new ToDo("random todo");
        todo.setDifficulty("L");
        schedule.add(new ScheduleEntry(todo, 0, new TimeRemainingStub()));
        recommended.add(new ScheduleEntry(todo, 0, new TimeRemainingStub()));

        todo = new ToDo("random todo hard");
        todo.setDifficulty("H");
        schedule.add(new ScheduleEntry(todo, 100000000, new TimeRemainingStub()));

        Deadline deadline = new Deadline("random deadline 2", LocalDateTime.of(2019, 11, 12, 0, 0));
        deadline.setDifficulty("H");
        schedule.add(new ScheduleEntry(deadline, 0, new TimeRemainingStub()));
        recommended.add(new ScheduleEntry(deadline, 0, new TimeRemainingStub()));

        deadline = new Deadline("random deadline 1", LocalDateTime.of(2019, 11, 7, 0, 0));
        schedule.add(new ScheduleEntry(deadline, 0, new TimeRemainingStub()));
        recommended.add(new ScheduleEntry(deadline, 0, new TimeRemainingStub()));

        for (ScheduleEntry entry : recommended) {
            entry.setTimeAlloc(3600);
        }
        Recommender recommender = new Recommender(schedule);
        ArrayList<ScheduleEntry> test_recommended = recommender.recommend(3 * 3600);
        
        for (int i = 0; i < recommended.size(); i++) {
            assertEquals(recommended.get(i).getTask(), test_recommended.get(i).getTask()); 
        }
    } 
}
