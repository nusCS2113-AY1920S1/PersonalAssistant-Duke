package gazeeebo.commands.capCalculator;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class CAPCommand extends Command {
    public String moduleCode;
    public int moduleCredit;
    public String grade;

    public CAPCommand(String moduleCode, int moduleCredit, String grade) {
        this.moduleCode = moduleCode;
        this.moduleCredit = moduleCredit;
        this.grade = grade;
    }

    /* Main Command to execute all the CAP commands */
    @Override
    public void execute(final ArrayList<Task> list, final  Ui ui, final Storage storage, final Stack<String> commandStack, final ArrayList<Task> deletedTask, final TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        String helpCAP = "__________________________________________________________\n"
                + "1. Add module: add\n"
                + "2. Find module: find moduleCode/semNumber\n"
                + "3. Delete a module: delete module\n"
                + "4. See your CAP list: list\n"
                + "5. Exit CAP page: esc\n"
                + "__________________________________________________________\n\n";
        System.out.print("Welcome to your CAP Calculator page! What would you like to do?\n\n");
        System.out.print(helpCAP);
        HashMap<String, ArrayList<CAPCommand>> map = storage.readFromCAPFile(); //Read the file
        Map<String, ArrayList<CAPCommand>> CAPList = new TreeMap<>(map);
        String lineBreak = "------------------------------\n";
        ui.readCommand();
        while (!ui.fullCommand.equals("esc")) {
            double cap = new CalculateCAPCommand().CAPCalculator(CAPList);
            if (ui.fullCommand.equals("add")) {
                new AddCAPCommand(ui, CAPList);
            } else if (ui.fullCommand.equals("list")) {
                new ListCAPCommand(ui, CAPList, lineBreak);
            } else if (ui.fullCommand.split(" ")[0].equals("find") && !ui.fullCommand.equals("find")) {
                new FindCAPCommand(ui, CAPList, lineBreak);
            } else if (ui.fullCommand.split(" ")[0].equals("delete") && !ui.fullCommand.equals("delete")) {
                new DeleteCAPCommand(ui, CAPList);
            } else if(ui.fullCommand.equals("help")) {
                System.out.println(helpCAP);
            } else {
                System.out.println("Command not Found:\n" + helpCAP);
            }
            String toStore = "";
            for (String key : CAPList.keySet()) {
                for (int i = 0; i < CAPList.get(key).size(); i++) {
                    toStore = toStore.concat(key + "|" + CAPList.get(key).get(i).moduleCode
                            + "|" + CAPList.get(key).get(i).moduleCredit
                            + "|" + CAPList.get(key).get(i).grade + "\n");

                }
            }
            storage.writeToCAPFile(toStore);
            System.out.println("What do you want to do next ?");
            ui.readCommand();
        }
        System.out.print("Going back to Main Menu\n");
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
