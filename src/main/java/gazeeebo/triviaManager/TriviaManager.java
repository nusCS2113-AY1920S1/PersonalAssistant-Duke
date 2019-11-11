//@@author mononokehime14

package gazeeebo.triviaManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import gazeeebo.exception.DukeException;
import gazeeebo.storage.TriviaStorage;

public class TriviaManager {
    public static Map<String, ArrayList<String>> CommandMemory;

    /**
     * This method initialize a trivial manager object
     * @param storage The object deals with access, modify and save files.
     * @throws IOException if the storage methods go wrong
     */
    public TriviaManager(TriviaStorage storage) throws IOException {
        this.CommandMemory = storage.readFromTriviaFile();
    }

    /**
     * This methods records and stores users' inputs.
     * @param inputCommand String of input
     * @param storage The object that deals with access,modify and save files.
     * @throws IOException if storage process fail.
     */
    public void learnInput(String inputCommand,TriviaStorage storage) throws IOException {
        if (CommandMemory.containsKey(inputCommand.split(" ")[0])) {
            ArrayList<String> oldlist = new ArrayList<String>(CommandMemory.get(inputCommand.split(" ")[0]));
            if (!oldlist.contains(inputCommand)) {
                oldlist.add(inputCommand);
                CommandMemory.put(inputCommand.split(" ")[0], oldlist);
                storage.writeToTriviaFile(inputCommand);
            }
        } else {
            ArrayList<String> newlist = new ArrayList<String>();
            newlist.add(inputCommand);
            CommandMemory.put(inputCommand.split(" ")[0],newlist);
            storage.writeToTriviaFile(inputCommand);
        }
        return;
    }

    /**
     * This method shows suggestions if user input is empty.
     * @param key the type of inputs
     * @throws DukeException if now such input.
     */
    public void showPossibleInputs(String key) throws DukeException {
        try {
            if (CommandMemory.get(key) == null) {
                throw new DukeException("Suggestions pool is empty = =");
            }
            System.out.println("Could it be one of the below inputs?");
            System.out.println(CommandMemory.get(key));
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Test function that prints out the map.
     */
    public void showAllMap() {
        CommandMemory.entrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "\n" + entry.getValue());
        });
    }
}
