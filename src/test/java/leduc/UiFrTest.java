package leduc;

import leduc.exception.*;
import leduc.task.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UiFrTest {
    private static Ui ui;
    private static TaskList tasks;
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @BeforeAll
    public static void BeforeUiFrExecuteTest(){
        ui = new UiFr();

        tasks = new TaskList(new ArrayList<>());

        assertTrue(tasks.size()==0);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public static void UiFrExecuteTest(){
        Task task = new TodoTask("UiFrTest");
        HomeworkTask homeTask = null;
        EventsTask eventTask = null;
        try {
            homeTask = new HomeworkTask("UiFrTest", new Date("21/11/2019 20:20"));
        } catch (NonExistentDateException e) {
            e.printStackTrace();
        }
        try {
            eventTask = new EventsTask("UiFrTest", new Date("21/11/2019 20:20"), new Date("22/11/2019 20:20"));
        } catch (NonExistentDateException e) {
            e.printStackTrace();
        }
        ArrayList<Task> emptyList = new ArrayList<>();
        tasks.add(task);
        ui.showBye();
        assertTrue(outContent.toString().contains("J'espère qu'on vous reverra bientôt"));
        ui.showDelete(task, 0);
        assertTrue(outContent.toString().contains("C'est noté. J'ai retiré la tâche"));
        ui.showDone(task);
        assertTrue(outContent.toString().contains("J'ai noté que vous aviez fini cette tâche"));
        ui.showTask(task, 1);
        assertTrue(outContent.toString().contains("Compris. J'ai ajouté cette tâche"));
        ui.showFindMatching("UiFrTest");
        assertTrue(outContent.toString().contains("Voici les tâches correspondants qui sont dans votre liste"));
        ui.showFindNotMatching();
        assertTrue(outContent.toString().contains("Il n'y a pas de tâche correspondant dans votre liste"));
        ui.showList(tasks);
        assertTrue(outContent.toString().contains("Voici les tâches dans votre liste"));
        ui.showNoTask();
        assertTrue(outContent.toString().contains("Il n'y a pas encore de tâche"));
        ui.showPostpone(homeTask);
        assertTrue(outContent.toString().contains("C'est noté. J'ai reporté cette tâche"));
        ui.showPrioritize(task);
        assertTrue(outContent.toString().contains("Compris. La priorité de cette tâche a été assignée"));
        ui.showReschedule(eventTask);
        assertTrue(outContent.toString().contains("C'est noté. J'ai replanifié cette tâche"));
        ui.showNewWelcome("UiTestFr");
        assertTrue(outContent.toString().contains("Le message de bienvenue a été édité"));
        ui.showSnooze(homeTask);
        assertTrue(outContent.toString().contains("C'est noté. J'ai snooze cette tâche"));
        ui.showSort();
        assertTrue(outContent.toString().contains("Voici la nouvelle liste de tâche dans l'ordre:"));
        ui.showGeneralStats(1,1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Voici quelques statistiques à propos de votre liste de tâche"));
        ui.showPriorityStats(1,1,1,1,1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Voici quelques statistiques prioritaires sur votre liste de tâches"));
        ui.showCompletionStats(1,1,1,1,1,1);
        assertTrue(outContent.toString().contains("Voici quelques statistiques d'achèvement de votre liste de tâches"));
        ui.showUnFinishedTasks(tasks.getList());
        assertTrue(outContent.toString().contains("Voici la liste des tâches inaccomplie"));
        ui.showUnFinishedTasks(emptyList);
        assertTrue(outContent.toString().contains("Il n'y a pas de tâche inaccomplie dans votre liste de tâche"));
        ui.showHelp();
        assertTrue(outContent.toString().contains("Toutes les commandes vont être affichées ainsi"));
        ui.showLanguage("French");
        assertTrue(outContent.toString().contains("French"));
        ui.showEditChooseTask();
        assertTrue(outContent.toString().contains("Choisissez une tâche dans la liste par son index s'il vous plait"));
        ui.showEdit2Choice();
        assertTrue(outContent.toString().contains("Choisissez ce que vous voulez éditer"));
        ui.showEditWhat("UiFrTest");
        assertTrue(outContent.toString().contains("Entrez le nouveau UiFrTest"));
        ui.showEdit(task);
        assertTrue(outContent.toString().contains("La tâche a été éditée:"));
        ui.showAskShortcut("UiFrTest");
        assertTrue(outContent.toString().contains("Entrez s'il vous plait un raccourci pour UiFrTest"));
        ui.showAskAllShortcut("UiFrTest", "UiFrTest");
        assertTrue(outContent.toString().contains("Le précédent raccourci pour UiFrTest est UiFrTest entrez un nouveau s'il vous plait"));
        ui.showOneShortcutSet("UiFrTest");
        assertTrue(outContent.toString().contains("Le raccourci pour UiFrTest"));
        ui.showAllShortcutSet();
        assertTrue(outContent.toString().contains("Tous les raccourcis ont été enregistrés"));
        ui.showEnterDayShow();
        assertTrue(outContent.toString().contains("Vous êtes entré dans le mode show date."));
        ui.showEnterDayOfWeekShow();
        assertTrue(outContent.toString().contains("Vous êtes entré dans le mode show jour de la semaine"));
        ui.showEnterMonthShow();
        assertTrue(outContent.toString().contains("Vous êtes entré dans le mode show mois."));
        ui.showEnterYearShow();
        assertTrue(outContent.toString().contains("Vous êtes entré dans le mode show année"));
        ui.showNotCompleteList(tasks.getList(), tasks);
        assertTrue(outContent.toString().contains("Voici les tâches dans votre liste"));
        ui.terminateShortcut();
        assertTrue(outContent.toString().contains("Le mode d'édition de shortcut a été terminé plus tôt"));
        ui.showErrorLanguage();
        assertTrue(outContent.toString().contains("La langue que vous aviez sélectionné est actuellement indisponible"));
        ui.showError(new DateComparisonEventException());
        assertTrue(outContent.toString().contains("La deuxième date ne devrait pas être avant la première."));
        ui.showError(new DuplicationShortcutException());
        assertTrue(outContent.toString().contains("Le raccourci existe déjà"));
        ui.showError(new EmptyArgumentException());
        assertTrue(outContent.toString().contains("Il devrait y avoir un argument"));
        ui.showError(new EmptyEventDateException());
        assertTrue(outContent.toString().contains("Veuillez entrer une période pour la tâche event"));
        ui.showError(new EmptyEventException());
        assertTrue(outContent.toString().contains("La description d'une tâche event ne peut pas être vide"));
        ui.showError(new EmptyHomeworkDateException());
        assertTrue(outContent.toString().contains("Veuillez entrer l'échéance pour la tâche homework"));
        ui.showError(new EmptyHomeworkException());
        assertTrue(outContent.toString().contains("La description d'une tâche homework ne peut pas être vide"));
        ui.showError(new EmptyTodoException());
        assertTrue(outContent.toString().contains("La description d'une tâche homework ne peut pas être vide"));
        ui.showError(new EmptyTodoException());
        assertTrue(outContent.toString().contains("La description d'un todo ne peut pas être vide"));
        ui.showError(new EventTypeException());
        assertTrue(outContent.toString().contains("La tâche devrait être de type event"));
        ui.showError(new FileException());
        assertTrue(outContent.toString().contains("Le fichier n'existe pas ou ne peut pas être créé ou ne peut pas être ouvert"));
        ui.showError(new HomeworkTypeException());
        assertTrue(outContent.toString().contains("La tâche devrait être de type homework"));
        ui.showError(new MeaninglessException());
        assertTrue(outContent.toString().contains("Je suis désolé mais je ne sais pas ce que cela signifie"));
        ui.showError(new NonExistentDateException());
        assertTrue(outContent.toString().contains("La date n'existe pas"));
        ui.showError(new NonExistentTaskException());
        assertTrue(outContent.toString().contains("La tâche n'existe pas"));
        ui.showError(new PostponeHomeworkException());
        assertTrue(outContent.toString().contains("Le nouveau homework ne devrait pas être avant l'ancien"));
        ui.showError(new PrioritizeFormatException());
        assertTrue(outContent.toString().contains("Veuillez respecter le format de prioritize"));
        ui.showError(new PrioritizeLimitException());
        assertTrue(outContent.toString().contains("La priorité d'une tâche doit être supérieur ou égale à 0"));
        ui.showError(new WrongParameterException());
        assertTrue(outContent.toString().contains("Les paramètres sont faux"));
        ui.showError(new EventDateException());
        assertTrue(outContent.toString().contains("La date de départ ne doit pas être postérieur à la "));
        ui.showError(new EditFormatException());
        assertTrue(outContent.toString().contains("Veuillez respecter le format de la command edit"));
        ui.showError(new UserAnswerException());
        assertTrue(outContent.toString().contains("Veuillez répondre correctement à la question"));
        ui.showError(new InvalidFlagException());
        assertTrue(outContent.toString().contains("Drapeau invalide"));
        ui.showError(new RecurrenceException());
        assertTrue(outContent.toString().contains("Respectez le format pour la recurrence"));
        ui.showError(new RecurrenceDateException());
        assertTrue(outContent.toString().contains("Vous êtes en train de créer un event récurrent"));
        ui.showError(new DateComparisonEventException());
        assertTrue(outContent.toString().contains("Il y a un conflit de date avec cet event"));
    }


    @AfterAll
    public static void AfterUiFrExecuteTest() {
        tasks.getList().removeAll(tasks.getList());
        System.setOut(originalOut);
    }

}
