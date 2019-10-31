package planner.logic.command;

import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.util.legacy.reminder.ThirtyMinReminder;
import planner.util.legacy.reminder.OneHourReminder;
import planner.util.legacy.reminder.TwevleHourReminder;
import planner.util.legacy.reminder.OneDayReminder;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ReminderCommand extends ModuleCommand {
    public ReminderCommand(Arguments args) {
        super(args);
    }

    private ThirtyMinReminder thirtyMinReminder;
    private OneHourReminder oneHourReminder;
    private TwevleHourReminder twevleHourReminder;
    private OneDayReminder oneDayReminder;

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

            case ("one") : {
                thirtyMinReminder.execute(LocalDateTime.now());
                break;
            }

            case ("two") : {
                oneHourReminder.execute(LocalDateTime.now());
                break;
            }

            case ("three") : {
                twevleHourReminder.execute(LocalDateTime.now());
                break;
            }

            case "four" :
            default: {
                oneDayReminder.execute(LocalDateTime.now());
                break;
            }
        }
    }
}
