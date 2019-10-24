package gazeeebo.commands.capCalculator;

import gazeeebo.UI.Ui;

import java.util.ArrayList;
import java.util.Map;

public class deleteCAPCommand {
    /**
     * Delete the module from CAPList.
     *
     * @param ui      prints things to the user.
     * @param CAPList deals stores semNumber, moduleCode, moduleCredits and GPA score.
     */
    public deleteCAPCommand(Ui ui, Map<String, ArrayList<CAPCommand>> CAPList) {
        boolean haveDeleted = false;
        String moduleName = ui.fullCommand.split(" ")[1];
        for (String key : CAPList.keySet()) {
            /*Only one element in the array */
            if (moduleName.equals(CAPList.get(key).get(0).moduleCode) && (CAPList.get(key).size() == 1)) {
                CAPList.remove(key); //Remove the entire key
                haveDeleted = true;
                break;
            } else {
                for (int i = 0; i < CAPList.get(key).size(); i++) {
                    if (moduleName.equals(CAPList.get(key).get(i).moduleCode)) {
                        CAPList.get(key).remove(i);
                        haveDeleted = true;
                        break;
                    }
                }
            }
        }
        if (!haveDeleted) {
            System.out.print(ui.fullCommand.split(" ")[1] + " is not found in the list.\n");
        } else {
            System.out.print("Successfully deleted: " + moduleName + "\n");
        }

    }
}
