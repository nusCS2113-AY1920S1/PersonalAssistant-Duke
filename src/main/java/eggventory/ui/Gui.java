package eggventory.ui;

import eggventory.StockList;
import eggventory.items.Stock;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This is a controller class used to control the Gui.fxml from the entry point for
 * our application, the Eggventory class. Inherits most of its functionality from Cli
 * to ensure backwards compatibility with testing and if we choose to fall back to a
 * Cli implementation. Overrides some Cli functionality to interface with the Gui instead
 * of Cli.
 */
public class Gui extends Cli {
    private TextField inputField;
    private TextArea outputField;
    private TableView outputTable;
    private ScrollPane outputTableScroll;

    /**
     * Takes in references to some of the nodes in the JavaFX Gui, so that they can be
     * controlled by Command.execute() functions and changes can be represented in the Gui
     * instead of Cli.
     * @param inputField Where the user can place input.
     * @param outputField Where the user will see Eggventory's text output.
     * @param outputTable User will see Eggventory's table output here.
     * @param outputTableScroll Scroll node to control outputTable scroll property.
     */
    public Gui(TextField inputField, TextArea outputField, TableView outputTable,
               ScrollPane outputTableScroll) {
        this.inputField = inputField;
        this.outputField = outputField;
        this.outputTable = outputTable;
        this.outputTableScroll = outputTableScroll;
    }

    /**
     * Prints text output in the outputField TextArea.
     * @param printString The raw String to be printed out, after some extra formatting.
     */
    @Override
    public String print(String printString) {
        String output; //= super.print(printString);
        output = addIndent() + addLine() + "\n";

        String[] linesToPrint = printString.split("\n", 0);
        for (int i = 0; i < linesToPrint.length; i++) {
            output += (addIndent() + linesToPrint[i]) + "\n";
        }
        output += addIndent() + addLine() + "\n";

        outputField.appendText("\n" + output);
        //outputField.setScrollTop(Double.MAX_VALUE);

        return output;
    }

    /**
     * Prints opening message.
     */
    @Override
    public void printIntro() {
        String logo = "  _      __    __                     __         ____         _   __         __               \n"
                + " | | /| / /__ / /______  __ _  ___   / /____    / __/__ ____ | | / /__ ___  / /____  ______ __\n"
                + " | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\  / _// _ `/ _ `/ |/ / -_) _ \\/ __/ _ \\/"
                + " __/ // /\n"
                + " |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/ /___/\\_, /\\_, /|___/\\__/_//_/\\__/\\___/_/"
                + "  \\_, / \n"
                + "                                                  /___//___/                           /___/  \n";

        outputField.appendText(logo);
        print("Hello! I'm Humpty Dumpty\n" + "What can I do for you?");
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
}
