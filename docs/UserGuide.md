# User Guide
By: `AY1920S1-CS2113T-W17-1` Last Updated: `26/09/2019` License: `MIT`

We are still working on the User Guide. Stay tuned as we roll out more new features!

[**1. Introduction**](/docs/UserGuide.md#1-introduction)

[**2. Understanding This User Guide**](/docs/UserGuide.md#2-understanding-this-user-guide) 
+ [2.1. Basic Information]()
+ [2.2. Navigating Around Our Application]()

[**3. Quick Start**](/docs/UserGuide.md#3-quick-start)

[**4. Features**](/docs/UserGuide.md#4-features)
+ [4.1. General Commands](/docs/UserGuide.md#41-general-commands)
    - [4.1.1. Viewing help: `help`](/docs/UserGuide.md#411-viewing-help-help)
    - [4.1.2. Listing all Tasks: `list`](/docs/UserGuide.md#412-listing-all-tasks-list)
    - [4.1.3. Deleting a Task: `delete`](/docs/UserGuide.md#413-deleting-a-task-delete)
    - [4.1.4. Completing a Task: `done`](/docs/UserGuide.md#414-completing-a-task-done)
    - [4.1.5. Deleting a Task: `delete`](/docs/UserGuide.md#415-deleting-a-task-delete)
    - [4.1.6. Viewing Tasks on a Specified Date: `view`](/docs/UserGuide.md#416-viewing-tasks-on-a-specified-date-view)
    - [4.1.7. View Reminder: `view-reminder`](/docs/UserGuide.md#417-view-reminder-view-reminder)
    - [4.1.8. Set Reminder: `set-reminder`](/docs/UserGuide.md#418-set-reminder-set-reminder)
    - [4.1.9. Exiting **COMPal**: `bye`](/docs/UserGuide.md#419-exiting-compal-bye)
+ [4.2. Detailed Management Commands](/docs/UserGuide.md#42-detailed-management-commands)
    - [4.2.1. Adding **Deadline**: `deadline`](/docs/UserGuide.md#421-adding-deadline-deadline)
    - [4.2.2. Adding **Event**: `event`](/docs/UserGuide.md#422-adding-event-event)
    - [4.2.3. Adding **Task** with **Fixed Duration**: `fixeddurationtask`](/docs/UserGuide.md#423-adding-task-with-fixed-duration-fixeddurationtask)
    - [4.2.4. Adding **Recurring Task**: `recurtask`](/docs/UserGuide.md#424-adding-recurring-task-recurtask)
    - [4.2.5. Adding **Lecture Session**: `lect`](/docs/UserGuide.md#425-adding-lecture-session-lect)
    - [4.2.6. Adding **Tutorial Session**: `tut`](/docs/UserGuide.md#426-adding-tutorial-session-tut)
    - [4.2.7. Adding **Sectional Session**: `sect`](/docs/UserGuide.md#427-adding-sectional-session-sect)
    - [4.2.8. Adding **Lab Session**: `lab`](/docs/UserGuide.md#428-adding-lab-session-lab)

[**5. Future Enhancements**](/docs/UserGuide.md#5-future-enhancements)

[**6. Frequently-Asked Questions**](/docs/UserGuide.md#6-frequently-asked-questions)

[**7. Command Summary**](/docs/UserGuide.md#7-command-summary)

## 1. Introduction
Welcome to **COMPal**!

**COMPal** is a Command Line Interface calendar application that targets students who prefer to use a desktop application for managing their busy student life. **COMPal** captures your timetable in a ***user-friendly layout***, giving you an ***informative overview*** of your schedule in a brief glance. 

Additionally, you can ***include non-academic activities*** along with your academic timetable, unlike other widely-used timetable tools. You also have the ***freedom to prioritise certain tasks*** over less important ones, and make use of ***timely reminders*** on pending tasks.

Take control with our [**Quick Start**](/docs/UserGuide.md#3-quick-start) guide. ***COMPal*** your life, today.

## 2. Understanding This User Guide
### 2.1 Basic Information

This guide explains how you can use **COMPal** to divide your time between your academic commitments and non-academic activities.

You can find comprehensive steps on how to fully utilise **COMPal**'s extensive suite of time-management tools. **Frequently Asked Questions** can also clarify any pressing doubts that you may have. Our **Command Summary** provides a concise, easy-to-read summary of our commands for your easy perusal. 

You can use the **Table of Contents** above to navigate effortlessly between each section.

We have developed a list of icons below that will help you a lot in digesting our material.

Icon                 | What does it mean?
---------------------|-----------------
:information_source: | Important information that you may want to take note of 
:bulb:               | Tips and Tricks! Follow these suggestions to make your life simpler.
:warning:            | Warning! You need to be careful when this appears.

## 3. Quick Start
1. Ensure you have [**Java Version 11**](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase11-5116896.html) or above installed on your computer. 

2. Download the latest version of `COMPal.jar` [here](https://github.com/AY1920S1-CS2113T-W17-1/main/releases).

3. Copy the `COMPal.jar` file to the folder that you want to use as the home folder for **COMPal**.

4. Double-click the `COMPal.jar` file to start the application. The **COMPal** GUI should appear in a few seconds.
    <img src="images/UIInit.png" alt="Initialisation before entering name" width="800"/>

5. As this is your first time starting **COMPal**, **COMPal** will ask for your name, like any new friend. You can type your name in the **command box** and press the `Enter` key.
    <img src="images/UIInitNameConfirmation.png" alt="Initialisation after entering name" width="800"/>
    
6. **COMPal** will then ask if you have entered your name correctly. If you have, then type `Yes` in the **command box** and press the `Enter` key. If not, please type `No` and press the `Enter` key, and **COMPal** will repeat **Step 5**.
    <img src="images/UIInitNameConfirmed.png" alt="Name confirmed" width="800"/>

7. And that's it! Initialisation is complete and **COMPal** just became your newest friend / most awesome assistant. You can now try entering commands in the **command box** and press `Enter` to execute it. 

8. Some example commands you can try:
    * `clear` : **clears** all output in the **output box**. Use this if the **output box** becomes too cluttered after typing too many commands.
    * `deadline <task description> /date <date of deadline>` : **adds** a task with a **deadline** 
    * `event <task description> /date <date of event> /time <time of event>` : **adds** an **event** that occurs at a specific time on a specific date
    * `find <search term>` : **find** a certain task related to a **search term**.
    * `list` : **displays the entire list of tasks** stored in **COMPal**'s massive memory. However, if you have just downloaded **COMPal**, it's likely that **COMPal** will not display any tasks. 
    * `bye` : **bid goodbye** to **COMPal** and exit the program. See you soon!!

This is the end of the **Quick Start** guide. Please refer to [4. Features](/docs/UserGuide.md#4-features) for more details on more commands. Enjoy planning your life with **COMPal**!

## 4. Features 
### 4.1. General Commands

#### 4.1.1. Viewing help: `help`
#### 4.1.2. Listing all Tasks: `list`
#### 4.1.3. Deleting a Task: `delete`
#### 4.1.4. Completing a Task: `done`
#### 4.1.5. Deleting a Task: `delete`
#### 4.1.6. Viewing Tasks on a Specified Date: `view`
#### 4.1.7. View Reminder: `view-reminder`
#### 4.1.8. Set Reminder: `set-reminder`
#### 4.1.9. Exiting **COMPal**: `bye`

### 4.2. Detailed Management Commands

#### 4.2.1. Adding **Deadline**: `deadline`
Dread it. Run from it. But **deadlines** still arrive.   
Command Syntax: `deadline <task description> /end <date of deadline> `

#### 4.2.2. Adding **Event**: `event`
Command Syntax: `event <task description> /date <date of event> /start <start time of event> /end <end time of event>`

#### 4.2.3. Adding **Task** with **Fixed Duration**: `fixeddurationtask`
Command Syntax: `fixeddurationtask <task description> /date  < date of the task> /start <the starting time of each task> /end <ending time of each task> `

#### 4.2.4. Adding **Recurring Task**: `recurtask`
Command Syntax: `recurtask <task description> /date <starting date of the first task> /start <the starting time of each task> /end <ending time of each task> /rep <number of repetitions(integer)> /freq <number of days between each iteration of the task>`

#### 4.2.5. Adding **Lecture Session**: `lect`
Command Syntax: `recurtask <task description> /date <starting date of the first task> /start <the starting time of each task> /end <ending time of each task> /rep <number of repetitions(integer)> /freq <number of days between each iteration of the task>`

#### 4.2.6. Adding **Tutorial Session**: `tut`
Command Syntax: `recurtask <task description> /date <starting date of the first task> /start <the starting time of each task> /end <ending time of each task> /rep <number of repetitions(integer)> /freq <number of days between each iteration of the task>`

#### 4.2.7. Adding **Sectional Session**: `sect`
Command Syntax: `recurtask <task description> /date <starting date of the first task> /start <the starting time of each task> /end <ending time of each task> /rep <number of repetitions(integer)> /freq <number of days between each iteration of the task>`

#### 4.2.8. Adding **Lab Session**: `lab`
Command Syntax: `recurtask <task description> /date <starting date of the first task> /start <the starting time of each task> /end <ending time of each task> /rep <number of repetitions(integer)> /freq <number of days between each iteration of the task>`

### **5. Future Enhancements**

### **6. Frequently-Asked Questions**

### **7. Command Summary**
