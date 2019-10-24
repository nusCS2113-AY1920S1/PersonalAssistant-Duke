package gazeeebo.commands.specialization;

import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class CompletedCommand {
    public CompletedCommand (Ui ui, Storage storage, HashMap<String, ArrayList<moduleCategories>> specMap) throws IOException {
        System.out.println("Which module have you completed?");
        ui.readCommand();
        //User types in the module code e.g. CS1231
        String moduleCode = ui.fullCommand;
        for(String key : specMap.keySet()) {
            System.out.println("in here");
            for(int i = 0; i < specMap.get(key).size(); i++) {
                if(specMap.get(key).get(i).code.equals(moduleCode)) {
                    specMap.get(key).get(i).isDone = true;
                    System.out.println("in here 1");
                }
            }
        }
        System.out.println(moduleCode + " has been completed!");

    }
}
