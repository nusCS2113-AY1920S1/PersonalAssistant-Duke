package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;
import compal.commons.CompalUtils;
import compal.model.tasks.Task;
import compal.model.tasks.Event;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Below are the required import statements in case IntelliJ changes it back to
 * import java.util.* again. This helps to pass codacy static analysis. Cheers.
 * import java.util.Date;
 * import java.util.ArrayList;
 * import java.util.Arrays;
 */

public class NusmodsCommand extends Command {
    /**
     * Public Constants.
     */
    public static final String MESSAGE_USAGE = "NUSMODS\n\t"
            + "Format: nusmods /link NUSMODS_SHARE_LINK /semstart WEEK_1_MONDAY_DATE\n\n\t"
            + "You can switch the order of any two blocks (a block starts with \"/\" and ends by the next block)\n\t"
            + "content in CAP: need to be fulfilled by the user\n\t"
            + "For WEEK_1_MONDAY_DATE: dd/mm/yyyy is the date format. e.g. 12/08/2019 for AY1920S1\n\t"
            + "This command will add an event for each lesson slot in your NUSMODS timetable.\n"
            + "Examples:\n\t"
            + "nusmods /link https://nusmods.com/timetable/sem-1/share?CS2100=LAB:06,TUT:08,LEC:1 "
            + "/semstart 12/08/2019\n\n";
    public static final String MESSAGE_INVALID_NUSMODS_LINK = "Invalid NUSMODS link!";
    public static final String MESSAGE_INVALID_SEM_START_DATE = "Please enter the date of the Monday of Week 1 of "
            + "your targeted semester.";
    public static final String MESSAGE_INVALID_MODULE = "We are unable to extract information from NUSMODS on this "
            + "module: ";
    public static final String NUSMODS_URL_PREFIX = "https://api.nusmods.com/v2/";
    public static final String NUSMODS_URL_MIDDLE = "/modules/";
    public static final String NUSMODS_URL_JSON_TAIL = ".json";
    public static final String MESSAGE_GREETING = "\nThe following tasks were added: \n";

    /**
     * Private Constants.
     */
    private static final String TOKEN_MODULE_CODE = "="; // CS2106=TUT:08,LAB:04,LEC:1
    private static final String TOKEN_LESSON_TYPE = ",";
    private static final String TOKEN_LESSON_NUM = ":";
    private static final String HYPHEN = "-";
    private static final String TOKEN_TUT = "TUT";
    private static final String TOKEN_LEC = "LEC";
    private static final String TOKEN_LAB = "LAB";
    private static final String TOKEN_REC = "REC";
    private static final int MODULE_CODE_LESSON_DETAILS_LENGTH = 2;
    private static final int LESSON_TYPE_NUMBER_LENGTH = 2;
    private static final int FIRST_IDX = 0;
    private static final int SECOND_IDX = 1;
    private static final int JANUARY = 0;
    private static final int AUGUST = 7;
    private static final int SEM1 = 1;
    private static final int SEM2 = 2;
    private static final int WEEK_1 = 1;
    private static final int WEEK_6 = 6;
    private static final int DEFAULT_WEEK_INTERVAL = 7;
    private static final int MON_IDX = 1;
    private static final int TUE_IDX = 2;
    private static final int WED_IDX = 3;
    private static final int THU_IDX = 4;
    private static final int FRI_IDX = 5;
    private static final int SAT_IDX = 6;
    private static final int SUN_IDX = 7;
    private static final String MON = "Monday";
    private static final String TUE = "Tuesday";
    private static final String WED = "Wednesday";
    private static final String THU = "Thursday";
    private static final String FRI = "Friday";
    private static final String SAT = "Saturday";
    private static final String SUN = "Sunday";
    private static final String JSONTOKEN_SEMESTER_DATA = "semesterData";
    private static final String JSONTOKEN_SEMESTER = "semester";
    private static final String JSONTOKEN_TIMETABLE = "timetable";
    private static final String JSONTOKEN_CLASSNO = "classNo";
    private static final String JSONTOKEN_LESSONTYPE = "lessonType";
    private static final String JSONTOKEN_LESSONWEEK = "weeks";
    private static final String JSONTOKEN_LESSONDAY = "day";
    private static final String JSONTOKEN_LESSONSTARTTIME = "startTime";
    private static final String JSONTOKEN_LESSONENDTIME = "endTime";
    private static final String JSON_TOKEN_TUT = "Tutorial";
    private static final String JSON_TOKEN_LAB = "Laboratory";
    private static final String JSON_TOKEN_REC = "Recitation";
    private static final String JSON_TOKEN_LEC = "Lecture";

    /**
     * Private Attributes.
     */
    private String semStartDateString;
    private Date semStartDate;
    private ArrayList<String> moduleDetailsList;
    private int semesterNumber;
    private String academicYear;

    /**
     * Constructor for NusmodsCommand.
     *
     * @param semStartDateString Date of Monday of Week 1.
     * @param moduleDetailsList Module Details parsed from the NUSMODS URL.
     */
    public NusmodsCommand(String semStartDateString, ArrayList<String> moduleDetailsList) {
        this.semStartDateString = semStartDateString;
        this.moduleDetailsList = moduleDetailsList;
        this.semStartDate = CompalUtils.stringToDate(semStartDateString);
    }

    /**
     * Set the academic year and semester number.
     *
     * @param semStartMonth Month taken from the date of Monday of Week 1.
     * @param semStartYear Year taken from the date of Monday of Week 1.
     * @throws CommandException If the date given is not in the starting month of either Sem 1 or Sem 2.
     */
    public void setAcadYearSemNum(int semStartMonth, int semStartYear) throws CommandException {
        String semStartYearString = String.valueOf(semStartYear);
        if (semStartMonth == JANUARY) {
            semesterNumber = SEM2;
            String acadYearSecondHalf = String.valueOf(semStartYear + 1);
            this.academicYear = semStartYearString + HYPHEN + acadYearSecondHalf;
        } else if (semStartMonth == AUGUST) {
            semesterNumber = SEM1;
            String acadYearFirstHalf = String.valueOf(semStartYear - 1);
            this.academicYear = acadYearFirstHalf + HYPHEN + semStartYearString;
        } else {
            throw new CommandException(MESSAGE_INVALID_SEM_START_DATE);
        }
    }

    @Override
    public CommandResult commandExecute(TaskList tasklist) throws CommandException {
        int semStartMonth = CompalUtils.getMonth(semStartDate);
        int semStartYear = CompalUtils.getYear(semStartDate);
        setAcadYearSemNum(semStartMonth, semStartYear);

        String resultString = new String();
        for (String moduleDetails : moduleDetailsList) {

            // {   CS2106, [TUT:08,LAB:04,LEC:1]   }
            String[] parsedModuleList = moduleDetails.split(TOKEN_MODULE_CODE);
            if (parsedModuleList.length != MODULE_CODE_LESSON_DETAILS_LENGTH) {
                throw new CommandException(MESSAGE_INVALID_NUSMODS_LINK);
            }
            String moduleCode = parsedModuleList[FIRST_IDX];

            // {   TUT:08, LAB:04, LEC:1   }
            ArrayList<String> parsedLessonDetailsList = new ArrayList<>(
                    Arrays.asList(parsedModuleList[SECOND_IDX].split(TOKEN_LESSON_TYPE)));

            // get json from NUSMODS for this particular module (e.g. CS2106) and semester
            String nusmodsUrlString = getNusmodsUrlString(moduleCode);
            JSONObject moduleJson;
            try {
                moduleJson = CompalUtils.readJsonFromNusmods(nusmodsUrlString);
            } catch (IOException e) {
                // throw new CommandException(MESSAGE_INVALID_MODULE + moduleCode);
                throw new CommandException(e.getMessage());
            }
            JSONArray semesterDataArray = moduleJson.getJSONArray(JSONTOKEN_SEMESTER_DATA);
            JSONObject semesterData;
            JSONArray timetable = new JSONArray();
            for (int count = 0; count < semesterDataArray.length(); count++) {
                semesterData = semesterDataArray.getJSONObject(count);
                if ((int)semesterData.get(JSONTOKEN_SEMESTER) == semesterNumber) {
                    timetable = semesterData.getJSONArray(JSONTOKEN_TIMETABLE);
                    break;
                }
            }
            // unable to insert feature for examDate.
            // examDate is not present in schemas of modules without exams. For those that have examDate,
            // examDuration is given but the start time of the exam is not given. It is possible to make
            // examDate a deadline but does not make sense because exams are clearly fixed duration events.

            // {   TUT, 08   } for each lessonDetail, of course
            ArrayList<String[]> lessonPairList = new ArrayList<>();
            for (String lessonDetail : parsedLessonDetailsList) {
                String[] parsedLessonPair = lessonDetail.split(TOKEN_LESSON_NUM);
                if (parsedLessonPair.length != LESSON_TYPE_NUMBER_LENGTH) {
                    throw new CommandException(MESSAGE_INVALID_NUSMODS_LINK);
                }
                parsedLessonPair[FIRST_IDX] = convertUrlLessonCodeToJsonToken(parsedLessonPair[FIRST_IDX]);
                lessonPairList.add(parsedLessonPair);
                // String lessonType = parsedLessonList[FIRST_IDX];
                // String lessonNum = parsedLessonList[SECOND_IDX];
                // ArrayList<ArrayList<Object>> lessonDetailsList = searchJson(timetable, lessonType, lessonNum);
                // resultString += createAndAddEvents(tasklist, lessonDetailsList, moduleCode);
            }
            ArrayList<ArrayList<Object>> lessonDetailsList = searchJson(timetable, lessonPairList);
            resultString += createAndAddEvents(tasklist, lessonDetailsList, moduleCode);
        }
        return new CommandResult(resultString, true);
    }

    /**
     * Generates the JSON server url to query.
     *
     * @param moduleCode The module code for the module to query.
     * @return JSON server url.
     */
    public String getNusmodsUrlString(String moduleCode) {
        return NUSMODS_URL_PREFIX + academicYear + NUSMODS_URL_MIDDLE + moduleCode + NUSMODS_URL_JSON_TAIL;
    }

    /**
     * Converts the lesson type provided in the NUSMODS URL to the equivalent JSON key, so that it
     * can be used for comparison.
     *
     * @param urlLessonCode Lesson type provided in the NUSMODS URL.
     * @return return The equivalent JSON key.
     */
    public String convertUrlLessonCodeToJsonToken(String urlLessonCode) throws CommandException {
        switch (urlLessonCode) {
        case TOKEN_LAB:
            return JSON_TOKEN_LAB;
        case TOKEN_LEC:
            return JSON_TOKEN_LEC;
        case TOKEN_TUT:
            return JSON_TOKEN_TUT;
        case TOKEN_REC:
            return JSON_TOKEN_REC;
        default:
            throw new CommandException(MESSAGE_INVALID_NUSMODS_LINK);
        }
    }

    /**
     * Searches through the timetable JSON for the details relevant to the particular lesson.
     *
     * @param timetableJson Entire timetable for module in JSON.
     * @param lessonPairList An ArrayList of String Arrays. Each String array contains (converted) lesson type and
     *                       lesson number provided from parsing of NUSMODS link. This function will make one pass
     *                       through the timetable json, looking for a lesson json that matches the lesson type and
     *                       lesson number of a String array. This is more efficient than making a pass through the
     *                       timetable json for each String array (previous implementation).
     * @return An Arraylist of Arraylist of Objects.
     *      Inner Arraylist of Objects contain all the weeks for each lesson, the day
     *      that the lesson is held on, the start time and end time for that lesson slot, lessonType and lessonNum.
     *      Outer Arraylist to store multiple slots for certain lessons. E.g. lectures that occur twice every week
     *      will have 2 of the inner Arraylist of Objects.
     * @throws CommandException Invalid NUSMODS link provided.
     */
    public ArrayList<ArrayList<Object>> searchJson(JSONArray timetableJson, ArrayList<String[]> lessonPairList)
            throws CommandException {
        JSONObject lessonDetails;
        ArrayList<ArrayList<Object>> lessonDetailsJsonList = new ArrayList<>();
        for (int count = 0; count < timetableJson.length(); count++) {
            lessonDetails = timetableJson.getJSONObject(count);
            String jsonLessonNum = lessonDetails.getString(JSONTOKEN_CLASSNO);
            String jsonLessonType = lessonDetails.getString(JSONTOKEN_LESSONTYPE);
            if (isJsonLessonMatched(lessonPairList, jsonLessonNum, jsonLessonType)) {
                ArrayList<Object> slotDetailsList = new ArrayList<>();
                slotDetailsList.add(lessonDetails.getJSONArray(JSONTOKEN_LESSONWEEK)); // 0
                slotDetailsList.add(lessonDetails.getString(JSONTOKEN_LESSONDAY)); // 1
                slotDetailsList.add(lessonDetails.getString(JSONTOKEN_LESSONSTARTTIME)); // 2
                slotDetailsList.add(lessonDetails.getString(JSONTOKEN_LESSONENDTIME)); // 3
                slotDetailsList.add(jsonLessonType); // 4
                slotDetailsList.add(jsonLessonNum); // 5
                lessonDetailsJsonList.add(slotDetailsList);
                // do not break here. May have multiple lecture slots in one week, stored as separate JSONObjects.
            }
        }
        return lessonDetailsJsonList;
    }

    /**
     * Checks through the lesson pair list to find a pair that matches the lesson number and lesson type
     * provided by the Json from NUSMODS.
     *
     * @param lessonPairList ArrayList of lesson pairs. Each pair contains the lesson type and lesson number. Provided
     *                       by the parsing of the NUSMODS link.
     * @param jsonLessonNum Lesson number provided from the parsing of NUSMODS json.
     * @param jsonLessonType Lesson Type provided from the parsing of NUSMODS json.
     * @return true if there is a matching lesson pair in the list, false if not.
     */
    public boolean isJsonLessonMatched(ArrayList<String[]> lessonPairList, String jsonLessonNum,
                                       String jsonLessonType) {
        for (String[] lessonPair : lessonPairList) {
            if (lessonPair[FIRST_IDX].equals(jsonLessonType) && lessonPair[SECOND_IDX].equals(jsonLessonNum)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create Event objects for each lesson, add them to tasklist and return string for CommandResult.
     *
     * @param taskList The tasklist to add lesson Events to.
     * @param lessonDetailsJsonList The list of lessonDetails. (May contain details for more than 1 slot)
     * @param moduleCode The module code. Required for description of Event.
     * @return String to be displayed, send to CommandResult.
     * @throws CommandException Invalid NUSMODS link.
     */
    public String createAndAddEvents(TaskList taskList, ArrayList<ArrayList<Object>> lessonDetailsJsonList,
                                     String moduleCode) throws CommandException {
        String finalList = "";
        for (ArrayList<Object> slotDetailsList : lessonDetailsJsonList) {
            String lessonDay = (String) slotDetailsList.get(1);
            int daysToIncrementForEachSlot = daysDiffFromMon(lessonDay);
            String lessonStartTime = (String) slotDetailsList.get(2);
            String lessonEndTime = (String) slotDetailsList.get(3);
            JSONArray lessonWeeks = (JSONArray) slotDetailsList.get(0);
            Date firstLessonDate = CompalUtils.incrementDateByDays(this.semStartDate, daysToIncrementForEachSlot);
            String description = moduleCode + " " + slotDetailsList.get(4) + " " + slotDetailsList.get(5);
            Task.Priority defaultLessonPriority = Task.Priority.low; // area of improvement in the future
            for (int weekIdx = 0; weekIdx < lessonWeeks.length(); weekIdx++) {
                int actualWeekNum = lessonWeeks.getInt(weekIdx);
                if (actualWeekNum > WEEK_6) { // Increment by one more week for weeks after Recess Week
                    actualWeekNum++;
                }
                Date currDate = CompalUtils.incrementDateByDays(firstLessonDate,
                        (actualWeekNum - WEEK_1) * DEFAULT_WEEK_INTERVAL);
                String currDateString = CompalUtils.dateToString(currDate);
                Event indivSlot = new Event(description, defaultLessonPriority, currDateString, currDateString,
                        lessonStartTime, lessonEndTime);
                taskList.addTask(indivSlot);
                finalList = finalList.concat(MESSAGE_GREETING.concat(indivSlot.toString() + "\n"));
            }
        }
        return finalList;
    }

    /**
     * Counts number of days between the current day and Monday.
     * Used to find out the number of days to increment the start date of each week.
     *
     * @param currDay The current day, extracted from the lesson details.
     * @return The number of days between the current day and Monday.
     */
    public int daysDiffFromMon(String currDay) throws CommandException {
        switch (currDay) {
        case MON:
            return MON_IDX - MON_IDX;
        case TUE:
            return TUE_IDX - MON_IDX;
        case WED:
            return WED_IDX - MON_IDX;
        case THU:
            return THU_IDX - MON_IDX;
        case FRI:
            return FRI_IDX - MON_IDX;
        case SAT:
            return SAT_IDX - MON_IDX;
        case SUN:
            return SUN_IDX - MON_IDX;
        default:
            throw new CommandException(MESSAGE_INVALID_NUSMODS_LINK);
        }
    }
}
