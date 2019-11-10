package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.stream.Collectors;

public class AddModuleCommand {
    /** This method allows user to add module code into study plan, store changes in data structure and external txt files.
     *
     * @param studyPlan The object contain all needed modules data structure and a showplan method to display plan table
     * @param storage The object that deals with access,modify and save files
     * @param ui The object that deals with interaction between users and the system.
     * @throws IOException if the user input is in wrong format.
     */
    public void execute(StudyPlannerCommand studyPlan, Storage storage, Ui ui) throws IOException,DukeException {
        try {
            if (ui.fullCommand.split(" ").length != 4){
                throw new DukeException("Please follow the correct input format~");
            }
            String moduleCode = ui.fullCommand.split(" ")[1];
            if (moduleCode.isEmpty()) {
                throw new DukeException("Module code could not be null");
            }
            if (studyPlan.MCMap.get(moduleCode) == null) {
                throw new DukeException("We currently do not support this module.");
            }
            int semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
            if (semester >= 8 || semester < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            boolean flag = false;
            for (int i = 0;i < studyPlan.StudyPlan.size() && !flag;i++) {
                if (studyPlan.StudyPlan.get(i).contains(moduleCode)) flag = true;
            }
            if (flag) {
                throw new DukeException("This module is already inside the study plan");
            }
            studyPlan.StudyPlan.get(semester).add(moduleCode);
            String toStore = "";
            for (int i = 0; i < studyPlan.StudyPlan.size(); i++) {
                toStore += studyPlan.StudyPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
                toStore += "\n";
            }
            storage.Storage_StudyPlan(toStore);
            System.out.println("This module " + moduleCode + " has been successfully added to Sem" + (semester + 1) + ".");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Please input correct Semester number");
        }
    }
}
