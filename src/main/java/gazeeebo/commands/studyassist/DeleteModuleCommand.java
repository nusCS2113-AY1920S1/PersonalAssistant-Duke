package gazeeebo.commands.studyassist;

import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.StudyAssistPageStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class DeleteModuleCommand {
    /**
     * This method allows users to delete certain module out of the module plan,
     * store changes in data structure and also external txt files.
     * @param studyPlan The object that holds data structure of module plan.
     * @param storage The object that deals with access, modify and save files.
     * @param ui The object that deals with interaction with users and the system.
     * @throws IOException if user input is in wrong format
     */
    public void execute(StudyPlannerCommand studyPlan,
                        StudyAssistPageStorage storage,
                        Ui ui,
                        Stack<ArrayList<ArrayList<String>>> oldStudyPlan) throws IOException,DukeException {
        try {
            if (ui.fullCommand.split(" ").length != 4) {
                throw new DukeException("Please follow the correct input format~");
            }
            String moduleCode = ui.fullCommand.split(" ")[1];
            if (moduleCode.isEmpty()) {
                throw new DukeException("Module code could not be null");
            }
            if (studyPlan.MCMap.get(moduleCode) == null) {
                throw new DukeException("We currently do not have this module.");
            }
            int semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
            if (semester >= 8 || semester < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            boolean flag = false;
            int semesterNumber = -1;
            for (int i = 0;i < studyPlan.StudyPlan.size() && !flag;i++) {
                if (studyPlan.StudyPlan.get(i).contains(moduleCode)) {
                    flag = true;
                    semesterNumber = i;
                }
            }
            if (!flag) {
                throw new DukeException("This module is not inside the study plan");
            }
            if (semesterNumber != semester) {
                throw new DukeException("This module is not in Sem "
                        + (semester + 1) + " but inside Sem " + (semesterNumber + 1));
            }
            studyPlan.StudyPlan
                    .get(semester)
                    .remove(studyPlan.StudyPlan.get(semester).indexOf(moduleCode));
            String toStore = "";
            for (int i = 0; i < studyPlan.StudyPlan.size(); i++) {
                toStore += studyPlan.StudyPlan
                        .get(i).stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.writeToStudyPlanFile(toStore);
            System.out.println("This module " + moduleCode
                    + " has been successfully deleted from Sem" + (semester + 1) + ".");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
            oldStudyPlan.pop();
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input correct Semester number");
            oldStudyPlan.pop();
        }
    }
}
