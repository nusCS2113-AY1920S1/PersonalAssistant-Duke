package gazeeebo.parser;

import gazeeebo.triviamanager.TriviaManager;
import gazeeebo.ui.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.studyassist.StudyPlannerCommand;
import gazeeebo.commands.studyassist.AddModuleCommand;
import gazeeebo.commands.studyassist.DeleteModuleCommand;
import gazeeebo.commands.studyassist.ShiftModuleCommand;
import gazeeebo.commands.studyassist.CheckPrerequisiteCommand;
import gazeeebo.commands.studyassist.UndoStudyPlannerCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.storage.StudyAssistPageStorage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class StudyAssistCommandParser extends Command {
    /** This method allows user to enter the module planner page,
     * Delete/shift/add/view their 4 year study plan, view prerequisite tree of modules
     * the method keeps repeating in while loop unless esc command is given.
     *
     * @param list          list of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       the object that deals with storing data
     * @param commandStack  the stack of previous commands.
     * @param deletedTask   the list of deleted task.
     * @param triviaManager the object for triviaManager
     * @throws IOException   Catch error if the read file fails
     * @throws DukeException throws a custom exception if
     *                       module index does not exist.
     */
    @Override
    public void execute(ArrayList<Task> list,
                        Ui ui,
                        Storage storage,
                        Stack<ArrayList<Task>> commandStack,
                        ArrayList<Task> deletedTask,
                        TriviaManager triviaManager) throws IOException, DukeException, ParseException {
        showCommands();
        StudyAssistPageStorage studyStorage = new StudyAssistPageStorage();
        StudyPlannerCommand studyPlan =  new StudyPlannerCommand(studyStorage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        while (!ui.fullCommand.equals("esc")) {
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(
                        null, null, null,
                        null, null,
                        null);
            } else if (splitCommand[0].equals("plan")) {
                studyPlan.showPlan();
            } else if (splitCommand[0].equals("add")) {
                copyStudyPlan(oldStudyPlan,studyPlan.StudyPlan);
                new AddModuleCommand().execute(studyPlan,studyStorage,ui,oldStudyPlan);
            } else if (splitCommand[0].equals("delete")) {
                copyStudyPlan(oldStudyPlan,studyPlan.StudyPlan);
                new DeleteModuleCommand().execute(studyPlan,studyStorage,ui,oldStudyPlan);
            } else if (splitCommand[0].equals("shift")) {
                copyStudyPlan(oldStudyPlan, studyPlan.StudyPlan);
                new ShiftModuleCommand().execute(studyPlan, studyStorage, ui);
            } else if (splitCommand[0].equals("prerequisite")) {
                new CheckPrerequisiteCommand().execute(ui, studyStorage);
            } else if (ui.fullCommand.equals("undo")) {
                studyPlan.StudyPlan = new UndoStudyPlannerCommand()
                        .undoStudyPlanner(oldStudyPlan, studyPlan.StudyPlan, studyStorage);
            } else if (ui.fullCommand.equals("commands")) {
                showCommands();
            } else if (!ui.fullCommand.equals("moduleplanner")) {
                System.out.println("Invalid input that i could not understand~");
            }
            ui.readCommand();
        }
        System.out.println("Going back to Main Menu...\n"
                + "Content Page:\n"
                + "------------------ \n"
                + "1. help\n"
                + "2. contacts\n"
                + "3. expenses\n"
                + "4. places\n"
                + "5. tasks\n"
                + "6. cap\n"
                + "7. spec\n"
                + "8. moduleplanner\n"
                + "9. notes\n"
                + "10. change password\n"
                + "To exit: bye\n");
    }

    /**
     * This methods saves a copy for undo function
     * @param oldStudyPlan data structure stores previous copy.
     * @param currentMods data structure stores copy after changes.
     */
    private void copyStudyPlan(Stack<ArrayList<ArrayList<String>>> oldStudyPlan,
                               ArrayList<ArrayList<String>> currentMods) {
        ArrayList<ArrayList<String>> currentPlan = new ArrayList<>();
        for (ArrayList<String> mods : currentMods) {
            ArrayList<String> arrayList = new ArrayList<>();
            for (String name : mods) {
                arrayList.add(name);
            }
            currentPlan.add(arrayList);
        }
        oldStudyPlan.push(currentPlan);
    }

    /**
     * This method checks input is esc or not, if yes quit.
     * @return boolean value of whether inout is esc or not.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * This method displays instructions of user inputs.
     */
    public void showCommands() {
        System.out.println("Welcome to Module Planner!");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add module to your plan: add CSXXXX to n(Semester number)");
        System.out.println("2. Delete module from your plan: Delete CSXXXX from n(Semester number)");
        System.out.println("3. Shift module to other semester: shift CSXXXX to n(Semester number)");
        System.out.println("4. See your Study Plan: plan");
        System.out.println("5. See your Prerequisite of a module: prerequisite CSXXXX(module code)");
        System.out.println("6. Undo Previous Command: undo");
        System.out.println("7. Exit Module Planner page: esc");
        System.out.println("__________________________________________________________");
    }
}
