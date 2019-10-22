package gazeeebo.commands.studyassist;

import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;
import java.io.IOException;
import java.util.stream.Collectors;

public class deleteModuleCommand {
    public void execute(StudyPlannerCommand StudyPlan, Storage storage, Ui ui) throws IOException {
        String ModuleCode = ui.fullCommand.split(" ")[1];
        int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3])-1;
        StudyPlan.StudyPlan.get(Semester).remove(StudyPlan.StudyPlan.get(Semester).indexOf(ModuleCode));
        String toStore = "";
        for(int i=0;i<StudyPlan.StudyPlan.size();i++){
            toStore += StudyPlan.StudyPlan.get(i).stream().map(Object::toString).collect(Collectors.joining(" "));
            toStore += "\n";
        }
        storage.Storage_StudyPlan(toStore);
        System.out.println("This module "+ModuleCode+" has been successfully deleted from Sem"+(Semester+1)+".");
    }
}

