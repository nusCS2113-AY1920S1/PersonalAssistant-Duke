package command;

import list.DegreeList;
import task.TaskList;

public class Memento
{
    private TaskList taskList;
    private DegreeList degreeList;

    public TaskList getTaskState()
    {
        return this.taskList;
    }

    public DegreeList getDegreeState()
    {
        return this.degreeList;
    }

    public void setState(TaskList taskList)
    {
        this.taskList = taskList;
    }

    public void setState(DegreeList degreeList)
    {
        this.degreeList = degreeList;
    }
}
