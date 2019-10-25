package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Adds a new module to the CAP list.
 */

public class AddCAPCommand {
    /**
     * Add a new module into GPAList.
     *
     * @param ui         prints things to the user.
     * @param CAPList    deals stores semNumber, moduleCode, moduleCredits and CAP score.
     * @throws IOException catch the error if the read file fails.
     */
    public AddCAPCommand(Ui ui, Map<String, ArrayList<CAPCommand>> CAPList) throws IOException {
        System.out.print("Input in this format: semNumber,Module_Code,total_MC,CAP\n");
        ui.readCommand();
        String[] splitAddInput = ui.fullCommand.split(",");
        String semNumber = splitAddInput[0];
        String moduleCode = splitAddInput[1];
        int moduleCredit = Integer.parseInt(splitAddInput[2]);
        String grade = splitAddInput[3];
        CAPCommand newCAP = new CAPCommand(moduleCode, moduleCredit, grade);
        if (CAPList.containsKey(semNumber)) {
            CAPList.get(semNumber).add(newCAP);
        } else {
            ArrayList<CAPCommand> semInfo = new ArrayList<>();
            semInfo.add(newCAP);
            CAPList.put(semNumber, semInfo);
        }
        System.out.print("Successfully added: " + moduleCode + "\n");
    }
}
