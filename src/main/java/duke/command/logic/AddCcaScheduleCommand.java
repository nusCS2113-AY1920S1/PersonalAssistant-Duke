package duke.command.logic;

import duke.exceptions.ModCcaScheduleException;
import duke.exceptions.ModEmptyListException;
import duke.exceptions.ModException;
import duke.exceptions.ModInvalidTimeException;
import duke.exceptions.ModOutOfBoundException;
import duke.modules.Cca;
import duke.modules.data.ModuleInfoDetailed;
import duke.util.CcaList;
import duke.util.DateTimeParser;
import duke.util.JsonWrapper;
import duke.util.PlannerUi;
import duke.util.Storage;
import duke.util.TimePeriodWeekly;
import duke.util.commons.ModuleTasksList;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

public class AddCcaScheduleCommand extends ModuleCommand {

    public AddCcaScheduleCommand(Arguments args) throws ModInvalidTimeException {
        super(args);
    }

    @Override
    public void execute(HashMap<String,
            ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws ModException {
        int index = arg("index", Integer.class) - 1;
        if (ccas.size() == 0) {
            throw new ModEmptyListException("ccas");
        }
        if (index < 0 || index >= ccas.size()) {
            throw new ModOutOfBoundException();
        }
        Cca cca = ccas.get(index);
        LocalTime begin = DateTimeParser.getStringToDate(arg("begin")).toLocalTime();
        LocalTime end = DateTimeParser.getStringToDate(arg("end")).toLocalTime();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(arg("dayOfWeek").toUpperCase());
        if (cca.isClashing(new TimePeriodWeekly(begin, end, dayOfWeek))) {
            throw new ModCcaScheduleException();
        }
        cca.addPeriod(begin, end, dayOfWeek);
        if (ccas.clashes(cca)) {
            cca.removePeriod(cca.getPeriods().size() - 1);
            throw new ModCcaScheduleException();
        }
        plannerUi.addedMsg(cca);
    }
}
