package duke.command.logic;

import duke.exceptions.ModCcaScheduleException;
import duke.exceptions.ModEmptyListException;
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
        Cca cca = ccas.get(this.index);
        if (cca.isClashing(new TimePeriodWeekly(this.begin, this.end, this.dayOfWeek))) {
            throw new ModCcaScheduleException();
        }
        cca.addPeriod(this.begin, this.end, this.dayOfWeek);
        if (ccas.clashes(cca)) {
            cca.removePeriod(cca.getPeriods().size() - 1);
            throw new ModCcaScheduleException();
        }
        plannerUi.addedMsg(cca);
    }
}
