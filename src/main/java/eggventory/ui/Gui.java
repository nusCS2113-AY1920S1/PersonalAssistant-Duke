package eggventory.ui;

import eggventory.StockList;
import eggventory.Storage;
import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.items.Stock;
import eggventory.parsers.Parser;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class Gui extends Application {
    @FXML
    private TextField inputField;
    @FXML
    private TextArea outputField;
    @FXML
    private TableView outputTable;
    @FXML
    private ScrollPane outputTableScroll;

    /**
     * Prints eggventory introduction message.
     */
    public void printIntro() {
        String logo = "  _      __    __                     __         ____         _   __         __               \n"
                + " | | /| / /__ / /______  __ _  ___   / /____    / __/__ ____ | | / /__ ___  / /____  ______ __\n"
                + " | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\  / _// _ `/ _ `/ |/ / -_) _ \\/ __/ _ \\/"
                + " __/ // /\n"
                + " |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/ /___/\\_, /\\_, /|___/\\__/_//_/\\__/\\___/_/"
                + "  \\_, / \n"
                + "                                                  /___//___/                           /___/  \n";

        outputField.appendText(logo + "Hello! I'm Humpty Dumpty\n" + "What can I do for you?");
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/Gui.fxml"));
            fxmlLoader.setController(this);
            Stage ap = fxmlLoader.load();
            ap.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/data/saved_tasks.txt";

        Cli cli = new Cli();
        Storage storage = new Storage(filePath);
        StockList stockList = storage.load();
        Parser parser = new Parser();
        printIntro();

        // Event handler for user pressing enter in the text input.
        inputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    String userInput = inputField.getText();
                    inputField.setText("");
                    outputField.appendText("\n" + userInput);

                    try {
                        Command command = parser.parse(userInput);
                        command.execute(stockList, cli, storage);
                        //drawTable(stockList);

                        if (command.getType() == CommandType.BYE) {
                            System.exit(0);
                        }
                    } catch (Exception e) {
                        printError(e);
                    }
                }
            }
        });
    }

    /**
     * Can be called to redraw the table output as and when needed. Takes in a StockList object
     * that it uses to redraw the entire table.
     * @param stockList Input StockList object to be used to draw the table.
     */
    public void drawTable(StockList stockList) {
        TableColumn<String, Stock> stockTypeCol = new TableColumn<>("Stock Type");
        stockTypeCol.setCellValueFactory(new PropertyValueFactory<>("stockType"));

        TableColumn<String, Stock> stockCodeCol = new TableColumn<>("Stock Code");
        stockCodeCol.setCellValueFactory(new PropertyValueFactory<>("stockCode"));

        TableColumn<Integer, Stock> quantityCol = new TableColumn<>("Quantity");
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<String, Stock> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        /*for (int i = 0; i < stockList.getStockQuantity(); i++) {
            for (int i = 0; i < stockList.getStockType().getQuantity(); i++) {
                outputTable.getItems().add(stockList.getStockType())
            }
        }
        // TODO: Can add more columns for loaned, lost, and minimum later...
        */

        outputTable.getColumns().removeAll();
        outputTable.getColumns().addAll(stockTypeCol, stockCodeCol, quantityCol, descriptionCol);
    }

    /**
     * Prints text output in the outputField TextArea to essentially replace the Cli.print()
     * command.
     * @param printString The raw String to be printed out, after some extra formatting.
     */
    public void print(String printString) {
        String line = "____________________________________________________________";
        String output;
        output = addIndent() + line + "\n";

        String[] linesToPrint = printString.split("\n", 0);
        for (int i = 0; i < linesToPrint.length; i++) {
            output += (addIndent() + linesToPrint[i]) + "\n";
        }
        output += addIndent() + line + "\n";

        //outputField.setText(outputField.getText() + "\n" + output);
        //outputField.setScrollTop(Integer.MAX_VALUE);
        outputField.appendText("\n" + output);
    }

    public void printError(Exception e) {
        print("Parser error: \n" + e);
    }

    public static String addIndent() {
        return "        ";
    }
}
