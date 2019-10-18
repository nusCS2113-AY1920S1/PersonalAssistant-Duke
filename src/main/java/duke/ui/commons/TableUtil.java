package duke.ui.commons;

import duke.model.product.Product;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class TableUtil {

    private static final String INDEX_COLUMN_HEADER = "S/N";
    public static void setUpIndexColumn(TableView table) {
        //Solution below adapted from: https://stackoverflow.com/questions/31212400/adding-index-of-records-in-a-javafx-tableview-column
        TableColumn<Product, Void> indexColumn = new TableColumn<>(INDEX_COLUMN_HEADER);
        indexColumn.setResizable(true);
        indexColumn.setMinWidth(50);
        indexColumn.setMaxWidth(50);
        indexColumn.setCellFactory(col -> {
            TableCell<Product, Void> cell = new TableCell<>();
            cell.textProperty().bind(Bindings.createStringBinding(() -> {
                if (cell.isEmpty()) {
                    return null;
                } else {
                    return Integer.toString(cell.getIndex() + 1);
                }
            }, cell.emptyProperty(), cell.indexProperty()));
            return cell;
        });
        table.getColumns().clear();
        table.getColumns().add(indexColumn);
    }

}
