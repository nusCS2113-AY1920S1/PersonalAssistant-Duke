//@@author jessteoxizhi

package gazeeebo.commands.studyassist;

import gazeeebo.storage.Storage;
import gazeeebo.storage.StudyAssistPageStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class UndoStudyPlannerCommand {
    /**
     * This method undo previous comment input.
     * @param oldStudyPlan Data structure that keeps a backup
     * @param currentPlan Data structure that have been modified
     * @param storage The object deals with access,modify and save external
     *                txt files.
     * @return Data structure that the system now holds
     * @throws IOException Exception when there is an error reading or writing the file
     */
    public ArrayList<ArrayList<String>> undoStudyPlanner(Stack<ArrayList<ArrayList<String>>> oldStudyPlan,
                                                         ArrayList<ArrayList<String>> currentPlan,
                                                         StudyAssistPageStorage storage) throws IOException {
        if (!oldStudyPlan.empty()) {
            currentPlan = oldStudyPlan.peek();
            String toStore = "";
            for (int i = 0; i < currentPlan.size(); i++) {
                toStore += currentPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.writeToStudyPlanFile(toStore);
            System.out.println("You have undo the previous command.");
        } else {
            System.out.println("The previous command cannot be undo.");
        }
        return currentPlan;
    }
}
