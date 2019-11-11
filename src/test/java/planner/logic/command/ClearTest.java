//@@author LongLeCE

package planner.logic.command;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.modules.TaskList;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.module.ModuleTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ClearTest extends CommandTest {

    private static final TaskList<Cca> emptyCcaList = new TaskList<>();
    private static final TaskList<ModuleTask> emptyModuleList = new TaskList<>();
    private String expectedOutput;
    private String inputCca = "add cca soccer --begin 3 --end 5 --dayOfWeek Monday\n"
                            + "add cca dance --begin 7 --end 9 --dayOfWeek Monday\n"
                            + "add module CS2101\n"
                            + "add cca\n"
                            + "add cca --begin 3 --end 5 --dayOfWeek Monday\n"
                            + "add cca clash --begin 3 --end 5 --dayOfWeek Monday\n"
                            + "add\n"
                            + "add module CG1111 --begin 3 --end 5 --dayOfWeek Wednesday\n"
                            + "scheduleCca 1 --begin 3 --end 5 --dayOfWeek Monday\n"
                            + "scheduleCca 1 --begin 3 --end 5 --dayOfWeek Thursday\n"
                            + "scheduleCca\n"
                            + "random string\n"
                            + "scheduleCca 1";
    private String inputModule = inputCca;

    ClearTest() throws ModException {
        super();
    }

    @DisplayName("Clear Cca Functionality Test")
    @Test
    public void clearCcaShouldClearCcaList() {
        resetAll();
        execute(inputCca);
        assertNotEquals(user.getCcas(), emptyCcaList);
        execute("clear cca\n"
                + "y");
        assertIterableEquals(user.getCcas(), emptyCcaList);
        assertNotEquals(user.getModules(), emptyModuleList);
    }

    @DisplayName("Clear Cca Output Test")
    @Test
    public void clearCcaOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputCca);
        expectedOutput = "Got it, added the follow cca!\n"
                       + "[C] soccer | 03:00 - 05:00 on MONDAY\n"
                       + "Got it, added the follow cca!\n"
                       + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                       + "Got it, added the follow module!\n"
                       + "[not taken] CS2101 | ModuleCode:CS2101, MC:4.0, SU: can S/U, grade: \n"
                       + "Got it, added the follow module!\n"
                       + "[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: "
                       + "| 03:00 - 05:00 on WEDNESDAY\n"
                       + "Got it, added the follow cca!\n"
                       + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());
        execute("clear cca\n"
                + "y");
        expectedOutput = "Are you sure you want to clear your cca?\n"
                       + "Done! Your cca have been cleared\n";
        assertEquals(expectedOutput, getOut());
    }

    @DisplayName("Clear Module Functionality Test")
    @Test
    public void clearModuleShouldClearModuleList() {
        resetAll();
        execute(inputModule);
        assertNotEquals(user.getModules(), emptyModuleList);
        execute("clear module\n"
                + "y");
        assertIterableEquals(user.getModules(), emptyModuleList);
        assertNotEquals(user.getCcas(), emptyCcaList);
    }

    @DisplayName("Clear Module Output Test")
    @Test
    public void clearModuleOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputModule);
        expectedOutput = "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                + "Got it, added the follow module!\n"
                + "[not taken] CS2101 | ModuleCode:CS2101, MC:4.0, SU: can S/U, grade: \n"
                + "Got it, added the follow module!\n"
                + "[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: "
                + "| 03:00 - 05:00 on WEDNESDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());
        execute("clear module\n"
                + "y");
        expectedOutput = "Are you sure you want to clear your module?\n"
                + "Done! Your module have been cleared\n";
        assertEquals(expectedOutput, getOut());
    }

    @DisplayName("Clear Data Functionality Test")
    @Test
    public void clearDataShouldClearModuleList() {
        resetAll();
        execute(inputCca);
        assertNotEquals(user.getCcas(), emptyCcaList);
        assertNotEquals(user.getModules(), emptyModuleList);
        execute("clear data\n"
                + "y");
        assertIterableEquals(user.getCcas(), emptyCcaList);
        assertIterableEquals(user.getModules(), emptyModuleList);
    }

    @DisplayName("Clear Data Output Test")
    @Test
    public void clearDataOutputShouldMatchExpectedOutput() {
        resetAll();
        execute(inputCca);
        expectedOutput = "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] dance | 07:00 - 09:00 on MONDAY\n"
                + "Got it, added the follow module!\n"
                + "[not taken] CS2101 | ModuleCode:CS2101, MC:4.0, SU: can S/U, grade: \n"
                + "Got it, added the follow module!\n"
                + "[not taken] CG1111 | ModuleCode:CG1111, MC:6.0, SU: can S/U, grade: "
                + "| 03:00 - 05:00 on WEDNESDAY\n"
                + "Got it, added the follow cca!\n"
                + "[C] soccer | 03:00 - 05:00 on MONDAY, 03:00 - 05:00 on THURSDAY\n";
        assertEquals(expectedOutput, getOut());
        execute("clear data\n"
                + "y");
        expectedOutput = "Are you sure you want to clear your data?\n"
                + "Done! Your data have been cleared\n";
        assertEquals(expectedOutput, getOut());
    }

    @DisplayName("Clear Password Functionality Test")
    @Test
    public void clearPasswordShouldDoNothingIfNoPasswordIsSet() {
        resetAll();
        assertNull(user.getPasswordHash());
        execute("clear password\n"
                + "y");
        assertNull(user.getPasswordHash());
    }

    @DisplayName("Clear Password Output Test")
    @Test
    public void clearPasswordOutputShouldBeAnErrorIfNoPasswordIsSet() {
        resetAll();
        assertNull(user.getPasswordHash());
        execute("clear password\n"
                + "y");
        expectedOutput = "Are you sure you want to clear your password?\n"
                + "No active password found!\n";
        assertEquals(expectedOutput, getOut());
    }
}
