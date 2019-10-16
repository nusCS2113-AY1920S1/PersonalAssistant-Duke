package duke.command.logic;

import duke.exceptions.ModCcaScheduleException;
import duke.exceptions.ModInvalidTimeException;
import duke.modules.Cca;
import duke.modules.data.ModuleInfoDetailed;
import duke.util.CcaList;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.commons.ModuleTasksList;
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
