package seedu.hustler.task;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortTaskTest {

    private static TaskList sampleTaskList;
    private static Task task1; // [D][-][H][#chem] lab report (by: 23rd of December 2019, 11:59PM)
    private static Task task2; // [E][-][L][#food] family dinner (by: 21rd of December 2019, 11:59PM)
    private static Task task3; // [D][-][M][#geog] homework (by: 22rd of December 2019, 11:59PM)

    @BeforeAll
    public static void initialize() {
        sampleTaskList = new TaskList(new ArrayList<>());
        // Creating dummy task items to be populated into task list.
        LocalDateTime ldt1 = LocalDateTime.of(2019,12,21,23,59);
        LocalDateTime ldt2 = LocalDateTime.of(2019,12,22,23,59);
        LocalDateTime ldt3 = LocalDateTime.of(2019,12,23,23,59);
        LocalDateTime now1 = LocalDateTime.of(2019,12,1,18,0);
        LocalDateTime now2 = LocalDateTime.of(2019,12,1,18,1);
        LocalDateTime now3 = LocalDateTime.of(2019,12,1,18,2);
        task1 = new Deadline("lab report", ldt3, "H", "chem", now1);
        task2 = new Event("family dinner", ldt1, "L", "food", now2);
        task3 = new Deadline("homework", ldt2, "M", "geog", now3);
        sampleTaskList.add(task1);
        sampleTaskList.add(task2);
        sampleTaskList.add(task3);
    }

    @Test
    public void test_sortType_datetime() {
        sampleTaskList.sortTask("datetime");
        // After sorting by "datetime", the task list will be sorted in chronological order.
        // That is to say, first element is task3, second element is task2, third element is task1.
        assertEquals(task2, sampleTaskList.get(0));
        assertEquals(task3, sampleTaskList.get(1));
        assertEquals(task1, sampleTaskList.get(2));
    }


    @Test
    public void test_sortType_normal() {
        sampleTaskList.sortTask("normal");
        // After sorting by "normal", the task list will be sorted in terms of when they were added.
        // That is to say, first element is task1, second element is task2, third element is task3.
        assertEquals(task1, sampleTaskList.get(0));
        assertEquals(task2, sampleTaskList.get(1));
        assertEquals(task3, sampleTaskList.get(2));
    }

    @Test
    public void test_sortType_priority() {
        sampleTaskList.sortTask("priority");
        // After sorting by "priority", the task list will be sorted in decreasing difficulty.
        // That is to say, first element is task1, second element is task3, third element is task2.
        assertEquals(task1, sampleTaskList.get(0));
        assertEquals(task3, sampleTaskList.get(1));
        assertEquals(task2, sampleTaskList.get(2));
    }
}
