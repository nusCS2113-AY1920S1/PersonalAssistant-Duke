import chronologer.command.Command;
import chronologer.command.ThemeCommand;
import chronologer.exception.ChronologerException;
import chronologer.ui.UiMessageHandler;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import chronologer.task.Task;
import chronologer.task.TaskList;
import java.util.ArrayList;

/**
 * Tests theme functionality.
 *
 * @author Sai Ganesh Suresh
 * @version v1.4
 */
class ThemeTest {
    private static ArrayList<Task> testTheme;
    private static TaskList testThemeChange;
    private static ObservableList<Integer> test;


    @BeforeAll
    public static void setup() {
        testTheme = new ArrayList<>();
        testThemeChange = new TaskList(testTheme);
        test = testThemeChange.getCurrentSetting();
    }

    @Test
    @Order(1)
    public void testPresetTheme() {
        int indexOfThemeInObservableList = 0;
        Assertions.assertEquals(0, test.get(indexOfThemeInObservableList));
    }

    @Test
    @Order(2)
    public void testThemeChange() {
        String messageToUser = testThemeChange.updateTheme(1);
        Assertions.assertEquals("Theme changed!", messageToUser);
    }

    @Test
    @Order(3)
    public void testThemeRepeat() {
        String messageToUser = testThemeChange.updateTheme(1);
        Assertions.assertEquals("Theme cannot be changed!", messageToUser);
    }

    @Test
    @Order(4)
    public void testThemeCommand() throws ChronologerException {
        Command theme = new ThemeCommand(0);
        theme.execute(testThemeChange, null, null);
        Assertions.assertEquals("Theme changed!", UiMessageHandler.getOutputForGui());
    }
}
