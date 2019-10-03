package eggventory.commands;

import eggventory.Storage;
import eggventory.TaskList;
import eggventory.Ui;

import static org.junit.jupiter.api.Assertions.assertEquals;



class FindCommandTest {
    Ui ui = new Ui();
    Storage storage = new Storage("");
    TaskList taskList = new TaskList();

    /*
    @Test
    public void execute_Task_success() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        taskList.addItem(TaskType.TODO, "Test FIND");
        new FindCommand(CommandType.FIND, "FIND").execute(taskList, ui, storage);
        assertEquals("1. [T] " + taskList.getTask(0).getStatusIcon() + "Test FIND", os.toString().trim());
    }*/
}
