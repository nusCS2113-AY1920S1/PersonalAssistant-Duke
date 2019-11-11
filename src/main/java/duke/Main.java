package duke;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.logic.Logic;
import duke.logic.LogicManager;
import duke.model.Income;
import duke.model.IncomeList;
import duke.model.Expense;
import duke.model.ExpenseList;
import duke.model.DukePP;
import duke.model.Model;
import duke.model.payment.Payment;
import duke.model.payment.PaymentList;
import duke.storage.BudgetStorage;
import duke.storage.BudgetViewStorage;
import duke.storage.ExpenseListStorage;
import duke.storage.ExpenseListStorageManager;
import duke.storage.IncomeListStorage;
import duke.storage.IncomeListStorageManager;
import duke.storage.PlanAttributesStorage;
import duke.storage.PlanAttributesStorageManager;
import duke.storage.Storage;
import duke.storage.StorageManager;
import duke.storage.payment.PaymentListStorage;
import duke.storage.payment.PaymentListStorageManager;
import duke.ui.Ui;
import duke.ui.UiManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
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
        if (storage.loadExpenseList().internalSize() == 0 || storage.loadExpenseList() == null) {
            loadListDemoData(storage);
        }
        if (storage.loadPaymentList().isEmpty()) {
            logger.warning("PaymentList is not loaded");
        }
        if (storage.loadExpenseList() == null) {
            logger.warning("expenseList is not loaded");
        }
        if (storage.loadIncomeList() == null) {
            logger.warning("incomeList is not loaded");
        }
        if (storage.loadBudget() == null) {
            logger.warning("budgetList is not loaded");
        }

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
        Expense.Builder expenseBuilder = new Expense.Builder();
        Income.Builder incomeBuilder = new Income.Builder();
        Payment.Builder paymentBuilder = new Payment.Builder();
        try {
            // loading expense demo data
            expenseBuilder.setAmount("3.50");
            expenseBuilder.setDescription("Chicken Rice");
            expenseBuilder.setTag("FOOD");
            expenseBuilder.setTime("18:00 09/11/2019");
            ExpenseList expenseList = storage.loadExpenseList();
            expenseList.add(expenseBuilder.build());

            expenseBuilder.setAmount("5.50");
            expenseBuilder.setDescription("Pineapple Fried Rice");
            expenseBuilder.setTag("FOOD");
            expenseBuilder.setTime("18:00 08/11/2019");
            expenseList.add(expenseBuilder.build());

            expenseBuilder.setAmount("4.99");
            expenseBuilder.setDescription("Mighty Zinger Burger");
            expenseBuilder.setTag("FOOD");
            expenseBuilder.setTime("12:00 08/11/2019");
            expenseList.add(expenseBuilder.build());

            expenseBuilder.setAmount("3.80");
            expenseBuilder.setDescription("Gong Cha");
            expenseBuilder.setTag("DRINKS");
            expenseBuilder.setTime("14:00 09/11/2019");
            expenseList.add(expenseBuilder.build());

            expenseBuilder.setAmount("78.50");
            expenseBuilder.setDescription("Uniqlo");
            expenseBuilder.setTag("CLOTHES");
            expenseBuilder.setTime("14:00 09/06/2019");
            expenseList.add(expenseBuilder.build());

            expenseBuilder.setAmount("85");
            expenseBuilder.setDescription("Mario Kart 8");
            expenseBuilder.setTag("GAMES");
            expenseBuilder.setTime("14:00 09/06/2018");
            expenseList.add(expenseBuilder.build());
            storage.saveExpenseList(expenseList);

            // loading income demo data
            incomeBuilder.setAmount("400");
            incomeBuilder.setDescription("Pocket Money");
            IncomeList incomeList = storage.loadIncomeList();
            incomeList.add(incomeBuilder.build());

            incomeBuilder.setAmount("250.70");
            incomeBuilder.setDescription("Part-Time Job");
            incomeList.add(incomeBuilder.build());
            storage.saveIncomeList(incomeList);

            // loading plan bot demo data
            Map<String, String> planAttributes = storage.loadPlanAttributes();
            planAttributes.put("NUS_STUDENT", "TRUE");
            planAttributes.put("ONLINE_SHOPPING", "100");
            planAttributes.put("MUSIC_SUBSCRIPTION", "TRUE");
            planAttributes.put("PHONE_BILL", "30.00");
            planAttributes.put("NETFLIX", "TRUE");
            storage.savePlanAttributes(planAttributes);

            // loading payment demo data

            paymentBuilder.setDescription("Raffles Hall Orientation Fee");
            paymentBuilder.setAmount("60").setTag("school life").setDue("05/01/2020");
            paymentBuilder.setPriority("Low").setReceiver("Raffles Hall");
            PaymentList paymentList = storage.loadPaymentList().get();
            paymentList.add(paymentBuilder.build());

            logger.info("*********loading sample payment");

            paymentBuilder.setDescription("Matriculation Card Replacement Fee");
            paymentBuilder.setAmount("30").setTag("school life").setDue("08/12/2019");
            paymentBuilder.setPriority("High").setReceiver("OSA");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("Top Up Mobile Data for November");
            paymentBuilder.setAmount("10").setTag("phone bill").setDue("01/11/2019");
            paymentBuilder.setPriority("Low").setReceiver("Singtel");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("Raffles Hall Room Preservation Fee");
            paymentBuilder.setAmount("200").setTag("housing").setDue("05/12/2019");
            paymentBuilder.setPriority("High").setReceiver("Raffles Hall");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("Pay Back Money to John");
            paymentBuilder.setAmount("35").setTag("loan").setDue("05/11/2019");
            paymentBuilder.setPriority("Medium").setReceiver("John");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("Pay Back Money to Alice");
            paymentBuilder.setAmount("15").setTag("loan").setDue("17/11/2019");
            paymentBuilder.setPriority("Medium").setReceiver("Alice");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("Repay OCBC Student Loan for November");
            paymentBuilder.setAmount("600").setTag("loan").setDue("30/11/2019");
            paymentBuilder.setPriority("High").setReceiver("OCBC");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("In Room Storage Fee");
            paymentBuilder.setAmount("150").setTag("housing").setDue("19/11/2019");
            paymentBuilder.setPriority("Medium").setReceiver("Alice");
            paymentList.add(paymentBuilder.build());

            paymentBuilder.setDescription("PhotoShop Camp Sign Up Fee");
            paymentBuilder.setAmount("60").setTag("study").setDue("03/12/2019");
            paymentBuilder.setPriority("Medium").setReceiver("NUSSU CommIT");
            paymentList.add(paymentBuilder.build());

            try {
                storage.savePaymentList(paymentList);
            } catch (IOException e) {
                logger.warning("Sample data did not save");
            }

        } catch (DukeException e) {
            e.printStackTrace();
        }
        return storage;
    }

}
