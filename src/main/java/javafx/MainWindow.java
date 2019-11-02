package javafx;

import degree.DegreeManager;
import exception.DukeException;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import list.DegreeList;
import main.Duke;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import module.ConjunctiveModule;
import module.Module;
import module.ModuleList;
import module.NonDescriptive;
import task.Task;
import task.TaskList;

import java.util.*;


/**
 * Controller for JavaFX.MainWindow. Provides the layout for the other controls.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    @FXML
    private TableView<TaskFX> taskView;
    @FXML
    private TableView<ChoicesFX> choicesView;
    @FXML
    private TableView<DegreesFX> degreesView;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabTask;
    @FXML
    private Tab tabChoices;
    @FXML
    private Tab tabDegrees;

    private Duke duke;
    private TaskList taskList;
    private DegreeList degreeList;
    private DegreeManager degreeManager;
    private ModuleList moduleList;
    private boolean typoFlag;

    private Set<String> autoSuggestion = new HashSet<>(Arrays.asList("list", "detail", "help", "todo", "delete", "clear",
            "add", "swap", "bye", "replace", "undo", "redo", "sort"));
    private SuggestionProvider<String> provider = SuggestionProvider.create(autoSuggestion);

    private ObservableList<TaskFX> dataTask;
    private ObservableList<ChoicesFX> dataChoices;
    private ObservableList<DegreesFX> dataDegrees;


    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Method that runs when duke GUI is started up.
     * It does not take any input from the terminal yet, and does not output to the terminal either.
     *
     * @param d The duke helper that is going to be initialized
     */
    //This method initializes duke
    public void setDuke(Duke d) throws DukeException {

        duke = d;


        new AutoCompletionTextFieldBinding<>(this.userInput, provider);
        dataTask = taskView.getItems();
        dataChoices = choicesView.getItems();
        dataDegrees = degreesView.getItems();


        String logo = "  _____  ______ _____ _____  ______ ______  _____  _   _ _    _  _____ \n"
                + " |  __ \\|  ____/ ____|  __ \\|  ____|  ____|/ ____|| \\ | | |  | |/ ____|\n"
                + " | |  | | |__ | |  __| |__) | |__  | |__  | (___  |  \\| | |  | | (___  \n"
                + " | |  | |  __|| | |_ |  _  /|  __| |  __|  \\___ \\ | . ` | |  | |\\___ \\ \n"
                + " | |__| | |___| |__| | | \\ \\| |____| |____ ____) || |\\  | |__| |____) |\n"
                + " |_____/|______\\_____|_|  \\_\\______|______|_____(_)_| \\_|\\____/|_____/ \n";

        String welcome = logo +"\n";

        String greeting;

        if (duke.reminder().isEmpty()) {
            greeting = "What would you like to do?\n\n";
        } else {
            greeting = duke.reminder() + "\n\n";
        }

        welcome += greeting;

        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(welcome)
        );

        this.dataTask = taskView.getItems();
        this.dataChoices = choicesView.getItems();

        this.taskList = duke.getTaskList();
        this.degreeList = duke.getDegreeList();

        String countString;

        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataTask.add(new TaskFX(countString, newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate()));
        }

        for (int i = 0; i < this.degreeList.size(); i++) {
            String newChoice = this.degreeList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataChoices.add(new ChoicesFX(countString, newChoice));
        }

    }


    /**
     * Creates two dialog boxes, one echoing user input and the other containing JavaFX.Main.Duke's
     * reply and then appends them to the dialog container.
     * Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws DukeException {
        typoFlag = false;
        String input = userInput.getText();
        String response = duke.run(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getDukeDialog(response)
        );

        typoFlag = duke.getTypoFlag();

        //Learn new auto suggestions based on user inputs, and do not learn typos
        if (!autoSuggestion.contains(input) && (!typoFlag)) {
            autoSuggestion.add(input);
            provider.clearSuggestions();
            provider.addPossibleSuggestions(autoSuggestion);
        }


        this.taskList = duke.getTaskList();
        this.degreeList = duke.getDegreeList();
        this.dataTask.clear();
        this.dataChoices.clear();

        String countString;

        for (int i = 0; i < this.taskList.size(); i++) {
            Task newTask = this.taskList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataTask.add(new TaskFX(countString, newTask.getStatusIcon(), newTask.getType(),
                    newTask.getDescription(), newTask.getDueDate()));
        }

        for (int i = 0; i < this.degreeList.size(); i++) {
            String newChoice = this.degreeList.get(i);
            countString = Integer.toString(i + 1);

            if (i <= 8) {
                countString = "0" + countString;
            }

            this.dataChoices.add(new ChoicesFX(countString, newChoice));
        }

        //Forces a tab to open corresponding to the list being displayed
        if (input.matches("list")) {
            tabPane.getSelectionModel().select(tabTask);
        } else if (input.matches("choices")) {
            tabPane.getSelectionModel().select(tabChoices);
        } else if (input.equals("bye")) { //If user wants to end program, create a separate thread with a timer to exit
            // delay & exit on other thread
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                }
                System.exit(0);
            }).start();
        } else {
            Scanner temp = new Scanner(input);
            String command = temp.next();

            if (command.matches("detail") && (!typoFlag)) {
                tabPane.getSelectionModel().select(tabDegrees);
                //tabDegrees.setText("hi"); use these to change the degree tab name
                this.dataDegrees.clear();
                String degreeName = temp.next();
                this.degreeManager = duke.getDegreesManager(); //gets the entire degree manager from duke

                List<Module> moduleList = new ArrayList<>(this.degreeManager.getModuleList(degreeName).getModules());
                NonDescriptive generalMod = new NonDescriptive("General Education Modules", 20);
                moduleList.add(generalMod);

                Collections.sort(moduleList);
                int count = 0;
                for(Module mod: moduleList)
                {
                    count++;
                    countString = Integer.toString(count);

                    if (count <= 9) {
                        countString = "0" + countString;
                    }

                    String mcString = Integer.toString(mod.getMc());

                    if (mcString.length() == 1) {
                        mcString = "0" + mcString;
                    }

                    //for standard modules
                    if (mod.getClass() == Module.class) {
                        this.dataDegrees.add(new DegreesFX(countString, mod.getCode(), mod.getName(),
                                mcString));
                    } else if (mod.getClass() == NonDescriptive.class) {
                        //Non descriptive class has no module code, but the moduleCode property contains the name
                        this.dataDegrees.add(new DegreesFX(countString, "-", mod.getCode(),
                                mcString));
                    } else if (mod.getClass() == ConjunctiveModule.class) {
                        this.dataDegrees.add(new DegreesFX(countString, mod.getCode(), mod.getFullModuleName(),
                                mcString));
                    }
                }
            }
        }

        //tabTask.setText("hi"); use these to change the degree tab name

        userInput.clear();
    }
}
