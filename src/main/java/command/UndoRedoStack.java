package command;

import common.AlphaNUSException;
import project.Project;
import project.ProjectManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Stack;

public class UndoRedoStack {
    private static ArrayList<Integer> undoRedoPointer = new ArrayList<>();
    private static Stack<ProjectManager> commandStack = new Stack<>();
    //private static ProjectManager projectManager;

    public static void commandExceute(ProjectManager projectManager) {
        commandStack.push(projectManager);
        //deleteElementsAfterPointer(undoRedoPointer);
        int i = commandStack.size();
        undoRedoPointer.add(i - 1);
    }
    /*public void addProjectCommand() throws AlphaNUSException {
        Command command =
                new InsertCharacterCommand();
        command.execute();
        commandStack.push(command);
        deleteElementsAfterPointer(undoRedoPointer);
        undoRedoPointer++;
    }
    */
//    public static void deleteElementsAfterPointer(int undoRedoPointer)
//    {
//        if(commandStack.size()<1)return;
//        for(int i = commandStack.size()-1; i > undoRedoPointer; i--)
//        {
//            commandStack.remove(i);
//        }
//    }

    public static LinkedHashMap<String, Project> undo()
    {
        int i = undoRedoPointer.size();
        int x = undoRedoPointer.get(i-2);
        ProjectManager projectManager = commandStack.get(x);
        undoRedoPointer.remove(i - 1);
        //undoRedoPointer--;
        //unexecute
        return projectManager.projectmap;
    }

    public static LinkedHashMap<String, Project> redo()
    {
        int i = undoRedoPointer.size();
        int x = undoRedoPointer.get(i);
        ProjectManager projectManager = commandStack.get(x);
        undoRedoPointer.add(i);
        //undoRedoPointer = undoRedoPointer + 2;
        return projectManager.projectmap;
        //execute
    }
}
