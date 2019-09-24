package duke.tasks;

import duke.exceptions.DukeException;
import duke.ui.Ui;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.time.YearMonth;
import java.util.Date;
import java.util.Scanner;

/**
 * Schedule is a public class that stores tasks in the same month in chronological order.
 * @author Foo Chi Hen
 */
public class Schedule {
    protected ArrayList[][] schedule = new ArrayList[31][24];
    protected ArrayList<Task> ponder = new ArrayList<Task>();
    protected ArrayList<ArrayList<Calendar>> ponderDate = new ArrayList<ArrayList<Calendar>>();

    /**
     * Default constructor for schedule.
     */
    public Schedule() {
        for (int i = 0; i < 31; i += 1) {
            for (int j = 0; j < 24; j += 1) {
                this.schedule[i][j] = new ArrayList<Task>();
            }
        }
    }

    /**
     * update updates the task into the appropriate timeslot.
     * @params task the task object to be inserted
     * @author Foo Chi Hen
     */
    public boolean update(Task task) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
        LocalDate now = LocalDate.now();
        if (task.getType().equals("D")) {
            if (task.getDate() != null) {
                if (now.getYear() == task.getDate().get(Calendar.YEAR)) {
                    if (now.getMonthValue() == task.getDate().get(Calendar.MONTH) + 1) {
                        int taskDate = task.getDate().get(Calendar.DAY_OF_MONTH);
                        int taskHour = task.getDate().get(Calendar.HOUR_OF_DAY);
                        this.schedule[taskDate - 1][taskHour].add(task);
                    }
                }
            }
        } else if (task.getType().equals("E")) {
            if (task.getDate() != null) {
                if (now.getYear() == task.getDate().get(Calendar.YEAR)) {
                    if (now.getMonthValue() == task.getDate().get(Calendar.MONTH) + 1) {
                        int taskDate = task.getDate().get(Calendar.DAY_OF_MONTH);
                        int taskHour = task.getDate().get(Calendar.HOUR_OF_DAY);
                        int taskEnd = task.getEnd().get(Calendar.HOUR_OF_DAY);
                        int duration = taskEnd - taskHour + 1;
                        boolean checkForAnomaly = detectAnomalies(this.schedule[taskDate - 1], taskHour, duration);
                        if (checkForAnomaly) {
                            System.out.println("There is already an event task at "
                                    + dateFormat.format(task.getDate().getTime()));
                            return false;
                        } else {
                            for (int i = 0; i < duration; i += 1) {
                                this.schedule[taskDate - 1][taskHour + i].add(task);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * remindMe outputs the tasks to be done with the user defined hour.
     * @params hour number of hours with reference to current time
     * @author Foo Chi Hen
     */
    public ArrayList<Task> remindMe(int hour) throws DukeException {
        LocalDate nowDay = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int currentDay = nowDay.getDayOfMonth();
        int currentHour = nowTime.getHour();
        ArrayList<Task> result = new ArrayList<Task>();
        ArrayList<Task> current;
        int counter = 0;
        Task prevCheck = null;
        for (int i = 0; i < hour; i += 1) {
            current = this.schedule[currentDay + (currentHour + i) / 24 - 1][(currentHour + i) % 24];
            for (int j = 0; j < current.size(); j += 1) {
                if (current.get(j).equals(prevCheck)) {
                    String bypass = "lol";
                } else {
                    if (current.get(j).getType() == "D" && current.get(j).getisDone() == false) {
                        result.add(current.get(j));
                    } else if (current.get(j).getType() == "E") {
                        result.add(current.get(j));
                    }
                    prevCheck = current.get(i);
                }
            }
        }
        if (result.size() == 0) {
            throw new DukeException("You have no upcoming tasks");
        }
        return result;
    }

    /**
     * function check whether there are conflicts.
     * @param activity arraylist of tasks
     * @return boolean value
     */
    private boolean detectAnomalies(ArrayList<Task>[] activity, int start, int duration) {
        for (int i = 0; i < duration; i += 1) {
            for (int j = 0; j < activity[i].size(); j += 1) {
                if (activity[i].get(j).equals("E") || activity[i].get(j).equals("T")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean detectAnomalies(ArrayList<Task> activity) {
        for (int j = 0; j < activity.size(); j += 1) {
            if (activity.get(j).equals("E") || activity.get(j).equals("T")) {
                return true;
            }
        }
        return false;
    }

    public void findFreeTime(int hour) {
        LocalDate nowDay = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        int currentDay = nowDay.getDayOfMonth();
        int currentHour = nowTime.getHour();
        int currentYear = nowDay.getDayOfMonth();
        int currentMonth = nowDay.getMonthValue();
        YearMonth yearMonthObject = YearMonth.of(currentYear, currentMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth(); //28
        boolean flag = false;
        boolean freeFlag = false;
        boolean dayFreeFlag = false;
        for (int i = currentDay; i <= daysInMonth; i += 1) {
            System.out.println(nowDay.toString());
            System.out.println("_____________");
            for (int j = ((flag) ? 0 : currentHour); j < 24; j += 1) {
                if (this.schedule[i - 1][j].size() == 0) {
                    for (int k = 0; k < hour; k += 1) {
                        if (i + (j + k) / 24 > daysInMonth) {
                            break;
                        }
                        if (this.schedule[i - 1 + (j + k) / 24][(j + k) % 24].size() != 0) {
                            break;
                        }
                        if (k == hour - 1) {
                            freeFlag = true;
                        }
                    }
                }
                if (freeFlag == true) {
                    LocalTime formatter = LocalTime.of(j,0);
                    System.out.println(formatter.toString());
                    dayFreeFlag = true;
                }
                freeFlag = false;
            }
            if (dayFreeFlag == false) {
                System.out.println("You are not free on " + nowDay.toString());
            }
            nowDay.plusDays(1);
            flag = true;
            System.out.println("_____________");
        }
    }

    public void snooze(int day, int hour, Ui ui) throws DukeException {
        Scanner temp = new Scanner(System.in);
        ArrayList<Task> selectedHome = this.schedule[day - 1][hour];
        ui.showList(selectedHome);
        System.out.println("What would you like to select");
        int index = Integer.parseInt(temp.nextLine());
        Task selected = selectedHome.get(index - 1);
        System.out.println("Which date would you like to choose");
        String input = temp.nextLine();
        int newDay = Integer.parseInt(input.split(" ")[0]);
        int newHour = Integer.parseInt(input.split(" ")[1]);
        ArrayList<Task> newHome = this.schedule[newDay - 1][newHour];
        if (selected.getType().equals("E")) {
            if (detectAnomalies(newHome)) {
                throw new DukeException("There is already an event task");
            } else {
                selected.getDate().set(Calendar.DAY_OF_MONTH, newDay);
                selected.getDate().set(Calendar.HOUR_OF_DAY, newHour);
                this.schedule[newDay - 1][newHour].add(selected);
                this.schedule[day - 1][hour].remove(index - 1);
            }
        } else {
            this.schedule[newDay - 1][newHour].add(selected);
            this.schedule[day - 1][hour].remove(index - 1);
        }
    }

    public void tentative() {
        System.out.println("Please enter description");
        Scanner temp = new Scanner(System.in);
        String input = temp.nextLine();
        String type = input.split(" ",2)[0];
        String description = input.split(" ",2)[1];
        System.out.println(input);
        if (type.equals("deadline")) {
            //Deadline tentativeTask = new Deadline(description,null);
            this.ponder.add(new Deadline(description,"null"));
        } else if (type.equals("event")) {
            //Event tentativeTask = new Event(description,null);
            this.ponder.add(new Event(description,"null"));
        }
        this.ponderDate.add(new ArrayList<Calendar>());
        input = temp.nextLine();
        while (true) {
            if (input.equals("done")) {
                break;
            }
            System.out.println(input);
            Calendar dateTime = Calendar.getInstance();
            SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date date;
            try {
                date = dateparser.parse(input);
                dateTime.setTime(date);
                System.out.println("Added. Enter done to stop.");
            } catch (ParseException e) {
                SimpleDateFormat altparser = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
                try {
                    date = altparser.parse(input);
                    dateTime.setTime(date);
                    System.out.println("Added. Enter done to stop.");
                } catch (ParseException f) {
                    System.out.println("Wrong date format");
                }
            }
            this.ponderDate.get(this.ponderDate.size() - 1).add(dateTime);
            input = temp.nextLine();
        }
    }

    public Task confirm() throws DukeException {
        System.out.println("These are the tentative task. Which would you like to select?");
        System.out.println("_____________");
        for (int i = 0; i < this.ponder.size(); i += 1) {
            System.out.print(Integer.toString(i + 1) + ". ");
            System.out.println(this.ponder.get(i).toString());
        }
        System.out.println("_____________");
        Scanner temp = new Scanner(System.in);
        String input = temp.nextLine();
        int taskIndex;
        try {
            taskIndex = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a number");
        }
        System.out.println("These are the tentative dates. Which would you like to select?");
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy hh.mm a");
        for (int i = 0; i < this.ponderDate.get(taskIndex - 1).size(); i += 1) {
            System.out.print(Integer.toString(i + 1) + ". ");
            System.out.println(dateFormat.format(this.ponderDate.get(taskIndex - 1).get(i).getTime()));
        }
        input = temp.nextLine();
        int dateIndex;
        try {
            dateIndex = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new DukeException("Please enter a number");
        }
        Task confirmed = this.ponder.get(taskIndex - 1);
        if (confirmed.getType().equals("E")) {
            Calendar selectedDate = this.ponderDate.get(taskIndex - 1).get(dateIndex - 1);
            int selectedDay = selectedDate.get(Calendar.DAY_OF_MONTH);
            int selectedHour = selectedDate.get(Calendar.HOUR_OF_DAY);
            if (detectAnomalies(this.schedule[selectedDay - 1][selectedHour])) {
                throw new DukeException("There is already an event task in this timeslot.");
            }
        }
        confirmed.setDate(this.ponderDate.get(taskIndex - 1).get(dateIndex - 1));
        this.ponder.remove(taskIndex - 1);
        this.ponderDate.remove(taskIndex - 1);
        return confirmed;
    }

    public ArrayList<Task> viewSchedule(String date) throws DukeException {
        int day = 1;
        try {
            SimpleDateFormat dateparser = new SimpleDateFormat("dd/MM/yyyy");
            Date tempDate;
            tempDate = dateparser.parse(date);
            Calendar temp = Calendar.getInstance();
            temp.setTime(tempDate);
            day = temp.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            throw new DukeException("Wrong date format");
        }
        ArrayList<Task> selected = new ArrayList<>();
        Task prevCheck = null;
        for (int i = 0; i < 24; i += 1) {
            ArrayList<Task> current = this.schedule[day - 1][i];
            for (int j = 0; j < current.size(); j += 1) {
                if (current.get(j).equals(prevCheck)) {
                    String bypass = "lol";
                } else {
                    selected.add(current.get(j));
                    prevCheck = current.get(j);
                }
            }
        }
        return selected;
    }

    public void doAfter(Task task) {
        Scanner temp = new Scanner(System.in);
        System.out.println("Please input ToDo task. Input exit to escape.");
        String input = temp.nextLine();
        while (true) {
            if (input.equals("exit")) {
                break;
            }
            System.out.println("Please input ToDo task. Input exit to escape.");
            String[] process = input.split(" ",2);
            ToDo doAfter = new ToDo(process[1]);
            task.getDoAfter().add(doAfter);
            System.out.println(doAfter.toString() + "has been added.");
            input = temp.nextLine();
        }
    }
}
