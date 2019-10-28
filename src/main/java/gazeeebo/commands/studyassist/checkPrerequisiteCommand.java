package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class checkPrerequisiteCommand {
    public void execute(Ui ui, Storage storage){
        HashMap<String, ArrayList<String>> PrerequisiteList = new HashMap<String,ArrayList<String>>(storage.readFromPrerequisiteFile());
        if(!PrerequisiteList.get(ui.fullCommand.split(" ")[1]).isEmpty()){
            StringBuilder buffer = new StringBuilder();
            String Prefix = "";
            String ChildrenPrefix = "";
//            test(ui.fullCommand.split(" ")[1],Prefix,ChildrenPrefix,buffer,PrerequisiteList);
            dfsPrerequisite(ui.fullCommand.split(" ")[1],Prefix,ChildrenPrefix,buffer,PrerequisiteList);
            System.out.println(buffer);
        }else{
            System.out.println("This module "+ui.fullCommand+" does not have any pre-requisite~");
        }
        return;
    }
    private void dfsPrerequisite(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
        buffer.append(Prefix);
        buffer.append(ModuleCode);
        buffer.append("\n");
        System.out.println(buffer);
        if(!PrerequisiteList.get(ModuleCode).isEmpty()) {
            for (int i = 0; i < PrerequisiteList.get(ModuleCode).size(); i++) {
                if (!PrerequisiteList.get(PrerequisiteList.get(ModuleCode).get(i)).isEmpty()) {
                    Prefix = ChildrenPrefix+ "├── ";
                    ChildrenPrefix += ChildrenPrefix+ "│   ";
                    dfsPrerequisite(PrerequisiteList.get(ModuleCode).get(i), Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                } else {
                    Prefix = ChildrenPrefix+ "└── ";
                    ChildrenPrefix += "    ";
                    dfsPrerequisite(PrerequisiteList.get(ModuleCode).get(i), Prefix, ChildrenPrefix, buffer, PrerequisiteList);
                }
            }
        }
    }
//    private void test(String ModuleCode,String Prefix, String ChildrenPrefix, StringBuilder buffer, HashMap<String, ArrayList<String>> PrerequisiteList){
//        System.out.println("Can.");
//    }
}


