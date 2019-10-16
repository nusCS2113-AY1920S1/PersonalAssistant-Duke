package gazeeebo.TriviaManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;
public class TriviaManager {
    public static Map<String, ArrayList<String>> CommandMemory;
//    static {
//        CommandMemory = new HashMap<>();
//    }
    public TriviaManager(){
        this.CommandMemory = new HashMap<>();
    }

    public void learnInput(String InputCommand,Storage storage) throws IOException {
        if(CommandMemory.containsKey(InputCommand.split(" ")[0])) {
            ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(InputCommand.split(" ")[0]));
            oldlist.add(InputCommand);
            CommandMemory.put(InputCommand.split(" ")[0], oldlist);
        }else{
            ArrayList<String> newlist = new ArrayList<String>();
            newlist.add(InputCommand);
            CommandMemory.put(InputCommand.split(" ")[0],newlist);
        }
        storage.Storage_Trivia(InputCommand);
        return;
    }

    public void showPossibleInputs(String key){
        System.out.println("Could it be one of the below inputs?");
        System.out.println(CommandMemory.get(key));
    }
    public void showAllMap(){
        CommandMemory.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + "\n" +entry.getValue());
        });
    }
}
