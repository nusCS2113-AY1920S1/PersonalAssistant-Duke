package leduc;

import leduc.task.Task;

public class UiFr extends Ui {
    public UiFr() {
        super();
    }

    public void showBye(){
        super.display("\t Bye. J'espère qu'on vous reverra bientôt!");
    }

    @Override
    public void showDelete(Task removedTask, int size) {
        super.display("\t C'est noté. J'ai retiré la tâche: \n" +
                "\t\t" + removedTask.toString() +
                "\n\t Maintenant vous avez "+ size +" tâches dans la liste");
    }
}
