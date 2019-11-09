package Commands;
import Commons.DukeConstants;
import Commons.Storage;
import Commons.UserInteraction;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.util.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FindFreeTimesCommand extends Command {
    private static final int HALF_HOUR_MARK = 30;
    private static final int HOUR_MARK = 60;
    private static final String twelveHourTimeFormatStartTime = "12:00 AM";
    private static final String twelveHourTimeAMPostFix = "AM";
    private static final String twelveHourTimeFormatMaximumHour = "12";
    private static final String twelveHourTimeFormatHourAndMinuteSeparator = ":";
    private final SimpleDateFormat timeFormat12 = new SimpleDateFormat("hh:mm aa");
    private final SimpleDateFormat timeFormat24 = new SimpleDateFormat("HHmm");
    private final SimpleDateFormat dateDayFormat = new SimpleDateFormat("E dd/MM/yyyy");
    private final SimpleDateFormat dateTimeFormat12 = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
    private final SimpleDateFormat dateTimeFormat24 = new SimpleDateFormat("E dd/MM/yyyy HHmm");

    private final Integer options = 5;
    private final Integer duration;
    private final ArrayList<Pair<Date, Date>> freeTimeData = new ArrayList<>();
    private final NavigableMap<String, ArrayList<Pair<String, String>>> dataMap = new TreeMap<>();
    private String message;

    public FindFreeTimesCommand(Integer duration) {
        this.duration = duration;
    }

    /**
     * This method generates a increased a dateTime given by days or hours based on given duration and returns the new dateTime.
     * @param inDate The dateTime given
     * @param duration The duration given
     * @return The new dateTime after increasing the inDate
     */
    private Date increaseDateTime(Date inDate, int duration){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, duration);
        return c.getTime();
    }

    /**
     * This method updates the dateTime to same date with time 2359 as upper Boundary.
     * @param inDate The date given
     * @return The updated date
     */
    private Date increaseToTwoThreeFiveNine(Date inDate){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, 23);
        c.add(Calendar.MINUTE, 59);
        return c.getTime();
    }

    /**
     * This method updates the dateTime to same date with time 0700 as lower Boundary.
     * @param inDate The date given
     * @return The updated date
     */
    private Date increaseZeroSevenZeroZero(Date inDate){
        Calendar c = Calendar.getInstance();
        c.setTime(inDate);
        c.add(Calendar.HOUR, 7);
        return c.getTime();
    }

    /**
     * This function rounds ups the time to the nearest half hour or hour mark.
     * @param date The date given to round up
     * @return The round up date
     */
    private Date roundByHalfHourMark(Date date) {
        long minuteToIncrease = 0;
        long diff = date.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;

        if(diffMinutes > HALF_HOUR_MARK) minuteToIncrease = HOUR_MARK - diffMinutes;
        else if(diffMinutes < HALF_HOUR_MARK) minuteToIncrease = HALF_HOUR_MARK - diffMinutes;
        Calendar c = Calendar.getInstance();
        c.setTime((date));
        c.add(Calendar.MINUTE, (int)minuteToIncrease);
        return c.getTime();
    }

    /**
     * This method generates a comparator to compare the key of two pairs of date based on 12 clock format.
     */
    private final Comparator<Pair<String, String>> compareByTime = (o1, o2) -> {
        String left = o1.getKey();
        String right = o2.getKey();

        String[] leftTimeSplit = left.split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);
        String[] rightTimeSplit = right.split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD);

        if(leftTimeSplit[1].equals(twelveHourTimeAMPostFix) && rightTimeSplit[1].equals(twelveHourTimeAMPostFix)){
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(twelveHourTimeFormatHourAndMinuteSeparator);
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(twelveHourTimeFormatHourAndMinuteSeparator);
            if(leftTimeSplitHourMinute[0].equals(twelveHourTimeFormatMaximumHour) && rightTimeSplitHourMinute[0].equals(twelveHourTimeFormatMaximumHour)) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if(leftTimeSplitHourMinute[0].equals(twelveHourTimeFormatMaximumHour)) {
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals(twelveHourTimeFormatMaximumHour)) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals(twelveHourTimeAMPostFix)) {
            return -1;
        } else if (rightTimeSplit[1].equals(twelveHourTimeAMPostFix)) {
            return 1;
        } else {
            return left.compareTo(right);
        }
    };

    /**
     * Executes the finding of earliest available block period inside the given TaskList objects with the given duration.
     * @param events The TaskList object for events
     * @param deadlines The TaskList object for deadlines
     * @param ui The Ui object to display the done task message
     * @param storage The Storage object to access file to load or save the tasks
     * @return This returns the method in the Ui object which returns the string to display freeTimes message
     * @throws Exception On date parsing error
     */
    public String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception {
        if(duration < DukeConstants.FIND_TIME_LOWER_BOUNDARY || duration > DukeConstants.FIND_TIME_UPPER_BOUNDARY) return ui.showFreeTimesInvalidDuration(duration.toString());
        mapDataMap(events);
        findFindTime();
        setOutput();
        return ui.showFreeTimes(message);
    }

    /**
     * This method compares current dateTime with database if current is between a task it takes the end time of the task.
     * @param events The list of event tasks in storage
     * @return The Reference Date selected
     */
    private Date getRefDate(TaskList events, Date date) throws ParseException {
        Comparator<Pair<Long,Assignment>> startTimeComparator = Comparator.comparing(Pair<Long,Assignment>:: getKey);

        String strDateDay = dateDayFormat.format(date);
        Date refDate = date;
        ArrayList<Pair<Long, Assignment>> extractedAssignmentsOnDate = new ArrayList<>();
        for(String module: events.getMap().keySet()) {
            HashMap<String, ArrayList<Assignment>> moduleValues = events.getMap().get(module);
            if(moduleValues.get(strDateDay) != null) {
                ArrayList<Assignment> data = moduleValues.get(strDateDay);
                for (Assignment task : data) {
                    String startAndEnd = task.getTime();
                    String[] spiltStartAndEnd = startAndEnd.split("to");
                    Date startDateTime = dateTimeFormat12.parse(strDateDay + " " + spiltStartAndEnd[0]);
                    Long inMillis = startDateTime.getTime();
                    extractedAssignmentsOnDate.add(new Pair<>(inMillis,task));
                }
            }
        }
        extractedAssignmentsOnDate.sort(startTimeComparator);


        for (Pair<Long,Assignment> task : extractedAssignmentsOnDate) {
            String startAndEnd = task.getValue().getTime();
            String[] spiltStartAndEnd = startAndEnd.split("to");
            Date startDateTime = dateTimeFormat12.parse(strDateDay + " " + spiltStartAndEnd[0]);
            Date endDateTime = dateTimeFormat12.parse(strDateDay + " " + spiltStartAndEnd[1]);

            if (startDateTime.equals(refDate)) refDate = endDateTime;
            else if((startDateTime.before(refDate) && endDateTime.after(refDate)) && endDateTime.after(refDate)) refDate = endDateTime;
        }
        return refDate;
    }


    /**
     * This method maps the list of events in that is in a module that is after reference data and time into dataMap.
     * @param refDate The date used at reference point
     * @param strCurrDateDay The date with day of the week based on the reference date
     * @param strCurrTime The time of the day based on the reference date
     * @param moduleValues The list of events that is under a module
     * @throws ParseException The error when parsing data error is found
     */
    private void mapDataMapSubMethod(Date refDate, String strCurrDateDay, String strCurrTime,HashMap<String, ArrayList<Assignment>> moduleValues) throws ParseException {
        for (String strDate : moduleValues.keySet()) {
            Date date = dateDayFormat.parse(strDate);
            date = increaseToTwoThreeFiveNine(date);
            ArrayList<Pair<String, String>> timeArray = new ArrayList<>();
            if (strDate.equals(strCurrDateDay)) timeArray.add(new Pair<>(strCurrTime, strCurrTime));
            if (date.after(refDate)) {
                ArrayList<Assignment> data = moduleValues.get(strDate);
                for (Assignment task : data) {
                    String startAndEnd = task.getTime();
                    String[] spiltStartAndEnd = startAndEnd.split("to");
                    Date startDateTime = dateTimeFormat12.parse(strDate + " " + spiltStartAndEnd[0]);
                    if(startDateTime.after(refDate)) timeArray.add(new Pair<>(spiltStartAndEnd[0].trim(), spiltStartAndEnd[1].trim()));
                }
                if(dataMap.containsKey(strDate)) timeArray = mergeTimeArray(dataMap.get(strDate), timeArray);
                timeArray.sort(compareByTime);
                dataMap.put(strDate, timeArray);
            }
        }
    }

    /**
     * This method maps the list of events that is after the reference date and time into dataMap.
     * @param events The list of event tasks in storage
     * @throws ParseException The error when the data provided in invalid
     */
    private void mapDataMap(TaskList events) throws ParseException {
        Date date = new Date();
        Date refDate = getRefDate(events, date);
        String strCurrDateDay = dateDayFormat.format(refDate);
        String strCurrTime = timeFormat12.format(refDate);

        for(String module: events.getMap().keySet()) {
            HashMap<String, ArrayList<Assignment>> moduleValues = events.getMap().get(module);
            mapDataMapSubMethod(refDate, strCurrDateDay, strCurrTime, moduleValues);
        }
    }

    /**
     * This method merges to arrayList and removes duplicated values.
     * @param left The list of start and end times
     * @param right The list of start and end times
     * @return The combines list
     */
    private ArrayList<Pair<String, String>> mergeTimeArray(ArrayList<Pair<String, String>> left, ArrayList<Pair<String, String>> right) {
        for(Pair<String, String> c: right) {
            if(!left.contains(c)) left.add(c);
        }
        return left;
    }

    /**
     * This method returns true is command completed.
     * @return True if freeTimeData has 5 options
     */
    private boolean checkFreeTimeOptions () {
        return freeTimeData.size() == options;
    }

    /*
    This checks if there are 5 options generated after going through the data in the event list
     */
    private void generateFreeTime() throws ParseException {
        if (checkFreeTimeOptions()) return;

        int size = freeTimeData.size();
        Pair<Date, Date> last;
        if(size == 0) {
            Date currDate = new Date();
            String strCurrDateDay = dateDayFormat.format(currDate) + " " + twelveHourTimeFormatStartTime;
            currDate = dateTimeFormat12.parse(strCurrDateDay);
            currDate = increaseZeroSevenZeroZero(currDate);
            currDate = increaseDateTime(currDate, 24);
            last = new Pair<>(currDate, increaseDateTime(currDate, duration));
            freeTimeData.add(last);
            size = 1;
        }
        else {
            last = freeTimeData.get(size-1);
        }
        generateFreeTimeUntilRequiredOptions(size, last);
    }

    /**
     * This method extends generateFreeTime generates free time slot by an hour difference
     * @param size The size of the freeTimeData
     * @param last The last Pair found in freeTimeData
     * @throws ParseException The error when parsing data error is found
     */
    private void generateFreeTimeUntilRequiredOptions(Integer size, Pair<Date, Date> last) throws ParseException {
        if (last == null) return;
        for(int i = size; i < options; i++){
            Date tempStart = last.getKey();
            Date tempEnd = last.getValue();
            String currDate = dateDayFormat.format(tempStart) + " " + twelveHourTimeFormatStartTime;
            Date dateBoundary = dateTimeFormat12.parse(currDate);
            Date dateUpperBoundary = increaseToTwoThreeFiveNine(dateBoundary);
            Date dateLowerBoundary = increaseZeroSevenZeroZero(dateBoundary);

            Pair<Date, Date> newFreeTime = null;
            Date dateTimeStart = increaseDateTime(tempStart, 1);
            Date dateTimeEnd = increaseDateTime(tempEnd, 1);

            if(dateTimeStart.after(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
            } else if(dateTimeStart.before(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                dateTimeStart = dateLowerBoundary;
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                if(dateTimeEnd.before(dateUpperBoundary)) {
                    newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
                }
            }
            else if (dateTimeEnd.after(dateUpperBoundary)){
                dateTimeStart = increaseDateTime(dateLowerBoundary, 24);
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                newFreeTime = new Pair<>(dateTimeStart, dateTimeEnd);
            }
            if(newFreeTime != null) {
                last = newFreeTime;
            }
            freeTimeData.add(last);
        }
    }

    /**
     * This generates find free times block based on time blocks in the database and adds if block is within boundary.
     * @param startAndEndTimes The start and end times of the event
     * @param i The index of the event in the date
     * @param date The date of the event
     * @throws ParseException The error when the data provided in invalid
     */
    private Integer addIfWithinTimeBoundary(ArrayList<Pair<String, String >> startAndEndTimes, Integer i, String date) throws ParseException {
        Date dateBoundary = dateTimeFormat12.parse(date + " " + twelveHourTimeFormatStartTime);
        Date dateUpperBoundary = increaseToTwoThreeFiveNine(dateBoundary);
        Date dateLowerBoundary = increaseZeroSevenZeroZero(dateBoundary);

        if(i < startAndEndTimes.size() - 1) {
            String dateTime = date + " " + startAndEndTimes.get(i).getValue();
            String dateTimeNextEvent = date + " " + startAndEndTimes.get(i+1).getKey();

            Date dateTimeStart = dateTimeFormat12.parse(dateTime);
            dateTimeStart = roundByHalfHourMark(dateTimeStart);
            Date dateNextEvent = dateTimeFormat12.parse(dateTimeNextEvent);
            Date dateTimeEnd = increaseDateTime(dateTimeStart, duration);
            if(dateTimeEnd.after(dateUpperBoundary)) {
                i = (startAndEndTimes.size() - 1);
            } else if(dateTimeEnd.before(dateNextEvent)) {
                if(dateTimeStart.before(dateLowerBoundary)) dateTimeStart = dateLowerBoundary;
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                if(dateTimeEnd.before(dateUpperBoundary)) freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
                else i = (startAndEndTimes.size() - 1);
            }
        } else if(i == (startAndEndTimes.size() - 1)) {
            String dateTime = date + " " + startAndEndTimes.get(i).getValue();
            Date dateTimeStart = dateTimeFormat12.parse(dateTime);
            dateTimeStart = roundByHalfHourMark(dateTimeStart);

            Date dateTimeEnd = increaseDateTime(dateTimeStart, duration);
            if(dateTimeStart.after(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
            } else if(dateTimeStart.before(dateLowerBoundary) && dateTimeEnd.before(dateUpperBoundary)) {
                dateTimeStart = dateLowerBoundary;
                dateTimeEnd = increaseDateTime(dateTimeStart, duration);
                if(dateTimeEnd.before(dateUpperBoundary)) freeTimeData.add(new Pair<>(dateTimeStart, dateTimeEnd));
            } else {//dateTimeEnd.after(dateBoundary)
                String nextKey = dataMap.higherKey(date);
                if(nextKey == null) {
                    Date nextDay = dateTimeFormat12.parse(date + " " + twelveHourTimeFormatStartTime);
                    nextDay = increaseDateTime(nextDay, 24);
                    Date nextDayStartTime = increaseDateTime(nextDay, 7);
                    Date nextDayEndTime = increaseDateTime(nextDayStartTime, duration);
                    freeTimeData.add(new Pair<>(nextDayStartTime, nextDayEndTime));
                } else {
                    ArrayList<Pair<String, String >> nextDayStartAndEndTimes = dataMap.get(nextKey);
                    String nextDateTime = nextKey + " " + nextDayStartAndEndTimes.get(0).getKey(); //Just need to check first item of next day start time
                    Date nextDateTimeStart = dateTimeFormat12.parse(nextDateTime);
                    dateLowerBoundary = increaseDateTime(dateLowerBoundary, 24); //0700 since look at next day boundary must be increase by a day
                    Date dateLowerBoundaryPlusDuration = increaseDateTime(dateLowerBoundary, duration); //2100

                    if(dateLowerBoundary.before(nextDateTimeStart) && dateLowerBoundaryPlusDuration.before(nextDateTimeStart)) {
                        freeTimeData.add(new Pair<>(dateLowerBoundary, dateLowerBoundaryPlusDuration));
                    }
                }
            }
        }
        return i;
    }

    /**
     * This method Finds the best time available with the list of events in dataMap
     * @throws ParseException The error when the data provided in invalid
     */
    private void findFindTime() throws ParseException {
        for (String date: dataMap.keySet()) {
            ArrayList<Pair<String, String >> startAndEndTimes = dataMap.get(date);
            for(int i = 0; i < startAndEndTimes.size(); i++){
                i = addIfWithinTimeBoundary(startAndEndTimes, i, date);
                if(checkFreeTimeOptions()) return;
            }
        }
        generateFreeTime();
    }

    private static final ArrayList<Pair<String, String>> compiledFreeTimes = new ArrayList<>();

    /**
     * This method generates the output to be shown
     */
    private void setOutput(){
        compiledFreeTimes.clear();
        Comparator<Pair<Long,Pair<String,String>>> startTimeComparator = Comparator.comparing(Pair<Long,Pair<String,String>>:: getKey);
        ArrayList<Pair<Long, Pair<String,String>>> sortCompiledFreeTimes = new ArrayList<>();
        for (int i = 0; i < freeTimeData.size(); i++) {
            String compiledFreeTimeToShow;
            String compiledFreeTimeCommand;

            compiledFreeTimeToShow = dateTimeFormat12.format(freeTimeData.get(i).getKey()) + " until " + timeFormat12.format(freeTimeData.get(i).getValue());

            String dateTime = dateTimeFormat24.format(freeTimeData.get(i).getKey());
            String[] spiltDateTime = dateTime.split(DukeConstants.STRING_SPACE_SPLIT_KEYWORD, 3);
            compiledFreeTimeCommand = DukeConstants.EVENT_DATE_DESCRIPTION_SPLIT_KEYWORD + " " + spiltDateTime[1]
                    + " " + DukeConstants.EVENT_DATE_SPLIT_KEYWORD+ " " + spiltDateTime[2] + " "
                    + DukeConstants.EVENT_TIME_SPLIT_KEYWORD + " " + timeFormat24.format(freeTimeData.get(i).getValue());

            //TODO: Sorted output method 1 using Pair<Long, Pair<String, String> > complicated to trace (10 lines)
            Long startDateTimeInMilliSeconds = (freeTimeData.get(i).getKey()).getTime();
            Pair<String, String> compliedData = new Pair<>(compiledFreeTimeToShow, compiledFreeTimeCommand);
            sortCompiledFreeTimes.add(new Pair<>(startDateTimeInMilliSeconds,compliedData));
        }
        sortCompiledFreeTimes.sort(startTimeComparator);

        message = new String();
        for(int i = 0; i < sortCompiledFreeTimes.size(); i++){
            compiledFreeTimes.add(sortCompiledFreeTimes.get(i).getValue());
            message += ((i+1) +". " + sortCompiledFreeTimes.get(i).getValue().getKey()) + "\n";
        }
    }

    public static ArrayList<Pair<String, String>> getCompiledFreeTimesList() {
        return compiledFreeTimes;
    }
}