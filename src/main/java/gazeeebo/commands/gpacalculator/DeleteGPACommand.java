package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.util.Map;

public class DeleteGPACommand {
    public DeleteGPACommand(Ui ui, Map<String, GPACommand> GPAList) {
        String moduleName = ui.fullCommand.split(" ")[1];
        if(ui.fullCommand.equals("delete")) {
            System.out.print("Incorrect format: delete module\n");
        } else if(GPAList.containsKey(moduleName)) {
            GPAList.remove(moduleName);
        } else {
            System.out.println(moduleName + " is not in the list.\\n");
        }
    }
}
