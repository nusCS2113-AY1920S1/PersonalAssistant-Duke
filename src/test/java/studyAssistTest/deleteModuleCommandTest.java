//@@author mononokehime14
package studyAssistTest;

import gazeeebo.ui.Ui;
import gazeeebo.commands.studyassist.StudyPlannerCommand;
import gazeeebo.commands.studyassist.DeleteModuleCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.StudyAssistPageStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

class deleteModuleCommandTest {
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
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete";
        try {
            new DeleteModuleCommand().execute(studyPlan,studyAssistPageStorage,ui,oldStudyPlan);
        } catch (DukeException e) {
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void deleteModule_wrongModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete CD1234 from 5";
        try {
            new DeleteModuleCommand().execute(studyPlan,studyAssistPageStorage,ui,oldStudyPlan);
        } catch (DukeException e) {
            assertEquals("We currently do not have this module.",e.getMessage());
        }
    }

    @Test
    void addModule_wrongSemesterException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete CS2040C from 9";
        try {
            new DeleteModuleCommand().execute(studyPlan,studyAssistPageStorage,ui,oldStudyPlan);
        } catch (DukeException | IOException e) {
            assertEquals("Please input correct Semester number.",e.getMessage());
        }
    }

    @Test
    void addModule_wrongFormatException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete CS2040C from";
        try {
            new DeleteModuleCommand().execute(studyPlan,studyAssistPageStorage,ui,oldStudyPlan);
        } catch (DukeException | IOException e) {
            assertEquals("Please follow the correct input format~",e.getMessage());
        }
    }

    @Test
    void addModule_not_existModuleException() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete CS3230 sem 5";
        try {
            new DeleteModuleCommand().execute(studyPlan,studyAssistPageStorage,ui,oldStudyPlan);
        } catch (DukeException | IOException e) {
            assertEquals("This module is not inside the study plan",e.getMessage());
        }
    }

    @Test
    void deleteModuleTest() throws IOException {
        Storage storage = new Storage();
        Ui ui = new Ui();
        StudyAssistPageStorage studyAssistPageStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan = new StudyPlannerCommand(studyAssistPageStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        oldStudyPlan.push(studyPlan.StudyPlan);
        ui.fullCommand = "delete CS2040C from 5";
        String moduleCode = "CS2040C";
        int semester = Integer.parseInt(ui.fullCommand.split(" ")[3]) - 1;
        boolean flag = false;
        int semester_number = -1;
        for (int i = 0;i < studyPlan.StudyPlan.size() && !flag;i++) {
            if (studyPlan.StudyPlan.get(i).contains(moduleCode)) {
                flag = true;
                semester_number = i;
            }
        }
        if (!flag) {
            try {
                new DeleteModuleCommand().execute(studyPlan, studyAssistPageStorage, ui,oldStudyPlan);
            } catch (DukeException | IOException e) {
                assertEquals("This module is not inside the study plan", e.getMessage());
            }
        } else if (flag && semester_number != semester) {
            try {
                new DeleteModuleCommand().execute(studyPlan, studyAssistPageStorage, ui, oldStudyPlan);
            } catch (DukeException | IOException e) {
                assertEquals("This module is not in Sem " + (semester + 1) + " but inside Sem "
                        + (semester_number + 1), e.getMessage());
            }

        } else {
            try {
                new DeleteModuleCommand().execute(studyPlan, studyAssistPageStorage, ui,oldStudyPlan);
            } catch (DukeException | IOException e) {
                assertEquals("This module " + moduleCode + " has been successfully deleted from Sem" + 5
                        + ".", e.getMessage());
            }
        }
    }
}