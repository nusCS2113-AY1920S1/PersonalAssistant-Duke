package executor.command;

import executor.task.TaskList;
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
                + "FORMAT : help \n";
        this.commandType = CommandType.HELP;
    }

    @Override
    public void execute(TaskList taskList) {
          String[] checkerForExtraInput;
          checkerForExtraInput = userInput.split(" ");
         try {
             if (!checkerForExtraInput[1].isEmpty()) {
                 System.out.println("Extra variable is added. Please follow -> FORMAT : help");
                 return;
             }
         } catch (Exception e) {

         }
          ArrayList<String> help = new ArrayList<String>();
          for (String s : CommandType.getNames()) {
              if (!s.equals("ERROR") && !s.equals("TASK") && !s.equals("BLANK")) {
                  CommandType commandType = CommandType.valueOf(s);
                  Command c = Executor.createCommand(commandType, "null");
                  String commandDesc = c.getDescription();
                  String temp = s.toUpperCase() + " - " + commandDesc;
                  help.add(temp);
                  Collections.sort(help, String.CASE_INSENSITIVE_ORDER);
              }
          }
          for (String a : help) {
              System.out.println(a);
          }
          Ui.printSeparator();
    }

    @Override
    public void execute(Wallet wallet) {
    }
}
