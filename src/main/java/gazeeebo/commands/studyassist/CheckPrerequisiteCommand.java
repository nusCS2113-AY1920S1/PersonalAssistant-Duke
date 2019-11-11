package gazeeebo.commands.studyassist;

import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.StudyAssistPageStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CheckPrerequisiteCommand {
    /**
     * This method allows user to check module's prerequisite modules,
     * display them in a tree structure from left to right.
     * @param ui The object that deals with interaction between users and the system.
     * @param storage The object that deals with modify,access and save external files.
     * @throws IOException if the user input is in wrong format.
     */
    public void execute(Ui ui, StudyAssistPageStorage storage) throws IOException,DukeException {
        HashMap<String, ArrayList<String>> prerequisiteList = new HashMap<String,ArrayList<String>>(
                storage.readFromPrerequisiteFile());
        try {
            if (ui.fullCommand.split(" ").length != 2) {
                throw new DukeException("Please follow the correct input format~");
            }
            if (prerequisiteList.get(ui.fullCommand.split(" ")[1]) == null) {
                throw new DukeException("We currently do not support this module");
            }
            if (!prerequisiteList.get(ui.fullCommand.split(" ")[1]).isEmpty()) {
                StringBuilder buffer = new StringBuilder();
                String prefix = "";
                String childrenPrefix = "";
                dfsPrerequisite(ui.fullCommand.split(" ")[1],
                        prefix, childrenPrefix, buffer, prerequisiteList);
                System.out.println(buffer);
            } else {
                System.out.println("This module " + ui.fullCommand
                        + " does not have any pre-requisite~");
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
        return;
    }

    /**
     * This method allows a Depth-First-Search on the prerequisite data structure,
     * finding all prerequisite module and add their
     * name string to string builder for later printing purpose.
     * @param moduleCode the module that we are going to search for its prerequisite
     * @param prefix parameter for string builder
     * @param childrenPrefix parameter for string builder
     * @param buffer parameter for string builder
     * @param prerequisiteList Data structure that stores all prerequisite information.
     */
    private void dfsPrerequisite(String moduleCode,String prefix,
                                 String childrenPrefix, StringBuilder buffer, HashMap<String,
            ArrayList<String>> prerequisiteList) {
        buffer.append(prefix);
        buffer.append(moduleCode);
        buffer.append("\n");
        if (prerequisiteList.get(moduleCode) != null) {
            for (Iterator<String> it = prerequisiteList.get(moduleCode).iterator(); it.hasNext(); ) {
                String next = it.next();
                if (it.hasNext()) {
                    prefix = childrenPrefix + "├── ";
                    childrenPrefix += childrenPrefix + "│   ";
                    dfsPrerequisite(next,prefix, childrenPrefix, buffer, prerequisiteList);
                } else {
                    prefix = childrenPrefix + "└── ";
                    childrenPrefix += "    ";
                    dfsPrerequisite(next, prefix, childrenPrefix, buffer, prerequisiteList);
                }
            }
        }
        return;
    }
}


