package oof.command;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;

public class AddSemesterCommand extends Command {
    private String line;
    private static final int INDEX_YEAR = 0;
    private static final int INDEX_NAME = 1;
    private Module module;

    /**
     * Constructor for AddSemesterCommand.
     *
     * @param line Command inputted by user for processing.
     */
    public AddSemesterCommand(String line) {
        super();
        this.line = line;
    }

    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) throws OofException {
        String yearAndName = line.replaceFirst("/year", "").trim();
        if (!hasYear(yearAndName)) {
            throw new OofException("OOPS!! The semester needs a year.");
        } else {
            String[] yearAndNameSplit = yearAndName.split("/name");
            if (!hasName(yearAndNameSplit)) {
                throw new OofException("OOPS!! The semester needs a name");
            }
        }
        String[] yearAndNameSplit = yearAndName.split("/name");
        String year = yearAndNameSplit[INDEX_YEAR].trim();
        String name = yearAndNameSplit[INDEX_NAME].trim();
        Semester semester = new Semester(year, name);
        semesterList.addSemester(semester);
        ui.printSemesterAddedMessage(semester);
        storage.writeSemesterList(semesterList, semester, module);
    }

    /**
     * Checks if semester has a year.
     *
     * @param year processed user input.
     * @return true if year is more than length 0 and is not whitespace
     */
    private boolean hasYear(String year) {
        return year.trim().length() > 0;
    }

    /**
     * Checks if semester has a name.
     *
     * @param lineSplit processed user input.
     * @return true if name is more than length 0 and is not whitespace
     */
    private boolean hasName(String[] lineSplit) {
        return lineSplit[INDEX_NAME].trim().length() > 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
