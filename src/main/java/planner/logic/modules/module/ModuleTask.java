package planner.logic.modules.module;

import planner.logic.exceptions.legacy.ModInvalidIndexException;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.exceptions.planner.ModBadGradeException;
import planner.logic.exceptions.planner.ModBadSuException;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.util.datetime.NattyWrapper;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ModuleTask extends TaskWithMultipleWeeklyPeriod {
    private ModuleInfoDetailed moduleInfoDetailed;

    public ModuleTask(String code, ModuleInfoDetailed moduleInfoDetailed) {
        super(code);
        this.moduleInfoDetailed = moduleInfoDetailed;
    }

    /**
     * Constructor for ModuleTask when users wants to input the time.
     * @param code The Module Code
     * @param moduleInfoDetailed The detailed information of the module
     * @param beginString begin time
     * @param endString end time
     * @param dayOfWeek day of the week when the module takes place
     * @throws ModInvalidTimeException when input time is invalid
     */
    public ModuleTask(String code, ModuleInfoDetailed moduleInfoDetailed,
                      String beginString, String endString, String dayOfWeek) throws ModInvalidTimeException {
        super(code, DayOfWeek.valueOf(dayOfWeek.toUpperCase()));
        this.moduleInfoDetailed = moduleInfoDetailed;
        NattyWrapper natty = new NattyWrapper();
        LocalTime begin = natty.dateToLocalDateTime(beginString).toLocalTime();
        LocalTime end = natty.dateToLocalDateTime(endString).toLocalTime();
        try {
            this.setPeriod(0, begin, end);
        } catch (ModInvalidIndexException ex) {
            ex.printStackTrace();
        }
    }

    public String getModuleCode() {
        return moduleInfoDetailed.getModuleCode();
    }

    public Double getModuleCredit() {
        return moduleInfoDetailed.getModuleCredit();
    }

    public String getModuleLevel() {
        return moduleInfoDetailed.getModuleLevel();
    }

    public ModuleInfoDetailed getModuleInfoDetailed() {
        return moduleInfoDetailed;
    }

    public String getGrade() {
        return moduleInfoDetailed.getGrade();
    }

    public void setGrade(String letterGrade) throws ModBadSuException, ModBadGradeException {
        moduleInfoDetailed.setGrade(letterGrade);
    }

    @Override
    public String toString() {
        return super.toString() + " | " + moduleInfoDetailed.toString();
    }

    @Override
    public String type() {
        return "module";
    }
}
