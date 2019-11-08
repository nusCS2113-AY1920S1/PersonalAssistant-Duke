import mistermusik.commons.Checklist;
import mistermusik.commons.Contact;
import mistermusik.commons.Goal;
import mistermusik.commons.budgeting.Budgeting;
import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.events.eventtypes.eventsubclasses.ToDo;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Exam;
import mistermusik.commons.events.eventtypes.eventsubclasses.assessmentsubclasses.Recital;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Lesson;
import mistermusik.commons.events.eventtypes.eventsubclasses.recurringeventsubclasses.Practice;
import mistermusik.commons.events.formatting.DateStringValidator;
import mistermusik.commons.events.formatting.EventDate;
import mistermusik.logic.ClashException;
import mistermusik.logic.EndBeforeStartException;
import mistermusik.logic.EventList;
import mistermusik.commons.Goal;
import mistermusik.commons.budgeting.CostExceedsBudgetException;
import mistermusik.ui.CalendarView;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {


    //@@author Ryan-Wong-Ren-Wei
    @Test
    /**
     * test clash handling for single event addition
     */
    public void clashTest(){
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/13";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        Event testEvent = new Practice("Horn practice", "3-12-2019 1400",
                "3-12-2019 1600");
        try {
            eventListTest.addEvent(testEvent);
        } catch (ClashException e){
            assertEquals(e.getClashEvent(), eventListTest.getEvent(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    /**
     * Test clash handling for recurring events
     */
    public void clashTestRecurring() {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/3";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        Event testEvent = new Practice("Horn practice", "28-11-2019 1400",
                "28-11-2019 1600");
        try {
            eventListTest.addRecurringEvent(testEvent, 4);
        } catch (ClashException e){
            assertEquals(e.getClashEvent(), eventListTest.getEvent(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSorting() throws Exception {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/5";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        boolean succeeded = true;
        Event testEvent1 = new Practice("Horn practice", "05-12-2019 1400",
                "05-12-2019 1600");
        Event testEvent2 = new Lesson("Full Orchestra rehearsal", "03-12-2019 1400",
                "03-12-2019 1500");
        Event testEvent3 = new ToDo("Complete theory homework CS2113", "01-12-2019");

        eventListTest.addEvent(testEvent1);
        eventListTest.addEvent(testEvent2);
        eventListTest.addNewTodo(testEvent3);
        eventListTest.sortList();
        ArrayList<Event> eventListCompare = new ArrayList<>();

        eventListCompare.add(new ToDo("Complete theory homework CS2113", "01-12-2019"));
        eventListCompare.add(new ToDo("fawpeifwe", "02-12-2019"));
        eventListCompare.add(new Lesson("Full Orchestra rehearsal", "03-12-2019 1400", "03-12-2019 1500"));
        eventListCompare.add(new Practice("apiejfpwiefw", "03-12-2019 1500", "03-12-2019 1800"));
        eventListCompare.add(new Concert("halloween", "04-12-2019 1600", "04-12-2019 1930", 5));
        eventListCompare.add(new Practice("Horn practice", "05-12-2019 1400", "05-12-2019 1600"));

        int i = 0;
        for (Event currEvent : eventListTest.getEventArrayList()) {
//            System.out.println(currEvent.toString());
//            System.out.println(eventListCompare.get(i).toString());
            if (!currEvent.toString().equals(eventListCompare.get(i).toString())) {
                succeeded = false;
            }
            ++i;
        }

        assertEquals(true, succeeded);
    }

    @Test
    public void testBudget () {
        ArrayList<String> readFromFile = new ArrayList<String>();
        String fileContent;
        fileContent = "XT/fawpeifwe/02-12-2019";
        readFromFile.add(fileContent);
        fileContent = "XP/apiejfpwiefw/03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        fileContent = "XC/halloween/04-12-2019 1600/04-12-2019 1930/5";
        readFromFile.add(fileContent);

        EventList eventListTest = new EventList(readFromFile);
        boolean succeededInAddingConcert;
        try {
            eventListTest.addEvent(new Concert("good concert", "05-12-2019 1500",
                    "05-12-2019 1600",44));
            succeededInAddingConcert = true;
        } catch (CostExceedsBudgetException | EndBeforeStartException | ClashException e) {
            System.out.println("1");
            succeededInAddingConcert = false;
        }
        assertTrue(succeededInAddingConcert);

        boolean CostExceededBudget = false;
        try {
            eventListTest.addEvent(new Concert("good concert", "06-12-2019 1500",
                    "06-12-2019 1600",2));
        } catch (CostExceedsBudgetException e) { //entry should exceed cost
            CostExceededBudget = true;
        } catch (ClashException | EndBeforeStartException e) {
        }

        assertTrue(CostExceededBudget);
    }

    @Test
    public void testSetBudget() {
        Budgeting testBudgeting = new Budgeting(new ArrayList<Event>(), 5);
        assertEquals(5, testBudgeting.getBudget());

        try {
            testBudgeting.updateMonthlyCost(new Concert("test1", "2-12-2019 1500",
                    "2-12-2019 1600", 6));
            fail();
        } catch (CostExceedsBudgetException e) {
        }

        testBudgeting.setBudget(75);
        assertEquals(75, testBudgeting.getBudget());

        try {
            testBudgeting.updateMonthlyCost(new Concert("test1", "2-12-2019 1500",
                    "2-12-2019 1600", 5));
        } catch (CostExceedsBudgetException e) {
            fail();
        }

        try {
            testBudgeting.updateMonthlyCost(new Concert("test2", "2-12-2019 1500",
                    "2-12-2019 1600", 5));
        } catch (CostExceedsBudgetException e) {
            fail();
        }

        try {
            testBudgeting.updateMonthlyCost(new Concert("test3", "2-12-2019 1500",
                    "2-12-2019 1600", 5));
        } catch (CostExceedsBudgetException e) {
            fail();
        }

        try {
            testBudgeting.updateMonthlyCost(new Concert("test4", "2-12-2019 1500",
                    "2-12-2019 1600", 61));
            fail();
        } catch (CostExceedsBudgetException e) {
        }
    }

    @Test
    public void dateValidatorTestEvent() {
        String correctString1 = "14-12-2019 1500";
        String correctString2 = "12-05-4938 1800";
        String correctString3 = "05-05-2000 0800";
        String wrongString1 = "5-5-5-5-3513";
        String wrongString2 = "5-5-3 3301";
        String wrongString3 = "21-12-1900 6000 7000";
        String wrongString4 = "alkjawfwe";
        String wrongString5 = "21-12-2019 15awawer";

        assertTrue(DateStringValidator.isValidDateForEvent(correctString1));
        assertTrue(DateStringValidator.isValidDateForEvent(correctString2));
        assertTrue(DateStringValidator.isValidDateForEvent(correctString3));

        assertFalse(DateStringValidator.isValidDateForEvent(wrongString1));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString2));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString3));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString4));
        assertFalse(DateStringValidator.isValidDateForEvent(wrongString5));
    }

    @Test
    public void dateValidatorTestToDo() {
        String correctString1 = "14-12-2019";
        String correctString2 = "12-05-4938";
        String correctString3 = "5-5-2000";

        String wrongString1 = "5-5--3931-5-3513";
        String wrongString2 = "5-5dsafs-3 3301";
        String wrongString3 = "21-12 6000 7000";
        String wrongString4 = "alkjawfwe";
        String wrongString5 = "50-50-50";

        assertTrue(DateStringValidator.isValidDateForToDo(correctString1));
        assertTrue(DateStringValidator.isValidDateForToDo(correctString2));
        assertTrue(DateStringValidator.isValidDateForToDo(correctString3));

        assertFalse(DateStringValidator.isValidDateForToDo(wrongString1));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString2));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString3));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString4));
        assertFalse(DateStringValidator.isValidDateForToDo(wrongString5));
    }

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
        assertEquals("\n" + "Below lists all the unachieved goal for past events. Please be reminded to add them to the future events." + "\n" + "You do not have any unachieved goals for past events! Yay!" + "\n", testList.getPastEventsWithUnachievedGoals());
    }

    @Test
    public void viewScheduleTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
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
        String foundTask = "";
        int viewIndex = 1;
        EventDate findDate = new EventDate(dateToView);
        for (Event testViewTask : testList.getEventArrayList()) {
            if (testViewTask.toString().contains(findDate.getFormattedDateString())) {
                foundTask += viewIndex + ". " + testViewTask.toString() + "\n";
                viewIndex++;
            }
        }
        boolean isTasksFound = !foundTask.isEmpty();
        assertEquals(true, isTasksFound);
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
        CalendarView calendarTest = new CalendarView(testList, new EventDate(new Date()));
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

    //@@author YuanJiayi
    @Test
    public void addRecurringEventTest() throws ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);

        // test practice type
        Event practiceTest = new Practice("practice 1", "06-11-2019 1200", "06-11-2019 1400");
        testList.addRecurringEvent(practiceTest, 60);
        assertEquals(2, testList.getNumEvents());

        // test lesson type
        Event lessonTest = new Lesson("lesson 1", "13-08-2019 1000", "13-08-2019 1200");
        testList.addRecurringEvent(lessonTest, 35);
        assertEquals(6, testList.getNumEvents());

        // test the period larger than one semester
        Event largePeriodTest = new Practice("practice 2", "23-09-2019 0900", "23-09-2019 1000");
        testList.addRecurringEvent(largePeriodTest, 113);
        assertEquals(7, testList.getNumEvents());

        // test the period exactly one semester (112 days)
        Event exactOneSemesterPeriodTest = new Lesson("lesson 2", "07-10-2019 0800", "07-10-2019 0900");
        testList.addRecurringEvent(exactOneSemesterPeriodTest, 112);
        assertEquals(9, testList.getNumEvents());

        // test the period just shorter than 112 days
        Event smallPeriodTest = new Practice("practice 3", "14-12-2019 1800", "14-12-2019 1900");
        testList.addRecurringEvent(smallPeriodTest, 111);
        assertEquals(11, testList.getNumEvents());

        // test recurring lesson with "isDone"
        Event notDoneLessonTest = new Lesson("lesson", false,"01-01-2020 2200", "01-01-2020 2300");
        testList.addRecurringEvent(notDoneLessonTest, 120);
        assertEquals(12, testList.getNumEvents());

        // test clash
        Event clashTest = new Lesson("lesson 3", "14-12-2019 1800", "14-12-2019 1900");
        try {
            testList.addRecurringEvent(clashTest, 100);
        } catch (ClashException e) {
            assertEquals(e.getClashEvent().toString(), clashTest.toString());
        }
    }

    @Test
    public void rescheduleStartDateTest() {
        ArrayList<String> readFromFile = new ArrayList<>();
        String fileContent;
        fileContent = "XP/practice 1 /03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        EventList eventListTest = new EventList(readFromFile);
        // test reschedule start date and time of an event
        Event practiceTest = eventListTest.getEvent(0);
        EventDate newPracticeStartDate = new EventDate("09-11-2019 0000");
        practiceTest.rescheduleStartDate(newPracticeStartDate);
        assertEquals(newPracticeStartDate, practiceTest.getStartDate());
    }

    @Test
    public void rescheduleEndDateTest() {
        ArrayList<String> readFromFile = new ArrayList<>();
        String fileContent;
        fileContent = "XP/practice 1 /03-12-2019 1500/03-12-2019 1800";
        readFromFile.add(fileContent);
        EventList eventListTest = new EventList(readFromFile);
        Event practiceTest = eventListTest.getEvent(0);
        // test reschedule end date and time of an event
        EventDate newPracticeEndDate = new EventDate("09-11-2019 0100");
        practiceTest.rescheduleEndDate(newPracticeEndDate);
        assertEquals(newPracticeEndDate, practiceTest.getEndDate());
    }

    @Test
    public void addContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Practice("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact normalContact = new Contact("name 1", "email 1", "phone 1");
        //test if contact is added
        testList.getEvent(0).addContact(normalContact);
        assertTrue(eventTest.getContactList().contains(normalContact));
    }

    @Test
    public void removeContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Lesson("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact normalContact = new Contact("name 1", "email 1", "phone 1");
        testList.getEvent(0).addContact(normalContact);
        //test if contact is removed
        testList.getEvent(0).removeContact(0);
        assertFalse(eventTest.getContactList().contains(normalContact));
    }

    @Test
    public void viewContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Exam("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact contact1 = new Contact("name 1", "email 1", "phone 1");
        Contact contact2 = new Contact("name 2", "", "phone 2");
        testList.getEvent(0).addContact(contact1);
        testList.getEvent(0).addContact(contact2);
        assertEquals("name 1", testList.getEvent(0).getContactList().get(0).getName());
        assertEquals("name 2", testList.getEvent(0).getContactList().get(1).getName());
        assertEquals("email 1", testList.getEvent(0).getContactList().get(0).getEmail());
        assertEquals("", testList.getEvent(0).getContactList().get(1).getEmail());
        assertEquals("phone 1", testList.getEvent(0).getContactList().get(0).getPhoneNo());
        assertEquals("phone 2", testList.getEvent(0).getContactList().get(1).getPhoneNo());
    }

    @Test
    public void editContactTest() throws CostExceedsBudgetException, EndBeforeStartException, ClashException {
        ArrayList<String> testListString = new ArrayList<>();
        EventList testList = new EventList(testListString);
        Event eventTest = new Recital("eventTest", "12-12-2019 1200", "12-12-2019 1300");
        testList.addEvent(eventTest);
        Contact contact1 = new Contact("name 1", "email 1", "phone 1");
        testList.getEvent(0).addContact(contact1);
        int contactIndex = 0;

        //test if name edited
        String newName = "name a";
        testList.getEvent(0).editContact(contactIndex, 'N', newName);
        assertEquals(newName, testList.getEvent(0).getContactList().get(contactIndex).getName());
        newName = "name";
        eventTest.getContactList().get(contactIndex).setName(newName);
        assertEquals(newName, eventTest.getContactList().get(contactIndex).getName());

        //test if email edited
        String newEmail = "email a";
        testList.getEvent(0).editContact(contactIndex, 'E', newEmail);
        assertEquals(newEmail, testList.getEvent(0).getContactList().get(contactIndex).getEmail());
        newEmail = "email";
        eventTest.getContactList().get(contactIndex).setEmail(newEmail);
        assertEquals(newEmail, eventTest.getContactList().get(contactIndex).getEmail());

        //test if phone number is edited
        String newPhone = "phone a";
        testList.getEvent(0).editContact(contactIndex, 'P', newPhone);
        assertEquals(newPhone, testList.getEvent(0).getContactList().get(contactIndex).getPhoneNo());
        newPhone = "phone";
        eventTest.getContactList().get(contactIndex).setPhoneNo(newPhone);
        assertEquals(newPhone, eventTest.getContactList().get(contactIndex).getPhoneNo());
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
