package duke.command.logic;

import duke.exceptions.ModException;
import duke.exceptions.planner.ModNotFoundException;
import duke.modules.data.ModuleInfoDetailed;
import duke.modules.data.ModuleInfoSummary;
import duke.util.PlannerUi;
import duke.util.Storage;
import java.util.HashMap;

public class SearchCommand extends ModuleCommand {

    private String moduleCode;

    public SearchCommand(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoSummary> summaryMap,
                        HashMap<String, ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi, Storage store) throws ModException {
        if (summaryMap.containsKey(moduleCode)) {
            ModuleInfoSummary temp = summaryMap.get(moduleCode);
            plannerUi.showObject(temp);
        }
        if (detailedMap.containsKey(moduleCode)) {
            ModuleInfoDetailed temp = detailedMap.get(moduleCode);
            plannerUi.showObject(temp);
        }
        throw new ModNotFoundException();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
