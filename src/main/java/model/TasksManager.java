package model;

import common.DukeException;

import java.io.Serializable;
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

    public String getTaskNameById(int index) {
        return getTaskById(index).getName();
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
            if (taskList.get(i).getName().contains(keyword)
                    || taskList.get(i).getDescription().contains(keyword)) {
                result += "\n" + taskList.get(i);
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
        ArrayList<Task> taskListCopy = (ArrayList<Task>) taskList.clone();
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
        ArrayList<Task> todoTasks = new ArrayList<>();
        todoTasks = pickTodo(taskListCopy);
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
        int size = toSort.size();
        for (int i = 0; i < size; i++) {
            Date earliest = new Date(Long.MAX_VALUE);
            int earliestIndex = -1;
            for (int j = 0; j < toSort.size(); j++) {
                Task temp = toSort.get(j);
                if (temp.getTime().before(earliest)) {
                    earliest = temp.getTime();
                    earliestIndex = j;
                }
            }

            sorted.add(toSort.get(earliestIndex));
            toSort.remove(earliestIndex);
        }
        return sorted;
    }

    private ArrayList<Task> pickTodo(ArrayList<Task> toFilter) {
        ArrayList<Task> filtered = new ArrayList<>();
        for (int i = 0; i < toFilter.size(); i++) {
            if (toFilter.get(i).isDone() == false) {
                filtered.add(toFilter.get(i));
            }
        }
        return filtered;
    }

    private String showScheduleOfTaskList(ArrayList<Task> toSorted) {
        String result = "";
        ArrayList<Task> tasks = sortByTime(toSorted);
        for (int i = 0; i < tasks.size(); i++) {
            result += "\n" + tasks.get(i);
        }
        return result;
    }

}
