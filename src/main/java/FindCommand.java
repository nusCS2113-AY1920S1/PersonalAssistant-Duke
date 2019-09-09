import java.io.IOException;

/**
 * Command to find and display tasks the user searches for
 */
public class FindCommand extends Command{

    /**
     * User input text to search
     */
    private String textToFind;
    public FindCommand(String input, String[] splitStr) throws DukeException {
        if (splitStr.length == 1) throw new DukeException("☹ OOPS!!! Please input a string to search");
        this.textToFind = input.substring(5);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, IOException {
        boolean found = false;
        for(int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).contains(textToFind)) {
                found = true;
                System.out.println(i + 1 + ". " + tasks.get(i).toString());
            }
        }
        if (!found) ui.showString("No items match your search!");
    }
}
