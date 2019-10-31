package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.list.GenericList;
import duke.storage.Storage;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FindToday extends Cmd<Ingredient> {
    private Date today = new Date();
    private String pattern = "dd/MM/yyyy";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    private String TodayDate = simpleDateFormat.format(today);


    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(GenericList<Ingredient> IngredientsList, Ui ui, Storage storage) {
        int i = 1;
        StringBuilder sb = new StringBuilder();

        for (Ingredient ingredient : IngredientsList.getAllEntries()) {     //for every ingredient, scan through the ingredientslist
            if (ingredient.getName().contains(TodayDate)) {
                sb.append("\t ").append(i++).append(".").append(IngredientsList.toString());
                sb.append(System.lineSeparator());
            }
        }
        if (sb.length() == 0) {
            System.out.println("No ingredients for today!");
        } else {
            System.out.println("\t Here are the ingredients for today");
            sb.setLength(sb.length() - 1);// to remove the last new line
            ui.showTask("\t " + i + "." + sb.toString());
            //ui.showTask(sb.toString());
            //System.out.println(sb.toString());
        }

    }
}

