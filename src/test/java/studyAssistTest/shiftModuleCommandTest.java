//@@author mononokehime14

package studyAssistTest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.studyassist.StudyPlannerCommand;
import gazeeebo.commands.studyassist.ShiftModuleCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.StudyAssistPageStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class shiftModuleCommandTest {
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
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift";
        try {
            new ShiftModuleCommand().execute(studyPlan,studyAssistPageStorage,ui);
        } catch (DukeException e) {
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void shiftModule_wrongModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift CD1234 to 5";
        try {
            new ShiftModuleCommand().execute(studyPlan,studyAssistPageStorage,ui);
        } catch (DukeException e) {
            assertEquals("We currently do not support this module.",e.getMessage());
        }
    }

    @Test
    void shiftModule_wrongSemesterException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift CS2040C to 9";
        try {
            new ShiftModuleCommand().execute(studyPlan,studyAssistPageStorage,ui);
        } catch (DukeException | IOException e) {
            assertEquals("Please input correct Semester number.",e.getMessage());
        }
    }

    @Test
    void shiftModule_wrongFormatException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift CS2040C to";
        try {
            new ShiftModuleCommand().execute(studyPlan,studyAssistPageStorage,ui);
        } catch (DukeException | IOException e) {
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void shiftModule_existedModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift CS2040C to 2";
        try {
            new ShiftModuleCommand().execute(studyPlan,studyAssistPageStorage,ui);
        } catch (DukeException | IOException e) {
            assertEquals("This module is already inside Sem "
                    + (Integer.parseInt(ui.fullCommand.split(" ")[3])) + ".",e.getMessage());
        }
    }

    @Test
    void shiftModuleTest() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        ui.fullCommand = "shift CS2040C sem 2";
        String moduleCode = "CS2040C";
        int Semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
        boolean flag = false;
        int semesterNumber = -1;
        for (int i = 0;i < studyPlan.StudyPlan.size() && !flag; i++) {
            if (studyPlan.StudyPlan.get(i).contains(moduleCode)) {
                flag = true;
                semesterNumber = i;
            }
        }
        if (!flag) {
            try {
                new ShiftModuleCommand().execute(studyPlan, studyAssistPageStorage, ui);
            } catch (DukeException | IOException e) {
                assertEquals("This module is not inside the study plan", e.getMessage());
            }
        } else if (Semester == semesterNumber) {
            try {
                new ShiftModuleCommand().execute(studyPlan, studyAssistPageStorage, ui);
            } catch (DukeException | IOException e) {
                assertEquals("This module is already inside Sem " + (Semester+1) + ".", e.getMessage());
            }
        } else {
            try {
                new ShiftModuleCommand().execute(studyPlan, studyAssistPageStorage, ui);
            } catch (DukeException | IOException e) {
                assertEquals("This module " + moduleCode + " has been successfully shifted to Sem"
                        + (Semester + 1) + ".", e.getMessage());
            }
        }
    }
}
