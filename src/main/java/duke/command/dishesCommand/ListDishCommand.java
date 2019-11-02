package duke.command.dishesCommand;


import duke.command.Command;
import duke.dish.Dish;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ui.Ui;



public class ListDishCommand extends Command<Dish> {
    //private TextTable tt;
    private String[] ColNames = {"Dish", "ingredient"}; //initialize the column names of the table


    @Override
    public void execute(GenericList<Dish> dish1, Ui ui, Storage storage) throws DukeException {
        Object[][] data = new Object[dish1.size()][2]; //using text utils to display data in form of a table
        if (dish1.size() == 0) { //if list is empty
            throw new DukeException("No Dishes yet!");
        } else {
            System.out.println("\t Here are the dishes in your list:");
            for( int a = 0; a < dish1.size(); a++) { //store each dish along with its ingredients into data
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(dish1.getEntry(a).toString());
                data[a][0] = dish1.getEntry(a).getDishname();
                data[a][1] = stringBuilder;
            }
            //tt = new TextTable(ColNames, data); //place data in table
            //tt.setAddRowNumbering(true);
            //tt.printTable(); //print out table to user
        }
    }
}
