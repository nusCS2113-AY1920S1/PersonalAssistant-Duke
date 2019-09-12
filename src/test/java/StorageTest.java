import duke.Duke;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StorageTest {

    @Test
    public void testCreate() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", false);

        //Step 1: Check for empty list (successful creation of new file)
        assertEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");

        //Step 2: Add an item
        test.getResponse("todo test1");

        //Step 3: Exit program
        test.getResponse("bye");

        //Step 4: Create a new instance of Duke
        Duke test2 = new Duke("data/dukeTest.txt", false);

        //Step 5: Check data persists across Duke instances.
        assertNotEquals(test.getResponse("list"),
                "____________________________________________________________\n"
                        + "Here are the tasks in your list:\n"
                        + "____________________________________________________________");
    }

    @Test
    public void testUpdate() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", false);

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
        Duke test2 = new Duke("data/dukeTest.txt", false);
        assertNotEquals(test2.getResponse("list"), testList);
    }

    @Test
    public void testDelete() {
        File file = new File("data/dukeTest.txt");
        file.delete();

        //Set pseudo-GUI mode to allow us to manually input commands
        Duke test = new Duke("data/dukeTest.txt", false);

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
        Duke test2 = new Duke("data/dukeTest.txt", false);

        //Step 6: Delete added item
        test2.getResponse("delete 1");

        // Exit program
        test2.getResponse("bye");

        //Step 7: Create a new instance of Duke
        Duke test3 = new Duke("data/dukeTest.txt", false);

        //Step 8: Verify that list is empty
        assertEquals(test3.getResponse("list"), testList);
    }

}
