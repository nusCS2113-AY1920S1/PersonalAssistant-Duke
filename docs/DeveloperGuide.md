# Duke++ Developer Guide


[TOC]

## 1. Setting up

### 1.1 Prerequisites
1. **JDK** `11` or above
2. **IntelliJ** IDE

    :information_source: IntelliJ by default has Gradle and JavaFx plugins installed. Go to `File` > `Settings` > `Plugins` to re-enable them if you have disabled them as this project requires the plugins. 
    
### 1.2 Setting Up On Your Computer
The following lists the steps to be taken in order to import the project to your local computer.

1. Fork this repo, and clone the fork to your computer
 
2. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)

3. Set up the correct JDK version for Gradle
        i. Click `Configure` > `Project Defaults` > `Project Structure`
        ii. Click `New…​` and find the directory of the JDK

4. Click `Import Project`

5. Locate the `build.gradle` file and select it. Click OK

6. Click `Open as Project`

7. Click `OK` to accept the default settings

8. Open a console and run the command `gradlew processResources` (Mac/Linux: `./gradlew processResources`). It should finish with the `BUILD SUCCESSFUL` message. This will generate all resources required by the application and tests.

### 1.3 Verifying The Set-Up
Run `duke.Launcher.main` and try a few commands.

### 1.4 Getting Started With Coding
When you are ready to start coding, we recommend  that you get some sense of the overall design by reading the other sections about the architecture of Duke++ in this Developer Guide.

## 2. Design

### 2.1 Architecture

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Architecture%20diagram%20(1).png?raw=true">

<code>Main</code> At app launch: Initializes the components in the correct sequence, and connects them up with each other.

<code>DukePP</code> object holds all of our models in memory.

### 2.2 Ui

The classes in the package <code>Ui</code>, other than <code>UiManager</code>, are controller classes, and will have a corresponding JavaFX <code>.fxml</code> file in <code>resources/view</code> and a optionmal css file in <code>resources/layout</code>.

<code>UiManager</code> acts as the bridge between and `mainWindow`.

<code>mainWindow</code> is the primary GUI container that will contain the outer most GUI and will contain inner GUI elements.

The inner GUI elements inherits from <code>UiPart</code>.
In <code>mainWindow</code> ,the method <code>showPane()</code> chooses which inner GUI elements to display and <code>fillInnerPart</code>  populates the inner GUI elements of  based on the user commands.



The implementation  of Ui is explained in [3.2](#3.2-Ui)

### 2.3 Logic

<code>LogicManager</code>is the bridge between the user inputs and the <code>Model</code> and <code>Storage</code>


<code>CommandParams</code> splits the user input into individual components of the Main <code>Command</code>and its
main parameters and secondary parameters.

The <code>command</code> package includes all of our Commands, which all inherits from the clas <code>Command</code>.

In each of the sub-classes of <code>Command</code>, execute is overridden with its own specialized Logic.


### 2.4 Model 

#### 2.4.1 DukeItem
A generic object in Duke++, for Duke++ to track other things other than expenses. It has the following attribute:
* `tag`
#### 2.4.2 DukeList
A generic list of DukeItems, for Duke++ to track other things other than expenses.
#### 2.4.3 Expense
An Expense inherits from DukeItem and includes the following attributes:
* `BigDecmial amount`
* `String description`
* `LocalDateTime time`
* `boolean isTentative`

It is constructed using <code>Builder()</code> methods and its attributes are modified and set using its setter methods.

#### 2.4.4 ExpenseList
Contains a list of all `Expense` objects.

#### 2.4.5 PlanBot
<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/PlanBot%20Class%20Diagram.png?raw=true">

*Figure 2.4.5 PlanBot Class Diagram*

`PlanBot` is designed as a singleton, i.e there should be only one instance of `PlanBot`. It contains the following:

* <code>List&lt;PlanDialog> dialogList</code> with an associating <code>ListPlanDialog ObservabledialogList</code> which is the dialog history between the bot and the user.
* <code>PlanQuestion currentQuestion to keep track of the current question being asked.</code>
* <code>Queue&lt;PlanQuestion> questionQueue questionQueue</code> a list of questions that is going to be asked, implemented in a Queue.
* <code>PlanQuestionBank planQuestionBank</code> a collection of all questions that we can retrive questions from. Further explained in [PlanQuestion](#PlanQuestion)

* <code> Map&lt;String,String> planAttributes</code> which are the known attributes of the user. The key is the attribute of the user, while the value is the variable of the attribute.

#### 2.4.6 PlanQuestionBank
<code>PlanQuestionBank</code> holds a Map of integers which the keys are the index of the questions, and the value is a PlanQuestion.

#### 2.4.7 PlanQuestion
Each question has the following attributes:
* <code>String question</code> which is the question itself
* <code>Map&lt;String, String> answersAttributesValue</code> a map of valid answers and attribute values each answer maps to
* <code>Map&lt;String, Set&lt;Integer>neighbouringQuestions</code>
a map of attributes values of the question to its set of integers contain the indices of the neighbouring questions.
* <code>String attribute</code> the attribute we want to determine of the user

For example,  a question of "Are you a NUS student?"" will have a answers of "yes" or "no", to determine the attribute of NUS_STUDENT which can have values "TRUE" or "FALSE", and for "TRUE" replies has neigbouring question of 2,3 and 4. It would look as follows
```
     question = "Are you a NUS student?"
     answersAttributesValue = YES : TRUE, NO : FALSE
     neighbouringQuestions = FALSE : {2,3,4}";
     attribute = "NUS_STUDENT";
```

#### 2.4.8 PlanRecommendation
PlanRecommendation is an internal class which holds:
`String recommendation` the text that will be displayed to the user.
` Map<String, BigDecimal> budget` the recommended budget for the user. Key is the Category, and the value is the monetary value for the category.
`List<Expense> recommendationExpenseList;` The expenses that is recommended to be added to ExpenseList.

#### 2.4.8 Income
<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Income%20List%20Class%20Diagram.png?raw=true"> 

*Figure 2.4.8 Income Class Diagram*

Each `Income` inherits from DukeItem and contains the following attributes:
- `BigDecimal amount`: The amount of money of the income
- `String description`: The source of the income
- `LocalDateTime time`: The time in which the income was added

#### 2.4.9 IncomeList
IncomeList inherits from DukeList&lt;Income> and contains the following:
- `List<Income> filteredSortedViewedList`: The list which stores all the income after filtering and sorting
- `ObservableList<Income> internalFinalList`: The ObservableList to track any changes in the income list
- `ObservableList<Income> externalFinalList`: The ObservableList which shows the list of income

#### 2.4.10 BudgetView
BudgetView contains the following attribute:
- `Map<Integer, String> budgetViewCategory`: The map that stores the wanted budget views

#### 2.4.11 Payment

A Payment includes the following attributes:

* `BigDecmial amount`
* `LocalDate due`
* `String description`
* `String receiver`
* `String tag`
* `Priority priority`

It is constructed and its atrributes are modified and set using <code>Builder()</code> methods.

#### 2.4.12 PaymentList

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Payment%20Reminder%20Class%20Diagram.png?raw=true">

The `PaymentList` Contains a list of all `Payment` objects.

It provides an external unmodiafiable list of sorted and filtered payments. 

The filter and the search function are supported by these `Predicate`s.

### 2.5 Storage

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Storage%20Class%20Diagram.png?raw=true">

*Figure 2.5 Structure of the Storage Component*

The `Storage` component, 

* can save the Expense List data and read it back.

* can save the Plan Attributes data and read it back.

* can save the Income List data and read it back.

* can save the Budget data and read it back.

* can save the Budget View data and read it back.

* can save the Payment List data in json format and read it back.


## 3. Implementation

### 3.1 Startup Sequence

1. Upon starting the Application, it will first run <code>Main.init()</code>, which will construct all of our classes and bridge them based on the architecure as described in [Chapter 2](#2-Design) 
2. <code>Storage</code> is first constructed.
3. <code>Model</code> is then constructed and populated by the loading <code>Storage</code>
4. <code>Logic</code> is then constructed with both <code>Model</code> and <code>Storage</code>. 
5. <code>Ui</code> is finally constructed using <code>Logic</code>, and is started as described in section [3.2](#3.2-Ui).


#### 3.1.1 General implementation

<img src= "https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/General%20Sequence%20Diagram%20Example.png?raw=true">

*Figure 3.1.1 Sequence Diagram of a general command being executed.*

1. When the user enters a command in the UI, upon the on the "Enter" key, the UI passes the command into Logic to execute the command.
2. Within Logic, the command is first split into parts using `CommandParams`
3. Then, the commandResult is generated by executing the `Command` Class, which modifies `Storage` and <code>Model</code>, which is returned to the <code>Ui</code>
4. `Ui` then reflects the changes made in <code>Model</code> and displays it graphically. This is done by using `ObservableList` and `StringProperty` objects in `Model` objects, so that we only need to link the `Ui` to `Model` only once and do not have to manually refresh everything in the GUI on every command.


### 3.2 Ui
1. <code>UiManager</code> is first constructed with a <code>Logic</code> object.
2. <code>UiManager</code> then constructs a <code>mainWindow</code> object, passing it the <code>Logic</code> object.
3. <code>mainWindow.show()</code> is called, and displays the welcome screen initially with a textField which is the user input.
4. Then, <code>mainwindow</code> waits for input
5. Upon user input, the method <code>handleKeyPress()</code> in main window is called and will execute either command history or <code>autoComplete()</code> based on the user input
6. <code>handleUserInput()</code> is executed on the enter keypress and on it passes the user input String to <code>Logic</code> to execute the command. It should be noted that the first command should be a <code>goto</code> command so that a inner pane can be shown.
7. A <code>commandResult</code> is returned from Logic, and will populate the inner part of MainWindow based on the <code>DisplayedPane</code> in <code>commandResult</code>, by setting the visibilty of each pane since we are using a JavaFX`StackPane` for the inner elements of the UI.

### 3.3 Logic
1. LogicManager is first constructed with a Model and Storage Object.
2. Upon a <code>execute("userInput")</code> command,  the <code>"userinput"</code> is passed on to <code>CommandParams</code>, which will parse the <code>"userinput"</code> into a <code>Command </code> object and be split into its main parameters and secondary parameters.
3. The <code>execute()</code> of the <code>Command </code> object is run, which is overridden in each of the subclasses as described in [2.3](#2.3-Logic), with <code>Models</code> and <code>Storage</code> passed in so that they could be modified based on the command.

### 3.4 Model 
1. DukePP is constructed with already populated <code>ExpenseList</code> and <code>PlanAttributes</code> that was loaded from Storage. DukePP is the container for all of our models, which are in the <code>Model</code> package.
2. During construction, it also starts a new instance of <code>PlanBot</code>, and passes it the <code>PlanAttributes</code>
3. When a method is called from a higher class (usually <code>Logic</code>), it calls the relevant method of the model to perform operation on the model.

#### 3.4.1 DukeItem
`DukeItem` is an abstract class to be implemented by its child classes.

#### 3.4.2 DukeList
`DukeList`  is an abstract class to be implemented by its child classes.

#### 3.4.3 Expense
##### Construction - `Expense.Builder()`
Expense is constructed using an internal `Builder` object. For example:

```
Expense.Builder builder = new Expense.Builder();
builder.setAmount("3.80");
builder.setDescription("gong cha");
builder.setTag("drinks");
builder.setTime("14:00 09/11/2019");
expenseList.add(builder.build());
```


`Expense.Builder` has the following methods:
* `setAmount(String amount)`
* `setDescription(String description)`
* `setRecurring(boolean isRecurring)`
* `setTentative(boolean isTentative)`
* `setTime(String Time)`
* `Build()`

##### Methods
`Expense` has a getter method for each of its attributes previously mentioned. 

#### 3.4.4 ExpenseList implementation
##### Construction - `new ExpenseList(List<Expenses> internalList)`
1. On construction, `ExpenseList` sets its `internalList`, which is a list of all expenses, to the list that was loaded from storage.
2. It sets the default `viewScope` to `ALL` and default `SortCriteria` to `time`.
3. It then updates the `externalList`, which is the list displayed to the user based on the `viewScope` and `sortCriteria`


##### Add -`ExpenseList.Add(Expense expense)`
1. An Expense object is constructed as described in the previous section.
2. This expense is then added to the `internalList`. 
3. The `externalList`, which is the list the user sees, is updated.

##### Delete - `ExpenseList.delete(int index)`
The item that corressponds to the index in `externalList` is deleted from both the `externalList` and `internalList`.
##### Sort - `ExpenseList.setSortCriteria(String sortCriteria)`

The inner enumerator `SortCriteria` represents the sort criteria used to sort the `externalList`. It contains:
* `AMOUNT` Expense with higher amount of money will be prior.
* `TIME` Expense with closer time will be prior.
* `DESCRIPTION` Alphabetical order of expense's description. 

1. The string `sortCriteria` is parsed by the inner enumerator `SortCriteria`.

2. The field `sortCriteria:SortCriteria` is updated accordingly.

3. The `externalList`, which is displayed to users, is sorted with the new `sortCriteria`.


##### View - `ExpenseList.setViewScope(String viewScope, int viewScopeNumber)`

The inner class `ViewScope` represents the view scope applied to the `externalList`. It contains two fields: 

* `viewScopeName` including `DAY`, `WEEK`, `MONTH`, `YEAR` and `ALL`.
* `viewScopeNumber` indicates the time scope is how many `DAY`s, `WEEK`s, `MONTH`s or `YEAR`s ago. It is forced to be zero if `viewScopeName` is `ALL`.

1. A `viewScope` object is created with the given two parameters.

2. The field `viewScope:ViewScope` is updated accordingly.

3. The `externalList`, which is displayed to users, is filtered with the new `viewScope`.


##### Tentative Expenses
If an `Expense` is tentative, we set its color to greyed out in the GUI, and the expense is not added to the total amount.

Confirming a tentative expense checks if the specified expense is a tentative one, and if it is, sets the expense to a regular one.

##### Recurring Expenses
If an `Expense` is recurring, we set its color to Green in the GUI. 

This expense will still appear in other months when `viewScope` is set to `month`, `year` or `all`. 


#### 3.4.5 PlanBot implementation


<code>getDialogObservableList()</code> is the getter method of the dialog history of <code>PlanBot</code>.

<code>getKnownPlanAttributes()</code> is the getter method of what we know about the user.

<code>ProcessPlanInput(String input)</code> processes the input entered by the user to <code>PlanBot</code>.

##### Construction - `Planbot.getInstance()`

1. On construction, <code>PlanBot</code> constructs the objects mentioned in [2.4.3](#2.4.3-PlanBot).
2. A single instance of <code>PlanQuestionBank</code> is created, initializing its internal list of questions. The list of questions is implemented as a `Map<int, PlanQuestion>` of the index of the question to a <code>PlanQuestion</code>. The implementation of <code>PlanQuestion</code> will be further explained in [3.4.5](#3.4.5-PlanQuestion).
3. It will then get questions from <code>PlanQuestionBank</code>, passing it the known attributes.<code> PlanQuestionBank</code> will return a set of questions based on the known attributes. The QuestionQueue in <code>PlanBot</code> is then populated with a set of Questions that <code> PlanQuestionBank</code> returned.
4. If the returned Queue is empty, it will show the recommendations based on the known attributes.
5. Else it will set the current Question to the question at the top of <code>questionQueue</code> and pop the question at the front of the <code>questionQueue</code>, and will display add the question to the <code>ObservableDialogList</code>.


##### Processing inputs - `PlanBot.processInput(String input)`
<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/PlanBot%20Activity%20Diagram.png?raw=true">

*Figure 3.4.3 Activity diagram of processing an input for `PlanBot`*

1. When <code>processInput(String input)</code> is called, it first adds the input string , which is the user's input, into the <code>ObservablePlanDialogList</code>, in order for the user to see what he has replied to the bot. 
2. It then checks if there is a <code>currentQuestion</code>, i.e what the user is typing is a reply to a question. If there is no question being asked, we can assume that the bot has already asked all the questions and it will respond to the user by adding a reply telling the user that he/she has completed answering all the questions. 
3.  If there is a valid <code>currentQuestion</code>, we pass the input into that question, output the reply into the <code>ObservablePlanDialogList</code> if it sucessfully executes, and the `knownAttributes` of the user is updated.
4. The next question is then asked to the user by querying `PlanQuestionBank`.
5. All errors (including inputs) are put into the <code>ObservableDialogList</code> as a reply from `PlanBot`.

##### Recommendations`getPlanBudgetRecommendation()`
Returns the `PlanBudgetRecommendation` that has been generated by `PlanQuestionBank`.

This method should only be executed after `PlanQuestionBank` sucessfully generates a budget recommendation after completing all the questions. 

##### Retriving user attributes ` getPlanAttributes()`
Returns `planAttributes`.

#### 3.4.6 PlanQuestionBank 
##### Construction - `PlanQuestionBank.getInstance()`
1.  <code>PlanQuestionBank</code> upon construction initalizes a new <code>HashMap&lt;>()</code>.
2.  It then populates the `HashMap` with the key being index of the questions and the values being all the questions that we have pre-defined.


##### Retrieving unasked questions -`getQuestions()`
<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/PlanQuestion%20Flow.png?raw=true">

*Figure 3.4.6.1 FlowChart of Questions being asked to the user*

1. Based on the known attributes, adds all the questions based on what we know about the user by using a <code>Queue</code>.
2. It does this by getting the neighbours as dictated by <code>neighbouringAttribute</code> and adds them to a Queue. 
3. A Queue is chosen to implement this so that we can easily pop the question at the top.
4. Their neighbours then are also added to the queue due to the implementation of the <code>while</code>loop.
5. While this is going on, the questions are added to a set of questions using a `hashMap<String, PlanQuestion>`, with the attribute of the question as the key and the question itself as the value. This is so that we can easily prune the set in the next step. 
6. Then, it prunes out the questions for attributes of the user that we already know about, and returns the set of questions.

##### Making recommendations -`makeRecommendation(Map<String, String> planAttributes)`

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/PlanBot%20Export%20Sequence.png?raw=true">

*Figure 3.4.6.2 Sequence diagram of the last question being answered, PlanBot generating recommendations and exporting recommendation data*

For each attribute we can add a:

* Text suggestion to be shown to the user to a `StringBuilder`, 
* Suggested expense to be added to the expenseList by adding an Expense to         `List<Expense> recommendationExpenseList` 
* suggested budget for a category by adding to `Map<String, BigDecimal>` a category and monetary amount.

The 3 objects are then passed into the container class `PlanRecommendation` which then is returned.

#### 3.4.7 PlanQuestion

##### Construction - `new PlanQestion(String question, String[] answers, String[] attributeValue,String attribute)`

`String question` the question that we are asking the user.

`String[] answers` is an Array of strings of the possible answers

`String[] attributeValue` is an Array of Attributes the attribute could take

`String attribute`  the attribute of the user we want to determine from the question

The i'th answer in `String[] answers` should correspond  to the i'th value of  `String[] attributeValue`, i.e if the first expected answer in answers is `YES`, the first value of attributeValue should be true.

For monetary values, both`String[] answers` and `String[] attributeValue` should contain a single String `"Double"` 


##### Add neighbouring questions - `addNeighbouring(Integer neighbouring)` 
Adds the index of the next question as a neighbour for no matter the `attributeValue`.

 Alternatively, `addNeighbouring(String attributeValue, Integer neighbouring)` Adds the index of the next question for a specific `attributeValue`.





##### Getting a reply from a PlanQuestion - `getReply(String input, Map<String, String> attributes)`

1. If the value of the attribute we are expecting is a monetary value, we parse the value to a big decimal and update `attributes`.
2. Else it will try to find the mapped attribute value to `input` from  `answersAttributesValue`.
3. Then, the updated `attributes` and a Success message is returned using a container class `Reply`

#### 3.4.7.1 Adding Questions to PlanQuestionBank
1. In the constructor of PlanQuestionBank, construct a new PlanQuestion object.
2. Then, put the question into `questionList`, where the key is the index of the question, and the value is the question you wish to add. The index you have chosen should not have been used by another question.
3. Then add the index to the desired (existing) question that you want your question to be asked after.


The following example shows how to add 2 questions(`question13` and `question14`) that is to be asked after `question11`(which is already in `Duke++` currently). Do note that:

* `Question13` is asking for a yes or no answer
*  `Question14` is asking for a monetary amount, 
* `question14` will only be asked if `HAS_HOBBIES` is `TRUE`. 
```
private PlanQuestionBank() throws DukeException {

...
PlanQuestion question11 = new PlanQuestion("Do you subscribe to a music subscription service? <yes/no>",
                BOOL_ANSWERS,
                BOOL_ATTRIBUTE_VALUES,
                "MUSIC_SUBSCRIPTION");
        question11.addNeighbouring(12);
        question11.addNeighbouring(13);
        questionList.put(11, question11);

...

PlanQuestion question13 = new PlanQuestion("Do you have hobbies,
                {YES, NO},
                {TRUE, FALSE},
                "HAS_HOBBIES");
question13.addNeighbouring("TRUE",14);
questionList.put(13, question11);

PlanQuestion question14 = new PlanQuestion("How much do you spend on your hobbies monthly? <money amount>" ,
                {"DOUBLE"},
                {"DOUBLE"},
                "HOBBIES");
questionList.put(14, question14);

}
```
4. Optionally, in `makeRecommendations`, by checking the attrribute and its corresponding value in `planAttributes` the following can be done:
*  Add Strings to the output by appending Strings to `StringBuilder recommendation`. 
* Add to the budget by mapping a category to a monetary amount, or
* Add an expense by constructing an `Expense` object and adding it to  `List<Expense> recommendationExpenseList`

Continuing from the previous example, the following shows how to make a `budgetRecommendation` when the user has a hobby and he spends a non-zero amount on it.

```
PlanRecommendation makeRecommendation(
Map<String, String>planAttributes)
throws DukeException {
        Map<String, BigDecimal> budgetRecommendation = new HashMap<>();
        StringBuilder recommendation = new StringBuilder();
        List<Expense> recommendationExpenseList = new ArrayList<>();
        try {
        
        ...
        
        if(planAttributes.get("HAS_HOBBIES") == "TRUE") {
            BigDecimal hobbyAmount = Parser.parseMoney(
            planAttributes.get("HOBBIES"))
            if(hobbyAmount.compareTo(BigDecimal.ZERO == 1)) {
                 recommendation.append(" You should allocate $) 
                 + hobbyAmount 
                 + ( to your hobby!\n\n");
                 budgetRecommendation.put("HOBBIES", hobbyAmount) 
            }
            
        ...
        
        }
        
        ...
        }catch {
        ...
        }
```

#### 3.4.8 Income

##### Overview
The Income feature allows the user to keep track of their sources of income. An `Income` class contain an `amount`, a `description` and a `time`.These incomes are stored in `IncomeList` class, which contains an `ObservableList<Income>`.

New incomes can be added using `AddIncomeCommand` class, which inherits from `Command` class. When an `addIncome` command is given as input, it retrieves the two parameters - `amount` and `description`, of the income. It then adds a new `Income` to `IncomeList` and then stores it to `income.txt`.  

Incomes from `IncomeList` can be deleted using `DeleteIncomeCommand` class, which also inherits from `Command` class. When a `deleteIncome` command is given as input, it retrieves a parameter - `index`, of the income as shown. The `Income` is then deleted from `IncomeList` and stores the update to `income.txt`.

*Example: User adds an income*

1. While on `BudgetPane`, the user enters an input `addIncome 1000.50 /d Internship at Grab`. `CommandResult` is constructed, which then calls its own `execute()` method, creating a new `CommandParam` as a result to parse the string input. 

2. `CommandParam` then breaks down the string, recognises and calls `AddIncomeCommand` to initialise it with *1000.50* as the amount and *Internship at Grab* as the description.

3. When the `execute()` method in `AddIncomeCommand` is called, a new `Income` is initialised with *1000.50* as the amount and *Internship at Grab* as the description through `Income.Builder`. This is added to `IncomeList` through the `addIncome()` method. 

4. This new income is saved to the `income.txt` file by overwritting the text file with the updated `IncomeList` through the `saveIncomeList()` method from `IncomeListStorageManager`. 

5. Finally, the `execute()` method in `AddIncomeCommand` returns a `CommandResult` which flashes a message in the GUI to show a completion of the addition of income.

##### Construction - `Income.Builder()`
Income is constructed using an internal `Builder` object, just like Expense. For example:

    Income.Builder builder = new Income.Builder();
    builder.setAmount("400");
    builder.setDescription("Pocket Money");
    incomeList.add(builder.build());
`Income.Builder` has the following methods:
- `setAmount(String amount)`
- `setAmount(BigDecimal amount)`
- `setDescription(String description)`
- `Build()`

#### 3.4.9 IncomeList Implementation
##### Construction - `new IncomeList(List<Income> internalList)`
1. On construction, `IncomeList` sets its `internalList`, which is a list of all expenses, to the list that was loaded from storage file `income.txt`.
2. In cases where the storage file has been corrupted in which the data in `income.txt` cannot be read and parsed to `internalList`, a new `internalList` will be instantiated.

##### Methods

##### Add - `IncomeList.add(Income income)`
1. An Income object is constructed as described in [Section 3.4.7](#3.4.7). 
2. This income is then added to the `internalList`.
3. The `externalList`, which is the list that the user sees, is updated

##### Delete - `IncomeList.delete(int index)`
The item that corresponds to the index in `externalList` is deleted from both the `externalList` and `internalList`.

#### 3.4.10 Budget View
##### Overview
The Budget View feature allows users to keep track of categories deemed as more important to monitor. A `BudgetView` class solely stores a map to handle the different view panes to their respectuve categories.

Budget views can be added or replaced accordingly using the `ViewBudgetCommand`, depending if a view was already visible. When a `viewBudget` command is given, it takes in two parameters - `view` which refers to the position of the pane the user wishes the budget view to be, and `category`, the specified budget category to track. `budgetView.txt` is then updated and saved accordingly.  

There are 6 positions for the budget view to be placed, represented by a number between *1 and 6*. 

*Example: User adds a budget view*

1. While on `BudgetPane`, the user enters an input `viewBudget 3 /tag Hall`. `CommandResult` is constructed, which then calls its own `execute()` method, creating a new `CommandParam` as a result to parse the string input.

2. `CommandParam` then breaks down the string, recognizes  and calls `ViewBudgetCommand` to initialise it with *3* as the view and *Hall* as the tag.

3. When the `execute()` method in `ViewBudgetCommand` is called, `setBudgetView()` method is called from the class `BudgetView`. This updates the map `budgetViewCategory` in `BudgetView` with the two parameters in the new budget view . 

4. The budget view is then saved to the `budgetView.txt` file by overwriting the text file with the updated `budgetViewCatagory` through the `saveBudgetView()` method from `BudgetViewStorageManager`. 

5. Finally, the `execute()` method in `ViewBudgetCommand` returns a `CommandResult` which flashes a message in the GUI to show a completion of the addition of budget view.

##### Construction - `new BudgetView(Map<Integer,String> budgetViewCategory)`
1. On construction, `BudgetView` sets its `budgetViewCategory`, which is a map of budget views, to the map that was loaded from storage file "budgetView.txt"
2. It then updates the view of BudgetPane accordingly 

##### Methods
##### Set - BudgetView.setBudgetView(int view, String category)
The view pane is set to the budget of the specified category by adding the two parameters to `budgetViewCategory`.

<img : src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/viewBudget%20Activity%20Diagram.png?raw=true">

*Figure 3.4.9 BudgetView Activity Diagram*

### 3.5 Storage
1. When launching the application, individual storages of all models are first initialized.

2. The `StorageManager` is then constructed with these storages of models and it implements the `Storage` as the API.

3. When constructing `Model`, the `Storage` provides data by its various `load...()` methods such as `loadExpenseList()` and `loadPlanAttributes()`.

4. When data in `Model` changes due to user commands and the change is supposed to be saved in `Storage`, the `Storage` saves changed data by its various `save...()` methods such as `saveExpenseList()` and `savePlanAttributes()`.

### 3.6 Reuse History Input
#### 3.6.1 Implementation
The Reuse History Input feature is facilitated by <code>InputHistory</code> class. It internally stores a list <code>inputHistory</code> containing all previous inputs entered by user since the launch of the application and an integer <code>iteratingIndex</code> to iterate through the list. 

It provides following public methods for <code>MainWindow</code>:

* <code>InputHistory#add(String userInput)</code> — Saves the entered input into history.

* <code>InputHistory#isAbleToLast()</code> — Indicates whether there are earlier inputs than current index.

* <code>InputHistory#isAbleToNext()</code> — Indicates whether there are more recent inputs than current index.

* <code>InputHistory#getLastInput()</code> — Returns one earlier input in history by subtracting index by one.

* <code>InputHistory#getNextInput()</code> — Returns one more recent input in history by adding index by one.

These methods are used by <code>textField</code> in <code>MainWindow</code> once a UP or DOWN KeyEvent is detected.

The following activity diagram summarizes what happens when user press UP or DOWN keys:

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Activity_Diagram_inputHistory.png?raw=true">

#### 3.6.2 Design Considerations
##### Aspect: Where to place inputHistory List
####      • Alternative 1 (current choice): Encapsulates it as individual class.
Pros: Maintains the Single Responsibility Principle.
Cons: Writes more lines of codes.
####      • Alternative 2 : Place it in the MainWindow class.
Pros: Writes fewer lines of codes.
Cons: Violates the Single Responsibility Principle as the MainWindow should not handle this feature.

### 3.7 Auto-Complete User Input
#### 3.7.1 Implementation

The auto-complete feature is facilitated by `AutoCompleter`. It receives content from `userInput` and modifies it into a completed command. Modification is done by replacing the fragment after the last space of original input with the complement token produced by `AutoCompleter`. 

A <code>complementList</code> is used to store all suitable complement tokens that can replace the original last token. An <code>iteratingIndex</code> is used to indicate which complement token will be adopted.

The `AutoCompleter` acquires information of commands by having a `Supplier<Stream<Command>>`  as its field. The following class diagram illustrates the classes involved.

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleter%20Class%20diagram.png?raw=true">

The `AutoCompleter` provides following public methods for <code>MainWindow</code>:

* <code>AutoCompleter#receiveText(String fromInput)</code> — Receives the content from userInput TextField.

* <code>AutoCompleter#getFullComplement()</code> — Returns the completed command.

These methods are be used by `textField` in `MainWindow` once a Tab keyEvent is detected.

After receiving the text, `AutoCompleter` first decides on its `purpose`. The `purpose` enumerator includes following elements:

* `COMPLETE_COMMAND_NAME`
* `PRODUCE_PARAMETER`
* `COMPLETE_PARAMETER`
* `ITERATE`
* `NOT_DOABLE`

If the `purpose` is one of `COMPLETE_COMMAND_NAME`, `PRODUCE_PARAMETER` and `COMPLETE_PARAMETER`, then the `complementList` is reset by all suitable complement tokens and `iteratingIndex` is reset to be zero.

If the `purpose` is `ITERATE`, then the `iteratingIndex` increases by one, or returns to zero if the tail of `complementList` is reached.

If the `purpose` is `NOT_DOABLE`, then the `complementList` will be cleared. i.e. There is no suitable complement token.

Given below is an example usage scenario and how the auto-complement is achieved step by step.

Step 1. The user launches the application. The `AutoCompleter` will be initialized with an empty `complementList` and an undefined `iteratingIndex`.

Step 2. The user types "add" inside the command box and presses Tab key (Enter key is not pressed yet). Based on "add", the purpose is decided as `COMPLETE_COMMAND_NAME`. The `complementList` is then reset with all suitable options as below. The `iteratingIndex` is reset as zero, so "addExpense" is adopted to replace "add" in the original input.

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleteState1.png?raw=true">

<p>

Step 3. The user presses Tab key again to iterate through other options. The `purpose` is decided as `ITERATE` and the `iteratingIndex` increases by one. Now "addPayment" is adopted.

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleteState2.png?raw=true">

<p>

Step 4. The user presses Tab key again. Now "addBudget" is adopted.

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleteState3.png?raw=true">

<p>

Step 5. The user presses Tab key again. As the `iteratingIndex` reached the tail of `complementList`, it then returned to the first element. The "addExpense" is adopted again.

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleteState4.png?raw=true">

<p>

Step 6. The user writes the cost (e.g. S$10) of the expense after "addExpense" and leaves a space. Then user presses Tab again. Based on the valid command name "addExpense", the purpose is decided as `PRODUCE_PARAMETER`. The `complementList` is then reset with all suitable parameter names as below. The `iteratingIndex` is reset as zero, so "/recurring" is adopted to replace the fragment after the last space (appended to the end of input).

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/AutoCompleteState5.png?raw=true">

<p>

The following activity diagram summarizes what happens when users press Tab key:

<img src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Activity%20Diagram%20for%20AutoCompleter.png?raw=true">

#### 3.7.2 Design Consideration
##### Aspect: Coverage of auto-complete
#### Alternative 1: AutoCompleter can fill the command names, parameter names and parameter contents.
Pros: User can auto-complete more contents in the command.
Cons: Increases tremendous couplings between `AutoCompleter` and `Model` as `AutoCompleter` has to access all components in `Model` to get suitable parameter contents for each command.  
#### Alternative 2 (current choice): AutoCompleter can only fill command names and parameter names.
Pros: Maintain lower couplings as `AutoCompleter` can get all information from all command classes.
Cons: User has to type contents of parameters by themselves.

### 3.8 <code>goto</code> command

The switchable region in the `MainWindow` is `StackPane`. The `StackPane` contains all our panes as its children.

To show a certain pane, that pane should be set visible, while all other panes are set invisible by using `Node.setVisibility(boolean isVisible)`.

### 3.9 <code>exit</code> command





## 4. Appendix

### Target User Profile
* NUS student
* has many different types of expenses
* has limited amount of money per month
* can type relatively fast
* does not like the conventional budget tracking app


### Value Proposition
Duke++ allows a user who can type quickly using his keyboard to manage his expenses much faster as compared to something like an app with touch interface.


### Requirements
#### User Stories
Priority     | As a...       | I can...      | so that       |
------------ | ------------- | ------------- | ------------- |
*** | Student | see a history of my spending, adding, editing and deleting entries of my past expenditure (CRUD). | I can track my spending |
**  | Student | sort my expenditure according to the amount spent based on categories|  I know which items are the most significant |
**  | Student | filter my expenditure according to the date|   I can see information relevant to me |
**  | Student | tag my spendings to general categories such as food, entertainment, transport, shopping | I can manage and track how much I spend on different categories |
**  | Student | set goals for overall spending and in each category of spending | so that I can keep within my budget |
**  | Student | have a graphical breakdown of my spending with pie charts and graphs throughout the month | I can visualise my spending |
**  | Student | filter my spending based on categories and know how much money I can spend in that category | I do not go over budget |
**  | Student | track total amount of money loaned out to people | I know how much money I have to work with |
**  | Student | keep note of the specific loans owed to me by different people | I can be aware of who owes me money| 
**  | Beginner Student | have a help menu to aid in navigation  | I can learn how to use the program |
**  | Student | view Duke++ in a nice User interface | I can interact with Duke more easily |
**  | Student | clear the past financial records before a certain time | I can remove outdated information I don't want anymore and release storage|
**  | Student | see the amounts of expense over recent months or weeks in one bar chart. | I can know the trend of how my expense varies over time to time.
**  | Student | have a grpahical user interface that will display things I want | I can navigate the software easily.
\*  | Student | define my own categories | I am not constrained by a fixed list of categories |
\*  | Student | have recurring spending with the same name to be automatically tagged to a category | I do not need to do it repeatedly |
\*  | Student | add tentative expenditures to my budget | I can choose the best option after comparing my options |
\*  | Student |undo commands | I can undo typing mistakes quickly |
\*  | Proficient student | have shorter commands | I don’t have to type out my commands fully |
\*  | Student |  add location information to certain places to see how much money I spend at each place | I know where my money is being spent |
\*  | Treasurer | export data to another file format e.g CSV | I can show it to others outside of Duke++ |
\* | Student | hide a specific category from the graphical breakdown of my spending | I can omit certain expenditure when managing my finances if needed|

#### Non-functional requirements:
* Java 11
* Open Source APIs only
* CLI/Text based interaction
* Single User
* Human editable storage file
* Portable (No installer)

#### Use Cases:
##### Use case: View trend for given category 
1. User requests to list a given category
2. Duke++ shows a list of spending in a category
3. User requests to show trend in the category
4. Duke++ shows a bar chart of month to month spending in the category
Use case ends.

##### Use case: Add expenditure
1. User requests to add a certain expenditure
2. Duke++ prompts user to fill in certain fields
3. User fills in requested fields
4. Duke++ confirms addition of expenditure by repeating expenditure added

##### Extensions:
2. a) List is empty
Use case ends.
 

##### Use case: Export data as file in .csv format
1. User requests to export currently displayed information as a csv file
2. Duke++ prompts user to indicate the address to store the exported file
3. User types in the address
4. Duke exports the csv file to the given address
Use case ends.
 

### Manual Testing Instructions


#### PlanBot

1. Navigate to `PlanPane` using `goto plan`.

2. Simply reply to PlanBot's questions by typing in the user input and follow the instructions on screen!

3. After answering all of PlanBot's questions, PlanBot will generate a list of recommendations.

4. Type `export` when prompted to export the recommendations.

#### Expense
1. Navigate to `ExpensePane` using `goto expense`.

2. Add an expense.
```
addExpense 2.60 /d Milk Tea /tag Drinks /isTentative
```
3. Delete the expense using its index as reflected.
```
deleteExpense 1
```
4. Confirm a tentative expense
```
confirm 1
```
5. Sort the expense list
```
sortExpense time
```
6. Change the view scope of the expense list
```
viewExpense month
```

#### Payment

Tips: Please follow the order below for better user experience.

1. Navigate to `PaymentPane` using `goto payment`.
```
goto payment
```

2. Add a payment with properties given below.
```
addPayment 65 /description Illustrator workshop sign up fee /due 02/12/2019 /priority medium /tag study /receiver Tuition Center
```

3. Change properties of an existing payment. Here we change the month from November to December both in description and due. After the change, the payment may be placed below as the sorting criteria is TIME.
```
changePayment 1 /description Top Up Mobile Data for December /due 01/12/2019
```

4. Delete a payment with given index.
```
deletePayment 1 
```

5. Finish a payment with given index. The finished payment will be recorded by Expense Tracker.
```
donePayment 1
```

6. Use `goto` command to check whether the payment is recorded by Expense Tracker.
```
goto expense
```

7. Change the view scope to only show payments coming in the current week.
```
viewPayment week
```

8. Switch the view scope to all.
```
viewPayment all
```

9. Sort payments with the sorting criteria `amount`.
```
sortPayment amount
```

10. Search payments with the keyword "Raffles".
```
searchPayment Raffles
```



#### Income List
1. Navigate to `BudgetPane` using `goto budget`.

2. Add an income.
```
addIncome 400 /d Pocket Money
```
3. Delete an income based on its index (0-based).
```
deleteIncome 1
```

#### Budget 
1. Navigate to `BudgetPane` using `goto budget`.

2. Add budget for the month.
```
addBudget 300
```
3. Add budget for a specific tag.
``` 
addBudget 20 /tag Shopping
```
4. Set a particular pane to the specified budget.
```
viewBudget 1 /tag Food
```

