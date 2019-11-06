//@@author unused
/**
 * Code is not used as we are not using CLI as our final product.
 * It is used for testing purposes when adding new features or commands.
 *
 */
/**
 * Code snippet from Duke
 */
//    /**
//     * Runs the duke program until exit command is executed.
//     */
//    public void run() {
//        ui.showWelcome();
//        //Ui.showReminder(items);
//        String sentence;
//
//        while (true) {
//            sentence = ui.readCommand();
//            ui.showLine();
//            try {
//                Command cmd = Parser.parse(sentence, items, budgetList, contactList);
//                if (cmd instanceof ExitCommand) {
//                    priorityStorage.write(priorityList);
//                    budgetStorage.write(budgetList);
//                    contactStorage.write(contactList);
//                    cmd.executeStorage(items, ui, storage);
//                    break;
//                } else if (cmd instanceof ListPriorityCommand
//                        || cmd instanceof DeleteCommand
//                        || cmd instanceof SetPriorityCommand
//                        || cmd instanceof FindTasksByPriorityCommand) {
//                    cmd.execute(items, priorityList, ui);
//                } else if (cmd instanceof BackupCommand) {
//                    priorityStorage.write(priorityList);
//                    budgetStorage.write(budgetList);
//                    contactStorage.write(contactList);
//                    storage.write(items);
//                    cmd.execute(items, ui);
//                    cmd.executeStorage(items, ui, storage);
//                } else {
//                    cmd.execute(items,ui);
//                    priorityList = priorityList.addDefaultPriority(cmd);
//                }
//            } catch (DukeException e) {
//                ui.showErrorMsg(e.getMessage());
//            } catch (Exception e) {
//                ui.showErrorMsg("     New error, please fix:");
//                logr.log(Level.WARNING,"New error, please fix", e);
//                e.printStackTrace();
//                ui.showErrorMsg("     Duke will continue as per normal.");
//            } finally {
//                ui.showLine();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        new Duke().run();
//    }