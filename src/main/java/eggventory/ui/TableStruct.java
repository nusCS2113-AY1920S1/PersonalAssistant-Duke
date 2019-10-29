package eggventory.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//@@author Raghav-B
public class TableStruct {
    private String tableName;
    private ArrayList<String> tableColumns;
    private ArrayList<ArrayList<String>> tableData;

    /**
     * TableStruct contains data that is to be inserted into the GUI table. To draw data
     * in the GUI, create a valid TableStruct and pass it into the Gui.drawTable() method.
     * @param tableName Name of table (will be displayed in GUI).
     */
    public TableStruct(String tableName) {
        this.tableName = tableName;
        this.tableColumns = new ArrayList<>();
    }

    /**
     * Sets names for table columns. REQUIRED. Ensure these are same as the number of
     * columns in the tableData ArrayList.
     * @param tableColumns Varargs for columns to create.
     */
    public void setTableColumns(String... tableColumns) {
        this.tableColumns.add("No.");
        this.tableColumns.addAll(Arrays.asList(tableColumns));
    }

    /**
     * Stores an ArrayList of data that will be later drawn in the GUI.
     * @param tableData ArrayList of data to store in TableStruct.
     */
    public void setTableData(ArrayList<ArrayList<String>> tableData) {
        this.tableData = tableData;
    }

    public String getTableName() {
        return tableName;
    }

    public int getTableColumnSize() {
        return tableColumns.size();
    }

    public int getTableRowSize() {
        return tableData.size();
    }

    public String getColumnName(int column) {
        return tableColumns.get(column);
    }

    public ArrayList<String> getRowData(int row) {
        return tableData.get(row);
    }
}
