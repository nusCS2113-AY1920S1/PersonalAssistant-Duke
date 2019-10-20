package cube.logic.command;

import cube.logic.command.exception.CommandErrorMessage;
import cube.logic.command.exception.CommandException;
import cube.model.FoodList.SortType;
import cube.model.FoodList;
import cube.storage.StorageManager;

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

    /**
     * Check whether a given index is a valid index or not.
     * @param list the food list.
     * @throws CommandException
     */
    private void checkValidIndex(FoodList list) throws CommandException {
        if (findIndex < 0 || findIndex >= list.size()) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * Check whether a given food name is inside the food list or not.
     * @param list the food list.
     * @throws CommandException
     */
    private void checkValidName(FoodList list) throws CommandException {
        if (!list.existsName(findDescription)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    /**
     * Check whether a given food type is inside the food list or not.
     * @param list the food list.
     * @throws CommandException
     */
    private void checkValidType(FoodList list) throws CommandException {
        if (!list.existsType(findDescription)) {
            throw new CommandException(CommandErrorMessage.FOOD_NOT_EXISTS);
        }
    }

    @Override
    public CommandResult execute(FoodList list, StorageManager storage) throws CommandException {
        switch (param) {
            case INDEX:
                checkValidIndex(list);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, list.get(findIndex), list.size()));
            case NAME:
                checkValidName(list);
                return new CommandResult(String.format(MESSAGE_SUCCESS_SINGLE, list.get(findDescription), list.size()));
            case TYPE:
                checkValidType(list);
                FoodList result = new FoodList();
                int count = 0;
                for (int i = 0; i < list.size(); i ++) {
                    if (list.get(i).getType()!=null && list.get(i).getType().equals(findDescription)) {
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