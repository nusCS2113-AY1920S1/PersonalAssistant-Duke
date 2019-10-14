package commands;

import MovieUI.Controller;
import MovieUI.MovieHandler;
import wrapper.CommandPair;

import java.io.IOException;
import java.util.*;

public abstract class CommandSuper {

    private Controller UIController;

    private COMMANDKEYS[] SubCommand;
    private COMMANDKEYS Root;

    private TreeMap<String, ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();
    private COMMANDKEYS subRootCommand;
    private String Payload;
    private boolean execute = false;


    /**
     * Function to decide if the command should be executed
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
        return Root;
    }

    public TreeMap<String, ArrayList<String>> getFlagMap() {
        return flagMap;
    }

    public COMMANDKEYS getSubRootCommand() {
        return subRootCommand;
    }

    public String getPayload() {
        return Payload;
    }

    /**
     * Constructor for each Command Super class
     */
    public CommandSuper(COMMANDKEYS Root , COMMANDKEYS[] SubCommand , Controller UIController){

        this.UIController = UIController;
        this.SubCommand = SubCommand;
        this.Root = Root;

    }

    public Controller getUIController() {
        return UIController;
    }


    /**
     * initialise the Command values
     *
     * @param CommandArr command that was entered by the user in split array form
     * @param Command   command that was entered by the user.
     */
    public void initCommand(String[] CommandArr ,String Command){
        subCommand(CommandArr);
        processFlags(CommandArr ,Command);
        processPayload(CommandArr);
        if (SubCommand.length == 0) {
            execute = true;
        }
    }


    /**
     * initialise the Command values
     *
     * @param CommandArr command that was entered by the user in split array form
     * @param Command   command that was entered by the user.
     * @param subRootCommand the subRoot command  that was found
     */
    public void initCommand(String[] CommandArr, String Command, COMMANDKEYS subRootCommand){

        this.subRootCommand = subRootCommand;
        processFlags(CommandArr ,Command);
        processPayload(CommandArr);


    }

    /**
     * Find the subRoot Command of the user command
     *
     * @param CommandArr command that was entered by the user in split array form
     */
    public void subCommand(String[] CommandArr){
        if(CommandArr.length <= 1){
            subRootCommand = COMMANDKEYS.none;
            if(CommandStructure.cmdStructure.get(Root).length > 0){
                setExecute(false);
                ((MovieHandler)UIController).setFeedbackText("You are missing a few Arguments!!");
            }

        }else{
            try{
                for(COMMANDKEYS cmd : SubCommand){
                    if(COMMANDKEYS.valueOf(CommandArr[1])== cmd){
                        System.out.println("FOUND Sub command" + cmd);
                        subRootCommand = cmd;
                        execute = true;
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            CommandPair cmds = Command_Debugger.commandSpellChecker(CommandArr, Root, this.UIController);
            subRootCommand = cmds.getSubRootCommand();
        }


    }

    /**
     * find flag values
     *
     * @param commandArr command that was entered by the user in split array form
     * @param command   command that was entered by the user.
     */
    public void processFlags(String[] commandArr , String command){

        String f = "";
        boolean found = false;
        String commandFlagSplit[] = command.split("-[a-z]");

        ArrayList<String> flagOrder = new ArrayList<>();

        for(String s :commandArr){
            if(s.matches("-[a-z]")){
                flagOrder.add(s);
            }
        }

        boolean first = true;

        int counter = 0;

        for(String flagValues : commandFlagSplit){
            if(first){
                first = false;
                continue;
            }
            String flagsIndividualValues[] = flagValues.split(",");

            ArrayList<String> listOfString = flagMap.get(flagOrder.get(counter));
            if (listOfString == null) {
                listOfString = new ArrayList<String>();
            }
            for(String individualFlags: flagsIndividualValues){
                listOfString.add(individualFlags);


            }

            flagMap.put(flagOrder.get(counter), listOfString);
            counter ++;
        }

        if(flagOrder.size() != 0){
            if(flagMap.get(flagOrder.get(flagOrder.size()-1)) == null){
                flagMap.put(flagOrder.get(flagOrder.size()-1) , new ArrayList<String>());
            }

        }

        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

    }

    /**
     * find payload of the user Command based on the interpretation by Command Parser
     *
     * @param CommandArr command that was entered by the user in split array form
     */
    public void processPayload(String []CommandArr){
        if(this.Root != COMMANDKEYS.none){
            if(this.subRootCommand != COMMANDKEYS.none){
                Payload =  getThePayload(2 , CommandArr);
            }else{
                Payload = getThePayload(1 , CommandArr);
            }
        } else {
            Payload = "";
        }
    }

    /**
     * find payload of the user Command
     *
     * @param CommandArr command that was entered by the user in split array form
     * @param start the start index of the payload in the user command
     */
    public static String getThePayload(int start, String[] CommandArr) {
        int i = 0;
        while (i < CommandArr.length && !CommandArr[i].matches("-[a-z]")) {
            System.out.println(i + "." + CommandArr[i]);
            i++;
        }
        String payload = "";
        for (int j = start; j < i; j++) {
            payload += CommandArr[j];
            payload += " ";
        }
        return payload.trim();
    }

    /**
     * Abstract class to be implemented for each root command class
     */
    public abstract void executeCommands() throws IOException;

}