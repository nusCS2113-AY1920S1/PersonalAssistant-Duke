package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import java.io.IOException;
import java.util.stream.Collectors;

public class shiftModuleCommand {
    public void execute(StudyPlannerCommand StudyPlan, Storage storage, Ui ui) throws IOException {
        String ModuleCode = ui.fullCommand.split(" ")[1];
        int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3])-1;
        try {
            boolean flag = false;
            for(int i=0;i<StudyPlan.StudyPlan.size();i++){
                if (StudyPlan.StudyPlan.get(i).contains(ModuleCode)) {
                    flag = true;
                    StudyPlan.StudyPlan.get(i).remove(StudyPlan.StudyPlan.get(i).indexOf(ModuleCode));
                }
            }
            if(!flag) throw new DukeException("This module is not inside the study plan");
        } catch (DukeException e){
            System.out.println(e);
        }
        StudyPlan.StudyPlan.get(Semester).add(ModuleCode);
        String toStore = "";
        for(int i=0;i<StudyPlan.StudyPlan.size();i++){
            toStore += StudyPlan.StudyPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
            toStore += "\n";
        }
        storage.Storage_StudyPlan(toStore);
        System.out.println("This module "+ModuleCode+" has been successfully shifted to Sem"+(Semester+1)+".");
    }
}

