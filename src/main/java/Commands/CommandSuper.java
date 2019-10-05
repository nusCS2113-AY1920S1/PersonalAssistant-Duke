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

    public CommandSuper(COMMAND_KEYS Root , COMMAND_KEYS[] SubCommand , Controller UIController){

        this.UIController = UIController;
        this.SubCommand = SubCommand;
        this.Root = Root;

    }

    public Controller getUIController() {
        return UIController;
    }

    public void initCommand(String[] CommandArr){
        subCommand(CommandArr);
        processFlags(CommandArr);
        processPayload(CommandArr);
        if(SubCommand.length ==0){
            execute = true;
        }
    }
    public void initCommand(String[] CommandArr, COMMAND_KEYS subRootCommand){
        this.subRootCommand = subRootCommand;
        processFlags(CommandArr);
        processPayload(CommandArr);
    }


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

    public void processFlags(String commandArr[]){

        String f = "";
        boolean found = false;
        for(String s : commandArr){
            if(found && !s.matches("-[a-z]")){
                ArrayList<String> listOfString = flagMap.get(f);
                if (listOfString == null) {
                    listOfString = new ArrayList<String>();
                    listOfString.add(s);
                    flagMap.put(f, listOfString);

                }else{
                    listOfString.add(s);
                }

            }
            if(s.matches("-[a-z]")) {
                f = s;
                //flagMap.put(f, new ArrayList<String>());
                found = true;
            }
        }
        for (Map.Entry<String, ArrayList<String>> entry : flagMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

    }


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

    public abstract void executeCommands();


}
