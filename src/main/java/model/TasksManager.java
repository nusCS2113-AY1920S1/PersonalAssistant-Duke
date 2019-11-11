package model;

import common.DukeException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TasksManager implements Serializable {
    public static final String MESSAGE_DUPLICATED_TASK_NAME = "Duplicated task name.";
    private ArrayList<Task> taskList;

    /**
     * instructor
     * @param taskList completed task list
     */
    public TasksManager(ArrayList<Task> taskList) {
        if (taskList != null) {
            this.taskList = taskList;
        } else {
            this.taskList = new ArrayList<Task>();
        }
    }

    //====================== add and delete task ======================

    //@@author JustinChia1997

    /**
     * Add a new task with name.
     *
     * @param name The name of the new task, case sensitive.
     * @throws DukeException If duplicated task name if found.
     */
    public Task addTask(String name) throws DukeException {
        name = name.trim();
        if (!hasTask(name)) {
            Task newTask = new Task(name);
            taskList.add(newTask);
            return newTask;
        } else {
            throw new DukeException(MESSAGE_DUPLICATED_TASK_NAME);
        }
    }

    //@@author yuyanglin28
    /**
     * This method is to delete a task
     * @param toDelete to be deleted task
     * @return if success return true, else false
     */
    public boolean deleteTask(Task toDelete) {
        return taskList.remove(toDelete);
    }

    //========================== get tasks or get something through tasks ==================
    /**
     * Get the Task object by its id.
     *
     * @param id The id, or the index of the Task ArrayList, which is non-persistent.
     *           An id starts with 0.
     * @return Return the Task object if found, return null if index is wrong.
     */
    public Task getTaskById(int id) {
        if (id >= 0 && id < taskList.size()) {
            return taskList.get(id);
        }
        return null;
    }

    //@@author JustinChia1997

    /**
     * Finds Task from task list. returns null if no match was found
     *
     * @param name arraylist
     * @return Task
     */
    public Task getTaskByName(String name) {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (taskList.get(i).getName().equals(name.trim())) {
                return taskList.get(i);
            }
        }
        return null;
    }

    public String getNameById(int index) {
        return getTaskById(index).getName();
    }

    public int getIndexInListByTask(Task task) {
        return taskList.indexOf(task) + 1;
    }

    public String getNameByTask(Task task) {
        return task.getName();
    }

    public String getTaskDes(int index) {
        return getTaskById(index).getDescription();
    }

    public String getTaskStatusString(int index) {
        return "[" + getTaskById(index).getStatusIcon() + "]";
    }

    //@@author yuyanglin28
    /**
     * This method is to get the time string in a special format
     * @param index the task index (from 0)
     * @return time string
     */
    public String getTaskTimeString(int index) {
        Date time = getTaskDateTimeById(index);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm'H'");
        return time == null ? "" : (" (due: " + sdf.format(time) + ")");
    }

    //@@author JasonChanWQ
    public Date getTaskDateTimeById(int index) {
        return getTaskById(index).getTime();
    }

    //@@author JustinChia1997
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public String getTasks() {
        return showTasks(taskList);
    }

    //@@author yuyanglin28

    /**
     * This method is to get the list string of todo tasks
     * @return a list of todo tasks, with no certain order
     */
    public String getTodoTasks() {
        ArrayList<Task> todoTasks = pickTodo(taskList);
        return showTasks(todoTasks);
    }

    //@@author yuyanglin28
    /**
     * This method is to get the number of todo tasks, with a string array of task name
     * @param tasksName a string array that stored task name
     * @return number of todo tasks
     */
    public int getTodoTasksNum(ArrayList<String> tasksName) {
        int todoNum = 0;
        for (int i = 0; i < tasksName.size(); i++) {
            Task task = getTaskByName(tasksName.get(i));
            if (!task.isDone()) {
                todoNum++;
            }
        }
        return todoNum;
    }

    //@@author yuyanglin28
    /**
     * This method is to get the progress with a string array of task name.
     * Progress = finished task number / all task number
     * If no task, the progress is 1.0.
     *
     * @param tasksName a string array that stored task name
     * @return a double data which represents the progress
     */
    public double getProgress(ArrayList<String> tasksName) {
        double total = tasksName.size();
        int todoNum = getTodoTasksNum(tasksName);
        double doneNum = total - todoNum;
        double progress;
        if (total == 0) {
            progress = 1.0;
        } else {
            progress = doneNum / total;
        }
        BigDecimal bd = new BigDecimal(progress).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //====================== add or delete something for task =======================

    //@@ JustinChia1997
    /**
     * @return true if skill req was sucessfully added
     */
    public boolean addReqSkill(String taskName, String skillName) {
        if (hasTask(taskName)) {
            return getTaskByName(taskName).addReqSkill(skillName);
        } else {
            return false;
        }
    }

    /**
     * Add link(s) from task(s) to member(s). Duplicated link will be cancelled.
     *
     * @param tasks Array of Member objects to link.
     * @param toAdd Array of Member object to link.
     */
    public void addMember(Task[] tasks, String[] toAdd) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < toAdd.length; j++) {
                tasks[i].addMember(toAdd[j]);
            }
        }
    }

    /**
     * Delete link(s) from task(s) to member(s). Non-existing link won't be deleted.
     * This is the reverse method of <code>addMember(Task[] tasks, Member[] toAdd)</code> method.
     *
     * @param tasks    arraylist
     * @param toDelete arraylist
     */
    public void deleteMember(Task[] tasks, String[] toDelete) {
        for (int i = 0; i < tasks.length; i++) {
            for (int j = 0; j < toDelete.length; j++) {
                tasks[i].deleteMember(toDelete[j]);
            }
        }
    }

    //@@author yuyanglin28
    /**
     * delete member (person in charge) in task list
     *
     * @param memberName member name to be deleted
     */
    public void deleteMemberInTasks(String memberName) {
        for (int i = 0; i < taskList.size(); i++) {
            Task toCheck = taskList.get(i);
            toCheck.deleteMember(memberName);
        }
    }


    //@@author yuyanglin28

    /**
     * This method is to update teh task description
     * @param index index of the task (from 0)
     * @param des new description
     */
    public void updateTaskDes(int index, String des) {
        Task task = getTaskById(index);
        task.setDescription(des);
    }

    //========================== common ============================
    //@@author JustinChia1997

    /**
     * checks if task is present in task list
     */
    public boolean hasTask(String name) {
        for (int i = 0; i < taskList.size(); i += 1) {
            if (taskList.get(i).getName().equals(name.trim())) {
                return true;
            }
        }
        return false;
    }

    //@@author yuaynglin28
    /**
     * This method is to transfer array list tasks to string
     *
     * @param tasks array list of tasks
     * @return string to represent tasks, contains index in list, status, name, and time (if has)
     */
    private String showTasks(ArrayList<Task> tasks) {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            result += "\n" + getIndexInListByTask(task) + ". " + task;
        }
        return result;
    }

    //======================== reminder ==============================

    /**
     * Sets the reminder time in the task of index given
     *
     * @param taskIndex Index of task
     * @param reminder  Date of reminder
     */
    public void addReminder(int taskIndex, Date reminder) {
        taskList.get(taskIndex).setReminder(reminder);
    }

    public void clearReminder(int taskIndex) {
        taskList.get(taskIndex).setReminder(null);
    }

    //======================== For find command =====================

    //@@author yuyanglin28
    /**
     * get the tasks contain keyword
     *
     * @param keyword keyword to be searched
     * @return a string shows the task list contain keyword
     */
    public String getTasksByKeyword(String keyword) {
        String result = "";
        for (int i = 0; i < taskList.size(); i++) {
            String des = getTaskDes(i);
            String name = getNameById(i);
            int indexInList = i + 1;
            if (name.contains(keyword)) {
                result += "\n" + indexInList + ". " + getByKeyWordInName(i, keyword);
            } else if (des != null && des.contains(keyword)) {
                result += "\n" + indexInList + ". " + getByKeyWordInDes(i, keyword);
            }
        }
        return result;
    }

    //@@author yuyanglin28

    /**
     * This method is to put '*' around the key word in task name.
     *
     * @param i the index of the task that contains the keyword (from 0)
     * @param keyword key word
     * @return a string that represents the task (excluding description)
     * with key word addressed name
     */
    private String getByKeyWordInName(int i, String keyword) {
        String name = getNameById(i);
        int index = name.indexOf(keyword);
        String front = name.substring(0, index);
        String back = "";
        if (index + keyword.length() < name.length()) {
            back = name.substring(index + keyword.length());
        }
        return getTaskStatusString(i)
                + front + "*" + keyword + "*" + back
                + getTaskTimeString(i);

    }

    //@@author yuyanglin28

    /**
     * This method is to put '*' around the key word in task description.
     *
     * @param i the index of the task that contains the keyword (from 0)
     * @param keyword key word
     * @return a string that represents the task (including description)
     * with key word addressed description
     */
    private String getByKeyWordInDes(int i, String keyword) {
        String des = getTaskDes(i);
        int index = des.indexOf(keyword);
        String front = des.substring(0, index);
        String back = "";
        if (index + keyword.length() < des.length()) {
            back = des.substring(index + keyword.length());
        }
        return taskList.get(i) + "\n" + "Description: "
                + front + "*" + keyword + "*" + back;

    }


    //================== For tasks in order of time =====================

    //@@author yuyanglin28
    /**
     * schedule all task list
     *
     * @return a string shows the scheduled task list
     */
    public String tasksAllInorderTime() {
        return showTasks(sortByTime(taskList));
    }

    //@@author yuyanglin28

    /**
     * schedule tasks supplied by task name
     *
     * @param tasksName tasks to be scheduled
     * @return a string shows the scheduled task list
     */
    public String tasksAllInorderTime(ArrayList<String> tasksName) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < tasksName.size(); i++) {
            tasks.add(getTaskByName(tasksName.get(i)));
        }
        return showTasks(sortByTime(tasks));
    }

    //@@author yuyanglin28
    /**
     * schedule todo task list
     *
     * @return a string shows the scheduled todo task list
     */
    public String tasksTodoInorderTime() {
        return showTasks(sortByTime(pickTodo(taskList)));
    }

    //@@author yuyanglin28

    /**
     * schedule todo tasks supplied by task name
     *
     * @param tasksName tasks to be scheduled (contain finished tasks)
     * @return a string shows the scheduled todo task list
     */
    public String tasksTodoInorderTime(ArrayList<String> tasksName) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int i = 0; i < tasksName.size(); i++) {
            tasks.add(getTaskByName(tasksName.get(i)));
        }
        return showTasks(sortByTime(pickTodo(tasks)));
    }


    //@@author yuyanglin28

    /**
     * This method is to sorted the tasks in order of time
     *
     * @param toSort an array list of tasks that need to be sorted
     * @return a sorted array list
     */
    private ArrayList<Task> sortByTime(ArrayList<Task> toSort) {
        ArrayList<Task> sorted = new ArrayList<>();
        ArrayList<Task> hasTime = new ArrayList<>();
        int size = toSort.size();
        for (int i = 0; i < size; i++) {
            Task task = toSort.get(i);
            Date time = task.getTime();
            if (time != null) {
                hasTime.add(task);
            }
        }
        size = hasTime.size();
        for (int i = 0; i < size; i++) {
            Date earliest = new Date(Long.MAX_VALUE);
            int earliestIndex = -1;
            for (int j = 0; j < hasTime.size(); j++) {
                Date time = hasTime.get(j).getTime();
                if (time.before(earliest)) {
                    earliest = time;
                    earliestIndex = j;
                }
            }
            sorted.add(hasTime.get(earliestIndex));
            hasTime.remove(earliestIndex);
        }
        return sorted;
    }

    //@@author yuyanglin28

    /**
     * This method is to get all todo tasks
     * @param toFilter original/total task list
     * @return todo tasks array list
     */
    private ArrayList<Task> pickTodo(ArrayList<Task> toFilter) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (int i = 0; i < toFilter.size(); i++) {
            if (!toFilter.get(i).isDone()) {
                filtered.add(toFilter.get(i));
            }
        }
        return filtered;
    }

    //===================== task in order of pic num ================

    //@@author yuyanglin28

    /**
     * This method is to get all tasks in order of number of PIC
     *
     * @return a sorted list in string format
     */
    public String tasksAllInorderPicNum() {
        return tasksInorderPicNum(taskList);
    }

    //@@author yuyanglin28

    /**
     * This method is to get todo tasks in order of number of PIC
     * @return a sorted list in string format
     */
    public String tasksTodoInorderPicNum() {
        ArrayList<Task> todoTasks = pickTodo(taskList);
        return tasksInorderPicNum(todoTasks);
    }

    //@@author yuyanglin28

    /**
     * This method is to get sorted tasks list in order of number of PICs
     * with a task array list.
     *
     * @param tasks a task array list to be sorted
     * @return a sorted list in string format
     */
    public String tasksInorderPicNum(ArrayList<Task> tasks) {
        ArrayList<Task> toSort = (ArrayList<Task>) tasks.clone();
        String result = "";
        int size = toSort.size();
        for (int i = 0; i < size; i++) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < toSort.size(); j++) {
                int num = toSort.get(j).getMemberList().size();
                if (num < min) {
                    minIndex = j;
                    min = num;
                }
            }
            int indexInList = getIndexInListByTask(toSort.get(minIndex));
            result += "\n" + indexInList + ". " + toSort.get(minIndex) + " has " + min + " PICs.";
            toSort.remove(minIndex);
        }
        return result;
    }

    //=============== check time crash ===========

    //@@author yuyanglin28
    /**
     * This method is to check time crash for just one member
     *
     * @param tasksName tasksName list stored in one member
     * @return if there is time crash, return time and tasks name, if no, empty string
     */
    public String check(ArrayList<String> tasksName) {
        ArrayList<Task> tasks = new ArrayList<>();
        String result = "";
        if (tasksName.size() > 1) {
            for (int i = 0; i < tasksName.size(); i++) {
                Task task = getTaskByName(tasksName.get(i));
                tasks.add(task);
            }
            ArrayList<Task> sorted = sortByTime(pickTodo(tasks));
            if (sorted.size() > 1) {
                int count = 1;
                int index = -1;
                for (int i = 0; i < sorted.size() - 1; i++) {
                    Date time1 = sorted.get(i).getTime();
                    Date time2 = sorted.get(i + 1).getTime();
                    String date1 = getDateString(time1);
                    String date2 = getDateString(time2);
                    if (date1.equals(date2)) {
                        count++;
                        index = i;
                    } else {
                        if (count != 1) {
                            result += getTimeCrashString(count, i + 1, date1, sorted);
                        }
                        count = 1;
                        continue;
                    }
                }
                if (count != 1) {
                    String date = getDateString(sorted.get(index).getTime());
                    result += getTimeCrashString(count, index + 2, date, sorted);
                }
            }
        }
        return result;
    }


    //@@author yuyanglin28
    /**
     * This method is to get time crash string
     * @param count number of tasks that on the same day
     * @param end the index in sorted array list of tasks that not equal to the previous
     * @param date the same day
     * @param sorted sorted (in order of time) array list of tasks
     * @return a string to represent the tasks on the same day
     */
    private String getTimeCrashString(int count, int end, String date, ArrayList<Task> sorted) {
        String name = "";
        for (int j = count; j > 0; j--) {
            Task task = sorted.get(end - j);
            name += "\n" + getIndexInListByTask(task) + ". " + getNameByTask(task);
        }
        return "\n" + date + " " + count + " tasks:" + name;
    }

    //@@author yuyanglin28
    /**
     * This method is to get the date string
     * @param date type Date data to be transferred
     * @return a string represent the date
     */
    private String getDateString(Date date) {
        String year = (date.getYear() + 1900) + "";
        String mm = (date.getMonth() + 1) + "";
        if (Integer.parseInt(mm) < 10) {
            mm = "0" + mm;
        }
        String day = date.getDate() + "";
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        return year + "/" + mm + "/" + day;
    }

    //============================= get something through tasks list or task ===================

    //@@author JasonChanWQ
    public String getTaskNameByIdOnList(int index) {
        return getTaskById(index - 1).getName();
    }

    //@@author JasonChanWQ
    public String getTaskIsDoneByIdOnList(int index) {
        return getTaskById(index - 1).getStatusIcon();
    }

    //@@author JasonChanWQ
    public Date getTaskDateTimeByIdOnList(int index) {
        return getTaskById(index - 1).getTime();
    }

    //@@author JasonChanWQ
    public ArrayList<String> getMemberListOfTaskByIdOnList(int index) {
        return getTaskById(index - 1).getMemberList();
    }

    //@@author JasonChanWQ
    public ArrayList<String> getSkillListOfTaskByIdOnList(int index) {
        return getTaskById(index - 1).getReqSkills();
    }

    //@@author JasonChanWQ
    public String getTaskDescriptionByIdOnList(int index) {
        return getTaskById(index - 1).getDescription();
    }

    //@@author JasonChanWQ
    public Date getTaskReminderByIdOnList(int index) {
        return getTaskById(index - 1).getReminder();
    }

}
