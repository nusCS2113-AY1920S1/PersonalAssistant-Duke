package leduc;
import leduc.command.SetWelcomeCommand;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.Task;
import leduc.task.TaskList;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Represents a JUnit test class for the SetWelcomeCommandTest.
 */
public class SetWelcomeCommandTest {
    /**Represents a JUnit test method for SetWelcomeCommandTest
     * test the change in welcome message based on the user command
     */
    @Test
    public void SetWelcomeCommandTest() {
        Ui ui = new Ui();
        Storage storage = new Storage(System.getProperty("user.dir")+ "/data/duke.txt");
        SetWelcomeCommand swc = new SetWelcomeCommand("setwelcome welcometest");
        TaskList tasks = new TaskList(new ArrayList<Task>());
        try {
            swc.execute(tasks,ui,storage);
        }
        catch(DukeException e){
            ui.showError(e);
        }
        try {
            File file = SetWelcomeCommand.openFile(System.getProperty("user.dir")+ "/data/welcome.txt");
            //create Scanner object to read file
            Scanner sc2 = null;
            try {
                sc2 = new Scanner(file);
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new FileException();
            }
            String welcomeMessage = "";
            //build welcome message
            while (sc2.hasNext()) {
                welcomeMessage = welcomeMessage + '\t' + sc2.nextLine() + '\n';
            }
            assertTrue(welcomeMessage == "\twelcometest\n");
        }
        catch(DukeException e){
            ui.showError(e);
        }
    }
}

