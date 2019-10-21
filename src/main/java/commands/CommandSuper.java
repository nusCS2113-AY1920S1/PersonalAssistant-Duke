package commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import com.sun.prism.CompositeMode;
import wrapper.CommandPair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class CommandSuper {

    private Controller uicontroller;

    private COMMANDKEYS[] subCommand;
    private COMMANDKEYS root;

    private TreeMap<String, ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();
    private COMMANDKEYS subRootCommand;
    private String payload;
    private boolean execute = false;
    private String helpInstructions;


    /**
     * Function to decide if the command should be executed.
     * If there is a typo in the command, the command should not be executed
     *
     */
    public boolean isExecute() {
        return execute;
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
    public CommandSuper(COMMANDKEYS root , COMMANDKEYS[] subCommand , Controller uicontroller) {

        this.uicontroller = uicontroller;
        this.subCommand = subCommand;
        this.root = root;


    }

    public Controller getUIController() {
        return uicontroller;
    }


    /**
     * initialise the Command values.
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     */
    public boolean initCommand(String[] commandArr , String command) {
        if (!subCommand(commandArr)) {
            return false;
        }
        processFlags(commandArr , command);
        processPayload(commandArr);


//        if (subCommand.length == 0) {
//            execute = true;
//        }
//
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
        processFlags(commandArr , command);
        processPayload(commandArr);
        setExecute(false);




    }

    /**
     * Find the subRoot Command of the user command.
     *
     * @param commandArr command that was entered by the user in split array form
     */
    public boolean subCommand(String[] commandArr) {
        if (commandArr.length <= 1) {
            subRootCommand = COMMANDKEYS.none;
            if (CommandStructure.cmdStructure.get(root).length > 0) {
                //Supposed to have Sub root but doesnt
                setExecute(false);
                ((MovieHandler) uicontroller).setAutoCompleteText("You are missing a few Arguments!!");
                return false;
            } else {
                setExecute(true);
                return true;
            }

        } else {
            try {
                for (COMMANDKEYS cmd : subCommand) {
                    if (COMMANDKEYS.valueOf(commandArr[1]) == cmd) {
                        subRootCommand = cmd;
                        execute = true;
                        return true;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            //Matching Subroot command not found

            CommandPair cmds = CommandDebugger.commandSpellChecker(commandArr, root, this.uicontroller);
            subRootCommand = cmds.getSubRootCommand();
            setExecute(false);
            ((MovieHandler) uicontroller).setAutoCompleteText("Did you mean :" + root + " " + subRootCommand + " "
                    + String.join(" ", Arrays.copyOfRange(commandArr, 2 , commandArr.length)));
            return true;

        }


    }

    /**
     * find flag values
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     */
    public void processFlags(String[] commandArr , String command) {

        String f = "";
        boolean found = false;
        String commandFlagSplit[] = command.split("-[a-z,A-Z]");

        ArrayList<String> flagOrder = new ArrayList<>();

        for (String s :commandArr) {
            if (s.matches("-[a-z,A-Z]")) {
                flagOrder.add(s);
            }
        }

        boolean first = true;

        int counter = 0;

        for (String flagValues : commandFlagSplit) {
            if (first) {
                first = false;
                continue;
            }
            String flagsIndividualValues[] = flagValues.split(",");

            ArrayList<String> listOfString = flagMap.get(flagOrder.get(counter));
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
                flagMap.put(flagOrder.get(flagOrder.size() - 1) , new ArrayList<String>());
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
        if (this.root != COMMANDKEYS.none) {
            if (this.subRootCommand != COMMANDKEYS.none) {
                payload =  getThePayload(2 , commandArr);
            } else {
                payload = getThePayload(1 , commandArr);
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
        while (i < commandArr.length && !commandArr[i].matches("-[a-z]")) {
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
            for (String val : entry.getValue()) {
                flagsStr += val;
                flagsStr += " , ";
            }
        }

        return getRoot().toString() + " " + getSubRootCommand().toString() + " "  + payload + " " + flagsStr;
    }


    /**
     * Abstract class to be implemented for each root command class.
     */
    public abstract void executeCommands() throws IOException;



}
