package leduc.command;

import leduc.ui.Ui;
import leduc.exception.DukeException;
import leduc.exception.MeaninglessException;
import leduc.exception.NonExistentDateException;
import leduc.exception.WrongParameterException;
import leduc.storage.Storage;
import leduc.task.EventsTask;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Represent the language command.
 * Show the tasks according to the type of date.
 * The type of date can be today, day, dayofweek, week, month, year
 */
public class ShowCommand extends Command {
    private static String showShortcut = "show";

    /**
     * Constructor of ShowCommand.
     *
     * @param userInput String which represent the input string of the user.
     */
    public ShowCommand(String userInput) {
        super(userInput);
    }

    /**
     * Helper method to convert the type of day(string) to an integer
     * @param dayOfWeek the day of the week, can be monday, tuesday, wednesday, thursday, friday, saturday, sunday
     * @return the integer that represent the day of week
     * @throws MeaninglessException thrown when the type of date is wrong
     */
    private int getDayOfWeekInInt(String dayOfWeek) throws MeaninglessException {
        int dayOfWeekInt = 0;
        switch(dayOfWeek){
            case "monday":
                dayOfWeekInt = 1;
                break;
            case "tuesday":
                dayOfWeekInt = 2;
                break;
            case "wednesday":
                dayOfWeekInt = 3;
                break;
            case "thursday":
                dayOfWeekInt = 4;
                break;
            case "friday":
                dayOfWeekInt = 5;
                break;
            case "saturday":
                dayOfWeekInt = 6;
                break;
            case "sunday":
                dayOfWeekInt = 7;
                break;
            default:
                throw new MeaninglessException();
        }
        return dayOfWeekInt;

    }

    /**
     * Helper method to add all the tasks corresponding to the day date from allTaskHavingDate to showTaskList
     * @param date the day
     * @param allTaskHavingDate all the task that have a date
     * @param showTaskList final result that will be shown
     */
    private void getListTaskExactDay(LocalDate date, ArrayList<Task> allTaskHavingDate, ArrayList<Task> showTaskList){
        for(Task t : allTaskHavingDate){
            if(t.isHomework()){
                if(((HomeworkTask)t).getDeadlines().getDate().toLocalDate().isEqual(date)){
                    showTaskList.add(t);
                }
            }
            if(t.isEvent()){
                if(((EventsTask)t).getDateFirst().getDate().toLocalDate().isEqual(date)){
                    showTaskList.add(t);
                }
            }
        }
    }

    /**
     * Helper method to add all the tasks corresponding to the month and the year date from allTaskHavingDate to showTaskList
     * @param dateMonth the date corresponding to the month
     * @param dateYear the date corresponding to the year
     * @param allTaskHavingDate all the task that have a date
     * @param showTaskList final result that will be shown
     */
    public void getListTaskMonth(int dateMonth, int dateYear, ArrayList<Task> allTaskHavingDate, ArrayList<Task> showTaskList){
        for(Task t : allTaskHavingDate){
            if(t.isHomework()){
                if(((HomeworkTask)t).getDeadlines().getDate().getMonthValue() == dateMonth && ((HomeworkTask)t).getDeadlines().getDate().getYear() == dateYear){
                    showTaskList.add(t);
                }
            }
            if(t.isEvent()){
                if(((EventsTask)t).getDateFirst().getDate().getMonthValue() == dateMonth && ((EventsTask)t).getDateFirst().getDate().getYear() == dateYear){
                    showTaskList.add(t);
                }
            }
        }
    }
    /**
     * Helper method to add all the tasks corresponding to the year date from allTaskHavingDate to showTaskList
     * @param dateYear the date corresponding to the year
     * @param allTaskHavingDate all the task that have a date
     * @param showTaskList final result that will be shown
     */
    public void getListTaskYear(int dateYear, ArrayList<Task> allTaskHavingDate, ArrayList<Task> showTaskList){
        for(Task t : allTaskHavingDate){
            if(t.isHomework()){
                if(((HomeworkTask)t).getDeadlines().getDate().getYear() == dateYear){
                    showTaskList.add(t);
                }
            }
            if(t.isEvent()){
                if(((EventsTask)t).getDateFirst().getDate().getYear() == dateYear){
                    showTaskList.add(t);
                }
            }
        }
    }

    /**
     * Show all the task corresponding to the user type of date
     * Type of date can be today, day, dayofweek, week, month, year
     * There are two behaviour :
     * - one line command
     * - multi line command
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.ui.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws DukeException all the exception catchable
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ArrayList<Task> allTaskHavingDate = tasks.filterTasks(tasks);
        ArrayList<Task> showTaskList = new ArrayList<>();
        String userSubstring;
        Boolean multiStep = false;
        if(isCalledByShortcut){
            userSubstring = userInput.substring(ShowCommand.showShortcut.length()).trim();
        }
        else {
            userSubstring = userInput.substring(4).trim();
        }
        String[] userSubSubString = userSubstring.split(" ");
        if(userSubSubString.length == 1) {
            multiStep = true;
        }

        if(userSubSubString[0].matches("day")){
            String date;
            if(multiStep){
                ui.showEnterDayShow();
                date = ui.readCommand();
            }
            else {
                date = userSubSubString[1];
            }

            LocalDate exactDate = null;
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
                exactDate = LocalDate.parse(date.trim(), formatter);
            }catch(Exception e){
                throw new NonExistentDateException();
            }
            getListTaskExactDay(exactDate, allTaskHavingDate, showTaskList);
        }
        else if(userSubSubString[0].matches("dayofweek")){
            int dayOfWeekInt = 0;
            String dayOfWeek;
            if(multiStep){
                ui.showEnterDayOfWeekShow();
                dayOfWeek = ui.readCommand();
            }
            else {
                dayOfWeek = userSubSubString[1];
            }

            dayOfWeekInt = getDayOfWeekInInt(dayOfWeek);
            LocalDate dateNow = LocalDate.now();
            LocalDate dateFindDayOfWeek = dateNow.plusDays(1);
            while(dateFindDayOfWeek.getDayOfWeek().getValue()!= dayOfWeekInt){
                dateFindDayOfWeek = dateFindDayOfWeek.plusDays(1);
            }
            getListTaskExactDay(dateFindDayOfWeek, allTaskHavingDate, showTaskList);
        }
        else if(userSubSubString[0].matches("today")){
            LocalDate dateNow = LocalDate.now();
            getListTaskExactDay(dateNow, allTaskHavingDate, showTaskList);
        }
        else if(userSubSubString[0].matches("week")){
            LocalDate dateNow = LocalDate.now();
            for (int i = 0; i < 7; i++){
                LocalDate dateWeek = dateNow.plusDays(i);
                getListTaskExactDay(dateWeek, allTaskHavingDate, showTaskList);
            }
        }
        else if (userSubSubString[0].matches("month")){
            String date;
            if(multiStep){
                ui.showEnterMonthShow();
                date = ui.readCommand();
            }
            else {
                date = userSubSubString[1];
            }
            String[] dateSplit = date.trim().split("/");
            if(dateSplit.length == 1 || dateSplit[0].isBlank()){
                throw new NonExistentDateException();
            }
            int dateMonth = 0;
            int dateYear = 0;
            try{
                dateMonth = Integer.parseInt(dateSplit[0]);
                dateYear = Integer.parseInt(dateSplit[1]);
            }catch(Exception e){
                throw new NonExistentDateException();
            }
            if(dateMonth <1 || dateMonth > 12){
                throw new NonExistentDateException();
            }
            getListTaskMonth(dateMonth, dateYear, allTaskHavingDate, showTaskList);
        }
        else if (userSubSubString[0].matches("year")){
            String date;
            if(multiStep){
                ui.showEnterYearShow();
                date = ui.readCommand();
            }
            else {
                date = userSubSubString[1];
            }

            int dateYear = 0;
            try{
                dateYear = Integer.parseInt(date);
            }catch(Exception e){
                throw new NonExistentDateException();
            }
            getListTaskYear(dateYear, allTaskHavingDate, showTaskList);
        }
        else {
            throw new WrongParameterException();
        }
        ui.showNotCompleteList(showTaskList, tasks);
    }

    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getShowShortcut() {
        return showShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param showShortcut the new shortcut
     */
    public static void setShowShortcut(String showShortcut) {
        ShowCommand.showShortcut = showShortcut;
    }
}
