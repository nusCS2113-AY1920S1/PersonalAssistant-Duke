package Operations;

import CustomExceptions.RoomShareException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Report {
    private Ui ui = new Ui();
    private TaskList taskList;
    public Report(TaskList taskList) {
        this.taskList = taskList;
    }

    public void fullReport() {
        ui.reportTasks();
        try {
            taskList.list();
        } catch (RoomShareException e) {
            ui.showListError();
        }
        finishedReport();
        unfinishedReport();
        upcomingReport();
        recurringReport();
    }

    public void finishedReport() {
        ui.reportFinished();
        try {
            double percentage = taskList.listDone();
            ui.reportDonePercentage(percentage);
        } catch (RoomShareException e) {
            ui.showListError();
        }
    }

    public void unfinishedReport() {
        ui.reportUnfinished();
        try {
            double percentage = taskList.listNotDone();
            ui.reportNotDonePercentage(percentage);
        } catch (RoomShareException e) {
            ui.showListError();
        }
    }

    public void upcomingReport() {
        ui.reportUpcoming();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatterNow = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String currentTime = now.format(dateTimeFormatterNow);
        try {
            int upcoming = taskList.listUpcoming(currentTime);
            ui.reportUpcomingCount(upcoming);
        } catch (RoomShareException e) {
            ui.showListError();
        }
    }

    public void recurringReport() {
        ui.reportRecurring();
        int recurring = taskList.listRecurringReport();
        ui.reportRecurringCount(recurring);
    }
}
