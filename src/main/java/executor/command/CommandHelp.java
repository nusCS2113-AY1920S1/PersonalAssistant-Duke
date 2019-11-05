package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Collections;

public class CommandHelp extends Command {

    /**
     * Constructor to provide the user with the details about the commands available.
     */
    public CommandHelp(String userInput) {
        this.userInput = userInput;
        this.description = "Provides the user with all the available commands and descriptions.\n"
                + "FORMAT : help";
        this.commandType = CommandType.HELP;
    }

    @Override
    public void execute(TaskList taskList) {
        String specificCommand = Parser.parseForPrimaryInput(CommandType.HELP, userInput).toUpperCase();
        Boolean flag = false;

        /* Helps to check if the extra input for a specific command is correct and if that input
           exists in the list of available commands.*/
        if (!specificCommand.isEmpty()) {
            for (String b : CommandType.getNames()) {
                if (b.equals(specificCommand)) {
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                Ui.dukeSays("Command invalid. Enter 'help' to see all the available commands");
                return;
            }
        }

        ArrayList<String> helpSortByAlphabet = new ArrayList<String>();
        for (String s : CommandType.getNames()) {
            if (s.equals(specificCommand) && !s.equals("ERROR") && !s.equals("TASK") && !s.equals("BLANK")) {
                CommandType specificCommandType = CommandType.valueOf(s);
                Command specific = Executor.createCommand(specificCommandType, "null");
                String specificDesc = specific.getDescription();
                String specificOut = s.toUpperCase() + " - " + specificDesc + "\n";
                Ui.dukeSays(specificOut);
                return;
            } else if (!s.equals("ERROR") && !s.equals("TASK") && !s.equals("BLANK")) {
                CommandType commandType = CommandType.valueOf(s);
                Command c = Executor.createCommand(commandType, "null");
                String commandDesc = c.getDescription();
                String temp = s.toUpperCase() + " - " + commandDesc + "\n";
                helpSortByAlphabet.add(temp);
                Collections.sort(helpSortByAlphabet, String.CASE_INSENSITIVE_ORDER);
            }
        }

        for (String a : helpSortByAlphabet) {
            System.out.println(a);
        }
        Ui.printSeparator();


    }

    @Override
    public void execute(Wallet wallet) {
    }
}
