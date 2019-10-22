package gazeeebo.commands.studyassist;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.commands.help.HelpCommand;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class studyassistCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException, DukeException, ParseException {
        System.out.println("Welcome to Module Planner!");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add module to your plan: add CSXXXX to n(Semester number)");
        System.out.println("2. Delete module from your plan: Delete CSXXXX from n(Semester number)");
        System.out.println("3. Shift module to other semester: shift CSXXXX to n(Semester number)");
        System.out.println("4. See your Study Plan: plan");
        System.out.println("5. Exit Module Planner page: esc");
        System.out.println("__________________________________________________________");
        StudyPlannerCommand StudyPlan =  new StudyPlannerCommand(storage);
        while(!ui.fullCommand.equals("esc")) {
            String command = ui.fullCommand;
            String[] splitCommand = command.split(" ");
            if (splitCommand[0].equals("help")) {
                new HelpCommand().execute(list,ui,storage,commandStack,deletedTask,triviaManager);
            } else if (splitCommand[0].equals("plan")) {
                StudyPlan.showPlan();
            } else if (splitCommand[0].equals("add")) {
                new addModuleCommand().execute(StudyPlan,storage,ui);
            } else if (splitCommand[0].equals("delete")) {
                new deleteModuleCommand().execute(StudyPlan,storage,ui);
            } else if (splitCommand[0].equals("shift")) {
                new shiftModuleCommand().execute(StudyPlan,storage,ui);
            }
            ui.readCommand();
        }
        System.out.println("Back to the main page!");
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
