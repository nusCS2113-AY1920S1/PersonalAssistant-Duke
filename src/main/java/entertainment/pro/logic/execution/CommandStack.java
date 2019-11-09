package entertainment.pro.logic.execution;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.CommandSuper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains all the commands the user entered to be kept track of.
 */
public class CommandStack {
    private static ArrayList<CommandSuper> myStack = new ArrayList<>();
    private static int counter = 0;
    private static long lastexec = System.currentTimeMillis();
    private static Logger logger = Logger.getLogger(CommandStack.class.getName());


    /**
     * Adds the command to the command Stack.
     */
    public static void pushCmd(CommandSuper cmd) throws IOException, Exceptions {
        if (cmd.getRoot() == COMMANDKEYS.YES) {
            executeLastCommand();
        } else {
            myStack.add(cmd);
            if (cmd.isExecute()) {
                cmd.executeCommands();
            }
        }

    }


    /**
     * Returns the next command for the command history.
     *
     * @return the next command in the list
     */
    public static String nextCommand() {

        if (myStack.size() == 0) {
            return null;
        }

        if (System.currentTimeMillis() - lastexec > 3000) {
            counter = 0;
        } else {
            counter += 1;
            counter %= myStack.size();
        }
        lastexec = System.currentTimeMillis();

        return myStack.get(myStack.size() - 1 - counter).toString();
    }


    /**
     * pops the latest command from the command list.
     *
     * @return returns the latest commmand
     */
    public static CommandSuper popCmd() {
        if (myStack.size() < 1) {
            return null;
        }
        CommandSuper topCmd = myStack.get(myStack.size() - 1);
        myStack.remove(myStack.size() - 1);
        return topCmd;
    }

    /**
     * returns the latest command from the command list.
     *
     * @return returns the latest commmand
     */
    public static CommandSuper topCmd() {

        return myStack.size() < 1 ? null : myStack.get(myStack.size() - 1);
    }

    /**
     * Clears the command list.
     */
    public static void clearStack() {
        myStack.clear();
    }

    /**
     * Execute the latest command.
     * This is in the case where by the user mistyped the command and has to type 'yes' to the
     * prompt to execute the predicted command
     */
    public static void executeLastCommand() throws IOException, Exceptions {
        logger.log(Level.INFO , PromptMessages.EXECUTING_MISTYPED_COMMAND);
        if (myStack.size() < 1) {
            return;
        }
        CommandSuper cmd = myStack.get(myStack.size() - 1);
        if (!cmd.isExecute()) {
            cmd.executeCommands();
            cmd.setExecute(true);
        }
    }

    /**
     * Returns the size of the command stack.
     * @return MyStack Size
     */
    public static int getSize() {
        return myStack.size();
    }
}
