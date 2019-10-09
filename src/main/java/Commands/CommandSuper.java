package Commands;

import MovieUI.Controller;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import wrapper.CommandPair;

import java.util.*;

public abstract class CommandSuper {

    private Controller UIController;

    private COMMAND_KEYS[] SubCommand;
    private COMMAND_KEYS Root;

    private TreeMap<String , ArrayList<String>> flagMap = new TreeMap<String, ArrayList<String>>();
    private COMMAND_KEYS subRootCommand;
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

    public COMMAND_KEYS getRoot() {
        return Root;
    }

    public TreeMap<String, ArrayList<String>> getFlagMap() {
        return flagMap;
    }

    public COMMAND_KEYS getSubRootCommand() {
        return subRootCommand;
    }

    public String getPayload() {
        return Payload;
    }

    /**
     * Constructor for each Command Super class
     */
    public CommandSuper(COMMAND_KEYS Root , COMMAND_KEYS[] SubCommand , Controller UIController){

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
        if(SubCommand.length ==0){
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
    public void initCommand(String[] CommandArr, String Command, COMMAND_KEYS subRootCommand){
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
        try{
            for(COMMAND_KEYS cmd : SubCommand){
                if(COMMAND_KEYS.valueOf(CommandArr[1])== cmd){
                    System.out.println("FOUND Sub command" + cmd);
                    subRootCommand = cmd;
                    execute = true;
                    return;
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }

        CommandPair cmds = Command_Debugger.commandSpellChecker(CommandArr , Root , this.UIController);
        subRootCommand = cmds.getSubRootCommand();

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

        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

    }

    /**
     * find payload of the user Command
     *
     * @param CommandArr command that was entered by the user in split array form
     */
    public void processPayload(String []CommandArr){
        if(this.Root != COMMAND_KEYS.none){
            if(this.subRootCommand != COMMAND_KEYS.none){
                Payload =  getThePayload(2 , CommandArr);
            }else{
                Payload = getThePayload(1 , CommandArr);
            }
        }else{
            Payload = "";
        }
    }

    public static String getThePayload(int start , String []CommandArr){
        int i  = 0;
        while( i < CommandArr.length && !CommandArr[i].matches("-[a-z]")){
            i++;
        }
        String payload = "";
        for(int j = start;j < i ; j++ ){
            payload += CommandArr[j];
            payload += " ";
        }
        return payload.trim();
    }


    /**
     * Abstract class to be implemented for each root command class
     */
    public abstract void executeCommands();


}
