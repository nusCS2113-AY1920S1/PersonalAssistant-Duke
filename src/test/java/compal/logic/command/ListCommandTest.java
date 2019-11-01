package compal.logic.command;

import compal.model.tasks.Deadline;
import compal.model.tasks.Event;
import compal.model.tasks.Task;
import compal.model.tasks.TaskList;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//@@author SholihinK
class ListCommandTest {
    private ArrayList<Task> taskArrListMain = new ArrayList<>();
    private ArrayList<Task> taskArrListEmpty = new ArrayList<>();

    private TaskList taskListMain = new TaskList();
    private TaskList taskListEmpty = new TaskList();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Event event1 = new Event("Event 1", Task.Priority.medium, "01/10/2019", "01/10/2019", "1400", "1500");
        Deadline deadline1 = new Deadline("Deadline 1", Task.Priority.high, "01/10/2019", "1500");

        taskArrListMain.add(event1);
        taskArrListMain.add(deadline1);

        this.taskListMain.setArrList(taskArrListMain);
        this.taskListEmpty.setArrList(taskArrListEmpty);
    }

    @Test
    void execute_listDeadline_success() {
        String expected = new ListCommand("deadline").commandExecute(taskListMain).feedbackToUser;
        String tested = listStub(taskListMain, "D");
        Assertions.assertEquals(expected, tested);
    }

    @Test
    void execute_listEvent_success() {
        String expected = new ListCommand("event").commandExecute(taskListMain).feedbackToUser;
        String tested = listStub(taskListMain, "E");
        Assertions.assertEquals(expected, tested);
    }

    @Test
    void execute_emptyList_success() {
        String expected = new ListCommand("deadline").commandExecute(taskListEmpty).feedbackToUser;
        String tested = listStub(taskListEmpty, "D");
        Assertions.assertEquals(expected, tested);
    }

    private String listStub(TaskList taskList, String type) {
        String listPrefix = "Here are the tasks in your list: \n";
        String prefixType = "";
        if (type.equals("D")) {
            prefixType = "deadline";
        } else if (type.equals("E")) {
            prefixType = "event";
        }
        String listPrefixTwo = "Here are the stored " + prefixType + " in your list:\n\n";
        String listEmpty = "Looks like your list is empty!\nStart adding in your task by looking at the help command!";

        ArrayList<Task> toList = taskList.getArrList();
        String finalList;
        if (type.isEmpty()) {
            finalList = listPrefix;
        } else {
            finalList = listPrefixTwo;
        }
        int count = 1;
        for (Task t : toList) {
            if (type.isEmpty()) {
                String taskString = count + "." + t.toString() + "\n";
                finalList += taskString;
                count += 1;
            } else {
                if (t.getSymbol().equals(type)) {
                    String taskString = count + "." + t.toString() + "\n";
                    finalList += taskString;
                    count += 1;
                }
            }

        }
        if (count == 1) {
            finalList = listEmpty;
        }

        return finalList;
    }

}