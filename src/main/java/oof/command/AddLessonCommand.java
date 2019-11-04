package oof.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

import oof.SelectedInstance;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.SemesterList;
import oof.model.task.TaskList;
import oof.storage.StorageManager;

public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "lesson";
    private ArrayList<String> arguments;
    private static final int INDEX_NAME = 0;
    private static final int INDEX_DAY = 1;
    private static final int INDEX_START_TIME = 2;
    private static final int INDEX_END_TIME = 3;
    private static final int ARRAY_SIZE_DAY = 2;
    private static final int ARRAY_SIZE_START_TIME = 3;
    private static final int ARRAY_SIZE_END_TIME = 4;

    /**
     * Constructor for AddLessonCommand.
     *
     * @param arguments Command inputted by user for processing.
     */
    public AddLessonCommand(ArrayList<String> arguments) {
        super();
        this.arguments = arguments;
    }

    /**
     * Adds a lesson to module.
     *
     * @param semesterList   Instance of SemesterList that stores Semester objects.
     * @param tasks          Instance of TaskList that stores Task objects.
     * @param ui             Instance of Ui that is responsible for visual feedback.
     * @param storageManager Instance of Storage that enables the reading and writing of Task
     *                       objects to hard disk.
     * @throws OofException if user input invalid commands.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, StorageManager storageManager)
            throws OofException {
        if (arguments.get(INDEX_NAME).isEmpty()) {
            throw new OofException("OOPS!!! The lesson needs a name.");
        } else if (arguments.size() < ARRAY_SIZE_DAY || arguments.get(INDEX_DAY).isEmpty()) {
            throw new OofException("OOPS!!! The lesson needs a day.");
        } else if (arguments.size() < ARRAY_SIZE_START_TIME || arguments.get(INDEX_START_TIME).isEmpty()) {
            throw new OofException("OOPS!!! The lesson needs a start time.");
        } else if (arguments.size() < ARRAY_SIZE_END_TIME || arguments.get(INDEX_END_TIME).isEmpty()) {
            throw new OofException("OOPS!!! The lesson needs an end time.");
        }
        String startTime = arguments.get(INDEX_START_TIME);
        String endTime = arguments.get(INDEX_END_TIME);
        if (!isDateValid(startTime)) {
            throw new OofException("OOPS!!! The start date is invalid.");
        } else if (!isDateValid(endTime)) {
            throw new OofException("OOPS!!! The end date is invalid.");
        }
        SimpleDateFormat format = new java.text.SimpleDateFormat("HH:mm");
        try {
            Date newStartTime = format.parse(startTime);
            Date newEndTime = format.parse(endTime);
            if (!isStartDateBeforeEndDate(newStartTime, newEndTime)) {
                throw new OofException("OOPS!!! The start time of a lesson cannot be after the end time.");
            }
        } catch (ParseException e) {
            throw new OofException("Timestamp given is invalid! Please try again.");
        }

        SelectedInstance selectedInstance = SelectedInstance.getInstance();
        Module module = selectedInstance.getModule();
        if (module == null) {
            throw new OofException("OOPS!! Please select a Module.");
        }
        String name = arguments.get(INDEX_NAME);
        String moduleCode = module.getModuleCode();
        String description = moduleCode + " " + name;
        if (exceedsMaxLength(description)) {
            throw new OofException("Task exceeds maximum description length!");
        }
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(arguments.get(INDEX_DAY).toUpperCase());
        Lesson lesson = new Lesson(moduleCode, name, dayOfWeek, startTime, endTime);
        module.addLesson(lesson);
        ui.printLessonAddedMessage(module.getModuleCode(), lesson);
        storageManager.writeSemesterList(semesterList);
    }

    /**
     * Checks if start and end date are chronologically accurate.
     *
     * @param startTime Start time of lesson being added.
     * @param endTime   End time of lesson being added.
     * @return true if start date occurs before end date, false otherwise.
     */
    private boolean isStartDateBeforeEndDate(Date startTime, Date endTime) {
        return startTime.compareTo(endTime) <= 0;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
