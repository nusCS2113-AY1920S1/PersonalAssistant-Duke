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

The Command class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method that is used by the following classes 

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

It is modeled as an abstract class,  with `TaskStorage.java` and `FridgeStorage.java` both inheriting from it. It allows data (tasks in the list, ingredients in the fridge, recipies in the recipeBook...) to be saved and remembered by our program.  

An example for the format of saving for tasks is :

- T|1|read book
- D|0|return book|Monday
- E|0|meeting|U-Town
- P|0|lecture|1600|1800

where the first column is denotes the type of task, T for todo, D for deadline, etc. 

The program can `load` or `generate` an entry from the storage and also `changeContent` and `addInFile`



#### 2.6 Task Component

API: `Task.java`



#### 2.7 Exception Component

API: `DukeException.java`

#### 2.8 Dishes Component

The Recipebook contains 2 classes, Dishes Class and DishList Class. The Dishes Class 

![Dishes](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/dishes%20diagram.png)

**<u>Dishes Class</u>**

| Attributes                   | Description                                     |
| ---------------------------- | ----------------------------------------------- |
| dishName: String             | name of the dish                                |
| total: int                   | the total number of orders for that dish        |
| rating: float                | the overall rating for that dish                |
| ingredientList: List<String> | a list of ingredients associated with that dish |



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

**<u>DishList Class</u>**

| Atrributes             | Description |
| ---------------------- | ----------- |
| dishList: List<dishes> |             |



| Constructor | Description                                       |
| ----------- | ------------------------------------------------- |
| DishList()  | initalize the empty dishLIst as a new ArrayList<> |



| Methods                  | Description |
| ------------------------ | ----------- |
| addDishes(Dishes): void  |             |
| deleteDish(int): void    |             |
| getDish(int): Dishes     |             |
| getSize(): int           |             |
| clearList(): void        |             |
| toString(Dishes): String |             |



#### 2.9 RecipeCommand Component

The RecipeCommand class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method that is used by the following classes

- AddDishCommand
- AddIngredient
- DeleteDishCommand
- InitCommand
- ListDishCommand

![DishesCommand](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/dishesCommand%20diagram.png)

#### 2.10 Fridge Component
API: `Fridge.java`

The Fridge class allows access and modification of the `Ingredient`s used by the chef. By keeping track of the Ingredients' expiry date, it allows the user to know which products have expired, and remove them. It allows for less ingredient waste, as it can return the most recently expiring ingredients, so that they can be used first. 

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
