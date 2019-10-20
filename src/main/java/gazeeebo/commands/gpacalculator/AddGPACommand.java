package gazeeebo.commands.gpacalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AddGPACommand {
    /**
     * Add a new module into GPAList.
     *
     * @param ui         the object that prints things to the user.
     * @param gpalist    the object that deals stores semNumber, moduleCode, moduleCredits and GPA score.
     * @throws IOException catch the error if the read file fails.
     */
    public AddGPACommand(final Ui ui, final Map<String, ArrayList<GPACommand>> gpalist) throws IOException {
        System.out.println("Input in this format: semNumber,Module_Code,total_MC,GPA");
        ui.readCommand();
        String[] splitCommand = ui.fullCommand.split(",");
        String semNumber = splitCommand[0];
        String moduleCode = splitCommand[1];
        int moduleCredit = Integer.parseInt(splitCommand[2]);
        double gpa = Double.parseDouble(splitCommand[3]);
        GPACommand inputGpa = new GPACommand(moduleCode, moduleCredit, gpa);
        if (gpalist.containsKey(semNumber)) {
            gpalist.get(semNumber).add(inputGpa);
        } else {
            ArrayList<GPACommand> semInfo = new ArrayList<>();
            semInfo.add(inputGpa);
            gpalist.put(semNumber, semInfo);
        }
        System.out.println("Successfully added: " + moduleCode);
    }
}
