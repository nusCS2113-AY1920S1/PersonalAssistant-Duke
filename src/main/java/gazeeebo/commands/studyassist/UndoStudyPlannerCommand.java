package gazeeebo.commands.studyassist;

import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class UndoStudyPlannerCommand {
    public ArrayList<ArrayList<String>> undoStudyPlanner(Stack<ArrayList<ArrayList<String>>> oldStudyPlan, ArrayList<ArrayList<String>> currentPlan, Storage storage) throws IOException {
        if (!oldStudyPlan.empty()) {
            currentPlan = oldStudyPlan.peek();
            String toStore = "";
            for (int i = 0; i < currentPlan.size(); i++) {
                toStore += currentPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.Storage_StudyPlan(toStore);
            System.out.println("I've undo your previous command");
        } else {
            System.out.println("The previous command cannot be undo");
        }
        return currentPlan;
    }
}
