package gazeeebo.commands.gpacalculator;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class GPACommand extends Command {
    public String moduleCode;
    public int moduleCredit;
    public String grade;

    public GPACommand(String moduleCode, int moduleCredit, String grade) {
        this.moduleCode = moduleCode;
        this.moduleCredit = moduleCredit;
        this.grade = grade;
    }

    /* Main Command to execute all the GPA commands */
    @Override
    public void execute(final ArrayList<Task> list, final  Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String helpGPA = "__________________________________________________________\n"
                + "1. Add module: add\n"
                + "2. Find module: find moduleCode/semNumber\n"
                + "3. Delete a module: delete module\n"
                + "4. See your GPA list: list\n"
                + "5. Exit GPA page: esc\n"
                + "__________________________________________________________\n";
        System.out.println("Welcome to your GPA Calculator page! What would you like to do?\n");
        System.out.println(helpGPA);
        HashMap<String, ArrayList<GPACommand>> map = storage.gpa(); //Read the file
        Map<String, ArrayList<GPACommand>> gpalist = new TreeMap<>(map);
        String lineBreak = "------------------------------\n";
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            double cap = new CalculateGPACommand().GPACalculator(gpalist);
            if (ui.fullCommand.equals("add")) {
                new AddGPACommand(ui, gpalist);
            } else if (ui.fullCommand.equals("list")) {
                new ListGPACommand(ui, gpalist, lineBreak, cap);
            } else if (ui.fullCommand.split(" ")[0].equals("find")) {
                new FindGPACommand(ui, gpalist, lineBreak);
            } else if (ui.fullCommand.split(" ")[0].equals("delete")) {
                new DeleteGPACommand(ui, gpalist);
            } else {
                System.out.println("Command not Found:\n" + helpGPA);
            }
            String toStore = "";
            for (String key : gpalist.keySet()) {
                for (int i = 0; i < gpalist.get(key).size(); i++) {
                    toStore = toStore.concat(key + "|" + gpalist.get(key).get(i).moduleCode
                            + "|" + gpalist.get(key).get(i).moduleCredit
                            + "|" + gpalist.get(key).get(i).grade + "\n");

                }
            }
            storage.Storages_gpa(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.println("Going back to Main Menu");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
