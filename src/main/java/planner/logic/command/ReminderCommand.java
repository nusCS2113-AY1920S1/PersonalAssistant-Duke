//@@author kyawtsan99

package planner.logic.command;

import planner.ModTimer;
import planner.logic.modules.cca.CcaList;
import planner.util.crawler.JsonWrapper;
import planner.ui.cli.PlannerUi;
import planner.util.storage.Storage;

import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;

import java.util.HashMap;

import java.util.Timer;
import planner.util.legacy.schedule.ScheduledTask;

public class ReminderCommand extends ModuleCommand {

    public ReminderCommand(Arguments args) {
        super(args);
    }

    /**
     * Prints the reminder message every thirty seconds.
     */
    public void printEveryThirtySec() throws InterruptedException {
        Timer time = new ModTimer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 30000);
    }

    /**
     * Prints the reminder message every one minute.
     */
    public void printEveryOneMin() throws InterruptedException {
        Timer time = new ModTimer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 60000);
    }

    /**
     * Prints the reminder message every two minutes.
     */
    public void printEveryTwoMin() throws InterruptedException {
        Timer time = new ModTimer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 120000);
    }

    /**
     * Prints the reminder message every five minutes.
     */
    public void printEveryFiveMin() throws InterruptedException {
        Timer time = new ModTimer();
        ScheduledTask st = new ScheduledTask();
        time.schedule(st, 0, 300000);
    }

    @Override
    public void execute(HashMap<String, ModuleInfoDetailed> detailedMap,
                        ModuleTasksList tasks,
                        CcaList ccas,
                        PlannerUi plannerUi,
                        Storage store,
                        JsonWrapper jsonWrapper) {

        switch (arg("toReminder")) {
            case ("list"): {
                plannerUi.reminderList();
                break;
            }

            case ("one"): {
                try {
                    printEveryThirtySec();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            case ("two"): {
                try {
                    printEveryOneMin();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            case ("three"): {
                try {
                    printEveryTwoMin();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            case ("four"): {
                try {
                    printEveryFiveMin();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                break;
            }

            case ("others") :
            default : {
                plannerUi.reminderWrongCommand();
            }
        }
    }
}
