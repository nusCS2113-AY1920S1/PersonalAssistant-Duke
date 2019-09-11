<<<<<<< HEAD
import UI.Ui;
=======
>>>>>>> f7938ba1adc707a6bdf34ebdb286314c7a8a91d1

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class ByeCommand extends Command {
    /**
     * Returns void execute function for ByeCommand.
     *
     * @param list An array list of type Task.
     * @param ui Class ui
     * @param storage class storage
     * @return Void.
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException {
        System.out.println("Bye! Hope to see you again soon!");
    }
    /**
     * Returns boolean true to exit the program.
     *
     * @return boolean.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
