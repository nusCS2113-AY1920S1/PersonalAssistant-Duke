package leduc;

import leduc.task.EventsTask;
import leduc.task.HomeworkTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.util.ArrayList;

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

    @Override
    public void showDone(Task doneTask) {
        super.display("\t Cool! J'ai noté que vous aviez fini cette tâche :\n\t " + doneTask.toString());
    }

    @Override
    public void showTask(Task newTask, int size) {
        super.display("\t Compris. J'ai ajouté cette tâche :\n\t   "
                + newTask.toString() +
                "\n\t Maintenant vous avez " + size + " tâches dans la liste.");
    }
    @Override
    public void showFindMatching(String result) {
        super.display("\tVoici les tâches correspondants qui sont dans votre liste:\n" + result);
    }

    @Override
    public void showFindNotMatching() {
        super.display("\t Il n'y a pas de tâche correspondant dans votre liste");
    }
    @Override
    public void showList(TaskList tasks){
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println("\t Voici les tâches dans votre liste:");
        for (int i = 0 ;i< tasks.size() ; i++ ){
            System.out.print(tasks.displayOneElementList(i));
        }
        System.out.println("\t---------------------------------------------------------------------------------");
    }
    @Override
    public void showNoTask() {
        super.display("\t Il n'y a pas encore de tâche");
    }
    @Override
    public void showPostpone(HomeworkTask postponeTask) {
        super.display("\t C'est noté. J'ai reporté cette tâche: \n" +
                "\t\t "+postponeTask.getTag() + postponeTask.getMark() + " " + postponeTask.getTask()+
                " de:" + postponeTask.getDeadlines() + "\n");
    }
    @Override
    public void showPrioritize(Task task) {
        super.display("\t Compris. La priorité de cette tâche a été assignée:\n\t   "
                + task.toString()+ " à " + task.getPriority());
    }
    @Override
    public void showReschedule(EventsTask rescheduleTask) {
        super.display("\t C'est noté. J'ai replanifié cette tâche: \n" +
                "\t\t "+rescheduleTask.getTag() + rescheduleTask.getMark() + " " +
                rescheduleTask.getTask()+ " à:" + rescheduleTask.getDateFirst() +
                " - " + rescheduleTask.getDateSecond() + "\n");
    }
    @Override
    public void showNewWelcome(String welcomeMessage) {
        super.display("\t Le message de bienvenue a été édité : " + welcomeMessage);
    }
    public void showSnooze(HomeworkTask snoozeTask) {
        super.display("\t C'est noté. J'ai snooze cette tâche : \n" +
                "\t\t "+snoozeTask.getTag() + snoozeTask.getMark() + " " + snoozeTask.getTask()+
                " à :" + snoozeTask.getDeadlines() + "\n");
    }
    @Override
    public void showSort() {
        super.display("\t Voici la nouvelle liste de tâche dans l'ordre: ");
    }
    @Override
    public void showStats(double numTasks, double numTodos, double numEvents, double numHomework, double numIncomplete, double numComplete, float percentComplete) {
        super.display("Voici quelques statistiques à propos de votre liste de tâche: \n" +
                "Nombre de tâche : " + numTasks + "\n" +
                "Nombre de Todo : " + numTodos + "\n" +
                "Nombre de Event: " + numEvents + "\n" +
                "Nombre de Homework: " + numHomework + "\n" +
                "Nombre de tâche inaccomplie: " + numIncomplete + "\n" +
                "Nombre de tâche accomplie: " + numComplete + "\n" +
                "Pourcentage accomplie: " + percentComplete + "%");
    }
    @Override
    public void showUnFinishedTasks(ArrayList<Task> unfinishedTasks) {
        //print the task so they have the same index
        String result = "";
        TaskList unfinishedTaskList = new TaskList(unfinishedTasks);
        for(int i = 0; i < unfinishedTaskList.size(); i++){
            Task task = unfinishedTaskList.get(i);
            result += unfinishedTaskList.displayOneElementList(i);
        }
        if(result.equals("")){
            System.out.println("\t---------------------------------------------------------------------------------");
            System.out.println("\t Il n'y a pas de tâche inaccomplie dans votre liste de tâche");
            System.out.println("\t---------------------------------------------------------------------------------");
        }
        else {
            System.out.println("\t---------------------------------------------------------------------------------");
            System.out.println("\t Voici la liste des tâches inaccomplie:");
            System.out.println(result);
            System.out.println("\t---------------------------------------------------------------------------------");
        }

    }
    @Override
    public void showHelp() {
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println("\t Toutes les commandes vont être affichées ainsi :");
        System.out.println("\t commandName [PARAMETERS] : description de la command");
        System.out.println("\t Tous les paramètres vont être écrits en MAJUSCULE");
        System.out.println("\t Les paramètres sont :");
        System.out.println("\t DESCRIPTION : la description d'une tâche");
        System.out.println("\t SORTTYPE : la date ou description");
        System.out.println("\t DATE : la date d'une tâche");
        System.out.println("\t INDEX : L'index d'une tâche (va de 1 à ...)");
        System.out.println("\t KEYWORD : mot-clé pour trouver une tâche");
        System.out.println("\t WELCOME: le message de bienvenue");
        System.out.println("\t DATEOPTION");
        System.out.println("\t Le format de date est DD/MM/YYYY HH:mm sauf pour show");
        System.out.println("\t Tous les espaces blancs doivent être respectés");
        System.out.println("\t Voici la liste de toutes les commandes:");
        System.out.println("\t todo DESCRIPTION prio INDEX: crée une tâche Todo ( prio index est facultatif)avec une priorité index");
        System.out.println("\t homework DESCRIPTION /by DATE: crée une tâche Homework ( prio index est facultatif)avec une priorité index");
        System.out.println("\t event DESCRIPTION /at DATE - DATE prio INDEX: crée une tâche event ( prio index est facultatif)avec une priorité index");
        System.out.println("\t list : affiche toutes les tâches");
        System.out.println("\t bye : quitte l'application");
        System.out.println("\t done INDEX : marque comme fait la tâche d'index INDEX");
        System.out.println("\t delete INDEX : supprime la tâche d'index INDEX");
        System.out.println("\t find KEYWORD : trouve une tâche avec un mot-clé");
        System.out.println("\t snooze INDEX : snooze une tâche d'index INDEX");
        System.out.println("\t postpone INDEX /by DATE : reporté une tâche homework");
        System.out.println("\t sort SORTTYPE : range les tâches par date/description");
        System.out.println("\t reschedule INDEX /at DATE - DATE : replanifié une tâche event");
        System.out.println("\t remind : Rappelle les trois premières tâches");
        System.out.println("\t setwelcome WELCOME : personnalise le message de bienvenue");
        System.out.println("\t edit : edite une tache (puis suivez les instructions)");
        System.out.println("\t show DATEOPTION DATE: montre les tâches par day/dayofweek/today/week/month/year ( day format is DD/MM/YYYY; " +
                "dayofweek format is monday,tuesday...; month format is MM/YYYY; year format is YYYY)");
        System.out.println("\t prioritize INDEX prio INDEX : donne une priorité à une tâche");
        System.out.println("\t unfinished: trouve et montre toutes les tâches inaccomplies");
        System.out.println("\t language LANG: change la langue du programme à la prochaine exécution du programme. LANG est égal à en ou fr");
        System.out.println("\t help : montre toutes les commandes");
        System.out.println("\t---------------------------------------------------------------------------------");
    }

    @Override
    public void showLanguage(String lang) {
        super.display("Voici la langue qui sera utilisé à la prochaine exécution :" + lang);
    }
    @Override
    public void showEditChooseTask() {
        super.display("\t Choisissez une tâche dans la liste par son index s'il vous plait: ");
    }

    @Override
    public void showEdit2Choice() {
        super.display("\t Choisissez ce que vous voulez éditer (1 ou 2)\n\t 1. The description " +
                "\n\t 2. The deadline/period");
    }

    @Override
    public void showEditWhat(String choice) {
        super.display("\t Entrez le nouveau " + choice + " de la tâche s'il vous plait");
    }

    @Override
    public void showEdit(Task task, int index) {
        super.display("\t La tâche a été éditée: \n\t "+ index + " " + task.toString());
    }
    @Override
    public void showAskShortcut(String commandName) {
        super.display("Entrez s'il vous plait un raccourci pour " + commandName);
    }

    @Override
    public void showAskAllShortcut(String commmandName, String shortcutName) {
        super.display("Le précédent raccourci pour "+commmandName+ " est " + shortcutName +" entrez un nouveau s'il vous plait");
    }

    @Override
    public void showOneShortcutSet(String commandName) {
        super.display("Le raccourci pour " + commandName +" a été enregistré");
    }

    @Override
    public void showAllShortcutSet() {
        super.display("Tous les raccourcis ont été enregistrés");
    }

    @Override
    public void showEnterDayShow() {
        super.display("Veuillez entrer la date comme DD/MM/YYYY");
    }

    @Override
    public void showEnterDayOfWeekShow() {
        super.display("Veuillez entrer le jour de la semaine comme monday, tuesday, wednesday, thursday, friday, saturday, sunday");
    }

    @Override
    public void showEnterMonthShow() {
        super.display("Veuilez entrer le mois comme MM/YYYY");
    }

    @Override
    public void showEnterYearShow() {
        super.display("Veuillez entrer l'année comme YYYY");
    }
    @Override
    public void showNotCompleteList(ArrayList<Task> notCompleteTasks, TaskList tasks) {
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println("\t Voici les tâches dans votre liste:");
        for(int i = 0; i < tasks.size(); i++){
            if(notCompleteTasks.contains(tasks.get(i))){
                System.out.print(tasks.displayOneElementList(i));
            }
        }
        System.out.println("\t---------------------------------------------------------------------------------");
    }
}
