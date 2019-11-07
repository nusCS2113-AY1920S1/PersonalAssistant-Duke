//@@author e0313687

package planner.logic.command;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import planner.credential.user.User;
import planner.logic.modules.cca.Cca;
import planner.logic.modules.cca.CcaList;
import planner.logic.modules.legacy.task.TaskWithMultipleWeeklyPeriod;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import planner.ui.cli.PlannerUi;
import planner.util.crawler.JsonWrapper;
import planner.util.storage.Storage;

public class SortCommand extends ModuleCommand {

    public SortCommand(Arguments args) {
        super(args);
    }

    private List<TaskWithMultipleWeeklyPeriod> filter(List<TaskWithMultipleWeeklyPeriod> all, DayOfWeek dayOfWeek) {
        List<TaskWithMultipleWeeklyPeriod> filtered = new ArrayList<>();
        for (TaskWithMultipleWeeklyPeriod task : all) {
            if (task.happensOnThisDayOfWeek(dayOfWeek)) {
                filtered.add(task);
            }
        }
        return filtered;
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) {
        String toSort = arg("toSort");
        plannerUi.sortMsg(toSort);
        switch (toSort) {
            case ("ccas"): {
                CcaList hold = ccas;
                hold.sort(Comparator.comparing((Object t) -> ((Cca) t).getTask()));
                plannerUi.showSorted(hold);
                break;
            }
            case ("times"):
                List<TaskWithMultipleWeeklyPeriod> holdForTime = new ArrayList<>();
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(arg("DayOfTheWeek").toUpperCase());
                for (Cca t : ccas) {
                    if (t.happensOnThisDayOfWeek(dayOfWeek)) {
                        holdForTime.add(t);
                    }
                }
                for (ModuleTask t : tasks.getTasks()) {
                    if (t.happensOnThisDayOfWeek(dayOfWeek)) {
                        holdForTime.add(t);
                    }
                }
                holdForTime.sort(Comparator.comparing((Object t) -> ((TaskWithMultipleWeeklyPeriod) t)
                        .getTimePeriodOfTheDay(dayOfWeek).get(0).getBegin()));
                plannerUi.showSortedTimes(holdForTime, dayOfWeek);
                break;
            case ("modules"):

            default:
                List<ModuleTask> taskList = tasks.getTasks();
                switch (arg("type")) {
                    case ("level"): {
                        taskList.sort(Comparator.comparing((Object t) -> ((ModuleTask) t).getModuleLevel()));
                        break;
                    }
                    case ("grade"): {
                        taskList.sort(Comparator.comparing((Object t) -> ((ModuleTask) t).getGradeAsNumbers()));
                        break;
                    }
                    case ("mc"): {
                        taskList.sort(Comparator.comparing((Object t) -> ((ModuleTask) t).getModuleCredit()));
                        break;
                    }
                    case ("code"):
                    default: {
                        taskList.sort(Comparator.comparing((Object t) -> ((ModuleTask) t).getModuleCode()));
                        break;
                    }
                }
                plannerUi.showSorted(taskList);
        }
    }
}