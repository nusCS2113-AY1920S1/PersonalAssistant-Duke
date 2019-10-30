package logic.command;

import model.Model;


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
                        + "5. snooze\n"
                        + "6. reminder\n"
                        + "7. remove\n"
                        + "8. member\n"
                        + "9. link\n"
                        + "10. unlink\n"

                        + "11. list\n"
                        + "12. schedule\n"
                        + "13. check\n"
                        + "14. bye\n";

        return new CommandOutput(output);
    }
}
