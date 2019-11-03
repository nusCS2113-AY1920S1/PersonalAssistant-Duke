package gazeeebo.commands.Contact;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class UndoContactCommand {
    /**
     * Undo previous commands
     * @param contacts
     * @param oldcontacts
     * @param storage
     * @return
     * @throws IOException
     */
    public static Map<String,String> Undo(Map<String, String> contacts, Stack<Map<String, String>> oldcontacts, Storage storage) throws IOException {
        if(!oldcontacts.empty()){
            contacts=oldcontacts.peek();
            String toStore="";
            for(String key:contacts.keySet()){
                toStore=toStore.concat(key+"|"+contacts.get(key)+"\n");
            }
            storage.storagesPlaces(toStore);
            oldcontacts.pop();
            System.out.println("You have undo the previous command.");
        } else{
            System.out.println("The previous command cannot be undo");
        }
        return contacts;
    }
}
