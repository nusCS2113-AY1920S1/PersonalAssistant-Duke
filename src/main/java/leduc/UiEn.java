package leduc;

import leduc.task.Task;

public class UiEn extends Ui {
    public UiEn() {
        super();
    }
    @Override
    public void showBye(){
        super.display("\t Bye. Hope to see you again soon!");
    }

    @Override
    public void showDelete(Task removedTask, int size) {
        super.display("\t Noted. I've removed this task: \n" +
                "\t\t" + removedTask.toString() +
                "\n\t Now you have "+ size +" tasks in the list");
    }
}
