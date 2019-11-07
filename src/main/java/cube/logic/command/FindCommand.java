//@@author ZKathrynx
package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.model.food.FoodList.SortType;
import cube.model.food.FoodList;
import cube.model.ModelManager;
import cube.storage.StorageManager;
import cube.logic.command.util.CommandResult;
import cube.logic.command.util.CommandUtil;

public class FindCommand extends Command{

    /**
     * Enum to indicate the type of delete.
     */
    public enum FindBy {
        INDEX, NAME, TYPE
    }

    SortType sortType;

    private int findIndex;
    private String findDescription;
    private FindBy param;
    private final String MESSAGE_SUCCESS_SINGLE = "This is the food you want to find:\n"
            + "%1$s\n";
    private final String MESSAGE_SUCCESS_MULTIPLE = "There are in total %1$s food you want to find:\n"
            + "%2$s\n";

    /**
     * Default constructor.
     */
    public FindCommand() {}

    /**
     * Constructor for delete using index.
     * @param index the index to be deleted.
     * @param param the parameter to indicate type of deletion.
     */
    public FindCommand(int index, String param) {
        this.findIndex = index - 1;
        this.param = FindBy.valueOf(param);
    }

    /**
     * Constructor for delete using food name or food type.
     * @param description the food name or food type to be deleted.
     * @param param the parameter to indicate type of deletion.
     */
    public FindCommand(String description, String param){
        this.findDescription = description;
        this.param = FindBy.valueOf(param);
    }

    /**
     * Constructor for delete using food name or food type.
     * @param description the food name or food type to be deleted.
     * @param param the parameter to indicate type of deletion.
     */
    public FindCommand(String description, String param, SortType sortType){
        this.findDescription = description;
        this.param = FindBy.valueOf(param);
        this.sortType = sortType;
    }

    @Override
    public CommandResult execute(ModelManager model, StorageManager storage) throws CommandException {
        FoodList list = model.getFoodList();
        switch (param) {
            case INDEX:
                CommandUtil.requireValidIndex(list, findIndex);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, list.get(findIndex), list.size()));
            case NAME:
                FoodList result = new FoodList();
                int count = 0;
                for (int i = 0; i < list.size(); i ++) {
                    if (list.get(i).getName().toLowerCase().contains(findDescription.toLowerCase())) {
                        result.add(list.get(i));
                        count ++;
                    }
                }
                if (sortType != null) {
                    result.sort(sortType);
                }
                if (count == 0) {
                    throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, count,result,list.size()));
            case TYPE:
                CommandUtil.requireValidType(list, findDescription);
                result = new FoodList();
                count = 0;
                for (int i = 0; i < list.size(); i ++) {
                    if (list.get(i).getType()!=null && list.get(i).getType().toLowerCase().equals(findDescription.toLowerCase())) {
                        result.add(list.get(i));
                        count ++;
                    }
                }
                if (sortType != null) {
                    result.sort(sortType);
                }
                return new CommandResult(String.format(MESSAGE_SUCCESS_MULTIPLE, count,result,list.size()));
        }
        return null;
    }
}