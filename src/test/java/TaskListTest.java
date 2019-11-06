import exceptions.FarmioException;
import org.junit.jupiter.api.Test;
import usercode.actions.BuySeedAction;
import usercode.conditions.BooleanCondition;
import usercode.conditions.BooleanConditionType;
import usercode.tasks.DoTask;
import usercode.tasks.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    TaskList tasks;

    public TaskListTest() throws FarmioException {
        tasks = new TaskList();
    }

    @Test
    public void testTaskAdd() {
        try {
            tasks.clear();
            DoTask t = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
            tasks.addTask(t);
            assertEquals(tasks.size(), 1);
            tasks.addTask(t);
            tasks.addTask(t);
            assertEquals(tasks.size(), 3);
        } catch (FarmioException e) {
            assert false;
        }
    }

    @Test
    public void testTaskAddCap() {
        try {
            DoTask t = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
            while (tasks.size() < 18) {
                tasks.addTask(t);
            }
            assertEquals(tasks.size(), 18);
            tasks.addTask(t);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }

    @Test
    public void testTaskEdit() throws FarmioException {
        DoTask t1 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
        DoTask t2 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
        try {
            tasks.clear();
            assertEquals(tasks.size(), 0);
            tasks.addTask(t1);
            tasks.editTask(1, t2);
            assertEquals(tasks.get(0), t2);
        } catch (FarmioException e) {
            assert false;
        }

        try {
            tasks.editTask(-1, t2);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }

        try {
            tasks.editTask(tasks.size() + 1, t2);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }

    @Test
    public void testTaskInsert() {
        DoTask t1 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
        DoTask t2 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
        try {
            tasks.clear();
            for (int i = 0; i < 3; i++) {
                tasks.addTask(t1);
            }
            tasks.insertTask(2, t2);
            assertEquals(tasks.get(1), t2);
        } catch (FarmioException e) {
            assert false;
        }
        try {
            tasks.insertTask(-1, t2);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try {
            tasks.insertTask(tasks.size() + 2, t2);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }

    @Test
    public void testTaskDelete() throws FarmioException {
        try {
            DoTask t1 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
            DoTask t2 = new DoTask(new BooleanCondition(BooleanConditionType.TRUE), new BuySeedAction());
            tasks.deleteAll();
            assertEquals(tasks.size(), 0);
            tasks.addTask(t1);
            tasks.addTask(t2);
            tasks.addTask(t1);
            tasks.deleteTask(2);
            assertEquals(tasks.get(1), t1);
        } catch (FarmioException e) {
            assert false;
        }
        try {
            tasks.deleteTask(-1);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
        try{
            tasks.deleteTask(5);
            assert false;
        } catch (FarmioException e) {
            assert true;
        }
    }

}
