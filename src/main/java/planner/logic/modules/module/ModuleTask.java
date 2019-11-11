//@@author namiwa

package planner.logic.modules.module;

import planner.logic.exceptions.legacy.ModInvalidIndexException;
import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.exceptions.planner.ModBadGradeException;
import planner.logic.exceptions.planner.ModBadSuException;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.util.datetime.NattyWrapper;
import planner.util.legacy.periods.TimePeriodWeekly;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ModuleTask extends TaskWithMultipleWeeklyPeriod {
    private ModuleInfoDetailed moduleInfoDetailed;

    public ModuleTask(String code, ModuleInfoDetailed moduleInfoDetailed) {
        super(code);
        this.moduleInfoDetailed = moduleInfoDetailed;
    }

    //@@author e0313687
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
        super(code, TaskWithMultipleWeeklyPeriod.getDayOfWeek(dayOfWeek));
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

    //@@author namiwa
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

    public String getPrerequisites() {
        return moduleInfoDetailed.getPrerequisites();
    }

    public String getGrade() {
        return moduleInfoDetailed.getGrade();
    }

    //@@author andrewleow97
    public void setGrade(String letterGrade) throws ModBadSuException, ModBadGradeException {
        moduleInfoDetailed.setGrade(letterGrade);
    }

    //@@author e0313687
    /**
     * Returns the grades as a number for sorting.
     * @return the number allocated for the grade.
     */
    public int getGradeAsNumbers() {
        switch (moduleInfoDetailed.getGrade()) {
            case "A+":
                return 0;
            case "A":
                return 1;
            case "A-":
                return 2;
            case "B+":
                return 3;
            case "B":
                return 4;
            case "B-":
                return 5;
            case "C+":
                return 6;
            case "C":
                return 7;
            case "D+":
                return 8;
            case "D":
                return 9;
            case "F":
                return 10;
            case "S":
                return 11;
            case "U":
                return 12;
            default:
                return 20;
        }
    }

    @Override
    public String toString() {
        StringBuilder prefix = new StringBuilder(super.toString() + " | " + moduleInfoDetailed.toString() + " | ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[HH:mm]");
        if (this.getPeriods() != null) {
            for (TimePeriodWeekly period: this.getPeriods()) {
                prefix.append(period.getBeginTime().format(formatter))
                        .append(" - ")
                        .append(period.getEndTime()
                                .format(formatter)).append(" on ")
                        .append(period.getDayOfWeek())
                        .append(", ");
            }
        }
        return prefix.toString().substring(0, prefix.length() - 2);
    }

    @Override
    public String type() {
        return "module";
    }
}
