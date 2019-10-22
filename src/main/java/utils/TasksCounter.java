package utils;

import java.util.ArrayList;

import tasks.Task;

public class TasksCounter {
	private ArrayList<Task> tasks;
	
	public TasksCounter(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}
	
	public int getPercCompleted()	{
		float totalCompleted = 0;
		for (int i = 0; i < tasks.size(); i++)	{
			if (tasks.get(i).getIsDone())
				totalCompleted += 1;
		}
		return (int)(totalCompleted / (float)tasks.size() * 100f);
	}
}
