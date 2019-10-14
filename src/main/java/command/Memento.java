package command;

import list.DegreeList;
import task.TaskList;

public class Memento
{
    private TaskList taskList;
    private DegreeList degreeList;

    public Memento(TaskList taskList)
    {
        this.taskList = taskList;
    }

    public Memento(DegreeList degreeList)
    {
        this.degreeList = degreeList;
    }

    public TaskList getTaskState()
    {
        return this.taskList;
    }

    public DegreeList getDegreeState()
    {
        return this.degreeList;
    }
}
