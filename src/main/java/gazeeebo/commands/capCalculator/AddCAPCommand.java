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
     * @param caplist    deals stores
     *                   semNumber, moduleCode, moduleCredits and CAP score.
     * @throws IOException catch the error if the read file fails.
     */
    public AddCAPCommand(final Ui ui, final Map<String,
            ArrayList<CAPCommand>> caplist) throws IOException {
        System.out.print("Input in this format: "
                + "semNumber,Module_Code,total_MC,CAP\n");
        ui.readCommand();
        String[] splitAddInput = ui.fullCommand.split(",");
        if (splitAddInput.length != 4) {
            System.out.print("Incorrect format.\n");
        } else {
            String semNumber = splitAddInput[0];
            String moduleCode = splitAddInput[1];
            int moduleCredit = Integer.parseInt(splitAddInput[2]);
            String grade = splitAddInput[3];
            CAPCommand newCAP = new CAPCommand(moduleCode, moduleCredit, grade);
            if (caplist.containsKey(semNumber)) {
                caplist.get(semNumber).add(newCAP);
            } else {
                ArrayList<CAPCommand> semInfo = new ArrayList<>();
                semInfo.add(newCAP);
                caplist.put(semNumber, semInfo);
            }
            System.out.print("Successfully added: " + moduleCode + "\n");
        }
    }
}
