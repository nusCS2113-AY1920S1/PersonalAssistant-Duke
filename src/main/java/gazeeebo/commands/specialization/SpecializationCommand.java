package gazeeebo.commands.specialization;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class SpecializationCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        HashMap<String, ArrayList<ModuleCategories>> specMap = storage.Specialization(); //Read the file
//        Map<String, ArrayList<moduleCategories>> expenses = new TreeMap<LocalDate, ArrayList<String>>(map);
   //     ArrayList<moduleCategories> expenseList = new ArrayList<>();

        System.out.print("Welcome to your specialization page! What would you like to do?\n\n");
        System.out.println("__________________________________________________________");
        System.out.println("1. Enter specialization: enter");
        System.out.println("2. Key in completed electives: complete");
        System.out.println("3. Exit contact page: esc");
        System.out.println("__________________________________________________________");


        ui.readCommand();
        while(!ui.fullCommand.equals("esc")) {
            if (ui.fullCommand.equals("enter")) {
                new EnterSpecialization(ui, storage, specMap);
            }
            else if (ui.fullCommand.equals("complete")) {
                new CompletedCommand(ui, storage, specMap);
            }
            ui.readCommand();
        }
        System.out.println("Go back to Main Menu");

    }
    @Override
    public boolean isExit() {
        return false;
    }
}
