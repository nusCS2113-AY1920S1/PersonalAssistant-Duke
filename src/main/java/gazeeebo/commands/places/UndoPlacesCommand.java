//@@author jessteoxizhi
package gazeeebo.commands.places;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

class UndoPlacesCommand {
    /**
     *  Undo places previous command
     *
     * @param places
     * @param oldplaces
     * @param storage
     * @return previous map before the command.
     * @throws IOException
     */
    static Map<String,String> Undo(Map<String, String> places, Stack<Map<String, String>> oldplaces, Storage storage) throws IOException {
        if(!oldplaces.empty()){
            places=oldplaces.peek();
            String toStore="";
            for(String key:places.keySet()){
            toStore=toStore.concat(key+"|"+places.get(key)+"\n");
            }
            storage.storagesPlaces(toStore);
            oldplaces.pop();
            System.out.println("You have undo the previous command.");
        } else{
            System.out.println("The previous command cannot be undo");
        }
        return places;
    }
}
