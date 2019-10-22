package planner.command;

import planner.exceptions.original.ModCcaScheduleException;
import planner.exceptions.original.ModInvalidTimeException;
import planner.modules.inherited.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.periods.CcaList;
import planner.util.commons.JsonWrapper;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.commons.ModuleTasksList;
import java.util.HashMap;

public class AddCcaCommand extends ModuleCommand {
    private Cca cca;

    public AddCcaCommand(String ccaName, String begin, String end, String dayOfWeek) throws ModInvalidTimeException {
        this.cca = new Cca(ccaName, begin, end, dayOfWeek);
    }

    @Override
    public void execute(HashMap<String,
                        ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModCcaScheduleException {
        if (ccas.clashes(this.cca)) {
            throw new ModCcaScheduleException();
        }
        ccas.add(this.cca);
        plannerUi.addedMsg(this.cca);
    }
}
