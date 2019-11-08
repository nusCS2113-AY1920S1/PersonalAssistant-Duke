//@@author LongLeCE

package planner.logic.command;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

import planner.credential.user.User;
import planner.logic.exceptions.legacy.ModCcaScheduleException;
import planner.logic.exceptions.legacy.ModEmptyListException;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.exceptions.legacy.ModOutOfBoundException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.cca.Cca;
import planner.util.datetime.NattyWrapper;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.util.legacy.periods.TimePeriodWeekly;

public class AddCcaScheduleCommand extends ModuleCommand {

    public AddCcaScheduleCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String,
            ModuleInfoDetailed> detailedMap,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) throws ModException {
        int index = arg("index", Integer.class) - 1;
        if (profile.getCcas().size() == 0) {
            throw new ModEmptyListException("ccas");
        }
        if (index < 0 || index >= profile.getCcas().size()) {
            throw new ModOutOfBoundException();
        }
        NattyWrapper natty = new NattyWrapper();
        LocalTime begin = natty.dateToLocalDateTime(arg("begin")).toLocalTime();
        LocalTime end = natty.dateToLocalDateTime(arg("end")).toLocalTime();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(arg("dayOfWeek").toUpperCase());
        TimePeriodWeekly timePeriodWeekly = new TimePeriodWeekly(begin, end, dayOfWeek);
        for (Cca cca: profile.getCcas()) {
            if (cca.isClashing(timePeriodWeekly)) {
                throw new ModCcaScheduleException();
            }
        }
        Cca cca = profile.getCcas().get(index);
        cca.addPeriod(timePeriodWeekly);
        plannerUi.addedMsg(cca);
    }
}
