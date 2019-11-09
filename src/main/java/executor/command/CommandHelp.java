package executor.command;

import executor.Executor;
import interpreter.Parser;
import storage.StorageManager;
import java.util.ArrayList;
import java.util.Collections;

public class CommandHelp extends Command {

    /**
     * Constructor to provide the user with the details about the commands available.
     */
    public CommandHelp(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Provides the user with all the available commands and descriptions.\n"
                + "FORMAT : help";
        this.commandType = CommandType.HELP;
    }

    @Override
    public void execute(StorageManager storageManager) {
        StringBuilder outputStr = new StringBuilder();
        String specificCommand = Parser.parseForPrimaryInput(CommandType.HELP, userInput).toUpperCase();

        /* Helps to check if the extra input for a specific command is correct and if that input
           exists in the list of available commands.*/
        if (!specificCommand.isEmpty()) {
            for (String b : CommandType.getNames()) {
                if (b.equals(specificCommand)) {
                    StringBuilder outputsSpecificCommandDesc = getDescriptionOfSpecificCommand(b);
                    this.infoCapsule.setCodeCli();
                    this.infoCapsule.setOutputStr(outputsSpecificCommandDesc.toString());
                    return;
                }
            }
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr("Command invalid. Enter 'help' to see all the available commands.\n");
            return;
        }

        ArrayList<String> listToStoreAllCommands = new ArrayList<String>();
        for (String s : CommandType.getNames()) {
            if (!s.equals("ERROR") && !s.equals("TASK") && !s.equals("BLANK")) {
                getDescriptionOfAllCommands(s, listToStoreAllCommands);
                Collections.sort(listToStoreAllCommands, String.CASE_INSENSITIVE_ORDER);
            }
        }
        for (String a : listToStoreAllCommands) {
            outputStr.append(a).append("\n");
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr.toString());
        for (String b : listToStoreAllCommands) {
            System.out.println(b);
        }
    }

    /**
     * Function to get the description of the specific command input by user.
     * @param b is the command that the user inputs
     * @return is the description of the command
     */
    private StringBuilder getDescriptionOfSpecificCommand(String b) {
        StringBuilder out = new StringBuilder();
        CommandType specificCommandType = CommandType.valueOf(b);
        Command specific = Executor.createCommand(specificCommandType, "null");
        String specificDesc = specific.getDescription();
        String specificOut = b.toUpperCase() + " - " + specificDesc + "\n";
        out.append(specificOut).append("\n");
        return out;
    }

    /**
     * Function to get all the descriptions of the commands available for the user.
     * @param s is the String to compare against all the available commands
     * @param newOut is the description of the current command
     */
    private void getDescriptionOfAllCommands(String s, ArrayList<String> newOut) {
        CommandType commandType = CommandType.valueOf(s);
        Command c = Executor.createCommand(commandType, "null");
        String commandDesc = c.getDescription();
        String temp = s.toUpperCase() + " - " + commandDesc + "\n";
        newOut.add(temp);
        return;
    }
}
