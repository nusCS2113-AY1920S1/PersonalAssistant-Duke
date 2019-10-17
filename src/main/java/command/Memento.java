package command;

import list.DegreeList;
import task.TaskList;
/**
 * Memento Class.
 * Used to store a previous state of a task or degree list, before any modifications.
 *
 * @author Lee Zhen Yu
 * @version 1.0
 * @since 10/16
 */
public class Memento
{
    private TaskList taskList;
    private DegreeList degreeList;

    /**
     * Constructer method that can be overloaded with either a taskList or a degreeList.
     *
     * @param taskList The state of the TaskList to be stored.
     */
    public Memento(TaskList taskList)
    {
        this.taskList = taskList;
    }

    /**
     * Constructer method that can be overloaded with either a taskList or a degreeList.
     *
     * @param degreeList The state of the degreeList to be stored.
     */
    public Memento(DegreeList degreeList)
    {
        this.degreeList = degreeList;
    }

    /**
     * Method to return the saved state of the taskList.
     *
     * @return The saved state of the taskList.
     */
    public TaskList getTaskState()
    {
        return this.taskList;
    }

    /**
     * Method to return the saved state of the degreeList.
     *
     * @return The saved state of the degreeList.
     */
    public DegreeList getDegreeState()
    {
        return this.degreeList;
    }
}
