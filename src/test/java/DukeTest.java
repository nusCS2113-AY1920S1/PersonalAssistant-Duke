import duke.Duke;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Integration testing of Duke.
 */
public class DukeTest {
    @Test
    public void duke_createNewTask_NewTaskCreatedPermanently() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add a task
        test.getResponse("todo test1");

        //Step 3: Exit program
        test.getResponse("bye");

        //Step 4: Create a new instance of Duke
        Duke test2 = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 5: Check data persists across Duke instances.
        assertNotEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");
    }

    @Test
    public void duke_updateNonExistentTask_exceptionThrown() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add an item
        test.getResponse("todo test1");

        //Step 3: Update item
        String testList = test.getResponse("list");

        assertEquals(test.getResponse("done 2"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");

        assertEquals(test.getResponse("done 0"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");

        assertNotEquals(test.getResponse("done 1"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");
    }

    @Test
    public void duke_updateExistingTask_ExistingTaskUpdatedPermanently() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add an item
        test.getResponse("todo test1");

        //Step 3: Update item
        String testList = test.getResponse("list");
        test.getResponse("done 1");

        //Step 4: Exit program
        test.getResponse("bye");

        //Step 5: Create a new instance of Duke
        Duke test2 = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);
        assertNotEquals(test2.getResponse("list"), testList);
    }

    @Test
    public void duke_deleteNonExistentTask_exceptionThrown() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        String testList = test.getResponse("list");
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add an item
        test.getResponse("todo test1");

        //Step 3: Check item is added to list
        assertNotEquals(test.getResponse("list"), testList);

        //Step 4: Exit program
        test.getResponse("bye");

        //Step 5: Create a new instance of Duke
        Duke test2 = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 6: Attempt to delete non-existent items
        assertEquals(test2.getResponse("delete 0"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");

        assertEquals(test2.getResponse("delete 2"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");

        assertEquals(test2.getResponse("delete 0"),
                "____________________________________________________________\n"
                        + "Invalid Input\n\n"
                        + "Invalid index entered. Type 'list' to see your list.\n"
                        + "____________________________________________________________");
    }

    @Test
    public void duke_deleteExistingTask_ExistingTaskDeletedPermanently() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        String testList = test.getResponse("list");
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add an item
        test.getResponse("todo test1");

        //Step 3: Check item is added to list
        assertNotEquals(test.getResponse("list"), testList);

        //Step 4: Exit program
        test.getResponse("bye");

        //Step 5: Create a new instance of Duke
        Duke test2 = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 6: Delete added item
        test2.getResponse("delete 1");

        // Exit program
        test2.getResponse("bye");

        //Step 7: Create a new instance of Duke
        Duke test3 = new Duke("data/dukeTest.txt", "data/fileTest.txt", false);

        //Step 8: Verify that list is empty
        assertEquals(test3.getResponse("list"), testList);
    }
}
