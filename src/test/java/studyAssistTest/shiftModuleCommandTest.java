//@@ author mononokehime14
package studyAssistTest;

import gazeeebo.UI.Ui;
import gazeeebo.commands.studyassist.StudyPlannerCommand;
import gazeeebo.commands.studyassist.shiftModuleCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class shiftModuleCommandTest {
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
    void shiftModule_emptyException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift";
        try {
            new shiftModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException e){
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void shiftModule_wrongModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift CD1234 to 5";
        try {
            new shiftModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException e){
            assertEquals("We currently do not support this module.",e.getMessage());
        }
    }
    @Test
    void shiftModule_wrongSemesterException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift CS2040C to 9";
        try {
            new shiftModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("Please input correct Semester number.",e.getMessage());
        }
    }
    @Test
    void shiftModule_wrongFormatException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift CS2040C to";
        try {
            new shiftModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }
    @Test
    void shiftModule_existedModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift CS2040C to 2";
        try {
            new shiftModuleCommand().execute(StudyPlan,storage,ui);
//            fail();
        } catch (DukeException | IOException e){
            assertEquals("This module is already inside Sem "+(Integer.parseInt(ui.fullCommand.split(" ")[3]))+".",e.getMessage());
        }
    }
    @Test
    void shiftModuleTest() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyPlannerCommand StudyPlan = new StudyPlannerCommand(storage);
        ui.fullCommand = "shift CS2040C sem 2";
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
                new shiftModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module is not inside the study plan", e.getMessage());
            }
        }else if(Semester==semester_number) {
            try {
                new shiftModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module is already inside Sem "+(Semester+1)+".", e.getMessage());
            }
        }else {
            try {
                new shiftModuleCommand().execute(StudyPlan, storage, ui);
//            fail();
            } catch (DukeException | IOException e) {
                assertEquals("This module " + ModuleCode + " has been successfully shifted to Sem" + (Semester + 1) + ".", e.getMessage());
            }
        }
    }
}
