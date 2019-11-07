package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class checkPrerequisiteCommand {
    public void execute(Ui ui, Storage storage) throws IOException {
        HashMap<String, ArrayList<String>> PrerequisiteList = new HashMap<String,ArrayList<String>>(storage.readFromPrerequisiteFile());
        try {
            if(ui.fullCommand.split(" ").length!=2) throw new DukeException("Please follow the correct input format~");
            if(PrerequisiteList.get(ui.fullCommand.split(" ")[1])==null)throw new DukeException("We currently do not support this module");
            if (!PrerequisiteList.get(ui.fullCommand.split(" ")[1]).isEmpty()) {
                StringBuilder buffer = new StringBuilder();
                String Prefix = "";
                String ChildrenPrefix = "";
//            test(ui.fullCommand.split(" ")[1],Prefix,ChildrenPrefix,buffer,PrerequisiteList);
                dfsPrerequisite(ui.fullCommand.split(" ")[1], Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                System.out.println(buffer);
            } else {
                System.out.println("This module " + ui.fullCommand + " does not have any pre-requisite~");
            }
        }catch (DukeException e){
            System.out.println(e.getMessage());
        }
        return;
    }
    private void dfsPrerequisite(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
        buffer.append(Prefix);
        buffer.append(ModuleCode);
        buffer.append("\n");
        if(PrerequisiteList.get(ModuleCode) != null) {
            for (Iterator<String> it = PrerequisiteList.get(ModuleCode).iterator(); it.hasNext(); ) {
                String next = it.next();
                if (it.hasNext()) {
                    Prefix = ChildrenPrefix + "├── ";
                    ChildrenPrefix += ChildrenPrefix + "│   ";
                    dfsPrerequisite(next, Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                } else {
                    Prefix = ChildrenPrefix + "└── ";
                    ChildrenPrefix += "    ";
                    dfsPrerequisite(next, Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                }
            }
        }
        return;
    }
//    private void test(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
//        System.out.println("Can.");
//    }
}


