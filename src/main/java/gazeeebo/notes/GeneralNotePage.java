package gazeeebo.notes;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class GeneralNotePage {
    public static String goal = "";
    public static ArrayList<Module> modules = new ArrayList<>();

    public void editGoal(Ui ui) throws IOException {
        System.out.println("What is your new goal?");
        ui.readCommand();
        goal = ui.fullCommand;
        System.out.println("Okay we have successfully updated your goal to:");
        System.out.println(goal);
    }

    public void addModule(Ui ui) throws IOException {
        System.out.println("What module do you want to add?");
        ui.readCommand();
        modules.add(new Module(ui.fullCommand));
        System.out.println("Okay we have successfully added this module:");
        System.out.println(ui.fullCommand);
    }
    public void deleteModule(Ui ui) throws IOException {
        System.out.println("Which module do you want to delete?");
        ui.readCommand(); //input module name here
        for (Module m : modules) {
            if (m.name.equals(ui.fullCommand)) {
                modules.remove(m);
                System.out.println("Okay we have successfully deleted this module:");
                System.out.println(ui.fullCommand);
                return;
            }
        }
        System.out.println("Sorry there is no such module.");
    }
}
