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
//    private String moduleName;
    public int moduleCredit;
    public double score;

    public GPACommand(int moduleCredit, double score) {
//        this.moduleName = moduleName;
        this.moduleCredit = moduleCredit;
        this.score = score;
    }
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your GPA Calculator page! What would you like to do?\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Add module: add");
        System.out.println("2. Find module GPA base on module_code: find module");
        System.out.println("3. Delete a module: delete module");
        System.out.println("4. See your GPA list: list");
        System.out.println("5. Exit GPA page: esc");
        System.out.println("__________________________________________________________");
        HashMap<String, GPACommand> map = storage.gpa(); //Read the file
        Map<String, GPACommand> GPAList = new TreeMap<>(map);
        String LINE_BREAK = "------------------------------------------\n";
        ui.readCommand();
        while(!ui.fullCommand.equals("esc")) {
            double cap = new CalculateGPACommand().GPACalculator(GPAList);
            if(ui.fullCommand.equals("add")) {
                new AddGPACommand(ui,GPAList);
            } else if(ui.fullCommand.equals("list")) {
                new ListGPACommand(GPAList,LINE_BREAK, cap);
            } else if(ui.fullCommand.split(" ")[0].equals("find")) {
                new FindGPACommand(ui, GPAList, LINE_BREAK);
            } else if(ui.fullCommand.split(" ")[0].equals("delete")) {
                new DeleteGPACommand(ui, GPAList);
            }
            String toStore = "";
            for (String key : GPAList.keySet()) {

                toStore = toStore.concat(key + "|" + GPAList.get(key).moduleCredit + "|" + GPAList.get(key).score + "\n");
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
