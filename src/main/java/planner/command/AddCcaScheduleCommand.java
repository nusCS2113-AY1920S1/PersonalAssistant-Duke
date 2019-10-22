package planner.command;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

import planner.exceptions.original.ModCcaScheduleException;
import planner.exceptions.original.ModEmptyListException;
import planner.exceptions.original.ModException;
import planner.exceptions.original.ModInvalidTimeException;
import planner.exceptions.original.ModOutOfBoundException;
import planner.modules.data.ModuleInfoDetailed;
import planner.modules.inherited.Cca;
import planner.util.commons.JsonWrapper;
import planner.util.commons.ModuleTasksList;
import planner.util.commons.PlannerUi;
import planner.util.commons.Storage;
import planner.util.periods.CcaList;
import planner.util.periods.DateTimeParser;
import planner.util.periods.TimePeriodWeekly;

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
