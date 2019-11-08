package unit;

import org.junit.jupiter.api.Test;
import spinbox.DateTime;
import spinbox.entities.items.tasks.Deadline;
import spinbox.entities.items.tasks.Event;
import spinbox.entities.items.tasks.Exam;
import spinbox.entities.items.tasks.Lab;
import spinbox.entities.items.tasks.Lecture;
import spinbox.entities.items.tasks.Todo;
import spinbox.entities.items.tasks.Tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {
    private DateTime startDateTime = new DateTime("01/01/2019 00:00");
    private DateTime endDateTime = new DateTime("01/01/2019 01:00");

    @Test
    public void createTodoSuccessful_createNewTodoTask_todoSuccessfullyCreated() {
        Todo todo = new Todo("Test 1");
        assertEquals(todo.getTaskType().toString(), "TODO");
        assertEquals(todo.storeString(), "T | 0 | Test 1");
        assertEquals(todo.toString(), "[T][NOT DONE] Test 1");
        todo.markDone();
        assertEquals(todo.storeString(), "T | 1 | Test 1");
        assertEquals(todo.toString(), "[T][DONE] Test 1");
    }

    @Test
    public void createDeadlineSuccessful_createNewDeadlineTask_deadlineSuccessfullyCreated() {
        Deadline deadline = new Deadline("Test 2", startDateTime);
        assertEquals(deadline.getTaskType().toString(), "DEADLINE");
        assertEquals(deadline.getStartDate(), startDateTime);
        assertEquals(deadline.storeString(), "D | 0 | Test 2 | 01/01/2019 00:00");
        assertEquals(deadline.toString(), "[D][NOT DONE] Test 2 (by: 01/01/2019 00:00)");
        deadline.markDone();
        assertEquals(deadline.storeString(), "D | 1 | Test 2 | 01/01/2019 00:00");
        assertEquals(deadline.toString(), "[D][DONE] Test 2 (by: 01/01/2019 00:00)");
    }

    @Test
    public void createEventSuccessful_createNewEventTask_eventSuccessfullyCreated() {
        Event event = new Event("Test 3", startDateTime, endDateTime);
        assertEquals(event.getTaskType().toString(), "EVENT");
        assertEquals(event.getStartDate(), startDateTime);
        assertEquals(event.getEndDate(), endDateTime);
        assertEquals(event.isOverlapping(startDateTime, endDateTime), true);
        assertEquals(event.storeString(), "E | 0 | Test 3 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(event.toString(), "[E][NOT DONE] Test 3 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
        event.markDone();
        assertEquals(event.storeString(), "E | 1 | Test 3 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(event.toString(), "[E][DONE] Test 3 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
    }

    @Test
    public void createExamSuccessful_createNewExamTask_examSuccessfullyCreated() {
        Exam exam = new Exam("Test 4", startDateTime, endDateTime);
        assertEquals(exam.getTaskType().toString(), "EXAM");
        assertEquals(exam.getStartDate(), startDateTime);
        assertEquals(exam.getEndDate(), endDateTime);
        assertEquals(exam.isOverlapping(startDateTime, endDateTime), true);
        assertEquals(exam.storeString(), "EXAM | 0 | Test 4 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(exam.toString(), "[EXAM][NOT DONE] Test 4 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
        exam.markDone();
        assertEquals(exam.storeString(), "EXAM | 1 | Test 4 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(exam.toString(), "[EXAM][DONE] Test 4 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
    }

    @Test
    public void createLabSuccessful_createNewLabTask_labSuccessfullyCreated() {
        Lab lab = new Lab("Test 5", startDateTime, endDateTime);
        assertEquals(lab.getTaskType().toString(), "LAB");
        assertEquals(lab.getStartDate(), startDateTime);
        assertEquals(lab.getEndDate(), endDateTime);
        assertEquals(lab.isOverlapping(startDateTime, endDateTime), true);
        assertEquals(lab.storeString(), "LAB | 0 | Test 5 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(lab.toString(), "[LAB][NOT DONE] Test 5 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
        lab.markDone();
        assertEquals(lab.storeString(), "LAB | 1 | Test 5 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(lab.toString(), "[LAB][DONE] Test 5 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
    }

    @Test
    public void createLectureSuccessful_createNewLectureTask_lectureSuccessfullyCreated() {
        Lecture lecture = new Lecture("Test 6", startDateTime, endDateTime);
        assertEquals(lecture.getTaskType().toString(), "LECTURE");
        assertEquals(lecture.getStartDate(), startDateTime);
        assertEquals(lecture.getEndDate(), endDateTime);
        assertEquals(lecture.isOverlapping(startDateTime, endDateTime), true);
        assertEquals(lecture.storeString(), "LEC | 0 | Test 6 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(lecture.toString(), "[LEC][NOT DONE] Test 6 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
        lecture.markDone();
        assertEquals(lecture.storeString(), "LEC | 1 | Test 6 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(lecture.toString(), "[LEC][DONE] Test 6 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
    }

    @Test
    public void createTutorialSuccessful_createNewTutorialTask_tutorialSuccessfullyCreated() {
        Tutorial tutorial = new Tutorial("Test 7", startDateTime, endDateTime);
        assertEquals(tutorial.getTaskType().toString(), "TUTORIAL");
        assertEquals(tutorial.isOverlapping(startDateTime, endDateTime), true);
        assertEquals(tutorial.getStartDate(), startDateTime);
        assertEquals(tutorial.getEndDate(), endDateTime);
        assertEquals(tutorial.storeString(), "TUT | 0 | Test 7 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(tutorial.toString(), "[TUT][NOT DONE] Test 7 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
        tutorial.markDone();
        assertEquals(tutorial.storeString(), "TUT | 1 | Test 7 | 01/01/2019 00:00 | 01/01/2019 01:00");
        assertEquals(tutorial.toString(), "[TUT][DONE] Test 7 (at: 01/01/2019 00:00 to 01/01/2019 01:00)");
    }
}