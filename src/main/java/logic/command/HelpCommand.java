package logic.command;

import model.Model;

//@@author AugGust
public class HelpCommand extends Command {

    /**
     * execute help command
     *
     * @param model no used
     * @return a string pass to commandOutput, which will be shown on window
     */
    public CommandOutput execute(Model model) {
        String output =
                "Available Commands\n"
                        + "1. add task/member\n"
                        + "2. find\n"
                        + "3. done\n"
                        + "4. delete\n"
                        + "5. reminder\n"
                        + "6. link\n"
                        + "7. unlink\n"
                        + "8. edit\n"
                        + "9. list\n"
                        + "10. schedule\n"
                        + "11. bye\n";

        return new CommandOutput(output);
    }
}
