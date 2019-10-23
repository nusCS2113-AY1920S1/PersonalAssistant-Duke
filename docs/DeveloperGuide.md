# Duke - Developer Guide

1. Setting Up

2. Design 

   1. Architecture
   2. UI
   3. Command Component
   4. Parser Component
   5. Storage Component
   6. Task Component
   7. Exception Component
   8. Recipebook Component
   9. RecipeCommand Component
   10. Order Component
   11. OrderCommand Component
   12. Fridge Component
   13. GenericList
   14. Ingredient

3. Implementation

4. Documentation

5. Testing

6. Dev Ops

   Appendix A: Product Scope

   Appendix B: User Stories

   Appendix C: Use Cases

   Appendix D: Non Functional Requirements 

   Appendix E: Glossary 

   Appendix F: Product Survey 

   Appendix G: Instructions for Manual



### 1. Setting Up

- see the Guide [Here]() 

### 2. Design 

#### 2.1  Architecture

![architecture]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/architectureV1.1.png )

`main` has 1 class called `Duke`. It is responsible for,

- at app launch: Loads all the data in storage into the application, initialize the  components, reads the commands and executes them correctly.
- at shut down: shuts down all component and exits the application

The Application consist of 6 other components 

- `command`: executes the command that is read from the user

- `exception`: handle error messages 
- `parser`: determine the next course of action from the user command
- `storage`: Reads, writes data from and to the hard disk
- `task`: stores a list of deadline/event/todo that needs to be done
- `ui`: The UI of the application

![sequence]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/UMLsequence.png )

The diagram above shows the program sequence when the user enters the command `todo buy eggs` or `list`. 

#### 2.2 UI


API: `Ui.java`

The Ui class consists of methods that outputs messages to the user as a response when the user enters a certain command.

The Ui component contains all the messages or replies whenever the User enters a command for example if the user enters:

`dishadd chicken rice /num 2`

`dishdelete 1`

The Ui will reply to the User with the following messages:

```
	 ____________________________________________________________
	 you have added the following dish: 
	 chicken rice 	amount: 2
	 ____________________________________________________________
```

```
	 ____________________________________________________________
	 The following dish have been removed:
	 chicken rice
	 ____________________________________________________________
```
The Ui class consists of methods that outputs messages to the user as a response when the user enters a certain command

- reads and return s user input using `scanner.nextLine()`
- outputs messages to the user as a response such as `showAddCommand`, `showRemoveCommand`, etc

#### 2.3 Command Component

API: `Command.java`

In the project, it has three types of commands: Ingredient Command, Dishes Command, Order Command. The three types of commands are packaged separately.

The Command class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method. that is used by the following classes 

- DoneCommand
- ExitCommand
- FindCommand
- ListCommand
- RemindCommand
- Snooze
- ViewCommand

each of the above class has its own implementation of the `execute` method

#### 2.4 Parser Component

API: `Parser.java`



#### 2.5 Storage Component

API: `Storage.java` 

This component  stores entries in a certain format, tasks, ingredients that are already in the Fridge, and anything else that needs to be saved on the hard disk.

It is modeled as an abstract class,  with `TaskStorage.java` and `FridgeStorage.java` both inheriting from it. It allows data (tasks in the list, ingredients in the fridge, recipes in the recipeBook...) to be saved and remembered by our program.  

An example for the format of saving for tasks is :

- T|1|read book
- D|0|return book|Monday
- E|0|meeting|U-Town
- P|0|lecture|1600|1800

where the first column is denotes the type of task, T for todo, D for deadline, etc. 

The program can `load` or `generate` an entry from the storage and also `changeContent` and `addInFile`

![Storage](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/StorageUML.png)

#### 2.6 Task Component

API: `Task.java`



#### 2.7 Exception Component

API: `DukeException.java`

#### 2.8 Dishes Component

The Recipebook contains 2 classes, Dishes Class and DishList Class. The Dishes Class 

![Dishes]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/dishes diagram.png)

**<u>Dishes Class</u>**

| Attributes                       | Description                                     |
| -------------------------------- | ----------------------------------------------- |
| dishName: String                 | name of the dish                                |
| total: int                       | the total number of orders for that dish        |
| rating: float                    | the overall rating for that dish                |
| ingredientList: `IngredientList` | a list of ingredients associated with that dish |



| Constructor    | Description                              |
| -------------- | ---------------------------------------- |
| Dishes(String) | assigns the name of the dish with String |



| Methods                       | Description                                                  |
| ----------------------------- | ------------------------------------------------------------ |
| getTotalNumberOfOrders(): int | returns `total` which is an int                              |
| setNumberofOrders(int): void  | takes in an `int` and increment `total` number of orders     |
| clearOrders(): void           | clears the ingredient list                                   |
| getDishName(): String         | returns the name of the dish                                 |
| setRating(int): void          | takes in an `int` and sets the new overall rating of the dish |
| getRating(): float            | returns the rating of that dish                              |
| addIngredients(String): void  | takes a string and adds into ingredientlist                  |
| toString(): String            | it returns a String of all the ingredients that the dish contains |

**<u>DishList Class</u>**

this class inherits the GenericList class  which takes in a List of Dish.

| Constructor          | Description |
| -------------------- | ----------- |
| DishList(List<Dish>) |             |
| DishList()           |             |

//Todo: give elaboration for the use of this class 



#### 2.9 dishesCommand Component

The dishesCommand class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method that is used by the following classes

- AddDishCommand
  - //Todo: put in a code snippet and explain how it works and why it is done this way. same goes for the other classes
- AddIngredient
- DeleteDishCommand
- InitCommand
- ListDishCommand

![DishesCommand](https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/dishesCommand diagram.png)

//Todo: add uml diagram from intelliJ. elaborate on current classes. future plans for this component

#### 2.10 Order Component

The Order component contains 2 classes, Order Class and Order Class. The Order Class 

// Todo: Add Order class diagram here

**<u>Order Class</u>**

| Attributes                                          | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| content: Map<Dishes,                       Integer> | the content of the order, specifying ordered dishes and amount |
| isDone: boolean                                     | the status of the order: *true* if done, *false* otherwise   |
| date: Date                                          | the serving date of the order (not the date when the order was created) |



| Constructor   | Description                                                  |
| ------------- | ------------------------------------------------------------ |
| Order()       | By default, the order is not done; the serving date is today. |
| Order(String) | Assigns the serving date of the order with String. Call if it is a pre-order. |



| Methods                                | Description                                                  |
| -------------------------------------- | ------------------------------------------------------------ |
| getDate(): Date                        | returns the serving  `date` of the order                     |
| setDate(String): void                  | takes in a `String` and alter the serving `date`  of the order |
| isToday(): boolean                     | returns a `boolean` indicating whether the serving date is today or not |
| isDone(): boolean                      | returns a `boolean` indicating whether the order is finished or not |
| markAsDone(): void                     | mark the order as finished                                   |
| getStatusIcon(): String                | takes in an `int` and sets the new overall rating of the dish |
| getOrderContent(): Map<Dishes, Integer> | returns the order content as `Map`                           |
| toString(): String                     | returns description of the order as `String`                 |
| printInFile(): String                  | returns description of the order that used to store in the txt file |
| hasDishes(Dishes): boolean             | returns a `boolean` indicating whether the order has the dishes or not |
| getDishesAmount(Dishes): int           | returns the amount of the query dishes in the order          |
| addDish(Dishes): void                  | add one more the dishes to the undone order                  |
| addDish(Dishes, int): void             | add the dishes to the undone order with adding amount        |

**<u>OrderList Class</u>**

| Atrributes             | Description |
| ---------------------- | ----------- |
| orderList: List<Order> |             |



| Constructor            | Description                                        |
| ---------------------- | -------------------------------------------------- |
| OrderList()            | initalize the empty orderLIst as a new ArrayList<> |
| OrderList(List<Order>) | Assign a list of orders to the orderList           |



| Methods                              | Description                                                  |
| ------------------------------------ | ------------------------------------------------------------ |
| size(): int                          | returns the number of orders in the orderList                |
| markOrderDone(int): void             | mark a order as completed                                    |
| getOrder(int): Order                 | return the order at the position indexed by number           |
| getAllUndoneOrders(): List<Order>    | return all undone orders in the orderList                    |
| getTodayOrders(): List<Order>        | return all today's orders in the orderList                   |
| getTodayUndoneOrders(): List<Order>  | return all today's orders which is undone in the orderList   |
| findOrderByDate(String): List<Order> | returns a list of orders on that date                        |
| findOrderByDishes(Dish): List<Order> | returns a list of orders that contains that dishes           |
| changeOrderDate(int, String): void   | alter the serving date of the order in the orderList         |
| getDishesTodayAmount(Dishes): int    | return required amount of the dishes that needed to be done before the end of today |
| addOrderDish(int, Dishes): void      | add dishes to the order in the orderList                     |
| addOrderDish(int, Dishes, int): void | add dishes with amount to the order in the orderList         |
| findDishesAmont(int, Dishes): int    | find dishes amount in the order among the orderList          |

#### 

#### 2.11 Order Command Component

The Order Command classes inherits from the `Command` class. They overwrite the abstract method `execute` of the `Command` class. The Order Command classes includes:

- AddOrderCommand
- AlterDateCommand
- DeleteOrderCommand
- DoneOrderCommand
- ListOrderCommand

#### 2.12 Fridge Component
API: `Fridge.java`

The Fridge class allows access and modification of the `Ingredient`s used by the chef. By keeping track of the Ingredients' expiry date, it allows the user to know which products have expired, and remove them. It allows for less ingredient waste, as it can return the most recently expiring ingredients, so that they can be used first. 

![Fridge](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/fridgeUML.png)

#### 2.13 GenericList
API: `GenericList.java`

This abstract class allows for creation of different types of lists, and basic list entry manipulations. It is extended by multiple classes, including `IngredientsList.java`, `TaksList.java`, `OrderList.java` and `DishList.java`. All of these classes inherit the basic methods from the Generic List and extend it with their specific methods, eg.  `allUndoneOrders()` from`OrderList.java`, or `changeAmount()` from `IngredientsList.java`. A UML Class Diagram is shown below.

![GenericList](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/GenericListUML.png)

#### 2.14 Ingredient Component

The Recipebook contains 2 classes, Ingredient and IngredientsList. 

//!add diagram of the Ingredient component.

**<u>Ingredient Class</u>**

| Attributes           | Description                            |
| -------------------- | -------------------------------------- |
| name: String         | Name of the ingredient                 |
| amount: int          | Total amount of the ingredient         |
| expiryDate: Date     | Expiry date of the given ingredient    |
| dateAsString: String | A string to store the date as a string |



| Constructor                       | Description                                               |
| --------------------------------- | --------------------------------------------------------- |
| Ingredient(String, Integer, Date) | Gives the name , amount and expiry date of the ingredient |



| Methods                             | Description                               |
| ----------------------------------- | ----------------------------------------- |
| Ingredient(String, Integer, String) | Converts the Date into String             |
| getAmount(): int                    | Returns amount of ingredient              |
| getName(): String                   | Returns name of the Ingredient            |
| changeDate(Date): void              | Changes the expiry date of the ingredient |
| setName(String): void               | Sets the name of the ingredient           |
| changeAmount(Integer):              | Changes the amount of the ingredient      |
| getExpiryDate(): Date               | Returns the expiry date of the ingredient |

**<u>IngredientsList Class</u>**

| Constructor                                       | Description                                                |
| ------------------------------------------------- | ---------------------------------------------------------- |
| IngredientsList(List<Ingredient> ingredientsList) | Initializes the IngredientsList as a new List<Ingredients> |



| Methods                          | Description                                                |
| -------------------------------- | ---------------------------------------------------------- |
| changeIngredientsDate(int, Date) | Changes the date of the Ingredient using an Index number   |
| changeName(int, String)          | Changes the name of the ingredient using an Index number   |
| changeAmount(int, String)        | Changes the amount of the ingredient using an index number |

//!add commands such as FindIngredient()

#### 

### 3. Implementation

### 4. Documentation



### 5. Testing



### 6. Dev Ops 



### Appendix A: Product Scope

Target user profile: Restaurant Chef

- needs to manage all the ingredients for his dishes  
- prefers a desktop application with GUI
- prefers to keep everything neat in terms of viewing information
- can also use CLI if needed
### Appendix B: User Stories

​	Priorities: High (must have), Medium (nice to have), Low (may not have)

| Priority | As a ...           | I want to ...                                                | So that ...                                          |
| -------- | ------------------ | ------------------------------------------------------------ | ---------------------------------------------------- |
| High     | restaurant manager | I want to ensure that only specific users are able to access the application | the data related to my restaurant is secure          |
| High     | restaurant manager | I want to keep track of food expiry dates                    | the restaurant will not serve expired food           |
| High     | restaurant manager | I want to note down my part timer's available dates          | I can ensure there is enough manpower in the kitchen |
| Medium   | restaurant manager | I want to keep track of my monthly expenses                  | I know whether I am making a profit or loss          |
| Medium   | restaurant manager | I want to keep track of my monthly hygienic checks           | my restaurant could keep it's working license        |
| Medium   | restaurant manager | I want to keep track of my company meetings                  | I am able to organize all my employees               |
| Low      | restaurant manager | note down customer feedbacks regarding dishes                | I can tell if my recipe is doing well or not         |
| Low      | restaurant manager | I want to keep track of holiday dates                        | I can prepare for special food menus                 |

### Appendix C: Use Case

**Use case: Exit the program**

**MSS**

​	1. User requests to exit the program

​	2. Program exits

​		Use case ends.

**Extensions**

- 2a. Incorrect syntax

  - 2a1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case:  Show all the tasks**

​	1. User requests to view all the existing tasks

​	2. Program loads up the lists of existing tasks

​		Use case ends.

**Extensions**

- 2a. Task List is empty

​		Use case ends.

- 2b. Incorrect syntax

  - 2c1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Mark a task as done**

​	1. User requests to mark a task as done

​	2. Program marks the task as done

​		Use case ends.

**Extensions**

- 2a. Given index is invalid

  - 2a1. Program shows an error message

    Use case resumes at step 2.

- 2b. Incorrect syntax

  - 2b1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new todo task**

​	1. User requests to add a new todo task

​	2. Program adds new todo task to Task List

​		Use case ends.

**Extensions**

- 2a. Incorrect syntax

  - 2a1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new deadline task**

1. User requests to add a new deadline task

2. Program adds new deadline task to Task List

   Use case ends.

**Extensions**

- 2a. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new event task**

​	1. User request to add a new event task

​	2. Program adds a new event task to Task List

​		Use case ends.

**Extensions**

- ​	2a. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Find a task by searching for keyword**

1. User requests to find tasks with the given keyword

2. Program loads up and shows tasks with the given keyword

**Extensions**

- 2a. Task List is empty

  - Program prompts for correct syntax

    Use case ends.

- 2b. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.

### Appendix D: Non Functional Requirement

1. should work on any windows OS as long it has `java 11` or newer installed 
2. The application needs to be secure. only specific users are able to access this application. for example, the restaurant manager as well as the chef
3. should be reliable in displaying accurate and correct data 
4. should be easy to use for users with basic knowledge of command line interface
5. should be able to handle large amounts of data without displaying any slowdown in application performance 
6. 



### Appendix E: Glossary 

1. 

### Appendix F: Product Survey



### Appendix G: Instruction for Manual Testing 
