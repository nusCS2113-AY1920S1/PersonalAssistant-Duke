package duke.command.dishesCommand;


import dnl.utils.text.table.TextTable;
import duke.command.Command;
import duke.dish.DishList;
import duke.exception.DukeException;
import duke.fridge.Fridge;
import duke.order.OrderList;
import duke.storage.FridgeStorage;
import duke.storage.OrderStorage;
import duke.ui.Ui;

public class ListDishCommand extends Command {
  //private TextTable tt;
  //private String[] ColNames = {"Dish", "ingredient"}; //initialize the column names of the table

    /**
     *
     * @param fridge
     * @param dishList
     * @param ol
     * @param ui
     * @param fs
     * @param os
     * @throws DukeException
     *
     * @@author  Hafidz
     */
    @Override
    public void execute(Fridge fridge, DishList dishList, OrderList ol, Ui ui, FridgeStorage fs, OrderStorage os) throws DukeException {
        //Object[][] data = new Object[dishList.size()][2]; //using text utils to display data in form of a table
        if (dishList.size() == 0) { //if list is empty
            throw new DukeException("No Dishes yet!");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\t Here are the dishes in your list:");
            for( int a = 0; a < dishList.size(); a++) { //store each dish along with its ingredients into data
                stringBuilder.append("\n\t" + (a+1) + ". ");
                stringBuilder.append(dishList.getEntry(a).toString());
                //data[a][0] = dishList.getEntry(a).getDishname();
                //data[a][1] = stringBuilder;
            }
            System.out.println(stringBuilder.toString());
          }
          //tt = new TextTable(ColNames, data); //place data in table
          //tt.setAddRowNumbering(true);
          //tt.printTable(); //print out table to user
        }
    }
