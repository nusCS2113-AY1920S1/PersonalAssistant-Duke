package Modes;

import Enums.ReportType;
import Operations.Parser;
import Operations.Report;
import Operations.TaskList;
import Operations.Ui;

public class ReportMode {
    private TaskList taskList;
    private Report report;
    private Parser parser = new Parser();
    private Ui ui = new Ui();
    public ReportMode(TaskList taskList) {
        this.taskList = taskList;
    }

    public void run() {
        report = new Report(taskList);
        boolean isExit = false;
        while (!isExit) {
            String temp = parser.getCommandLine();
            ReportType reportType;
            try {
                reportType = ReportType.valueOf(temp);
            } catch (IllegalArgumentException e) {
                reportType = ReportType.others;
            }
            switch (reportType) {
                case exit:
                    isExit = true;
                    break;

                case full:
                    report.fullReport();
                    break;

                case finished:
                    report.finishedReport();
                    break;

                case unfinished:
                    report.unfinishedReport();
                    break;

                case upcoming:
                    report.upcomingReport();
                    break;

                case recurring:
                    report.recurringReport();
                    break;

                default:
                    ui.showCommandError();
                    break;
            }
        }
    }
}
