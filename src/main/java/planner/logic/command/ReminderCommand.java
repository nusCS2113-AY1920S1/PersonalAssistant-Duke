//@@author kyawtsan99

package planner.logic.command;

import planner.credential.user.User;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.util.legacy.reminder.ThirtyMinReminder;
import planner.util.legacy.reminder.OneHourReminder;
import planner.util.legacy.reminder.TwevleHourReminder;
import planner.util.legacy.reminder.OneDayReminder;
import planner.util.legacy.reminder.Reminder;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;

import java.util.HashMap;

public class ReminderCommand extends ModuleCommand {
    private ThirtyMinReminder thirtyMinReminder;
    private OneHourReminder oneHourReminder;
    private TwevleHourReminder twevleHourReminder;
    private OneDayReminder oneDayReminder;
    private Reminder reminder;

    public ReminderCommand(Arguments args) {
        super(args);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper,
                        User profile) {

        switch (arg("toReminder")) {
            case ("list") : {
                plannerUi.reminderList();
                break;
            }

            case ("one") : {
                thirtyMinReminder.run();
                break;
            }

            case ("two") : {
                oneHourReminder.run();
                break;
            }

            case ("three") : {
                twevleHourReminder.run();
                break;
            }

            case "four" :
            default: {
                oneDayReminder.run();
                break;
            }
        }
    }
}
