import duke.command.NewReminderCommand;
import duke.exception.DukeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * JUnit class testing the class NewReminderCommand.
 *
 * @author Pang Jia Jun Vernon
 */
public class NewReminderCommandTest extends CommandTest {
    /**
     * Tests parse() with valid input. Except test to be successful.
     *
     * @throws DukeException If the input specified is in an incorrect format.
     */
    @Test
    public void parseInputs_validInputs_success() throws DukeException {
        new NewReminderCommand().parse("remind 1 /after 18/09/2019 0200");
        //TODO: assert
    }

    /**
     * Tests parse() with invalid inputs. Expect exceptions to be thrown.
     */
    @Test
    public void parseInputs_invalidInputs_exceptionThrown() {
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind"));
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind 1"));
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind 1 /after"));
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind 1 /after 18/09/2019"));
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind 1 /after 0200"));
        assertThrows(DukeException.class, () -> new NewReminderCommand().parse("remind 1 18/09/2019 0200"));
    }
}
