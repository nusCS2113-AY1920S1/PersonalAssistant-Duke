package wrapper;


import task.Tasks;

public class Pair {
    private Tasks task1;
    private Tasks task2;

    public Pair(Tasks task1 , Tasks task2){
        this.task1 = task1;
        this.task2 = task2;
    }

    public Tasks getTask1() {
        return task1;
    }

    public Tasks getTask2() {
        return task2;
    }
}
