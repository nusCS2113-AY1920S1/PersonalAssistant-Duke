//@@lmtaek

package duke.commands;

import duke.commands.functional.HelpCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpCommandTest {

    @Test
    public void showHelpCommands() {
        ArrayList<String> testHelpCommands = new HelpCommand().getHelpCommands();
        assertTrue(testHelpCommands.size() > 0, "HelpCommand's help commands"
                + "could not be accessed.");
    }
}
