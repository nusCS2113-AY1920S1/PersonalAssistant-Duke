import mistermusik.commons.Goal;
import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.ToDo;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Recital;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.commons.events.formatting.EventDate;
import mistermusik.logic.ClashException;
import mistermusik.logic.EndBeforeStartException;
import mistermusik.logic.EventList;
import mistermusik.ui.CalendarView;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {




    //@@author yenpeichih
    @Test
    public void goalsListAddTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event practiceTest1 = new Practice("band rehearsal", "12-12-2019 1800", "12-12-2019 2100");
        testList.addEvent(practiceTest1);
        Goal practiceGoal1 = new Goal("Finish Flight of the Bumblebee");
        testList.getEvent(0).addGoal(practiceGoal1);
        int goalIndex = 1;
        String testOutput = "";
        for (Goal goalObject : practiceTest1.getGoalList()) {
            testOutput += goalIndex + ". " + goalObject.getGoal() + " - " + "Achieved: " + goalObject.getStatus();
            goalIndex += 1;
        }
        boolean isGoalFound = !testOutput.isEmpty();
        assertTrue(isGoalFound);
    }

    @Test
    public void goalsListEditTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("theory lesson 1", "19-12-2019 1800", "19-12-2019 2100");
        testList.addEvent(lessonTest1);
        Goal lessonGoal1 = new Goal("Finish Flight of the Bumblebee");
        testList.getEvent(0).addGoal(lessonGoal1);
        Goal lessonGoal2 = new Goal("Finish Symphony No.9");
        testList.getEvent(0).editGoalList(lessonGoal2, 0);
        boolean isUpdated = false;
        if (testList.getEvent(0).getGoalList().get(0).getGoal().equals("Finish Symphony No.9")) {
            isUpdated = true;
        }
        assertTrue(isUpdated);
    }

    @Test
    public void goalsListDeleteTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("theory lesson 1", "19-12-2019 1800", "19-12-2019 2100");
        testList.addEvent(lessonTest1);
        Goal lessonGoal1 = new Goal("Finish Flight of the Bumblebee");
        testList.getEvent(0).addGoal(lessonGoal1);
        testList.getEvent(0).removeGoal(0);
        boolean isDeleted = false;
        if (testList.getEvent(0).getGoalList().isEmpty()) {
            isDeleted = true;
        }
        assertTrue(isDeleted);
    }

    @Test
    public void goalsListAchievedTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("theory lesson 1", "19-12-2019 1800", "19-12-2019 2100");
        testList.addEvent(lessonTest1);
        Goal lessonGoal1 = new Goal("Finish Flight of the Bumblebee");
        testList.getEvent(0).addGoal(lessonGoal1);
        testList.getEvent(0).getGoalObject(0).setAchieved();
        assertEquals("Yes", testList.getEvent(0).getGoalObject(0).getStatus());
        assertNotEquals("No", testList.getEvent(0).getGoalObject(0).getStatus());
    }

    @Test
    public void goalsListViewTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("theory lesson 1", "19-12-2019 1800", "19-12-2019 2100");
        testList.addEvent(lessonTest1);
        Goal lessonGoal1 = new Goal("Finish Flight of the Bumblebee");
        testList.getEvent(0).addGoal(lessonGoal1);
        String testPrint = testList.getEvent(0).getGoalObject(0).getGoal();
        assertEquals("Finish Flight of the Bumblebee", testPrint);
    }

    @Test
    public void pastEventIndexTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        EventDate currentDate1 = new EventDate("7-11-2019 1300");
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event practiceTest1 = new Practice("full band rehearsal", "12-12-2019 1800", "12-12-2019 2100");
        testList.addEvent(practiceTest1);
        Event practiceTest2 = new Practice("individual practice", "3-11-2019 1800", "3-11-2019 1900");
        testList.addEvent(practiceTest2);
        Event practiceTest3 = new Practice("sectionals practice", "4-11-2019 1400", "4-11-2019 1600");
        testList.addEvent(practiceTest3);
        Event practiceTest4 = new Practice("full band sound check", "19-11-2019 1600", "19-11-2019 1900");
        testList.addEvent(practiceTest4);
        testList.findNextEventAndSetBoolean(currentDate1.getEventJavaDate());
        assertEquals(3, testList.currentDateIndex);
    }

    @Test
    public void pastEventUnachievedGoalsListTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        EventDate currentDate1 = new EventDate("7-11-2019 1300");
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event practiceTest1 = new Practice("full band rehearsal", "12-12-2019 1800", "12-12-2019 2100");
        testList.addEvent(practiceTest1);
        Event practiceTest2 = new Practice("individual practice", "3-11-2019 1800", "3-11-2019 1900");
        testList.addEvent(practiceTest2);
        Event practiceTest3 = new Practice("sectionals practice", "4-11-2019 1400", "4-11-2019 1600");
        testList.addEvent(practiceTest3);
        Goal pastGoal1 = new Goal("finish chapter 2");
        testList.getEvent(0).addGoal(pastGoal1);
        Goal pastGoal2 = new Goal("finish chapter 6");
        testList.getEvent(1).addGoal(pastGoal2);
        Goal futureGoal1 = new Goal("finish chapter 9");
        testList.getEvent(2).addGoal(futureGoal1);
        testList.findNextEventAndSetBoolean(currentDate1.getEventJavaDate());
        assertTrue(testList.gotPastUnachieved);
        assertNotEquals("You do not have any unachieved goals for past events! Yay!" + "\n", testList.getPastEventsWithUnachievedGoals());
    }

    @Test
    public void noPastUnachievedGoalsTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        EventDate currentDate1 = new EventDate("1-11-2019 1300");
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event practiceTest1 = new Practice("full band rehearsal", "12-12-2019 1800", "12-12-2019 2100");
        testList.addEvent(practiceTest1);
        Event practiceTest2 = new Practice("individual practice", "3-11-2019 1800", "3-11-2019 1900");
        testList.addEvent(practiceTest2);
        Event practiceTest3 = new Practice("sectionals practice", "4-11-2019 1400", "4-11-2019 1600");
        testList.addEvent(practiceTest3);
        Goal pastGoal1 = new Goal("finish chapter 2");
        testList.getEvent(0).addGoal(pastGoal1);
        Goal pastGoal2 = new Goal("finish chapter 6");
        testList.getEvent(1).addGoal(pastGoal2);
        Goal futureGoal1 = new Goal("finish chapter 9");
        testList.getEvent(2).addGoal(futureGoal1);
        testList.findNextEventAndSetBoolean(currentDate1.getEventJavaDate());
        assertFalse(testList.gotPastUnachieved);
        assertEquals("\n" + "Below lists all the unachieved goal for past events. Please be reminded to add them to the future events." + "\n" + "You do not have any unachieved goals for past events! Yay!" + "\n", testList.getPastEventsWithUnachievedGoals());
    }

    @Test
    public void viewScheduleTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        boolean isEventsFound;
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event toDoTest = new ToDo("cheese", "19-09-2019");
        testList.addNewTodo(toDoTest);
        Event practiceTest1 = new Practice("individual practice", "19-09-2019 1900", "19-09-2019 2000");
        testList.addEvent(practiceTest1);
        Event practiceTest2 = new Practice("sectional practice", "19-09-2019 2100", "19-09-2019 2200");
        testList.addEvent(practiceTest2);
        Event practiceTest3 = new Practice("full band rehearsal", "19-09-2020 1000", "19-09-2020 1100");
        testList.addEvent(practiceTest3);
        Event eventTest = new Recital("band recital", "20-09-2019 2100", "20-09-2019 2200");
        testList.addEvent(eventTest);
        String dateToView = "19-09-2019";
        ArrayList<String> eventsOnASpecificDate = new ArrayList<>();
        EventDate findDate = new EventDate(dateToView);
        for (int i = 0; i < testList.getEventArrayList().size(); i += 1) {
            Event viewEvent = testList.getEvent(i);
            String eventStringWithIndex = "";
            if (viewEvent.toString().contains(findDate.getFormattedDateString())) {
                eventStringWithIndex += i + 1 + ". " + viewEvent.toString();
                eventsOnASpecificDate.add(eventStringWithIndex);
            }
        }
        if (eventsOnASpecificDate.isEmpty()) {
            isEventsFound = false;
        } else {
            isEventsFound = true;
        }
        assertEquals(true, isEventsFound);
    }

    //@@author ZhangYihanNus
    @Test
    public void checkFreeDaysTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/15-11-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/07-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/08-11-2019 1600/04-12-2019 1930/5";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/09-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/10-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/11-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/12-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/13-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/14-11-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);

        EventDate dayToCheckIfFreeObject = new EventDate(new Date());
        dayToCheckIfFreeObject.addDaysAndSetMidnight(0);
        Queue<String> daysFree = new LinkedList<>();
        int nextDays = 1;
        while (daysFree.size() <= 3) {
            boolean isFree = true;
            for (Event viewEvent : eventListTest.getEventArrayList()) {
                if (viewEvent.getStartDate().getFormattedDateString().substring(0, 16).equals(dayToCheckIfFreeObject.getFormattedDateString())) {
                    isFree = false;
                    break;
                }
            }
            if (isFree) {
                daysFree.add(dayToCheckIfFreeObject.getFormattedDateString());
            }
            dayToCheckIfFreeObject.addDaysAndSetMidnight(1);
            nextDays++;
        }

        boolean checkFreeFlag = true;
        EventDate eventDateCompare = new EventDate(new Date());
        eventDateCompare.addDaysAndSetMidnight(0);
        if (!daysFree.poll().equals("Sat, 16 Nov 2019")) {
            checkFreeFlag = false;
        }
        if (!daysFree.poll().equals("Sun, 17 Nov 2019")) {
            checkFreeFlag = false;
        }
        if (!daysFree.poll().equals("Mon, 18 Nov 2019")) {
            checkFreeFlag = false;
        }
        assertEquals(true, checkFreeFlag);
    }

    @Test
    public void checklistAddTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event practiceTest1 = new Practice("rehearsal No1", "10-12-2019 1800", "10-12-2019 2100");
        testList.addEvent(practiceTest1);

        testList.getEvent(0).addChecklist("Bring glasses");
        testList.getEvent(0).addChecklist("Edit DG");
        int checklistItemIndex = 1;
        String testOutput = "";
        for (String checklistItem : practiceTest1.getChecklist()) {
            testOutput += checklistItemIndex + ". " + checklistItem;
            checklistItemIndex += 1;
        }
        boolean isChecklistFound = !testOutput.isEmpty();
        assertTrue(isChecklistFound);
    }

    @Test
    public void checklistEditTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("CG2271", "10-12-2019 1800", "10-12-2019 2100");
        testList.addEvent(lessonTest1);

        String itemString1 = "bring glasses";
        testList.getEvent(0).addChecklist(itemString1);
        String itemString2 = "bring a pair of glasses";
        testList.getEvent(0).editChecklist(0, itemString2);
        boolean isUpdated = false;
        if (testList.getEvent(0).getChecklist().get(0).equals(itemString2)) {
            isUpdated = true;
        }
        assertTrue(isUpdated);
    }

    @Test
    public void checklistDeleteTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("CG2271", "10-12-2019 1800", "10-12-2019 2100");
        testList.addEvent(lessonTest1);

        String itemString = "bring glasses";
        testList.getEvent(0).addChecklist(itemString);
        testList.getEvent(0).deleteChecklist(0);
        boolean isDeleted = false;
        if(testList.getEvent(0).getChecklist().isEmpty()) {
            isDeleted = true;
        }
        assertTrue(isDeleted);
    }

    @Test
    public void checklistViewTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("CG2271", "10-12-2019 1800", "10-12-2019 2100");
        testList.addEvent(lessonTest1);

        String itemString = "bring glasses";
        testList.getEvent(0).addChecklist(itemString);
        String testChecklistItem = testList.getEvent(0).getChecklist().get(0);
        assertEquals(itemString, testChecklistItem);
    }

    @Test
    public void calendarTest()  throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event lessonTest1 = new Lesson("CG2271", "12-11-2019 1800", "12-11-2019 2100");
        testList.addEvent(lessonTest1);

        Date testDate = new GregorianCalendar(2019, Calendar.NOVEMBER, 7).getTime();
        CalendarView calendarTest = new CalendarView(testList, new EventDate(testDate));
        calendarTest.setCalendarInfo();
        String calendarCompare = "________________________________________________________________________________________________________________________\n" +
                "|                                                  Events of the week                                                  |\n" +
                "________________________________________________________________________________________________________________________\n" +
                "|   <Thursday>   |    <Friday>    |   <Saturday>   |    <Sunday>    |    <Monday>    |   <Tuesday>    |   <Wednesday>  |\n" +
                "|   07-11-2019   |   08-11-2019   |   09-11-2019   |   10-11-2019   |   11-11-2019   |   12-11-2019   |   13-11-2019   |\n" +
                "________________________________________________________________________________________________________________________\n" +
                "|                |                |                |                |                |* 18:00 ~ 21:00 |                |\n" +
                "|                |                |                |                |                |CG2271          |                |\n" +
                "|                |                |                |                |                |----------------|                |\n" +
                "|                |                |                |                |                |                |                |\n" +
                "________________________________________________________________________________________________________________________";
        assertEquals(calendarCompare, calendarTest.getStringForOutput());
    }

    //@@author
//
//
//
//    @Test
//    public void reminderTest () {
//
//    	ArrayList<String> testcase = new ArrayList<String>();
//    	ArrayList<String> all = new ArrayList<String>();
//    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
//
//    	// case 1: task due long ago (printed)
//    	Task dueLongAgo = new Deadline("longAgo", "09/08/1965 0000");
//    	all.add(dueLongAgo.toString());
//    	testcase.add(dueLongAgo.toString());
//
//    	// case 2: task due now (printed)
//    	Date now = new Date();
//    	Calendar c = Calendar.getInstance();
//    	c.setTime(now);
//    	String nowStr = formatter.format(now);
//    	Task dueNow = new Deadline("now", nowStr);
//    	all.add(dueNow.toString());
//    	testcase.add(dueNow.toString());
//
//    	// case 3: task due 2 days later (printed)
//    	c.add(Calendar.DATE, 2);
//    	Date twoDays = c.getTime();
//    	String twoDaysStr = formatter.format(twoDays);
//    	Task dueTwoDays = new Deadline("twoDays", twoDaysStr);
//    	all.add(dueTwoDays.toString());
//    	testcase.add(dueTwoDays.toString());
//
//    	// case 4: task due 3 days later (printed)
//    	c.add(Calendar.DATE, 1);
//    	Date threeDays = c.getTime();
//    	String threeDaysStr = formatter.format(threeDays);
//    	Task dueThreeDays = new Deadline("threeDays", threeDaysStr);
//    	all.add(dueThreeDays.toString());
//    	testcase.add(dueThreeDays.toString());
//
//    	// case 5: task due 4 days later (not printed)
//    	c.add(Calendar.DATE, 1);
//    	Date fourDays = c.getTime();
//    	String fourDaysStr = formatter.format(fourDays);
//    	Task dueFourDays = new Deadline("fourDays", fourDaysStr);
//    	all.add(dueFourDays.toString());
//
//    	// case 6: task due 10 days later (not printed)
//    	c.add(Calendar.DATE, 6);
//    	Date tenDays = c.getTime();
//    	String tenDaysStr = formatter.format(tenDays);
//    	Task dueTenDays = new Deadline("tenDays", tenDaysStr);
//    	all.add(dueTenDays.toString());
//
//    	EventList expected = new EventList(testcase);
//    	EventList allitms = new EventList(all);
//
//    	EventDate limit = new EventDate();
//    	limit.addDays(4);
//    	limit.setMidnight();
//    	Predicate<Object> pred = new Predicate<>(limit, GREATER_THAN);
//    	String cmp = expected.listOfTasks_String();
//    	String result = allitms.filteredlist(pred, DATE);
//
//    	assertEquals(cmp, result);
//    }
}
