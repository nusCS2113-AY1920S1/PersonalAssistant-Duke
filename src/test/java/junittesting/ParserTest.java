package junittesting;

import javacake.JavaCake;
import javacake.exceptions.CakeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class ParserTest {


    private static JavaCake javaCake;
    private static String response;


    /**
     * Tests if the helper blocks any actual command and if it helps the user
     * in case of typos.
     */
    @Test
    public void testHelper() {
        try {
            javaCake = new JavaCake();
        } catch (CakeException e) {
            System.out.println(e.getMessage());
        }

        String[] commands = {"exit", "list", "back", "help", "score", "reset", "goto",
                             "overview", "deadline", "editnote", "createnote", "listnote", "deletenote",
                             "change", "reminder", "viewnote", "done", "delete", "snooze"};

        for (int i = 0; i < commands.length; i++) {
            response = javaCake.getResponse(commands[i]);
            String errorMessage = "Sorry, but do you mean this : ";
            assertFalse(response.contains(errorMessage));
        }

        String[] typos = {"exir", "exitt", "exi"};
        for (int i = 0; i < typos.length; i++) {
            response = javaCake.getResponse(typos[i]);
            String errorMessage = "Sorry, but do you mean this :";
            assertTrue(response.contains(errorMessage));
        }
    }
}
