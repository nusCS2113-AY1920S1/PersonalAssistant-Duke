package gazeeebo.parsers;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.commands.studyassist.*;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class studyassistCommandParser extends Command {
    /** This method allows user to enter the module planner page,
     * Delete/shift/add/view their 4 year study plan, view prerequisite tree of modules
     * the method keeps repeating in while loop unless esc command is given.
     *
     * @param list          list of all tasks
     * @param ui            the object that deals with
     *                      printing things to the user
     * @param storage       the object that deals with storing data
     * @param commandStack
     * @param deletedTask
     * @param triviaManager
     * @throws IOException   Catch error if the read file fails
     * @throws DukeException throws a custom exception if
     *                       module index does not exist.
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException, ParseException {
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
        StudyPlannerCommand StudyPlan =  new StudyPlannerCommand(storage);
        Stack<ArrayList<ArrayList<String>>> oldStudyPlan = new Stack<>();
        while(!ui.fullCommand.equals("esc")) {
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("plan")) {
                StudyPlan.showPlan();
            } else if (splitCommand[0].equals("add")) {
                copyStudyPlan(oldStudyPlan,StudyPlan.StudyPlan);
                new addModuleCommand().execute(StudyPlan,storage,ui);
            } else if (splitCommand[0].equals("delete")) {
                new deleteModuleCommand().execute(StudyPlan,storage,ui);
            } else if (splitCommand[0].equals("shift")) {
                new shiftModuleCommand().execute(StudyPlan,storage,ui);
            } else if (splitCommand[0].equals("prerequisite")) {
                new checkPrerequisiteCommand().execute(ui, storage);
            } else if (ui.fullCommand.equals("undo")) {
               StudyPlan.StudyPlan = new UndoStudyPlannerCommand().undoStudyPlanner(oldStudyPlan,StudyPlan.StudyPlan, storage);
            } else if(!ui.fullCommand.equals("moduleplanner")){
                System.out.println("Invalid input that i could not understand~");
            }
            ui.readCommand();
        }
        System.out.println("Going back to Main Menu...\n" +
                "Content Page:\n" +
                "------------------ \n" +
                "1. help\n" +
                "2. contacts\n" +
                "3. expenses\n" +
                "4. places\n" +
                "5. tasks\n" +
                "6. cap\n" +
                "7. spec\n" +
                "8. moduleplanner\n" +
                "9. notes\n" +
                "To exit: bye\n");
    }

    private void copyStudyPlan(Stack<ArrayList<ArrayList<String>>> oldStudyPlan, ArrayList<ArrayList<String>> currentMods) {
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
    @Override
    public boolean isExit() {
        return false;
    }
}
