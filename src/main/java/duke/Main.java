package duke;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.Logic;
import duke.logic.LogicManager;
import duke.model.DukePP;
import duke.model.Expense;
import duke.model.ExpenseList;
import duke.model.Model;
import duke.storage.*;
import duke.storage.payment.PaymentListStorage;
import duke.storage.payment.PaymentListStorageManager;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Bridge between duke and MainWindow.
 */
public class Main extends Application {

    private static final Logger logger = LogsCenter.getLogger(Main.class);

    private Ui ui;
    private Logic logic;
    private Model model;
    private Storage storage;

    @Override
    public void init() throws Exception {
        super.init();

        ExpenseListStorage expenseListStorage = new ExpenseListStorageManager();
        PlanAttributesStorage planAttributesStorage = new PlanAttributesStorageManager();
        IncomeListStorage incomeListStorage = new IncomeListStorageManager();
        BudgetStorage budgetStorage = new BudgetStorage();
        BudgetViewStorage budgetViewStorage = new BudgetViewStorage();
        PaymentListStorage paymentListStorage = new PaymentListStorageManager();

        storage = new StorageManager(expenseListStorage,
                                     planAttributesStorage,
                                     incomeListStorage,
                                     budgetStorage,
                                     budgetViewStorage,
                                     paymentListStorage);

        logger.info("Initialized the storage");


        //Demo Code, loads demo data on first boot
        if(storage.loadExpenseList().internalSize() == 0 || storage.loadExpenseList() == null) {
            loadListDemoData(storage);
        }

        if(!storage.loadPaymentList().isPresent()) logger.info("PaymentList is not loaded");
        if(storage.loadExpenseList() == null) logger.info("expenseList is not loaded");
        if(storage.loadIncomeList() == null) logger.info("incomeList is not loaded");
        if(storage.loadBudget() == null) logger.info("budgetList is not loaded");



        model = new DukePP(storage.loadExpenseList(),
                storage.loadPlanAttributes(),
                storage.loadIncomeList(),
                storage.loadBudget(),
                storage.loadBudgetView(),
                storage.loadPaymentList());

        logger.info("Initialized the model");

        logic = new LogicManager(model, storage);

        logger.info("Initialized the logic");

        ui = new UiManager(logic);
        logger.info("Initialized the app");

    }


    /**
     * Starts Duke with MainWindow.
     *
     * @param primaryStage The main GUI of Duke
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        ui.start(primaryStage);

    }

    public static void main(String[] args) {
        launch(args);
    }

    private final Storage loadListDemoData(Storage storage) {
        Expense.Builder builder = new Expense.Builder();
        try {
            ExpenseList expenseList = storage.loadExpenseList();
            builder.setAmount("3.50");
            builder.setDescription("chicken rice");
            builder.setTag("food");
            builder.setTime("18:00 09/11/2019");
            expenseList.add(builder.build());

            builder.setAmount("5.50");
            builder.setDescription("pineapple friend rice");
            builder.setTag("food");
            builder.setTime("18:00 08/11/2019");
            expenseList.add(builder.build());

            builder.setAmount("4.99");
            builder.setDescription("Might zinger burger");
            builder.setTag("food");
            builder.setTime("12:00 08/11/2019");
            expenseList.add(builder.build());

            builder.setAmount("3.80");
            builder.setDescription("gong cha");
            builder.setTag("drinks");
            builder.setTime("14:00 09/11/2019");
            expenseList.add(builder.build());

            builder.setAmount("78.50");
            builder.setDescription("uniqlo");
            builder.setTag("clothes");
            builder.setTime("14:00 09/06/2019");
            expenseList.add(builder.build());


            builder.setAmount("85");
            builder.setDescription("Mario Kart 8");
            builder.setTag("games");
            builder.setTime("14:00 09/06/2018");
            expenseList.add(builder.build());
            storage.saveExpenseList(expenseList);

            Map<String, String> planAttributes = storage.loadPlanAttributes();
            planAttributes.put("NUS_STUDENT" , "TRUE");
            planAttributes.put("ONLINE_SHOPPING" , "100");
            planAttributes.put("MUSIC_SUBSCRIPTION" , "TRUE");
            planAttributes.put("PHONE_BILL" , "30.00");
            planAttributes.put("NETFLIX" , "TRUE");
            storage.savePlanAttributes(planAttributes);



        } catch (DukeException e) {
            e.printStackTrace();
        }
        return  storage;
    }

}
