package planner.logic.command;

import planner.logic.modules.cca.Cca;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;
import planner.util.legacy.reminder.Reminder;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTask;
import planner.logic.modules.module.ModuleTasksList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReminderCommand extends ModuleCommand {
    public ReminderCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {
        switch (arg("toReminder")) {
            case ("list") : {
                plannerUi.reminderList();
                break;
            }

            case ("one") :
            default: {
                break;
            }


        }
    }
}
