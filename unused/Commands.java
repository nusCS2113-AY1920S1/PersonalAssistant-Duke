//@@author talesrune-unused
/**
 * Code is not used as we are not using CLI as our final product.
 * It is used for testing purposes when adding new features or commands.
 *
 */
/**
 * Code snippet from Command
 */
//    /**
//     * Executes a command with task list and ui.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is executed successfully.
//     */
//    public abstract void execute(TaskList items, Ui ui);

//@@author maxxyx96-unused
/**
 * Code snippet from AddBudgetCommand
 */
//    /**
//     * Executes the command to add a certain amount to the existing budget.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui    To tell the user that it is executed successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showAddBudget(amount, budgetList.getBudget());
//        budgetList.addToBudget(Float.toString(amount), remark);
//        ui.showBudget(budgetList.getBudget());
//    }

//@@author talesrune-unused
/**
 * Code snippet from AddCommand
 */
//    /**
//     * Executes a command that adds the task into task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is added successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        items.add(task);
//        ui.showAdd(items);
//    }

//@@author e0318465-unused
/**
 * Code snippet from AddContactsCommand
 */
//    /**
//     * Adds the user input to a list of contacts.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is executed successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        contactList.add(contactObj);
//        ui.showAddedContact(contactList);
//    }

//@@author talesrune-unused
/**
 * Code snippet from AddNotesCommand
 */
//    /**
//     * Executes a command that adds or updates the notes of the task in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is added or updated successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        items.get(index).setNotes(notesDesc);
//        ui.showAddNotes(items, index);
//    }

//@@author talesrune-unused
/**
 * Code snippet from DeleteCommand
 */
//    /**
//     * Executes a command that deletes the task from the task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is deleted successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        String deletedStr = "       " + items.get(index).toString();
//        items.remove(index);
//        ui.showDelete(items, deletedStr);
//    }

//@@author e0318465-unused
/**
 * Code snippet from DeleteContactCommand
 */
//    /**
//     * Executes a command that deletes the contact from the contact list.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is deleted successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        if (indexOfContactToDelete >= Numbers.ZERO.value && indexOfContactToDelete
//                + Numbers.ONE.value <= contactList.size()) {
//            String deletedContact = contactList.getAndDisplay(indexOfContactToDelete);
//            contactList.remove(indexOfContactToDelete);
//            ui.showContactDeleted(contactList, deletedContact);
//        } else if (contactList.size() == Numbers.ZERO.value) {
//            ui.showErrorMsgGui("     No contacts to be deleted!");
//        } else {
//            ui.showErrorMsg("     Invalid index! Please choose 1 "
//                    + ((contactList.size() == Numbers.ONE.value) ? "" : "to " + contactList.size()));
//        }
//    }

//@@author talesrune-unused
/**
 * Code snippet from DeleteNotesCommand
 */
//    /**
//     * Executes a command that adds or updates the notes of the task in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is added or updated successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        String deletedNotes = items.get(index).getNotes();
//        items.get(index).deleteNotes();
//        ui.showDeleteNotes(items, index, deletedNotes);
//    }

//@@author talesrune-unused
/**
 * Code snippet from DoneCommand
 */
//    /**
//     * Executes a command that marks the task as done in the task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is marked as done successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        items.get(index).setStatusIcon(true);
//        ui.showDone(items, index);
//    }

//@@author maxxyx96-unused
/**
 * Code snippet from DuplicateFoundCommand
 */
//    /**
//     * Outputs the alert when duplicated task is detected.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is marked as done successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showDuplicateMsg();
//    }


//@@author talesrune-unused
/**
 * Code snippet from FilterCommand
 */
//    /**
//     * Executes a command that filters tasks in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user the filtered tasks based on the task's type.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showFilter(items, taskType);
//    }

//@@author talesrune-unused
/**
 * Code snippet from FindCommand
 */
//    /**
//     * Executes a command that locates matching tasks in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user the matching tasks based on the keyword.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showFind(items, keyword);
//    }

//@@author e0318465-unused
/**
 * Code snippet from FindContactCommand
 */
//    /**
//     * Executes a command that locates matching contacts in contact list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user the matching tasks based on the keyword.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showFoundContacts(contactList, keyword);
//    }

//@@author talesrune-unused
/**
 * Code snippet from ListContactsCommand
 */
//    /**
//     * Executes a command that gathers all tasks from task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user the list of tasks stored in task list.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showTaskList(items);
//    }

//@@author e0318465-unused
/**
 * Code snippet from ListContactsCommand
 */
//    /**
//     * Executes a command that gathers all contacts from contact list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user the matching tasks based on the keyword.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showContactList(contactList);
//    }

//@@author gervaiseang-unused
/**
 * Code snippet from RemindCommand
 */
//    /**
//     * Executes a command that sets a reminder of a task in a specified noOfDays.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is sets a reminder successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        Task task = items.getTasks().get(taskIndex);
//        task.setReminder(reminder);
//        ui.showErrorMsg(String.format("You will get a reminder for this task in %d days", reminder));
//        ui.showErrorMsg(" " + task.toString());
//    }

//@@author maxxyx96-unused
/**
 * Code snippet from ResetBudgetCommand
 */
//    /**
//     * Executes a command with task list and ui.
//     * (not used)
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui    To tell the user that it is executed successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showResetBudget(budgetList.getBudget());
//        budgetList.resetBudget(amount);
//        ui.showBudget(budgetList.getBudget());
//    }

//@@author talesrune-unused
/**
 * Code snippet from ShowNotesCommand
 */
//    /**
//     * Executes a command that adds or updates the notes of the task in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is added or updated successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showNotes(items, index);
//    }

//@@author talesrune-unused
/**
 * Code snippet from UpdateCommand
 */
//    /**
//     * Executes a command that updates the task in task list and outputs the result.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui To tell the user that it is updated successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        try {
//            if (typeOfUpdate == Numbers.ONE.value) {
//                items.get(index).setDescription(taskDesc);
//            } else if (typeOfUpdate == Numbers.TWO.value) {
//                if (items.get(index) instanceof Todo || items.get(index) instanceof FixedDuration) {
//                    throw new DukeException("     (>_<) OOPS!!! This task does not have date and time!");
//                }
//                items.get(index).setDateTime(dateDesc);
//            } else if (typeOfUpdate == Numbers.THREE.value) {
//                Task newtaskObj = null;
//                if (typeDesc.equals("todo")) {
//                    if (items.get(index) instanceof Todo) {
//                        throw new DukeException("     You are updating the same type of task! (Todo)");
//                    } else {
//                        newtaskObj = new Todo(items.get(index).getDescription());
//                    }
//                } else if (typeDesc.equals("deadline")) {
//                    if (items.get(index) instanceof Deadline) {
//                        throw new DukeException("     You are updating the same type of task! (Deadline)");
//                    } else {
//                        newtaskObj = new Deadline(items.get(index).getDescription(), "01/01/2001 0001");
//                    }
//                } else if (typeDesc.equals("fixedduration")) {
//                    if (items.get(index) instanceof FixedDuration) {
//                        throw new DukeException("     You are updating the same type of task! (FixedDuration)");
//                    } else {
//                        newtaskObj = new FixedDuration(items.get(index).getDescription(), Numbers.ZERO.value, "min");
//                    }
//                }
//                items.setTaskType(index, newtaskObj);
//            }
//            ui.showUpdate(items, index);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            ui.showErrorMsg(e.getMessage());
//        }
//
//    }

//@@author maxxyx96-unused
/**
 * Code snippet from ViewBudgetCommand
 */
//    /**
//     * Executes a command with task list and ui.
//     * (not used)
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param ui    To tell the user that it is executed successfully.
//     */
//    @Override
//    public void execute(TaskList items, Ui ui) {
//        ui.showBudget(budgetList.getBudget());
//    }

