//@@ author mononokehime14
package studyAssistTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.studyassist.StudyPlannerCommand;
import gazeeebo.commands.studyassist.deleteModuleCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class deleteModuleCommandTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }
    @Test
    void deleteModule_emptyException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete";
        try {
            new deleteModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException e){
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void deleteModule_wrongModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete CD1234 from 5";
        try {
            new deleteModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException e){
            assertEquals("We currently do not have this module.",e.getMessage());
        }
    }
    @Test
    void addModule_wrongSemesterException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete CS2040C from 9";
        try {
            new deleteModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("Please input correct Semester number.",e.getMessage());
        }
    }
    @Test
    void addModule_wrongFormatException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete CS2040C from";
        try {
            new deleteModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }
    @Test
    void addModule_not_existModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete CS3230 sem 5";
        try {
            new deleteModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("This module is not inside the study plan",e.getMessage());
        }
    }
    @Test
    void deleteModuleTest() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "delete CS2040C from 5";
        String ModuleCode = "CS2040C";
        int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
        boolean flag = false;
        int semester_number = -1;
        for(int i=0;i<StudyPlan.StudyPlan.size()&& !flag;i++){
            if(StudyPlan.StudyPlan.get(i).contains(ModuleCode)) {
                flag = true;
                semester_number = i;
            }
        }
        if(!flag){
            try {
                new deleteModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module is not inside the study plan", e.getMessage());
            }
        }else if(flag && semester_number != Semester){
            try {
                new deleteModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module is not in Sem "+(Semester+1)+" but inside Sem "+(semester_number+1), e.getMessage());
            }

        }else {
            try {
                new deleteModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module " + ModuleCode + " has been successfully deleted from Sem" + 5+".", e.getMessage());
            }
        }
    }
}
