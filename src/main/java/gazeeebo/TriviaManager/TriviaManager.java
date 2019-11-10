package gazeeebo.TriviaManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

public class TriviaManager {
    public static Map<String, ArrayList<String>> CommandMemory;
//    static {
//        CommandMemory = new HashMap<>();
//    }
    public TriviaManager(Storage storage) throws IOException {
        this.CommandMemory = storage.Read_Trivia();
    }

    public void learnInput(String InputCommand,Storage storage) throws IOException {
        if(CommandMemory.containsKey(InputCommand.split(" ")[0])) {
            ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(InputCommand.split(" ")[0]));
            if(!oldlist.contains(InputCommand)){
                oldlist.add(InputCommand);
                CommandMemory.put(InputCommand.split(" ")[0], oldlist);
                storage.Storage_Trivia(InputCommand);
            }
        }else{
            ArrayList<String> newlist = new ArrayList<String>();
            newlist.add(InputCommand);
            CommandMemory.put(InputCommand.split(" ")[0],newlist);
            storage.Storage_Trivia(InputCommand);
        }
        return;
    }

    public void showPossibleInputs(String key) throws DukeException{
        try {
            if(CommandMemory.get(key) == null) throw new DukeException("Suggestions pool is empty = =");
            System.out.println("Could it be one of the below inputs?");
            System.out.println(CommandMemory.get(key));
        }catch (DukeException e){
            System.out.println(e.getMessage());
        }
    }
    public void showAllMap(){
        CommandMemory.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + "\n" +entry.getValue());
        });
    }
}
