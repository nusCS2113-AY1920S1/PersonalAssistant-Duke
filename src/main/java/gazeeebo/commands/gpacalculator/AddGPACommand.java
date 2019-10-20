package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.Map;

public class AddGPACommand {
    public AddGPACommand(final Ui ui, final Map<String, GPACommand> GPAList) throws IOException {
        System.out.println("Input in this format Module_Code,total_MC,GPA");
        ui.readCommand();
        String[] splitCommand = ui.fullCommand.split(",");
        String moduleCode = splitCommand[0];
        int moduleCredit = Integer.parseInt(splitCommand[1]);
        double gpa = Double.parseDouble(splitCommand[2]);
        GPACommand inputGpa = new GPACommand(moduleCredit, gpa);
        GPAList.put(moduleCode,inputGpa);
    }
}
