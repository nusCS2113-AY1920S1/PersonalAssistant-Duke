package planner.logic.command;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

import planner.logic.exceptions.legacy.ModCcaScheduleException;
import planner.logic.exceptions.legacy.ModEmptyListException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModOutOfBoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.Cca;
import planner.util.datetime.NattyWrapper;
import planner.util.crawler.JsonWrapper;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.logic.modules.cca.CcaList;
import planner.util.legacy.periods.TimePeriodWeekly;

public class AddCcaScheduleCommand extends ModuleCommand {

    public AddCcaScheduleCommand(Arguments args) {
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
        NattyWrapper natty = new NattyWrapper();
        LocalTime begin = natty.dateToLocalDateTime(arg("begin")).toLocalTime();
        LocalTime end = natty.dateToLocalDateTime(arg("end")).toLocalTime();
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
