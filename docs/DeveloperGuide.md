# Chef Duke - Developer Guide

By: `Team CS213-T14-2`       Since: `Oct 2019`

[TOC]

### 1. Setting Up

- see the Guide [Here]() 

### 2. Design 

#### 2.1  Architecture

![architecture]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/archh.png )

`main` has 1 class called `Duke`. It is responsible for,

- at app launch: Loads all the data in storage into the application, initialize the  components, reads the commands and executes them correctly.
- at shut down: shuts down all component and exits the application

The Application consist of 10 other components 

- `command`: executes the command that is read from the user
  - `dishesCommand`
  - `orderCommand`
  - `ingredientCommand`
- `exception`: handle error messages
- `dish`: contains the dishlist as well the the dish class 
- ` Fridge` : contains 
- `ingredient`: contains ingredient list 
- `list`: a class which contains a generic list. this list is used by various other classes
- `order`:  
- `parser`: determine the next course of action from the user command
- `storage`: Reads, writes data from and to the hard disk
- `ui`: The UI of the application, manages the IO 

![sequence]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/UMLsequence.png )

The diagram above shows the program sequence when the user enters the command `todo buy eggs` or `list`. 

#### 2.2 UI


API: `Ui.java`

The Ui class manages the Input/Output interactions with the user. It consists of methods that allow reading the user input from the console and printing messages to the user as a response to the specific commands read.

The Ui component contains all the messages or replies whenever the User enters a command, for example if the user, while in the dish mode, enters:

`add chicken rice`

`remove 1`

The Ui will be used to reply to the User with the following messages:

`ui.showAddedDishes(String)`

`ui.showDeletedDish(String)`

```
	 ____________________________________________________________
	 you have added the following dish: 
	 chicken rice 
	 ____________________________________________________________
```

```
	 ____________________________________________________________
	 The following dish have been removed:
	 chicken rice
	 ____________________________________________________________
```
the `Ui` also consist of templates for the different sections of the program, such as a template for dish, orders and ingredient

<u>ingredient</u> 

```
         _________________________________________________________________________________________
         Continue by adding, removing or using an ingredient
         Template:
         _________________________________________________________________________________________
         add <Ingredient name> <amount> <expiry date: DD/MM/YYYY>
         remove <ingredient number>
         use <ingredient name> <amount> *always use most recently expiring ingredients 			 first, to prevent food waste!*
```

<u>order</u>

```
         _________________________________________________________________________________________
         Continue by adding, removing, altering, listing order and initializing order 			 list.
         Command Template:
         _________________________________________________________________________________________
         init
         add [-d ORDER_DATE-(dd/mm/yyyy)] -n DISH1_NAME[*DISH_AMOUNT], 						 	 DISH2_NAME[*DISH_AMOUNT]
         alter ORDER_INDEX ORDER_DATE-(dd/mm/yyyy)
         remove ORDER_INDEX
         done ORDER_INDEX
         list [-l LIST_TYPE-(option: all (default) | undone | today | undoneToday)]
         list -n DISH_NAME    *** Find the dishes in today's undone orders ***
         list -d ORDER_DATE-(dd/mm/yyyy) [-l LIST_TYPE-(option: all (default) | undone)]
         _________________________________________________________________________________________
```

<u>Dish</u>

```
         _________________________________________________________________________________________
         Continue by adding, removing, listing, adding ingredient and initializing
         Template:       _________________________________________________________________________________________
         add <dish name>
         remove <dish index>
         list
         ingredient <description> <amount> <index>
         initialize (REMOVES all entries in the list)
         back, return to main menu
         template     _________________________________________________________________________________________
```

The `Ui` class consists of methods that outputs messages to the user as a response when the user enters a certain command

- reads and returns the user input by using `scanner.nextLine()`, where `scanner` is a `java.util.Scanner` Object
- allows for user/app dialogs, by using the methods such as `showDialogAddingExpired()` , used when the user tries to add an expired ingredient. Namely, the user is prompted if he whishes to continue with adding an expired Ingredient, and upon receiving the user response, by using the `readCommand()` method, a suitable response is printed on the console
- outputs messages to the user as a response such as `showRemovedIngredient(String,Int)` and `showAddOrder(String,Int)`, etc
- consist of diagrams of the different templates

#### 2.3 Command Component

API: `Command*.java`

In the project, it has three types of commands: Ingredient Command, Dishes Command, Order Command. The three types of commands are packaged separately.

The Command class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method. that is used by the following classes 

- DishCommand
  - AddDishCommand
  - DeleteDishCommand
  - ListDishCommand
  - FindDishCommand
  - ChangeDishCommand
  - ResetCommand
  - AddIngredient
- OrderCommand
  - AddOrderCommand
  - AlterOrderCommand
  - DeleteOrderCommand
  - DoneOrderCommand
  - ListOrderCommand
  - InitOrderListCommand
- IngredientCommand
  - AddCommand
  - DeleteCommand
  - FindIngredientCommand
  - FindToday
  - ListCommand
  - RemoveAllExpired
  - UseCommand
  - ViewCommand
- ViewTodoListCommand
- ExitCommand

each of the above class has its own implementation of the `execute` method

#### 2.4 Parser Component

API: `Parser.java`

makes sense of the data that is read by the user from the Duke Class. 

this component gets the command from the user through the Duke Class. This component will then make sense of the command by splitting the command into different parts as well as determining the command type.

![Parser](C:\Users\s1014\Desktop\local_clone\docs\images\Parser.png)

| methods                           | description                                       |
| --------------------------------- | ------------------------------------------------- |
| parse(String,Type)                | to differentiate the type of command from user    |
| order(String)                     | method to execute different order commands        |
| dish(String)                      | method to execute different dish commands         |
| ingredient(String)                | method to execute different ingerdient commands   |
| checkLength(String[])             | checks length of the user command                 |
| parseInt(String,int)              | ensure that int is more the 0 and less than limit |
| checkInt(String)                  | ensure that int is more the 0 and less than limit |
| addOrderDateParser(String[])      | returns AddOrderCommand()                         |
| alterOrderDateParser(String[])    | returns AlterDateCommand()                        |
| cancelOrDoneOrderParser(String[]) | return DoneOrderCommand() or CancelOrderCommand() |

firstly the full command read from the user will go through parse method. depending on the the type, it will either return order, dish or ingredient commands. in the order, dish and ingredient methods, the content of the splitted value class will execute different commands.

#### 2.5 Storage Component

##### 2.5.1 Storage

![Storage]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/storage.png )

Figure. Structure of the Storage Component

API: `Storage.java` 

The `Storage` component, consists of `Storage.java`, `RecipeStorage.java`, `OrderStorage.java` and `FridgeStorage.java`. `Storage` is modelled as an abstract class,  with `RecipeStorage`, `OrderStorage` and `FridgeStorage` inheriting from it, as shown in the figure above. 

The `Storage` component, 

- can store `ingredient` in the fridge (API: `FridgeStorage.java`) with a certain format, by storing it into and reading it back from the `fridge.txt` file stored on the hard disc. The `IngredientList` as a `GenericList` of ingredients also changes dynamically with the program execution. The format print in file follows `ingredient_name|ingredient_amount|ingredient_expiry date` . See below example:

- ```
  milk|3|09/09/2019
  cheese|4|12/12/2019
  rice|50|12/12/2019
  ```

- can store `orders` in the order list (API: `OrderStorage.java`) in a certain format, by storing it into and reading it back from the `order.txt` file stored on the hard disc. The `OrderList` as a `GenericList` of orders also changes dynamically with the program execution.The format print in file follows  `order_status|serving_date|D|dish_name|dish_amount|D|dish_name|dish_amount|...` , where status refers to `done(1)`/`undone(0)`, and `D` seperates each ordered dishes with its amount. See below example:

- ```
  1|02/11/2019|D|fish|1|D|chili crab|1|D|rice|2
  0|12/11/2019|D|beef noodle|1|D|pork dumplings|2
  0|11/11/2019|D|cereal shrimp|1|D|soup|4
  ```

- can store `dishes` in the recipebook (API: `RecipeStorage.java`) in a certain format, by storing it into and reading it back from the `recipebook.txt` file stored on the hard disc. The `DishList` as a `GenericList` of dishes also changes dynamically with the program execution. The format print in file follows:

  ```
  ???? I don't know the output
  ```
  
##### 2.5.2 Printable

![Printable](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/PrintableUML.png)

Figure. Structure of Printable

API: `Printable.java` 

It models an *Interface*, implemented by all the classes that have the feature to print their representation in a file. 

Offers one abstract method `printInFile()` whose implementation should indicate the format of printing the representation of the specific object calling it. A UML Class Diagram is shown below.

#### 2.6 GenericList

API: `GenericList.java` 

This *generic, abstract* class allows for the creation of different types of lists, and basic list entry manipulations. It is extended by multiple classes, including `IngredientsList.java`, `OrderList.java` and `DishList.java`. All of these classes inherit the basic methods from the Generic List and extend it with their specific methods, eg.  `allUndoneOrders()` from`OrderList.java`, or `changeAmount()` from `IngredientsList.java`. A UML Class Diagram is shown below.

![GenericList](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/GenericListUML.png)

##### 2.6.1 IngredientList

API: `IngredientList.java` 

A child class of `GenericList` , using the `Ingredient` as a generic type. Inherits and further extends the attributes and methods of this superclass. 

Below is a table of the methods implemented in this class.

| Constructor                                       | Description                                               |
| ------------------------------------------------- | --------------------------------------------------------- |
| IngredientsList(List<Ingredient> ingredientsList) | Initializes the IngredientsList as a new List<Ingredient> |



| Methods                                                      | Description                                                  |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| addEntry(Ingredient Object): void                            | Adds a new Ingredient of Ingredients attributes into the Ingredient List |
| hasEnough(Ingredient Object): Boolean                        | Returns true or false when comparing 2 Objects.<br />True: We have enough of the required Ingredient<br />False: Otherwise |
| getEntry(Ingredient Object): Ingredient Object               | Looks for the queried ingredient and returns it              |
| getNonExpiredEntry(Ingredient ingredient): Ingredient Object | Looks for the queried Ingredient in the list that is not expired and returns it |
| sortByExpiryDate(): Ingredient Object                        | Sorts the Ingredient lists accordingly by a descending amount |
| removeEntry(Ingredient Object):  Boolean                     | Looks for the queried Ingredient in the list and remove the amount that we want to use.<br />True:  Enough amount of the queried ingredient<br />False: Not enough amount  of the queriedingredient |



##### 2.6.2 OrderList

API: `OrderList.java` 

A child class of `GenericList` , using the `Order` as a generic type. Inherits and further extends the attributes and methods of this superclass. 

Below is a table of the methods implemented in this class.

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



##### 2.6.3 DishList

API: `DishList.java` 

This class inherits from the `GenericList` class  which takes in a List of `Dish`. this class holds all the dishes that is stored in the csv file thus this acts as a menu for the chef

Below is a table of the methods implemented in this class.

| Constructor          | Description                                      |
| -------------------- | ------------------------------------------------ |
| DishList(List<Dish>) | assigns a list of dishes to dishList             |
| DishList()           | assigns an empty ArrayList<>() to dishList       |
| toString(): String   | returns all the dishes in the dishList in String |



#### 2.7 Exception Component

API: `DukeException.java`

whenever the program runs into an error, an exception will be thrown notifying the user that they may have entered an invalid command.

eg. `throw new DukeException("enter a valid amount/index")`

`DukeException` will print out:

```
	 OOPS!!! enter a valid amount/index 
	 You can type: 
	 'template' to see the format of the commands, 
	 'back' to see all your options, 
	 'q' to exit
```



#### 2.8 Dish Component

API: `Dish.java`, `DishList.java`

The Recipebook contains 2 classes, Dishes Class and DishList Class

![dishes](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/dishes.PNG)

**<u>Dish Class</u>**

This class holds the name of the dish as well the ingredients that are associated to that specific dish. 

| Attributes                       | Description                                     |
| -------------------------------- | ----------------------------------------------- |
| dishName: String                 | name of the dish                                |
| ingredientList: `IngredientList` | a list of ingredients associated with that dish |

| Constructor    | Description                              |
| -------------- | ---------------------------------------- |
| Dishes(String) | assigns the name of the dish with String |

| Methods                             | Description                                                  |
| ----------------------------------- | ------------------------------------------------------------ |
| changeName(String): void            | change the name of the dish                                  |
| getDishName(): String               | returns the name of the dish                                 |
| addIngredients(String,int): boolean | takes a string and integer and adds into ingredientList      |
| printInFile():String                | formats dish andingredient list for writing into file        |
| toString(): String                  | it returns a String of all the ingredients that the dish contains |


#### 2.9 dishesCommand Component

API: `addDishCommand.java` `addIngredient.java` `deleteDishCommand.java` `ListDishCommand.java` `ResetDishCommand.java` `FindDishCommand.java` `ChangeDishCommand.java`

The dishesCommand component  enables the chef to modify the dishList which acts as a menu or recipebook. this component inherits from other classes. 

AddDishCommand, AddIngredient, DeleteDishCommand, ListDishCommand, ResetDishCommand, FindDishCommand and ChangeDishCommand inherits from the Command class

- **<u>AddDishCommand</u>**
  
  user intends to add a dish to the dishList. this command takes in a description which is the dish name. user enters the command `addish chicken rice` which denotes adding the dish called chicken rice into the dishList. the program will enter the AddDishCommand class and executes the method below.
  
  If the dishList is empty(size of dishList is 0), immediately add the dish into the dishList. however, if the dishList is not empty, the program will need to go through the entire dishList to check if the dish has already been added. this is done so that there are no duplicate dishes.
  
  once the dish is added to the dishList, the method will use the Ui class with method call ui.showAddedDishes() as a reply to the user that the dish has been successfully added into the list.
  
- **<u>AddIngredient</u>**

  user intends to add an ingredient to a certain dish in dishList. this command takes in a description which is the ingredient name, amount and index. thus user enters `ingredient rice 100 1` which denotes adding an ingredient called rice with a amount of 100g to the index of the dish. in this case the index is 1. this command will then call the method`addIngredient` from the Dish class. in this method, it will iterate through the ingredient list to check for duplicates. if there are duplicates, same name and same amount, notify the user that the ingredient already exist. if the name of the ingredient is the same but amount is different, it only change the amount to the new amount. 

  if any of the description is empty, an exception will be thrown to inform the user. 

- **<u>DeleteDishCommand</u>**

  user intends to remove a dish in dishList. thus user enters `remove 1` which denotes removing a dish of index 1 from the dishList. this command takes in an integer index and the command will remove dish from dishList by the given index. 

  if there is no such dish with user keyed index, an exception will be thrown to the user.

- **<u>ResetDishCommand</u>**

  user intends to clear the dishList. thus user enters `initialize` which will clear the dishList

- **<u>ListDishCommand</u>**

  user intends to list all the dishes in the dishList. user needs to enter `list` for the program to list all the dishes in the dishList. for each dish in the list, there are also ingredients associated to it. hence, this command prints all the dishes as well as the ingredients associated to it.
  
- **<u>FindDishCommand</u>**

  user intends to find a dish in the dishList by keyword. user enters `find rice`, which denotes to list all the dishes which has the string rice in the name. this command takes in a String keyword and it will iterate through the dishList to find all the dishes that contain the keyword given by user. 

  it will then print out the dish as well as the ingredient list of the dish to the user.

- **<u>ChangeDishCommand</u>**

  user intends to change the name of the dish. user needs to enter `change 1 chicken noodle` which denotes changing name if dish at index 1 to chicken noodle. this command takes in an integer index and string which is the new dish name. it will then change the name of the dish in dishList by the index to the new name.

![dishesCommand](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/dishesCommand.PNG)

**<u>future additions</u>**

- **<u>ChangeIngredientCommand</u>**

#### 2.10 Order Component
API: `Order.java`, `OrderList.java`

The Order component contains 2 classes, Order Class and OrderList Class. The chef can add new orders and update his "todo list" today. When an order comes, the program calculates dishes amount for each type of dishes. It then access the recipebook which contains every dishes in the menu including the recipe (the amount of consisting ingredients) so as to get the total amount of the ingredients needed to finish this order. It checks with the storage of those ingredients in the `Fridge` and returns the information that if ingredients are enough.

Besides, in current stage, we assume that the dishes in chef's todo list should be finished by the end of "today". At the beginning of every day, the todo list will be initialized. However, the order supports pre-order, allowing the order date is not today. That is, the initialization of chef's todo list might not be empty.


**<u>Order Class</u>**

| Attributes                                          | Description                                                  |
| --------------------------------------------------- | ------------------------------------------------------------ |
| content: Map<Dishes, Integer>                       | the content of the order, specifying ordered dishes and amount |
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



#### 2.11 Order Command Component
API: `AddOrderCommand.java`, `AlterDateCommand.java`, `DeleteOrderCommand.java`, `DoneOrderCommand.java`, `ListOrderCommand.java`

The Order Command classes inherits from the `Command` class. They overwrite the abstract method `execute` of the `Command` class. The Order Command classes includes:

- AddOrderCommand: This command will add order to the orderlist and update the chef's todo list. The order can be loaded manually by command. The order can also be read from the file when initializing -- which is the case of the pre-order.
- AlterDateCommand: This command will change the serving date of the order. If it changed to the date of today, then chef's todo list should be updated. If it changed to the date before today, then the change is considered invalid.
- DeleteOrderCommand: This command is used for cancelled orders. It also synchronizes with chef's todo list.
- DoneOrderCommand: This command is used for changing the status of the order to be finished. The done dishes in the order can be removed from the chef's todo list. 
- ListOrderCommand: The command is to list all orders or is to list orders after filtering. The filter feature can be date, dishes, order status.

#### 2.12 Fridge Component
API: `Fridge.java` ,`Ingredient.java`

The `Fridge` component allows access and modification of the `Ingredient`s used by the chef. By keeping track of the Ingredients' expiry date, it allows the user to know which products have expired, and remove them. The ingredients are always kept sorted by their expiry date, with the most recently expiring coming first. Furthermore, it allows for less ingredient waste, as it can return the most recently expiring ingredients, so that they can be used first.  

![Fridge](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/fridgeUML1.png)

**<u>Ingredient Class</u>**

An abstraction of an `Ingredient`, stored in the `Fridge`, defined by it's name, amount and expiry date.

Below is a table with some of the methods provided by this class.

| Attributes           | Description                            |
| -------------------- | -------------------------------------- |
| name: String         | Name of the ingredient                 |
| amount: int          | Total amount of the ingredient         |
| expiryDate: Date     | Expiry date of the given ingredient    |
| dateAsString: String | A string to store the date as a string |



| Constructor                       | Description                                               |
| --------------------------------- | --------------------------------------------------------- |
| Ingredient(String, Integer, Date) | Gives the name , amount and expiry date of the ingredient |



| Methods                               | Description                                                  |
| ------------------------------------- | ------------------------------------------------------------ |
| Ingredient(String, Integer, String)   | Converts the `Date` into `String`                            |
| getExpiryDate(): Date                 | Returns the expiry date of the ingredient                    |
| setDate(Date): void                   | Setting the expiry date of the ingredient                    |
| getAmount(): int                      | Returns the amount of the ingredient                         |
| setAmount(Integer): void              | Setting the amount of an ingredient                          |
| getName(): String                     | Returns the name of the ingredient                           |
| setName(String): void                 | Setting the name of the ingredient                           |
| equals(Ingredients): Boolean          | Returns a `boolean` when comparing 2 Ingredients<br />*True*: Ingredient names are identical<br />*False*: Otherwise |
| isExpired(): Boolean                  | Returns a `boolean` depending on the expiry date<br />*True*: Ingredient's date is before or equal to today's date<br />*False*: Otherwise |
| isExpiredToday(String): Boolean       | Returns a `boolean` depending on the expiry date<br />*True*: `Date` of string is is equal to today's date<br />*False*: Otherwise |
| equalsCompletely(Ingredient): Boolean | Returns a `boolean` when comparing 2 Ingredients<br />*True*: Ingredients have same name and expiry date<br />*False*: Otherwise |


#### 2.13 ingredientCommand Component

API: `AddCommand.java`, `DeleteCommand.java`, `FindToday.java`, `ListCommand.java`, `RemoveAllExpired.java`, `FindIngredientCommand.java`, `UseCommand.java`, `ChangeAmountCommand.java`, `ChangeNameCommand.java`

The `ingredientCommand` classes all inherit from the `Command` class. They all have a specific implementation of the abstract method `execute` of the class `Command`. The `ingredientCommand` classes includes:


- **AddCommand** This command adds an `Ingredient` to the `Fridge`, passed as an argument when creating this command. The `IngredientsList` used by the `Fridge` can also be read from the file when initializing. When adding an ingredient, it is compared to all the existing ingredients in the `Fridge`, if there is an match, having the same name and expiry date, no new entry is created in the `IngredientList` of the `Fridge`, namely, only the amount of the already existing matching ingredient is augmented by the new amount to be added. Otherwise, a new ingredient entry is created in the `IngredientList`. 
- **DeteleCommand**: This command deletes an ingredient from the `IngredientList` of the `Fridge`, indicated by it's index in the list, passed as a parameter when creating this command. 
- **FindToday**: This command is used to look for expired ingredients on the date itself.
- **ListCommand**: This command is used to show the chef's entire IngredientsList.
- **RemoveAllExpired**: The command is used to remove all expired ingredients from the  `IngredientList` of the `Fridge`.
- **FindIngredientCommand**: This command is used to find all ingredients with the queried keyword entered by the chef.
- **UseCommand**: This command is executed to use and remove the specified amount of an ingredient stored in the  `IngredientList` of the `Fridge`, when the Chef wants to use it. An important *note* is that ingredients are used based on their expiry date, meaning the most recently expiring ingredients, matching the ingredient indicated by the chef, are used first. Otherwise, if there is not enough of the required non-expired amount of this ingredient needed by the chef, the program will prompt it to the chef. 
- ChangeAmountCommand: This command is used to change the amount of an ingredient given the index number of the ingredient
- ChangeNameCommand: This command is used to change the name of an ingredient given the index number of the ingredient



### 3. Implementation

### 4. Documentation

### 5. Testing

### 6. Dev Ops 



### Appendix A: Product Scope

Target user profile: Restaurant Chef

- needs to manage all the ingredients for his dishes  
- needs to keep track of orders 
- needs to manage ingredients in the fridge
- needs to manage dishes in recipe book 
- prefers a desktop application, CLI
- prefers to keep everything neat in terms of viewing information
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
| high     | restaurant Chef    | a list to keep all my recipes                                | I can refer to them if needed                        |
| high     | restaurant Chef    | keep track of all the ingredients in the kitchen             | there are ample supply and no expired ingredient     |
| high     | restaurant Chef    | I want to create a recipe book                               | I know which ingredient is needed for that dish      |
| high     | restaurant Chef    | I want to keep track of the orders                           | I know which to complete first                       |

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

​	1. User requests to view all the existing todo list

​	2. Program loads up the lists of existing tasks

​		Use case ends.

**Extensions**

- 2a. todoList is empty

​		Use case ends.

- 2b. Incorrect syntax

  - 2c1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Mark a order as done**

​	1. User requests to mark order as done

​	2. Program marks the order as done

​		Use case ends.

**Extensions**

- 2a. Given index is invalid

  - 2a1. Program shows an error message

    Use case resumes at step 2.

- 2b. Incorrect syntax

  - 2b1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new order**

​	1. User requests to add a new order

​	2. Program adds new order to order List

​		Use case ends.

**Extensions**

- 2a. Incorrect syntax

  - 2a1. Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new dish**

1. User requests to add a new dish

2. Program adds new deadline dish to dish List

   Use case ends.

**Extensions**

- 2a. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Create a new ingredient**

​	1. User request to add a new ingredient

​	2. Program adds a new event ingredient to ingredient List

​		Use case ends.

**Extensions**

- ​	2a. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.



**Use case: Find a ingredient by searching for keyword**

1. User requests to find tasks with the given keyword

2. Program loads up and shows ingredient with the given keyword

**Extensions**

- 2a. ingredient List is empty

  - Program prompts for correct syntax

    Use case ends.

- 2b. Incorrect syntax

  - Program prompts for correct syntax

    Use case resumes at step 2.

### Appendix D: Non Functional Requirement

1. should work on any windows or Mac OS as long it has `java 11` or newer installed 
2. The application needs to be secure. only specific users are able to access this application. for example, the restaurant manager as well as the chef, coming in version 2
3. should be reliable in displaying accurate and correct data 
4. should be easy to use for users with basic knowledge of command line interface
5. should be able to handle large amounts of data without displaying any slowdown in application performance 

### Appendix F: Instruction for Manual Testing 

1. Ensure you have Java `11` or above installed in your Computer.
2. Download the latest Duke.jar [here](https://github.com/AY1920S1-CS2113-T14-2/main/releases).
3. Copy the file to the folder you want to use as the home folder for your Duke application.
4. Use the command prompt and navigate to the path where the application is downloaded `cd ../FILEPATH`
5. run the command `java -jar v1.4` , application will then be executed 

initially you are greeted with the main page, if there are expired ingredients in the fridge, the program will alert the user to clear the items. user can either enter `yes` or `no`. if user enters yes, all expired ingredients will be cleared. if user enters no, the ingredients will remain.

in the main page, there are several actions for the user:

| Index | Keyword | Usage   | Description                 |
| ----- | ------- | ------- | --------------------------- |
| 1     | options | options | show options                |
| 2     | q       | q       | exit program                |
| 3     | t       | t       | view todo list              |
| 4     | a       | a       | remove expired ingredients  |
| 5     | b       | b       | go into ingredient template |
| 6     | c       | c       | go into order template      |
| 7     | d       | d       | go into dish template       |

#### E1. Launch and shutdown

1. Initial Launch
   1. Download Jar file and copy into empty folder
   2. open command prompt and navigate to that `FILEPATH` and run `java -jar v1.4`. 
   3. resize window if size is not optimum
   4. enter `q` to close the program or close the window

#### E2. Adding an ingredient

1. Adding an ingredient to the Fridge

   1. prerequisite: user must be in `b` option of the main menu. See all ingredients in the Fridge by using `show` 

      Test case 1: `add chicken 2 12/2/2020`

      Expected: search through the `Fridge`, if there is an already existing ingredient chicken with the same expiry date, 12/2/2020, update it's amount by adding 2 to it, otherwise,  adds an ingredient chicken, 2 expiring on 12th of February 2020 in the `Fridge`. Output message to user that the ingredient has been added

      Test case 2: `add chicken 2 9/11/2019`

      Expected: output a message asking the user for confirmation if he really wants to proceed with adding an expired ingredient

      Test case 3: `add`

      Expected: output a message to user that the description of add cannot be empty



#### E3. Removing an ingredient

Removing an ingredient from the Fridge

1. prerequisite: user must be in `b` option of the main menu. Show all ingredients using `show` , assuming the number of ingredients currently  in the Fridge is for eg. 5.

   Test case 1: `remove 1` 

   Expected: remove the first, most recently expiring ingredient from the Fridge, and output back the details of the removed ingredient

   Test case 2: `remove 6`

   Expected: no ingredient is removed, outputs to the user that the index is not valid 

   Test case 3: `remove`

   Expected:  no ingredient is removed, outputs to the user that he must specify an index

#### E4. Using an ingredient

Using an ingredient from the Fridge

1. prerequisite: user must be in `b` option of the main menu. See all ingredients in the Fridge by using `show` , assuming there is an amount of 3 of tomato in the `Fridge`

   Test case 1: `use tomato 2 `

   Expected: search through the `Fridge`, use and remove an amount of 2 of the tomato, by removing the most recently expiring tomato first

   Test case 2: `use tomato 3`

   Expected: outputs a message notifying the user that there is not a sufficient amount of tomato that is not expired

   Test case 3: `use tomato`

   Expected: output a message to user that he must specify an amount 

#### E5. Listing all ingredient

1. Adding an ingredient to the List

#### E6. Removing all expired ingredient

1. Adding an ingredient to the List

#### E7. Finding an ingredient

#### E8. Listing ingredients that expired today

1. Adding an ingredient to the List

#### E9. Changing ingredient name

1. Adding an ingredient to the List

#### E10. Changing ingredient amount

1. Adding an ingredient to the List

#### E11. Adding an order

#### E12. Marking order as done

#### E13. Altering order

#### E14. Removing order

#### E15. Adding a dish

1. adding a dish to the dishList

   1. prerequisite: user must be in `dish` template. list all dishes using `list` 

   2. Test case 1: `add chicken rice`

      Expected: search through the dishList, if there is no match, adds a dish called chicken rice into the dishList. output message to user that the dish has been added

   3. Test case 2: `add chicken rice`

      Expected: search through the dishList, if there is a match, dish will not be added into the dishList. output message to user that the dish is already in the list

   4. Test case 3: `add`

      Expected: output message to user that the description cannot be empty

#### E16. Removing a dish

1. removing a dish from the dishList

      1. prerequisite: user must be in `dish` template. list all dishes  using `list`, eg the size dishList is more than 0 less than 100 

      2. Test case 1: `remove 1`

         Expected: deletes the first dish in the list, outputs the details of the deleted dish to the user 

      3. Test case 2: `remove`

         Expected: no dish is deleted. outputs to the user to enter a valid index

      4. Test case 3: `remove two`

         Expected: no dish is deleted. output to the user to enter alternative command 

      5. Test case 4: `remove 101`

         Expected: no dish is deleted. outputs to the user that the dish does not exist 

#### E17. Adding an ingredient to a dish

1. associating an ingredient to a dish in the dishList	

   1. prerequisite: user must be in `dish` template. list all dishes using `list`, eg size of dishList is more than 0 and less than 100

   2. Test case 1: `ingredient rice 50 1`

      Expected: adds an ingredient rice of 50g to dish of index 1 in dishList. outputs message to user, added ingredient rice to dish.

   3. Test case 2: `ingredient`

      Expected: no ingredient is added to a dish. outputs message to user that description cannot be empty

   4. Test case 3: `ingredient rice`

      Expected: no ingredient is added to a dish. outputs message to user that index/amount needs to be valid

#### E18. Finding a dish

1. finding a dish in list given a keyword

   1. prerequisite: user must be in `dish` template. list all dishes using `list`

   2. Test case 1: `find rice`

      Expected: find all dishes that contains the keyword rice and output to user

   3. Test case 2: `find`

      Expected: program prints message to user informing that description cannot be empty
1. prerequisite: list all dishes  using `list`, eg the size 
   
2. Test case 1: `remove 1`
   
   Expected: deletes the first dish in the list, 

#### E19. Changing name of a dish

1. changing the name of a dish in list

   1. prerequisite: user must be in dish template. list all dishes using `list`. dish that intended to be changed must be in the list

   2. Test case1: `change 1 chicken noodle`

      Expected: change the name of dish at index 1 to chicken noodle

   3. Test case 2: `change 1`

      Expected: no changes to the list. program prints message to user asking to enter a valid index/description
