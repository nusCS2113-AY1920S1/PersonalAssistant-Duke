//@@author mononokehime14
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

    /**
     * This method initialize a trivial manager object
     * @param storage
     * @throws IOException if the storage methods go wrong
     */
    public TriviaManager(Storage storage) throws IOException {
        this.CommandMemory = storage.Read_Trivia();
    }

    /**
     * This methods records and stores users' inputs.
     * @param InputCommand String of input
     * @param storage The object that deals with access,modify and save files.
     * @throws IOException if storage process fail.
     */
    public void learnInput(String InputCommand,Storage storage) throws IOException {
        if (CommandMemory.containsKey(InputCommand.split(" ")[0])) {
            ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(InputCommand.split(" ")[0]));
            if (!oldlist.contains(InputCommand)) {
                oldlist.add(InputCommand);
                CommandMemory.put(InputCommand.split(" ")[0], oldlist);
                storage.Storage_Trivia(InputCommand);
            }
        } else {
            ArrayList<String> newlist = new ArrayList<String>();
            newlist.add(InputCommand);
            CommandMemory.put(InputCommand.split(" ")[0],newlist);
            storage.Storage_Trivia(InputCommand);
        }
        return;
    }

    /**
     * This method shows suggestions if user input is empty.
     * @param key the type of inputs
     * @throws DukeException if now such input.
     */
    public void showPossibleInputs(String key) throws DukeException{
        try {
            if (CommandMemory.get(key) == null) throw new DukeException("Suggestions pool is empty = =");
            System.out.println("Could it be one of the below inputs?");
            System.out.println(CommandMemory.get(key));
        } catch (DukeException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test function that prints out the map.
     */
    public void showAllMap(){
        CommandMemory.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + "\n" +entry.getValue());
        });
    }
}
