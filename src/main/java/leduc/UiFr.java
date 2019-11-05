package leduc;

import leduc.exception.*;
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
        System.out.println("\t SORTTYPE :  date, description, priority, type ou done");
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
        System.out.println("\t sort SORTTYPE : range les tâches par date/description/priority/type/done");
        System.out.println("\t reschedule INDEX /at DATE - DATE : replanifié une tâche event");
        System.out.println("\t remind : Rappelle les trois premières tâches");
        System.out.println("\t setwelcome WELCOME : personnalise le message de bienvenue");
        System.out.println("\t edit :\n\t\t Pour la commande en plusieurs étapes  : 'edit' et puis suivez les instructions" +
                           "\n\t\t Pour la commande en une étape:" +
                           "\n\t\t\t edit la description: 'edit INDEX description DESCRIPTION' " +
                           "\n\t\t\t edit la date d'une tâche homework: 'edit INDEX /by DATE' " +
                           "\n\t\t\t edit la period d'une tâche event: 'edit INDEX /at DATE - DATE' ");
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
    public void showEdit(Task task) {
        super.display("\t La tâche a été éditée: \n\t "+ task.toString());
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
    @Override
    public void showError(DukeException e) {
        if(e instanceof ConflictDateException){
            String conflictTasks = "";
            for (Task t : ((ConflictDateException)e).getTasks()){
                conflictTasks += "\n\t\t\t" + t.toString();
            }

            System.out.println("\t ConflictDateException:\n\t\t ☹ OOPS!!! Il y a un conflit de date avec cet event :" +
                    conflictTasks);
        }
        else if(e instanceof DateComparisonEventException){
            System.out.println("\t DateComparisonEventException:\n\t\t ☹ OOPS!!! La deuxième date ne devrait pas être avant la première.");
        }
        else if(e instanceof DuplicationShortcutException){
            System.out.println("\t DuplicationShortcutException:\n\t\t ☹ OOPS!!! Le raccourci existe déjà");
        }
        else if(e instanceof EmptyArgumentException){
            System.out.println("\t EmptyArgumentException:\n\t\t ☹ OOPS!!! Il devrait y avoir un argument");
        }
        else if(e instanceof EmptyEventDateException){
            System.out.println("\t emptyEventDateException:\n\t\t ☹ OOPS!!! Veuillez entrer une période pour la tâche event");
        }
        else if(e instanceof EmptyEventException){
            System.out.println("\t emptyEventException:\n\t\t ☹ OOPS!!! La description d'une tâche event ne peut pas être vide");
        }
        else if(e instanceof EmptyHomeworkDateException){
            System.out.println("\t emptyHomeworkDateException:\n\t\t ☹ OOPS!!! Veuillez entrer l'échéance pour la tâche homework");
        }
        else if(e instanceof EmptyHomeworkException){
            System.out.println("\t emptyHomeworkException:\n\t\t ☹ OOPS!!! La description d'une tâche homework ne peut pas être vide");
        }
        else if(e instanceof EmptyTodoException){
            System.out.println("\t emptyTodoException:\n\t\t ☹ OOPS!!! La description d'un todo ne peut pas être vide.");
        }
        else if(e instanceof EventTypeException){
            System.out.println("\t EventTypeException:\n\t\t ☹ OOPS!!! La tâche devrait être de type event");
        }
        else if(e instanceof FileException){
            System.out.println("Le fichier n'existe pas ou ne peut pas être créé ou ne peut pas être ouvert ");
        }
        else if(e instanceof HomeworkTypeException){
            System.out.println("\t HomeworkTypeException:\n\t\t ☹ OOPS!!! La tâche devrait être de type homework");
        }
        else if(e instanceof MeaninglessException){
            System.out.println("\t MeaninglessException:\n\t\t OOPS!!! Je suis désolé mais je ne sais pas ce que cela signifie:-(\"");
        }
        else if(e instanceof NonExistentDateException){
            System.out.println("\t NonExistentDateException:\n\t\t ☹ OOPS!!! \n\t\t\t La date n'existe pas)");
        }
        else if(e instanceof NonExistentTaskException){
            System.out.println("\t NonExistentTaskException:\n\t\t ☹ OOPS!!! La tâche n'existe pas");
        }
        else if(e instanceof PostponeHomeworkException){
            System.out.println("\t PostponeHomeworkException:\n\t\t ☹ OOPS!!! Le nouveau homework ne devrait pas être avant l'ancien");
        }
        else if(e instanceof PrioritizeFormatException){
            System.out.println("\t PrioritizeFormatException:\n\t\t ☹ OOPS!!! Veuillez respecter le format de prioritize" +
                    "\n\t\t\t prioritize INDEX prio INDEX");
        }
        else if(e instanceof PrioritizeLimitException){
            System.out.println("\t PrioritizeLimitException:\n\t\t ☹ OOPS!!! La priorité d'une tâche doit être supérieur ou égale à 0 et inférieur ou égale à 9.");
        }
        else if(e instanceof WrongParameterException){
            System.out.println("\t WrongParameterException:\n\t\t ☹ OOPS!!! Les paramètres sont faux");
        }
        else if(e instanceof EditFormatException){
            System.out.println("\t EditFormatException:\n\t\t ☹ OOPS!!! Veuillez respecter le format de la command edit" +
                    "\n\t\t Command interactive : 'edit' puis suivez les instructions" +
                    "\n\t\t Command en une ligne:" +
                    "\n\t\t\t éditer la description: 'edit INDEX description DESCRIPTION'"+
                    "\n\t\t\t éditer la date d'une tache homework : 'edit INDEX /by DATE'"+
                    "\n\t\t\t éditer la période d'une tâche event: 'edit INDEX /at DATE - DATE'");
        }
    }
}
