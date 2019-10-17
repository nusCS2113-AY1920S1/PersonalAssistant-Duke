package gazeeebo.commands.Modules;

import gazeeebo.Exception.DukeException;
import gazeeebo.Storage.Storage;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import gazeeebo.commands.expenses.AddExpensesCommand;

public class AddModulesCommand {
    public AddModulesCommand(Ui ui, Storage storage, Map<String, ArrayList<ArrayList>> modules) throws DukeException, IOException {
        System.out.println("Enter modules that you want to add: ");
        ui.ReadCommand();
        try {
            if (!ui.FullCommand.equals("CS2113T") || !ui.FullCommand.equals("CG2027") || !ui.FullCommand.equals("CG2028")
                    || !ui.FullCommand.equals("CS2101") || !ui.FullCommand.equals("CG2771")) {
                throw new DukeException("Module does not exist in NUS Computer Engineering course!");
            } else {
                String moduleEntered = ui.FullCommand;

                System.out.println("What do you want to update?: ");
                ui.ReadCommand();
                //type in tutorial/project/other notes, tpo reresents the initials of Tutorial, Project, Others
                String tpo = ui.FullCommand;

                System.out.println("Enter your updates on " + moduleEntered + "'s " + tpo);
                ui.ReadCommand();
                String update = ui.FullCommand;

                boolean isEqual = false;
                for (String mod : modules.keySet()) {
                   if(mod.equals(modules.get(mod))) {
                       for(int i = 0; i < modules.get(mod).size(); i++) {
                           if(tpo.equals(modules.get(mod).get(i))) {
                               modules.get(mod).get(i).add(update);
                           }
                       }
                        isEqual = true;
                    }
                }

                if (isEqual == false) {
                    for()
                    }
                }






        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}
