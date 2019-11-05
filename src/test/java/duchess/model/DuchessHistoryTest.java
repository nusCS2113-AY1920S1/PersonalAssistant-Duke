package duchess.model;

import duchess.exceptions.DuchessException;
import duchess.logic.commands.Command;
import duchess.logic.commands.DoneCommand;
import duchess.logic.commands.HistoryCommand;
import duchess.logic.commands.ListTasksCommand;
import duchess.logic.commands.RedoCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DuchessHistoryTest {

    @Test
    public void addToFullLog_invalidEmptyInput_success() {
        DuchessHistory duchessHistory = new DuchessHistory();

        assertTrue(DuchessHistory.getFullLog().size() == 0);
        duchessHistory.add("");
        assertTrue(DuchessHistory.getFullLog().size() == 1);
    }

    @Test
    public void addToFullLog_invalidInput_success() {
        DuchessHistory duchessHistory = new DuchessHistory();

        assertTrue(DuchessHistory.getFullLog().size() == 0);
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);

        duchessHistory.add("random stuffs");
        assertTrue(DuchessHistory.getFullLog().size() == 1);
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
    }

    @Test
    public void addToValidCommandLog_invalidEmptyInput_failure() {
        DuchessHistory duchessHistory = new DuchessHistory();
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
        duchessHistory.add("");
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
    }

    @Test
    public void addToValidCommandLog_invalidInput_failure() {
        DuchessHistory duchessHistory = new DuchessHistory();
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
        duchessHistory.add("random stuffs");
        assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
    }

    @Test
    public void addValidCommands_validInput_success() {
        DuchessHistory duchessHistory = new DuchessHistory();

        try {
            final Command command1 = new DoneCommand(2);
            final Command command2 = new HistoryCommand();
            final Command command3 = new ListTasksCommand();
            final Command command4 = new RedoCommand(new ArrayList<String>());

            assertTrue(DuchessHistory.getValidCommandLog().size() == 0);
            duchessHistory.addValidCommands(command1);
            assertTrue(DuchessHistory.getValidCommandLog().size() == 1);
            duchessHistory.addValidCommands(command2);
            assertTrue(DuchessHistory.getValidCommandLog().size() == 2);
            duchessHistory.addValidCommands(command3);
            assertTrue(DuchessHistory.getValidCommandLog().size() == 3);
            duchessHistory.addValidCommands(command4);
            assertTrue(DuchessHistory.getValidCommandLog().size() == 4);
        } catch (DuchessException e) {
            assertTrue(e instanceof DuchessException);
        }
    }
}
