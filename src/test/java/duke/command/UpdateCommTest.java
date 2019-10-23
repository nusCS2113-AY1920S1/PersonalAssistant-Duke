package duke.command;

import duke.task.Deadline;
import duke.task.DoAfter;
import duke.task.Event;
import duke.task.Task;
import duke.task.TaskList;
import duke.task.Todo;
import duke.ui.Ui;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import java.text.ParseException;

//@@author talesrune
class UpdateCommTest {

    @Test
    void updateTest() throws ParseException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        items.add(task);
        task = new Event("movie", "05/05/2015 1500");
        items.add(task);

        String taskDesc = "run";
        String dateDesc = "";
        String typeDesc = "";
        int typeOfUpdate = 1;
        int tasknum = 0;
        Command cmd = new UpdateCommand(taskDesc,dateDesc,typeDesc,typeOfUpdate,tasknum);
        Ui ui = new Ui();

        Task taskdummy = new Todo("dummy");
        assertEquals("     Nice! I've updated this task ^^:\n"
                + "       1.[T]" + taskdummy.getStatusIconGui()
                + " run", cmd.executeGui(items,ui));

        dateDesc = "08/08/2018 1708";
        typeOfUpdate = 2;
        tasknum = 1;
        cmd = new UpdateCommand(taskDesc,dateDesc,typeDesc,typeOfUpdate,tasknum);
        assertEquals("     Nice! I've updated this task ^^:\n"
                + "       2.[D]" + taskdummy.getStatusIconGui()
                + " homework (by: 8th of August 2018, 5:08 PM)", cmd.executeGui(items,ui));

        taskDesc = "trampoline";
        typeOfUpdate = 1;
        tasknum = 2;
        cmd = new UpdateCommand(taskDesc,dateDesc,typeDesc,typeOfUpdate,tasknum);
        assertEquals("     Nice! I've updated this task ^^:\n"
                + "       3.[E]" + taskdummy.getStatusIconGui()
                + " trampoline (at: 5th of May 2015, 3PM)", cmd.executeGui(items,ui));

        typeDesc = "event";
        typeOfUpdate = 3;
        tasknum = 1;
        cmd = new UpdateCommand(taskDesc,dateDesc,typeDesc,typeOfUpdate,tasknum);
        assertEquals("     Nice! I've updated this task ^^:\n"
                + "       2.[E]" + taskdummy.getStatusIconGui()
                + " homework (at: 8th of August 2018, 5:08 PM)", cmd.executeGui(items,ui));

        typeDesc = "todo";
        typeOfUpdate = 3;
        tasknum = 2;
        cmd = new UpdateCommand(taskDesc,dateDesc,typeDesc,typeOfUpdate,tasknum);
        assertEquals("     Nice! I've updated this task ^^:\n"
                + "       3.[T]" + taskdummy.getStatusIconGui() + " trampoline", cmd.executeGui(items,ui));
    }

    @Test
    public void updateTest_exceptionThrown() throws ParseException {
        TaskList items = new TaskList();
        Task task = new Todo("walk");
        items.add(task);
        task = new Deadline("homework", "07/07/2017 1707");
        items.add(task);
        task = new DoAfter("movie", "walk");
        items.add(task);

        try {
            items.get(0).setDateTime("07/07/2017 1707");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("     Error! Todo does not have date/time.", e.getMessage());
        }
        try {
            items.get(1).setDateTime("08/08/2018");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Unparseable date: \"08/08/2018\"", e.getMessage());
        }
        try {
            items.get(2).setDateTime("09/09/2019");
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("     Error! This task does not have date/time.", e.getMessage());
        }
    }
}