




# COMPal - Developer Guide

Welcome to the **COMPal** Developer Guide! This Developer Guide is still being worked on for now!

# Table of Contents
[**1. Introduction**](/docs/DeveloperGuide.md#1-introduction)

[**2. About this Developer Guide**](/docs/DeveloperGuide.md#2-about-this-developer-guide)

[**3. Setting Up**](/docs/DeveloperGuide.md#3-setting-up)
+ [3.1 Prerequisites](/docs/DeveloperGuide.md#31-prerequisites)
+ [3.2 Setting up the Project in your Computer](/docs/DeveloperGuide.md#32-setting-up-the-project-in-your-computer)
+ [3.3 Verifying the Setup](/docs/DeveloperGuide.md#33-verifying-the-setup)
+ [3.4 Configurations to do before Writing Code](/docs/DeveloperGuide.md#34-configurations-to-do-before-writing-code)

[**4. Design**](/docs/DeveloperGuide.md#4-design)

 + [4.1 Architecture](/docs/DeveloperGuide.md#41-architecture)
 + [4.2 UI Component](/docs/DeveloperGuide.md#42-ui-component)
 + [4.3 Logic Component](/docs/DeveloperGuide.md#43-logic-component)
 + [4.4 Commons Component](/docs/DeveloperGuide.md#44-commons-component)
 + [4.5 Storage Component](/docs/DeveloperGuide.md#45-storage-component)
 + [4.6 Model Component](/docs/DeveloperGuide.md#46-model-component)


[**5. Implementation**](/docs/DeveloperGuide.md#5-implementation)
 + [5.1 View Feature](/docs/DeveloperGuide.md#51-view-feature)
 + [5.4 Reminder Feature](/docs/DeveloperGuide.md#54-reminder-feature)
 + [5.5 Find Feature](/docs/DeveloperGuide.md#55-find-feature)


[**6. Documentation**](/docs/DeveloperGuide.md#6-documentation)

[**7. Testing**](/docs/DeveloperGuide.md#7-testing)

[**8. DevOps**](/docs/DeveloperGuide.md#8-dev-ops)

[**Appendix A: User Profile**](/docs/DeveloperGuide.md#appendix-a-user-profile) 
 
[**Appendix B: User Stories**](/docs/DeveloperGuide.md#appendix-b-user-stories)  

[**Appendix C: Use Cases**](/docs/DeveloperGuide.md#appendix-c-use-cases)  

[**Appendix D: Non-Functional Requirements**](/docs/DeveloperGuide.md#appendix-d-non-functional-requirements)  

[**Appendix E: Glossary**](/docs/DeveloperGuide.md#appendix-e-glossary)  


## 1. Introduction
**COMPal** is a desktop application specifically designed for the **hectic schedule** for the **modern student** in mind. By **simply inputing** their busy and compact schedule, the application is able to automatically **generate a prioritize daily schedule** for the user ! This ensures that the student can **focus** on the more **important upcoming task**! Additionally with **features such as** reminders of task and also finding of free time slot, COMPal allows the **ease of planning** for future task.

It is catered to student-users who prefer to use and are adept at using a **Command-Line Interface (CLI)**, while still having a clean **Graphical User Interface (GUI)** to properly **visualize schedules** and **organize tasks** better. 

## 2. About This Developer Guide

This **Developer Guide** provides a detailed documentation on the implementation of all the time-management tools of **COMPal**. To navigate between the different sections, you could use the [**Table of Contents**](/docs/DeveloperGuide.md#table-of-contents) above.

For ease of communication, tbe following **terminology** will be used:

Term                 | Definition
---------------------|-----------------------------
Task                 | The general term that is used to describe an action that might need to be done by the user. 
 
Additionally, throughout this **Developer Guide**, there will be various **icons** used as described below.

Icon                 | Description
---------------------|-----------------
:information_source: | Additional important information about a term/concept
:bulb:               | A tip that can improve your understanding about a term/concept
:warning:            | A warning that you should take note of  

## 3. Setting Up
### 3.1. Prerequisites
1. [**JDK 11**](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase11-5116896.html) or later
2. [**IntelliJ** IDE](https://www.jetbrains.com/store/?fromNavMenu#personal?billing=yearly)

 <img src="images/DG_2nd_gradle.png" alt="Locating gradle icon on sidebar" width="800"/>
<!---| | |
|---------------------|-----------------------------|
|:information_source: | **IntelliJ** by default has **Gradle** and **JavaFx** plugins installed. Do not disable them. If you have disabled them, go to `File` > `Settings` > `Plugins` to re-enable them.|--->


### 3.2. Setting up the Project in your Computer
1. Fork this repo, and clone the fork to your computer. 

2. Open **IntelliJ** (if you are not in the welcome screen, click `File` > `Close Project` to close your existing project dialogue first)

3. Set up the correct **JDK** version for Gradle  
    
    1. Click `Configure` > `Project Defaults` > `Project Structure`  
    
    2. Click `New...` and find the directory of the **JDK**.
    
4. Click `Import Project`.

5. Locate the `build.gradle` file and select it. Click `OK`.

6. Click `Open as Project`

7. Click `OK` to accept the default settings. 

8. Use `Gradle` to run the project. 
    
    1. Click on the small `Gradle` icon at the top right of your screen. It should open up the `Gradle` sidebar.
        <img src="images/DG_locating_gradle.png" alt="Locating gradle icon" width="800"/>
    
    2. Click on the small `Gradle` icon at the centre of the `Gradle` sidebar.
        <img src="images/DG_2nd_gradle.png" alt="Locating gradle icon on sidebar" width="800"/>
        
    3. Type `gradle run` to run the project.
    
    4. The **GUI** should show up in a few seconds. Try running a few commands.
    
9. Observe for any code errors displayed in the **console** of the **IntelliJ** IDE. 

### 3.3. Verifying the Setup

1. Run the project using `gradle run`. Try a few commands in the **GUI**.

2. [Run the tests](/docs/DeveloperGuide.md#7-testing) to ensure that they all pass. 

### 3.4. Configurations to do before Writing Code

## 4. Design

#### 4.1. Architecture

<img src="https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/ArchitectureDiagram.png" alt="Overview of architecture" width="700"/>
Figure 1. Architecture Diagram

   
|||
|--|--|       
| :bulb: | The `.pptx` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY1920S1-CS2113T-W17-1/main/docs/diagrams/) folder. To update a diagram, modify the diagram in the pptx file, select the objects of the diagram, and choose `Save as picture`. |

The  **_Architecture Diagram_**  given above explains the high-level design of the App. Given below is a quick overview of each component.

[**`Commons`**](https://github.com/AY1920S1-CS2113T-W17-1/main/tree/master/src/main/java/compal/commons)  represents a collection of classes used by multiple other components. Two of those classes play important roles at the architecture level.

-   `Messages`  : This class contain all types of messages that are to be called from depending  on the type of execution. E.g. Invalid syntax commands messages

-   `COMPal`  : Used by many classes to call the needed functionality such as user interface, storage or parser.
    
The rest of the App consists of four components.

-   [**`UI`**](https://github.com/AY1920S1-CS2113T-W17-1/main/tree/master/src/main/java/compal/ui): The UI of the App.
    
-   [**`Logic`**](https://github.com/AY1920S1-CS2113T-W17-1/main/tree/master/src/main/java/compal/logic): The command executor.
    
-   [**`Model`**](https://github.com/AY1920S1-CS2113T-W17-1/main/tree/master/src/main/java/compal/model): Holds the data of the App in-memory.
    
-   [**`Storage`**](https://github.com/AY1920S1-CS2113T-W17-1/main/tree/master/src/main/java/compal/storage): Reads data from, and writes data to, the hard disk.

For example, the `Parser` component (see the class diagram given below) defines it’s API in the `CommandParser.java` interface and exposes its functionality using the `ParserManager.java` class.

<img src="/docs/diagrams/LogicClassDiagram.png" alt="Overview of Logic parser" width="700"/>
Figure 2. Class Diagram of Logic Parser Component

**Events-Driven nature of the design**

The  _Sequence Diagram_  below shows how the components interact for the scenario where the user issues the command  `delete 1`.

<img src="https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/SDforDeleteSlot.png" alt="Sequence Diagram for deletion of slot" width="700"/>
Figure 3. Component interactions for `delete 1` command.

The sections below give more details of each component.

### 4.2. UI component


<img src="https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/UIClassDiagram.png" alt="Overview of UI Component" width="700"/>
Figure 5. Structure of the UI Component

**API**  :  [`Ui.java`](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/src/main/java/compal/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`UserInput`,`SecondaryOutput`, `tabWindow`which tabs consist of `MainOutput`, `DailyCalender`. Although the application is only input text-based application, our outputs are both GUI and text-based. 

The  `UI`  component uses JavaFx UI framework. The layout of these UI parts are defined in matching  `.fxml`  files that are in the  `src/main/resources/view`  folder.   For example, the layout of the  [`MainWindow`](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/src/main/java/compal/ui/MainWindow.java)  is specified in  [`MainWindow.fxml`](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/src/main/resources/view/MainWindow.fxml)

The `DailyCalender` use information from the `Model` and `COMPal` component to generate or refresh the stage to reflect changes made to the data.

The  `UI`  component,

- Executes user commands using the  `Logic`  component.  
- Displays text-based command results in to the user via `MainOutput` or `SecondaryOutput`.
- Display ​daily calendar of the user via `DailyCalender`. 

### 4.3. Logic component
<img src="/docs/diagrams/LogicClassDiagram.png" alt="logic Diagram" width="700"/>
Figure 6. Structure of the Logic component

The  `Logic`  component handles the parsing of user input and interacts with the Task objects.

1. Uses the `CommandParser` class to parse user input. 
    - This results in a `Command` object which is executed.
2. The execution of `Command` can affect a `Task` object (e.g. adding a `Task` to the `TaskList`)
3. The result of the `Command` execution is encapsulated as a `CommandResult` object which is passed to the `UI` to be rendered as output for the user. 

A Sequence Diagram for interactions within the `Logic` component will be uploaded soon.

### 4.4. Commons Component
Classes used by multiple components are in the [`commons`](/src/main/java/compal/commons) package. It contains 2 important classes: [`Compal`](/src/main/java/compal/commons/Compal.java) and [`Messages`](/src/main/java/compal/commons/Messages.java).

`Compal.java` creates an instance of `Ui`, `Storage`, `TaskList` and `ParserManager`. Other classes will then use `Compal` to call on the aforementioned classes for different method invocations.

In addition, `Compal` contains the `viewReminder` method, which will be called when the GUI is initialised. This provides the user with the reminders set or due within 7 days.

`Messages.java` contains all the error messages that will be printed on the GUI when the user has made an error in their input. This will notify the user to check what he/she has keyed in the command box, and make necessary adjustments. 

### 4.5. Storage Component
API: StorageManager.java

We use very simple and user-editable text files to store user data. Data is stored as data strings separated by underscores. The separation token however, can be easily changed if desired. 
Data is thereafter parsed as a string and then processed by our storage API into application-useful datatypes such as Task Objects. 

While it might be viewed as primitive, the advantage of this approach is that it is an almost no-frills implementation and is easily comprehended the average developer. The average user can also understand and easily directly edit the data file if so desired. 

### 4.6. Model Component

<img src="/docs/diagrams/DG_ArchitectureDiagram_Task.png" width="700" alt="Overall structure of the Model Component"/>
Figure 2. Overall structure of the Model Component

**API**: [`Model`](/src/main/java/compal/model)
 
 The `Model` component
 - stores a `TaskList` object that represents the list of user's tasks
 - stores the Schedule data.
 - does not depend on any of the other four components.


## 5. Implementation




### 5.1. View feature

This feature presents the timetable in different formats to the user.

The available formats are the day view, week view, and the month view. This section will detail how this feature is implemented.​

#### [](LINK)5.1.1. Current Implementation

Upon invoking the  `view`  command with valid parameters (refer to  [UserGuide.adoc](LINK)  for  `view`  usage), a sequence of events is then executed. 

For clarity, the sequence of events will be in reference to the execution of a  `view day`  command. A graphical representation is included in the Sequence Diagram below for your reference when following through the sequence of events. The sequence of events are as follows:

1.  The `view day`  command is passed into the  `logicExecute`  function of  `LogicManager`  to be parsed.
    
2.  `LogicManager`  then invokes the  `processCmd`  function of  `ParserManager`.
    
3.  `ParserManager`  in turn invokes the  `parseCommand`  function of the appropriate parser for the  `view`  command which in this case, is  `ViewCommandParser`.
    
|| Description
---------------------|-----------------
:information_source: | The  `view`  command can be parsed into only 3 general types of views which are the month, week or day view as specified in the command parameter.However only the daily view command can be viewed in GUI format.
:information_source: | Only the `view day` command will invoke a `daily task:<DATE>` GUI for the daily task of the user.
        
 
4.  Once the parsing is done,  `ViewCommandParser`  would instantiate the  `ViewCommand`  object which would be returned to the  `LogicManager`.
    
5.  `LogicManager`  is then able to invoke the  `commandExecute`  function of the returned  `ViewCommand`  object.
    
6.  In the  `execute`  function of the  `ViewCommand`  object, task data will be retrieved from the  `TaskList`  component.  
    
7.  Now that the  `ViewCommand`  object has the data of the current task of the user, it is able to invoke the  `displayDailyView`  method.
    
8.  With the output returned from the  `displayDailyView`, the  `CommandResult`  object will be instantiated.

9.  Only if the user input `view day`, the `CalenderUtil` object will be instantiated and invoke a `dateViewRefresh` to spawn the daily GUI Calendar. 
    
10.  The  `CommandResult`  object would then be returned to the  `LogicManager`  which then returns the same  `CommandResult`  object back to the  `UI`  component.
    
11.  Finally, the  `UI`  component would display the contents of the  `CommandResult`  object to the user. For this  `view day`  command, the displayed result would be the daily task view of the current day along.
    

![ViewDaySequenceDiagram.png](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/ViewDaySequenceDiagram.png?raw=true)

Figure X. Sequence Diagram executing the  `view month`  command.


The 3 general types of view (day, week, month) are generated by the methods  `displayDayView`,  `displayWeekView`,   `displayMonthView`  from the  `ViewCommand`  class and the implementation of these methods is explained below.

|| Description
---------------------|-----------------
:information_source: |If a given `date is not input` by the user, COMPal will deem that `specified date` is the `current date of the user system`.


`displayDayView`  method displays the details of all  the _task_  of a specified date. The implementation of this method can be broken down into 3 parts:

1.  Retrieve all  _tasks_  for the specified day.

2.  Print daily header of day,date. (e.g  Tue,22/10/2019).
    
3.  Print all details of each  _task_  found in chronological time and priority order.

`displayWeekView`  method displays the weekly calendar format of a specified week. The implementation of this method can be broken down into the following steps:

1.  From the current date or input date, determine the next 6 days of the calendar days. 

2.  Print week header (e.g.  21/10/2019 - 27/10/2019).

3.  Invoke `displayDayView` method with the week given.

`displayMonthView`  method displays the current month in a monthly calendar format.  The implementation of this method can be broken down into 2 parts:

1.  From the current date or input date, determine the month and amount of days in a month.

2.  Print the  month header (e.g. January 2020)

3.  Invoke `displayDayView` method for each day in a given month.


    

#### []()5.1.2. Design Considerations

This section details our considerations for the implementation of the  `view`  feature.

##### []()Aspect: Functionality of  `view day`  command

-   **Alternative 1 (current choice):**  Shows the user _tasks_ output on `main window tab `in text format and `Daily View:<Date> tab` in  a `schedule GUI` format.

	-   Pros: Efficient for the user as user is only required to enter a single command to view all details of _tasks_ or _deadline_ in chronological time and priority ranking and quickly know what is needed to prioritize and be do done first from looking at `Daily View tab` .
	
       -   Cons: The formatted day view will be separated into two different tabs showing 2 different output which may confused new user. 
    
-   **Alternative 2:**  Shows the user _tasks_ output only on `main window tab `in text format .
    
    -   Pros: Efficient for the user as user is only required to enter a single command to view all details of all  _task_.
        
    -   Cons:   No `Daily View tab` of the current day for the user to look at the 24-hours schedule of the given date.
    
Alternative 1 was chosen to be implemented as it allow the user to view the  of details to be displayed in the output of the  `view day`  command in  `main window tab `in text format and `Daily View tab` in  `GUI` format. 
The output of alternative 1 allows the user to understand what task are to be prioritized 2 and thus enhances the differentiating factor of  **COMPal**.​

##### []()Aspect: Functionality of  `view week`  command

-   **Alternative 1 :**  Shows the user _tasks_ output on `main window tab `in text format and spawn 7 `Daily View tab` in  `GUI` format for each given date of the week.

	-   Pros: Able to see the  overview of the given week from 1 of the 7 `Daily View tab` .
	
       -   Cons: Very cluttered GUI as user has to slowly pick each tab he would want to view for given date.
    
-   **Alternative 2 (current choice):**  Shows the user _tasks_ output on `main window tab `in text format for given week.
    
    -   Pros: Efficient for the user as user is only required to enter a single command to view all details of all  _task_ of given week.
        
    -   Cons: No `Daily View tab` of the current week to quickly look at the schedule of the given date.

Alternative 2 was chosen as it reduce the cluttering of tabs that will be created due to the invocation of creating 7 new `Daily view tab`. Additionally,instead user is able to use `view day /date <input date>` to specify which date they would want to view in `GUI` format.
The choice of alternative 2 prevent the user to be overwhelm what task are to be prioritized 1 and thus enhances the usability of  **COMPal**.​

#### []()5.1.3 Future Implementation

Though the current implementation has much prevent cluttering the application which allow the users to focus and prioritize on the upcoming tasks at hand. There are still possible enhancement for the view command.

1.  `Weekly View Tab` of _task_ in a  weekly schedule GUI format
    
2.  `Monthly View Tab` of _task_ in a  monthly schedule GUI format

#### [](LINK) 5.4. Reminder feature 

This feature allows users to keep track of undone tasks that are urgent or important. 

Undone tasks that are due within the week and overdue tasks are preset to be included. Additionally, users can manually turn on reminders for important tasks they want to keep track of. 

- To manually turn on/off reminders, the format is `set-reminder /id <TASK ID> /status <Y/N>`. This edits the reminder settings of the task with the specified task ID to the specified status. (on/off)

- To view urgent and important tasks, the format is `view-reminder`. This displays the list of undone tasks that are either overdue, due within the week, or have the reminder settings turned on.

This section will detail how this feature is implemented.​

#### []() 5.4.1. Current Implementation

##### []()Command: `set-reminder`  

Upon invoking the  `set-reminder`  command with valid parameters (refer to  [UserGuide.adoc](LINK)  for  `set-reminder`  usage), a sequence of events is then executed. 

For clarity, the sequence of events will be in reference to the execution of a  `set-reminder /id 1 /status Y`  command. A graphical representation is included in the Sequence Diagram below for your reference when following through the sequence of events. The sequence of events are as follows:

1.  The `set-reminder /id 1 /status Y`  command is passed into the  `logicExecute`  function of  `LogicManager`  to be parsed.
    
2.  `LogicManager`  then invokes the  `processCmd`  function of  `ParserManager`.
    
3.  `ParserManager`  in turn invokes the  `parseCommand`  function of the appropriate parser for the  `set-reminder`  command which in this case, is  `SetReminderCommandParser`.
    
4.  Once the parsing is done,  `SetReminderCommandParser`  would instantiate the  `SetReminderCommand`  object which would be returned to the  `LogicManager`.
    
5.  `LogicManager`  is then able to invoke the  `commandExecute`  function of the returned  `SetReminderCommand`  object.
    
6.  In the  `commandExecute`  function of the  `SetReminderCommand`  object, task data will be retrieved from the  `TaskList`  component.  
    
7.  Now that the  `SetReminderCommand`  object has the data of the current task of the user, it is able to invoke the  `setHasReminder`  method.
    
8.  With the output returned from the  `setHasReminder`, the  `CommandResult`  object will be instantiated.
    
9.  The  `CommandResult`  object would then be returned to the  `LogicManager`  which then returns the same  `CommandResult`  object back to the  `UI`  component.
    
10.  Finally, the  `UI`  component would display the contents of the  `CommandResult`  object to the user. 
    

![SetReminderSequenceDiagram.png](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/SetReminderSequenceDiagram.png?raw=true)

Figure X. Sequence Diagram executing the  `set-reminder`  command.

##### []()Command: `view-reminder` 

Upon invoking the  `view-reminder`  (refer to  [UserGuide.adoc](LINK)  for  `view-reminder`  usage), a sequence of events is then executed. 

A graphical representation is included in the Sequence Diagram below for your reference when following through the sequence of events. The sequence of events are as follows:

1.  The `view-reminder`  command is passed into the  `logicExecute`  function of  `LogicManager`  to be parsed.
    
2.  `LogicManager`  then invokes the  `processCmd`  function of  `ParserManager`.
    
3.  `ParserManager`  in turn invokes the  `parseCommand`  function of the appropriate parser for the  `view-reminder`  command which in this case, is  `ViewReminderCommandParser`.
    
4.  Once the parsing is done,  `ViewReminderCommandParser`  would instantiate the  `ViewReminderCommand`  object which would be returned to the  `LogicManager`.
    
5.  `LogicManager`  is then able to invoke the  `commandExecute`  function of the returned  `ViewReminderCommand`  object.
    
6.  In the  `commandExecute`  function of the  `ViewReminderCommand`  object, task data will be retrieved from the  `TaskList`  component.  
    
7.  Now that the  `ViewReminderCommand`  object has the data of the current tasks of the user, it is able to invoke the  `getTaskReminders`  method.
    
8.  With the output returned from the  `getTaskReminders`, the  `CommandResult`  object will be instantiated.
    
9.  The  `CommandResult`  object would then be returned to the  `LogicManager`  which then returns the same  `CommandResult`  object back to the  `UI`  component.
    
10.  Finally, the  `UI`  component would display the contents of the  `CommandResult`  object to the user. 
    

![ViewReminderSequenceDiagram.png](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/ViewReminderSequenceDiagram.png?raw=true)

Figure X. Sequence Diagram executing the  `view-reminder`  command.


#### []()5.4.2. Design Considerations

This section details our considerations for the implementation of the  `set-reminder` and `view-reminder` feature.

##### []()Aspect: Functionality of  `set-reminder`  command

- `set-reminder` has a `/status` field so that the user can choose to turn on/off the reminder for the specified task.

##### []()Aspect: Functionality of  `view-reminder`  command

-   **Alternative 1 (current choice):**  Shows the user undone tasks that are either overdue, due within the week, or have reminder settings turned on.

	-   Pros: Overdue tasks and tasks that are due within the week are automatically included in the `view-reminder` command. The user does not have to manually turn on the reminder settings for each upcoming task.
	
    -   Cons: There are some restrictions on the user as the user cannot exclude overdue tasks or tasks due within a week from the `view-reminder` command.
    
-   **Alternative 2 :**  Shows the user only tasks that have reminder settings turned on. 
    
    -   Pros: The user can entirely dictate whether the tasks are shown when `view-reminder` command is executed by turning the reminder settings on/off.
        
    -   Cons: It is troublesome for the user to change each task's reminder settings one by one every time the tasks are near their deadlines. The user might also forget to turn on reminders at times, missing important deadlines.

Alternative 1 was chosen as it is more user-friendly. By automatically including upcoming and overdue tasks, the user can easily keep track of the more urgent tasks that need to be completed. 
The user can also include important tasks in the reminders by turning the reminder settings on for the respective tasks.​

#### []()5.4.3 Future Implementation

1.  Allow the automatic addition of tasks due within a certain time period to reminders to be user-defined. 
Example: If the user inputs 14 days, tasks due within 14 days will be automatically included in the `view-reminder` command.
   

### 5.5 Find Feature
  This feature allows the user to search for a keyword or phrase in the description field belonging to all of the tasks.

#### 5.5.1. Current Implementation
The current implementation matches the keyword or phrase exactly to the description. As long as the keyword or phrase is a sub-string in the description field, the task is returned as a match. Likeness of the words are not considered at the moment e.g 'frst' will not match 'first'. 

1. Upon the user entering the find command with a valid keyword, the `LogicManager` is called and sends the user input to `ParserManager`. 
2. `LogicManager` then invokes the `parseCommand` function of `ParserManager`.
3. `ParserManager` in turn invokes the `parse` function of the appropriate parser for the `find` command which in this case,
is `FindCommandParser`.
4. After parsing is done, `FindCommandParser` would instantiate the `FindCommand` object which would be returned to the `LogicManager`.
5. `LogicManager` is then able to call the `execute` function of the `FindCommand` object just returned to it.
6. In the  `execute`  function of the  `ViewCommand`  object, task data will be retrieved from the  `TaskList`  component.  
7.  Now that the  `FindCommand`  object has the data of the current task of the user, it is able to execute its logic. 
8. `FindCommand` will loop through all tasks to find any description matching (non-case sensitive) the keyword or phrase.
9. The result of the command execution, a list of matches from the keyword/phrase passed in,  is encapsulated as a `CommandResult` object which is passed back to `Ui` for displaying to the user.

Here is a sequence diagram portraying the above sequence of events:

![FindSequenceDiagram.png](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/diagrams/FindSequenceDiagram.png?raw=true)


#### []()5.5.2. Design Considerations
The 'find' command should not be too complicated. It should be very intuitive to use and hence there was no need for any much parsing on the part of the `FindCommandParser` object. The user should be able to search for any task with ease and without referring to the user-guide as much as possible. 
#### []()5.1.3 Future Implementation
##### Case-Sensitive Search
Involves just using a different match/regex API
##### Match Based On Likeness/Regular Expressions
Make use of regex to match words based on likeness/regex rules rather than an exact sub-string match. This will help users with typographical errors but is not considered a must to implement. 

## 6. Documentation

## 7. Testing

## 8. Dev Ops

## Appendix A: User Profile

**System**: **COMPal**

**Target User Profile**: Students who
-   want to better organize their time not just according to deadlines but by perceived priorities
-   prefer interacting with a CLI
-   prefers typing over mouse input

**Persons that can play this role** : Undergraduate student, graduate student, a staff member doing a part-time course, exchange student

**Value Proposition**: Students wanting to be more organized without going through too much of a hassle can now better manage their schedules and tasks with Compal’s clean and intuitive user-interface and user-defined priority-based organization.


## Appendix B: User Stories


Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| As a ...|      I want to...     |  So that I can ... | Priority |
|:----------|:-------------:|:------:|:----------|
|Student| Add the due dates of tasks that I have| Neatly organize my schedule|***
|Student| Add my academic timetable| Store my academic schedule|***
|Student| Add meeting schedules| Easily remember about scheduled meetings|***
|Student| Add examination dates and times| View and track upcoming assessments|***
|Student| Add a description to a task that I have| Record necessary information about the task|***
|Student| Edit due dates of tasks that I have| Update the description and deadlines of the tasks|***
|Student| Edit my academic timetable| Update my academic schedule|***
|Student| Edit meeting schedules| Update my appointment timings|***
|Student| Edit examination dates and times| Update assessment dates|***
|Student| View the application in a graphical user interface| View things in an organised and quick manner|***
|Student| View the tasks that are soon to be overdue| Keep track of the things to do|***
|Student| View the timetable in a daily view| See the overview of the whole day|***
|Student| View my ongoing school-related task| Keep track of my progress|***
|Student| Be notified of my classes to attend|Be reminded of my schedule|***
|Student| Be notified of the tasks due|Be reminded of my schedule|***
|Student| Be notified of upcoming examinations| Be reminded of my schedule|***
|Student| Be notified of upcoming meetings| Be reminded of my schedule|***
|Student| Sort my tasks according to the deadlines and importance| Know which task needs to be focused on|***
|Student| Find specific things in the application using a keyword| Find related things|***
|Student| Remove a scheduled slot| Delete cancelled meetings/classes|***
|Student| Remove tasks| Delete tasks|***
|Student| Priortise more important timetable slots based on personal ranking| rearrange my schedule in the event that there is a timetable clash|***
|Student| View the timetable in a monthly view| See the overview of the whole month|**
|Student| View the timetable in a weekly view| See the overview of the whole week|**
|Student| Mark my ongoing school-related task as completed by task and subtask| Keep track of the progress of individual task and subtasks|**
|Student| Track my assignment progress| Know what needs to be done|**
|Student| Add the result/grade of module assignment, attendance, midterm results|Store module's component grades|*
|Student| Add my received module grades for each semester| Store the semester's grades|*
|Student| Edit the result/grade of module assignment, attendance, midterm results| Estimate the grade that I will receive|*
|Student| Track my cumulative GPA| Work towards the GPA I aim for|*




## Appendix C: Use Cases



**Use case 1: Store task or academic schedule**

**_Main Success Scenario (MSS)_**
1.  . User inputs event command followed by all the mandatory parameters.
    
2.  . System reflects the additions to the planner.

	Use case ends.
	
**_Extensions_**

-   1a. System detects an error in the entered data.
    
    -   1a1. System outputs error message.
        
        Use case ends.
        
    
-   1b. System detects insufficient parameters in the entered data.
    
    -   1b1. System outputs error message.
        
        Use case ends.​

**Use Case 2: Edit Task**

*Prerequisite: User is aware of the TaskID*

**MSS**
1. User inputs command to edit a task along with the TaskID, followed by the parameters which is needed to be changed.
    
2. System changes the specified parameters for the slot.
    
3. System then reflects the task parameters as well as the parameters changed.

   Use case ends.

**_Extensions_**

-   1a. TaskID does not exist in COMPal.
    
    -   1a1. System outputs error message.
        
        Use case ends.
        
    
-   1b. System detects an error in the entered data.
    
    -   1b1. System outputs error message.
        
        Use case ends.​

**Use Case 3: Mark Task as Done**

*Prerequisite: User is aware of the TaskID.*

**MSS**
1.  User enters command to mark task as done
    
2.  COMPal reflects task status changes

     Use case ends.

 **_Extensions_**

-   1a. TaskID does not exist in COMPal.
    
    -   1a1. System outputs error message.
        
        Use case ends.
        
    
-   1b. System detects an error in the entered data.
    
    -   1b1. System outputs error message.
        
        Use case ends.​
    
  

**Use Case 4: Change the daily view date**

**MSS**
1.  User enters command to change the date of daily calendar view.
  
2.  COMPal displays the selected view date on GUI.

    Use case ends.
 
 **_Extensions_**

-   1a. System detects an error in the entered data.
    
    -   1a1. System outputs error message.
        
        Use case ends.
-   1b. System detects no task on selected view date.
    
    -   1b1. System outputs message indicating no task on chosen date.
        
        Use case ends.​
    

**Use Case 5: Search for Tasks**

**MSS**
1.  User enter find command along with the parameter to search for.
    
2.  COMPal reflects search results

  **_Extensions_**

-   1a. System does not find matching keyword
    
    -   1a1. System indicates that there are no matching keyword.
    
    Use case ends.

## Appendix D: Non-Functional Requirements
    
1.  COMPal can store up to 1,000,000 tasks in a clear text file.
    
2.  COMPal must respond fast, within 2 seconds so that the user does not have to wait too long.
    
3.  COMPal system application should take up relatively little space on the local machine.
    
4.  COMPal’s GUI must be intuitive and pleasant to the eyes.
    
5.  COMPal consistently performs specified function without failure.

6.  The user’s OS date and time must be correctly synchronizes to local date and time.


## Appendix E: Glossary
**Task**:  A generic term used to refer to any instance of an object in the user's schedule  
**View**: The layout in which the schedule is displayed to the user 
