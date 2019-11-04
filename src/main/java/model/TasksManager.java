package model;

import common.DukeException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

public class TasksManager implements Serializable {
    public static final String MESSAGE_DUPLICATED_TASK_NAME = "Duplicated task name.";
    private ArrayList<Task> taskList;

    /**
     * add javadoc please
     */
    public TasksManager(ArrayList<Task> taskList) {
        if (taskList != null) {
            this.taskList = taskList;
        } else {
            this.taskList = new ArrayList<Task>();
        }
    }

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
     * Delete a task from the task list.
     */
    public boolean deleteTask(Task toDelete) {

        return taskList.remove(toDelete);
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

    //@@author JustinChia1997
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public int getTaskListSize() {
        return taskList.size();
    }

    public String getTodoTasks() {
        ArrayList<Task> todoTasks = pickTodo(taskList);
        return showTasks(todoTasks);
    }
    
    public int getTodoTasks(ArrayList<String> tasksName) {
        int todoNum = 0;
        for (int i = 0; i < tasksName.size(); i++) {
            Task task = getTaskByName(tasksName.get(i));
            if (!task.isDone()) {
                todoNum++;
            }
        }
        return todoNum;
    }

    public double getProgress(ArrayList<String> tasksName) {
        double total = tasksName.size();
        int todoNum = getTodoTasks(tasksName);
        double progress = todoNum/total;
        BigDecimal bd = new BigDecimal(progress).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getTaskNameById(int index) {
        return getTaskById(index).getName();
    }

    public int getIndexInListByTask(Task task) {
        return taskList.indexOf(task) + 1;
    }


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

    public String getNameByTask(Task task) {
        return task.getName();
    }


    public void updateTaskDes(int index, String des) {
        Task task = getTaskById(index);
        task.setDescription(des);
    }

    public String getTaskDes(int index) {
        Task task = getTaskById(index);
        return task.getDescription();
    }

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
            String name = getTaskNameById(i);
            int indexInList = i + 1;
            if (name.contains(keyword)) {
                result += "\n" + indexInList + ". " + taskList.get(i);
            } else if (des != null && des.contains(keyword)) {
                result += "\n" + indexInList + ". " + taskList.get(i);
            }
        }
        return result;
    }

    //@@author yuyanglin28

    /**
     * schedule all task list
     *
     * @return a string shows the scheduled task list
     */
    public String scheduleTeamAll() {
        ArrayList<Task> taskListCopy = (ArrayList<Task>)taskList.clone();
        return showScheduleOfTaskList(taskListCopy);
    }

    //@@author yuyanglin28

    /**
     * schedule todo task list
     *
     * @return a string shows the scheduled todo task list
     */
    public String scheduleTeamTodo() {
        ArrayList<Task> taskListCopy = (ArrayList<Task>) taskList.clone();
        ArrayList<Task> todoTasks = pickTodo(taskListCopy);
        return showScheduleOfTaskList(todoTasks);

    }

    //@@author yuyanglin28

    /**
     * schedule tasks supplied by task name
     *
     * @param tasksName tasks to be scheduled
     * @return a string shows the scheduled task list
     */
    public String scheduleAllTasks(ArrayList<String> tasksName) {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int i = 0; i < tasksName.size(); i++) {
            allTasks.add(getTaskByName(tasksName.get(i)));
        }
        return showScheduleOfTaskList(allTasks);
    }

    //@@author yuyanglin28

    /**
     * schedule todo tasks supplied by task name
     *
     * @param tasksName tasks to be scheduled (contain finished tasks)
     * @return a string shows the scheduled todo task list
     */
    public String scheduleTodoTasks(ArrayList<String> tasksName) {
        ArrayList<Task> allTasks = new ArrayList<>();
        for (int i = 0; i < tasksName.size(); i++) {
            allTasks.add(getTaskByName(tasksName.get(i)));
        }
        ArrayList<Task> todoTasks = pickTodo(allTasks);
        return showScheduleOfTaskList(todoTasks);
    }

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

    private ArrayList<Task> pickTodo(ArrayList<Task> toFilter) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (int i = 0; i < toFilter.size(); i++) {
            if (!toFilter.get(i).isDone()) {
                filtered.add(toFilter.get(i));
            }
        }
        return filtered;
    }

    private String showScheduleOfTaskList(ArrayList<Task> toSorted) {
        ArrayList<Task> tasks = sortByTime(toSorted);
        return showTasks(tasks);
    }

    public String tasksAllInorderPicNum() {
        return tasksInorderPicNum(taskList);
    }

    public String tasksTodoInorderPicNum() {
        ArrayList<Task> todoTasks = pickTodo(taskList);
        return tasksInorderPicNum(todoTasks);
    }

    private String tasksInorderPicNum(ArrayList<Task> tasks) {
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
            System.out.println(result);
            toSort.remove(minIndex);
        }
        return result;
    }

    private String showTasks(ArrayList<Task> tasks) {
        String result = "";
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            result += "\n" + getIndexInListByTask(task) + ". " + task;
        }
        return result;
    }

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
                for (int i = 0; i < sorted.size() - 1; i++) {
                    Date time1 = sorted.get(i).getTime();
                    Date time2 = sorted.get(i + 1).getTime();
                    if (time1.equals(time2)) {
                        count++;
                    } else {
                        if (count != 1) {
                            String name = "";
                            for (int j = count; j > 0; j--) {
                                Task task = sorted.get(i - count);
                                name += " " + getIndexInListByTask(task) + ". " + getNameByTask(task);
                            }
                            result += "\n" + time2 + " " + count + "tasks:" + name;
                        }
                        count = 1;
                        continue;
                    }
                }
            }
        }
        return result;
    }

}
