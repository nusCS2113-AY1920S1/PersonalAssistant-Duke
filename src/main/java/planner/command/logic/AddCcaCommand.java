package planner.command.logic;

import planner.exceptions.ModCcaScheduleException;
import planner.exceptions.ModInvalidTimeException;
import planner.modules.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.CcaList;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
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
