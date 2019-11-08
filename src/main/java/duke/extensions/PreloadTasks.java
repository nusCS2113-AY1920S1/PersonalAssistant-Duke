package duke.extensions;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Optional;

import duke.exception.DukeException;
import duke.task.Event;
import duke.task.Task;

public class PreloadTasks {

    public ArrayList<Task> defaultTaskList() throws DukeException {
        ArrayList<Task> t = new ArrayList<>();

        //Recurrence parameters
        Recurrence recurrenceWeekly = new Recurrence(Optional.of("weekly"));
        Recurrence recurrenceDaily = new Recurrence(Optional.of("daily"));
        Recurrence recurrenceNone = new Recurrence(Optional.empty());

        //Filter parameters
        Optional<String> cs2113 = Optional.of("cs2113");
        Optional<String> home = Optional.of("home");
        Optional<String> personal = Optional.of("self");
        Optional<String> cca = Optional.of("cca");
        Optional<String> empty = Optional.empty();

        //Date parameters
        Optional<LocalDateTime> emptyDate = Optional.empty();
        Optional<LocalDateTime> dateTime1 = Optional.of(LocalDateTime.of(2019, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime2 = Optional.of(LocalDateTime.of(2019, Month.OCTOBER, 29,
                0, 0));
        Optional<LocalDateTime> dateTime3 = Optional.of(LocalDateTime.of(2019, Month.FEBRUARY, 10,
                10, 0));
        Optional<LocalDateTime> dateTime4 = Optional.of(LocalDateTime.of(2019, Month.NOVEMBER, 10,
                23, 59));

        //Description parameters
        String gym = "go to the gym";
        String sweep = "sweep the floor";
        String UG = "Submit UG";
        String DG = "Submit DG";
        String washBedsheet = "wash bedsheet";
        String washDishes = "wash dishes";
        String assignment = "cs2113 assignment";
        String sleep = "sleep";
        String PPP = "PPP submission";
        String Oral = "Oral Exam";
        String PE = "CS2113 PE";
        String cg2271Tutorial = "CG2271 Tutorial 10";
        String proposal = "cca camp proposal";
        String st2334 = "st2334 tutorial";
        String permitApplication = "Vehicle permit application";
        String internship = "Internship application";
        String applyVisa = "Visa application";
        String ippt = "Ippt at Maju Camp";
        String defer = "Defer reservice";
        String kattis = "Kattis grind";

        t.add(new Task(personal, emptyDate, recurrenceDaily, gym, 1, "l"));
        t.add(new Task(cs2113, dateTime2, recurrenceNone, UG, 0, "h"));
        t.add(new Task(home, emptyDate, recurrenceDaily, sweep, 2, "l"));
        t.add(new Task(home, emptyDate, recurrenceNone, washBedsheet, 0, "l"));
        t.add(new Task(empty, emptyDate, recurrenceNone, washDishes, 0, "l"));
        t.add(new Task(empty, emptyDate, recurrenceNone, assignment, 0, "l"));
        t.add(new Task(cs2113, dateTime2, recurrenceNone, DG, 0, "h"));
        t.add(new Task(personal, emptyDate, recurrenceDaily, sleep, 8, "l"));
        t.add(new Task(empty, emptyDate, recurrenceNone, PPP, 0, "m"));
        t.add(new Event(empty, dateTime1, recurrenceNone, Oral, 0, "m"));
        t.add(new Event(empty, dateTime3, recurrenceNone, PE, 0, "h"));
        t.add(new Task(empty, dateTime1, recurrenceNone, cg2271Tutorial, 2, "l"));
        t.add(new Task(cca, dateTime4, recurrenceNone, proposal, 0, "l"));
        t.add(new Task(empty, emptyDate, recurrenceWeekly, st2334, 0, "m"));
        t.add(new Task(cca, emptyDate, recurrenceNone, permitApplication, 0, "h"));
        t.add(new Task(personal, emptyDate, recurrenceNone, internship, 0, "h"));
        t.add(new Task(empty, emptyDate, recurrenceNone, applyVisa, 0, "m"));
        t.add(new Event(personal, dateTime4, recurrenceNone, ippt, 2, "l"));
        t.add(new Task(personal, emptyDate, recurrenceNone, defer, 0, "h"));
        t.add(new Task(empty, emptyDate, recurrenceWeekly, kattis, 1, "l"));

        return t;
    }
}
