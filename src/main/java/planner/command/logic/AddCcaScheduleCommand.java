package planner.command.logic;

import planner.exceptions.ModCcaScheduleException;
import planner.exceptions.ModEmptyListException;
import planner.exceptions.ModInvalidTimeException;
import planner.exceptions.ModOutOfBoundException;
import planner.modules.Cca;
import planner.modules.data.ModuleInfoDetailed;
import planner.util.CcaList;
import planner.util.DateTimeParser;
import planner.util.JsonWrapper;
import planner.util.PlannerUi;
import planner.util.Storage;
import planner.util.TimePeriodWeekly;
import planner.util.commons.ModuleTasksList;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;

public class AddCcaScheduleCommand extends ModuleCommand {
    private int index;
    private LocalTime begin;
    private LocalTime end;
    private DayOfWeek dayOfWeek;

    /**
     * Constructor for AddCcaScheduleCommand.
     * @param index input index of Cca
     * @param begin begin time
     * @param end end time
     * @param dayOfWeek day of week on which cca takes place
     * @throws ModInvalidTimeException when input time is invalid
     */
    public AddCcaScheduleCommand(int index, String begin, String end, String dayOfWeek) throws ModInvalidTimeException {
        this.index = index - 1;
        this.begin = DateTimeParser.getStringToDate(begin).toLocalTime();
        this.end = DateTimeParser.getStringToDate(end).toLocalTime();
        this.dayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
    }

    @Override
    public void execute(HashMap<String,
            ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) throws
            ModCcaScheduleException,
            ModEmptyListException,
            ModOutOfBoundException {
        if (ccas.size() == 0) {
            throw new ModEmptyListException("ccas");
        }
        if (index < 0 || index >= ccas.size()) {
            throw new ModOutOfBoundException();
        }
        CcaList temp = (CcaList) ccas.clone();
        Cca cca = temp.get(this.index);
        if (cca.isClashing(new TimePeriodWeekly(this.begin, this.end, this.dayOfWeek))) {
            throw new ModCcaScheduleException();
        }
        cca.addPeriod(this.begin, this.end, this.dayOfWeek);
        if (ccas.clashes(cca)) {
            throw new ModCcaScheduleException();
        }
        ccas.set(this.index, cca);
        plannerUi.addedMsg(cca);
    }
}
