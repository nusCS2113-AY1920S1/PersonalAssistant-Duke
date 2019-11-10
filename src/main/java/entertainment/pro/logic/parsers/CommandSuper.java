package entertainment.pro.logic.parsers;

import entertainment.pro.commons.strings.PromptMessages;
import entertainment.pro.commons.assertions.CommandAssertions;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.InvalidFormatCommandException;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.model.CommandPair;

import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Template command class for each root command.
 * Contains helper functions to breakdown command to useful functions.
 *
 */
public abstract class CommandSuper {

    private Controller uicontroller;

    private COMMANDKEYS[] subCommand;
    private COMMANDKEYS root;

    private TreeMap<String, ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();
    private COMMANDKEYS subRootCommand;
    private String payload;
    private boolean execute = false;

    private static Logger logger = Logger.getLogger(CommandSuper.class.getName());

    protected CommandSuper() {
        logger = Logger.getLogger(CommandSuper.class.getName());
    }


    /**
     * Function to decide if the command should be executed.
     * If there is a typo in the command, the command should not be executed
     *
     */
    public boolean isExecute() {
        return execute;
    }

    public static Logger getLogger() {
        return logger;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public COMMANDKEYS getRoot() {
        return root;
    }

    public TreeMap<String, ArrayList<String>> getFlagMap() {
        return flagMap;
    }

    public COMMANDKEYS getSubRootCommand() {
        return subRootCommand;
    }

    public String getPayload() {
        return payload;
    }

    /**
     * Constructor for each Command Super class.
     */
    public CommandSuper(COMMANDKEYS root, COMMANDKEYS[] subCommand, Controller uicontroller) {

        this.uicontroller = uicontroller;
        this.subCommand = subCommand;
        this.root = root;
        logger = Logger.getLogger(root.toString());

    }

    public Controller getUiController() {
        return uicontroller;
    }


    /**
     * initialise the Command values.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     */

    public boolean initCommand(String[] commandArr , String command) throws MissingInfoException {

        assert (CommandAssertions.assertIsLowerString(command));
        assert (CommandAssertions.assertIsLowerStringArr(commandArr));

        if (!subCommand(commandArr)) {
            return false;
        }

        try {
            processFlags(command);
        } catch (InvalidFormatCommandException e) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
        }
        processPayload(commandArr);

        return true;
    }


    /**
     * initialise the Command values.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     * @param subRootCommand the subRoot command  that was found
     */
    public void initCommand(String[] commandArr, String command, COMMANDKEYS subRootCommand) {

        this.subRootCommand = subRootCommand;

        try {
            processFlags(command);
        } catch (InvalidFormatCommandException e) {
            ((MovieHandler) this.getUiController()).setGeneralFeedbackText(PromptMessages.INVALID_FORMAT);
        }
        processPayload(commandArr);
        setExecute(false);

    }

    /**
     * Find the subRoot Command of the user command.
     *
     * @param commandArr command that was entered by the user in split array form
     */
    public boolean subCommand(String[] commandArr) throws MissingInfoException {
        if (commandArr.length <= 1) {
            subRootCommand = COMMANDKEYS.NONE;
            if (CommandStructure.cmdStructure.get(root).length > 0) {
                setExecute(false);
                if (uicontroller != null) {
                    throw new MissingInfoException(PromptMessages.COMMAND_MISSING_ARGS);
                }
                return false;
            } else {
                setExecute(true);
                return true;
            }

        } else {
            try {
                for (COMMANDKEYS cmd : subCommand) {
                    assert (CommandAssertions.assertIsLowerString(commandArr[1]));
                    if (COMMANDKEYS.valueOf(commandArr[1].toUpperCase()) == cmd) {
                        subRootCommand = cmd;
                        execute = true;
                        return true;
                    }
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.toString());
            }

            CommandPair cmds = CommandDebugger.commandSpellChecker(commandArr, root, this.uicontroller);
            subRootCommand = cmds.getSubRootCommand();
            setExecute(false);
            if (uicontroller != null) {
                ((MovieHandler) uicontroller).setAutoCompleteText(getDidYouMeanText(commandArr));
            }

            return true;

        }


    }


    /**
     * Get Feedback String.
     * @return Feedback to ask user about his/her intentions for the command
     */
    private String getDidYouMeanText(String[] commandArr) {
        return PromptMessages.DID_YOU_MEAN + root.toString().toLowerCase() + " "
                + subRootCommand.toString().toLowerCase() + " "
                + String.join(" ", Arrays.copyOfRange(commandArr, 2, commandArr.length));
    }

    /**
     * find flag values.
     *
     * @param command   command that was entered by the user.
     */
    public void processFlags(String command) throws InvalidFormatCommandException {

        String[] commandArr = command.split(" ");

        String f = "";
        boolean found = false;
        String[] commandFlagSplit = command.split("-[a-z,A-Z]");

        ArrayList<String> flagOrder = new ArrayList<>();

        logger.log(Level.INFO, "Finding Flags");
        for (String s :commandArr) {
            if (s.matches("-[a-z,A-Z]")) {
                flagOrder.add(s);
            }
        }

        boolean first = true;

        if (flagOrder.size() == 0) {
            return;
        }

        int counter = 0;

        for (String flagValues : commandFlagSplit) {
            if (first) {
                first = false;
                continue;
            }
            String[] flagsIndividualValues = flagValues.split(",");
            ArrayList<String> listOfString = new ArrayList<>();
            try {
                listOfString = flagMap.get(flagOrder.get(counter));
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidFormatCommandException();
            }
            if (listOfString == null) {
                listOfString = new ArrayList<String>();
            }
            for (String individualFlags: flagsIndividualValues) {
                listOfString.add(individualFlags.toLowerCase().trim());
            }

            flagMap.put(flagOrder.get(counter), listOfString);
            counter++;
        }

        if (flagOrder.size() != 0) {
            if (flagMap.get(flagOrder.get(flagOrder.size() - 1)) == null) {
                flagMap.put(flagOrder.get(flagOrder.size() - 1), new ArrayList<String>());
            }

        }

        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

    }

    /**
     * find payload of the user Command based on the interpretation by Command Parser.
     *
     * @param commandArr command that was entered by the user in split array form
     */
    public void processPayload(String []commandArr) {
        if (this.root != COMMANDKEYS.NONE) {
            if (this.subRootCommand != COMMANDKEYS.NONE) {
                payload =  getThePayload(2, commandArr);
            } else {
                payload = getThePayload(1, commandArr);
            }
        } else {
            payload = "";
        }
    }

    /**
     * find payload of the user Command.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param start the start index of the payload in the user command
     */
    public static String getThePayload(int start, String[] commandArr) {
        int i = 0;
        while (i < commandArr.length && !commandArr[i].matches("-[a-z,A-Z]")) {
            System.out.println(i + "." + commandArr[i]);
            i++;
        }
        String payload = "";
        for (int j = start; j < i; j++) {
            payload += commandArr[j];
            payload += " ";
        }
        return payload.trim();
    }

    /**
     * Print Command method.
     */
    public String toString() {
        String payload = getPayload();

        String flagsStr = "";
        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            flagsStr += entry.getKey();
            flagsStr += " ";
            boolean first = true;
            for (String val : entry.getValue()) {
                if (first) {
                    flagsStr += val;
                    first = false;
                    continue;
                }
                flagsStr += " , ";
                flagsStr += val;
            }
            flagsStr += " ";
        }

        return getRoot().toString().toLowerCase() + " " + getSubRootCommand().toString().toLowerCase()
                + " "  + payload + " " + flagsStr;
    }

    /**
     * Abstract class to be implemented for each root command class.
     */
    public abstract void executeCommands() throws IOException, Exceptions;
}
