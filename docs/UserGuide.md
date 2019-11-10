# User Guide

The purpose of this user guide is to give a brief outline on how to use the different
features of the application BetterDuke.


## Table of Contents

- Introduction
- Quick Start
- Features
- FAQ
- Command Summary


# 1. Introduction
    Betterduke is a desktop application for NUS students with poor time management. 
    Students, more often than not, are on their laptops, hence Betterduke
    will provide a convenient platform for students to toggle between checking their timetables and scheduling their tasks. More importantly, Betterduke is optimized for those who
    prefer to work with a Command Line Interface (CLI) while still having the benefits
    of a Graphical User Interface (GUI). If you can type fast, Betterduke can
    schedule your tasks faster than traditional GUI apps.  Enjoy!
# 2. Quick Start
    Ensure you have Java ​ 11 ​or above installed in your Computer.
    Installation guide:
       1. Download the latest jar file here
   [JAR](https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/jar-release/v1.3.1.jar)
   
       2. Copy the file to the folder you want to use as the home folder for your
          Address Book.
       3. Double-click the file to start the app. The GUI should appear in a few
          seconds.
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI1.png" width = 800 length = 450>

         4. The Command line at the bottom is where users will input their commands
        and the response box is where the system will return it’s output.
        5. Type the command in the command box and press **Submit** to execute it.
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI2.png" width = 800 length = 450>
       
        6. E.g. typing "help"  and pressing **Submit** will open the help window.
        7. Some example commands you can try:
            a.     show/workload     ​ : show workload for following week’s timetable
            b.     show/filter CS2113T     ​ : show tasks which contains keyword “CS2113T”
            c.     bye     ​: exits the app
        Refer to [Features](#Features)​ for details of each command.


# 3. Features
   ## Things to note
* Dates entered must be within NUS academic calendar (12/08/2019 to 01/12/2019)
*  Please note that any events or deadline added before or after this period will not be shown on the GUI.
* /by is used for all commands related to deadline
* /at is used for all commands related to event
* Multiple dateline can be added at the same date and time
* Multiple events cannot be added at the same date and time

    
  ##  3.1. Adding task:  add
_Adds a task to the list_
- Allow users to add in events or deadline, specifying NUS module code,task description, date and time
- Clash detection added in the add function would inform user about an event happening at the same time and same day preventing clashes in schedules
- Module code checker to ensure Module code added is valid
   
 ### Format    
* for event : ​add/e Mod_Code TASK_DESCRIPTION /at
       DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
* for deadline :​add/d Mod_Code TASK_DESCRIPTION /by
       DATE(DD/MM/YYYY or week x day) TIME
         
### Example:
 #### STEPS
1. User input command :add/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD-INPUT.png">
2. System: Shows progress indicator
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD-PROGRESS.png">
3. System: Shows response
   _Task will be added._
    <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD-RESPONSE.png">


##    3.2. Done task: done
 _Complete a task from the list_
### *Format:* 
- for event : done/e Mod_Code TASK_DESCRIPTION /at 
      DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
- for deadline :done/d Mod_Code TASK_DESCRIPTION /by 
      DATE(DD/MM/YYYY or week x day) TIME
### Example:
 #### STEPS
1. User input command : done/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE-INPUT.png">
2.System: Shows progress indicator
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE-PROGRESS.png">
3.System: Shows response
_Task will be marked done._
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE-RESPONSE.png">
## 3.3. Delete task: ​ delete
_Delete a task from the list_
### Format:    
- for event : delete/e Mod_Code TASK_DESCRIPTION /at
    DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
- for deadline :​delete/d Mod_Code TASK_DESCRIPTION /by
    DATE(DD/MM/YYYY or week x day) TIME
### Example:
#### STEPS
1. User input command :  delete/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE-INPUT.png">
2.System: Shows progress indicator
_Progress bar would be removed if no tasks are in the module._ 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE-progress.png">
3.System: Shows response
_Task will be removed._
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE-RESPONSE.png">    


  ##  3.4. Search for specific taskings using keywords:     ​     filter  
 _Find matching taskings using given keywords._    
 ### Format : filter KEYWORD    
● Search is not case sensitive    
    ● Every tasking made by the user will be searched for  
    ● Only the full words will be matched e.g. ​books​ will not match​ book    
    ● List of all task matching the keyword will be displayed for the user    
### Example:
 #### STEPS
1. User input command :show/filter tutorial
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FILTER/FILTER_INPUT.PNG">
2.System: Shows response
_Returns any task with assignment in the description_ 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FILTER/FILTER_REPONSE.PNG">

  ##  3.5. Set reminder for upcoming task: remind    
_This sets a reminder for current task. A notification will pop-up when set timing is met.
 The date and time of /by and /on if remind/rm is called has to be same as the date and time of /by and /on when remind/set was called._

### Format : 
- Set Reminder: remind/set Mod_Code TASK_DESCRIPTION /by 
      DATE(DD/MM/YYYY or week x day) TIME /to      
      DATE(DD/MM/YYYY or week x day) TIME
-Remove Reminder : remind/rm Mod_Code TASK_DESCRIPTION /by
      DATE(DD/MM/YYYY or week x day) TIME /to
      DATE(DD/MM/YYYY or week x day) TIME

### Example:
 #### STEPS
1. User input command : remind/set CS2113T Submission /by 11/11/2019 0420 /on week 13 mon 0400
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_INPUT.PNG">
2. System: Shows response
_Reminder will be added. Reminder must be after current date but before date of deadline._
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_RESPONSE.PNG">
3. System: Show reminder
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_SHOW.PNG">

   ## 3.6. View the previous inputs: show/previous
_Displays previous inputs entered based on your choice_
### Format : 
- show/previous x where x is an integer Or show/previous <command type>

### Example:
 #### STEPS
1.User input command : show/previous 3
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_PRE/SHOW_PRE_INPUT.PNG"> 
2.System: Shows response
_This command will show the 3 previous input from the current input in the response box as shown below._
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_PRE/SHOW_PRE_RESPONSE.PNG"> 

   ## 3.7.Retrieve the previous input that users want to re-enter: retrieve/previous
_Set the command line text to the chosen previous command retrieved_
### Format : 
retrieve/previous x where x is an integer

### Example:
 #### STEPS
1.For instance the show previous list is: 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_PRE/SHOW_PRE_RESPONSE.PNG"> 
2.User input command : retrieve/previous 2
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RETRIEVE/RETRIEVE_PRE_INPUT.PNG">
3.System response
The command retrieve/previous will set the text in the command line to be show/filter 1 so that edits can be made easily without having to re-type the whole command. 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RETRIEVE/RETRIEVE_PRE_OUTPUT.PNG">

   ## 3.8. Finding earliest free time based on the given schedule    
Finds 5 earliest free time block with a given duration in hours, the information found will be displayed in the ‘Response’ Box.

### Format:
- find/time x hours 
- where ‘x’ is an integer between 1 - 16
	
### Example:
 #### STEPS
1.User input command : find/time 5 hours
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND_FREE_TIME/FIND_FREE_TIME_INPUT.PNG">
2.System response :
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND_FREE_TIME/FIND_FREE_TIME_OUTPUT.PNG">


   ## 3.9. Retrieve earliest free time found based on the given schedule : retrieve/ft 
_Retrieves anything 1 of the 5 earliest free time block offered and populates into the command line_

### Format:
- retrieve/time x
where ‘x’ is option selected in integer between 1 - 5

*Can only be entered after a find/time command

### Example:
 #### STEPS
 

1.For instance the find/time 5 hours is:  
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND_FREE_TIME/FIND_FREE_TIME_OUTPUT.PNG">

2.User input command : retrieve/time 1
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND_FREE_TIME/RETRIEVE_PRE_TIME%20_INPUT.PNG">

3.System response :
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND_FREE_TIME/RETRIEVE_PRE_TIME%20_OUTPUT.PNG">
     

   ## 3.10. Select the week :  week
_Generates all assignment entries in the week selected_
### Format:
- show/week x
where ‘x’ is an integer between 1 - 13 or ‘x’ is non-integer either 'recess', 'reading', or 'exam'.

### Example:
 #### STEPS
 1. User inputs command: show/week 1
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_WEEK/SHOW_WEEK_INPUT.PNG">
2. System response 
_The Week and calendar display will be switched to week 1._
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_WEEK/SHOW_WEEK_OUTPUT.PNG">


   ## 3.11. Viewing help : help 
_Shows list of commands and correct format_
### Format:
- help
### Example:
 #### STEPS
 1. User inputs command: show/week 1
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/HELP/HELP_INPUT.PNG">
2. System response: 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/HELP/HELP_OUTPUT.PNG">

   ## 3.12. Show recommended workload 
_ This will show a recommended workload schedule for the immediate coming week to the user._

### Format:
- show/workload 

### Example:
 #### STEPS
 1. User inputs command:
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_WORKLOAD/SHOW_WORKLOAD_INPUT.PNG">
2. System response:
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW_WORKLOAD/SHOW_WORKLOAD_OUTPUT.PNG">

   ## 3.13. Add or remove recurring activities 
_This will help user add or remove event that is recurring over a period of time._
### Format: 
- **Add recurring task weekly**:  recur/weekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
- **Remove a weekly recurring task that was set**: recur/rmweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
-  **Add recurring bi-weekly task**: recur/biweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
- **Remove a bi-weekly recurring task that was set**: recur/rmbiweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to
 End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME

### Example:
 #### STEPS
 1. User inputs command: recur/weekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_INPUT.PNG">
2. System response
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_RESPONSE.PNG">
        

   ## 3.14. Exiting the program : bye    
_Exits the program._   
#### Format:
- bye

 ## 3.15. Saving the data    
_All taskings are saved automatically when user wants to modify their schedule. No manual saving is required._
    
 ##   3.16. Listing all tasks: ​ list (Coming in v2.0)
_Shows the list of all tasks in the schedule._
#### Format :list MODULE_CODE
##### Example :
- list CS2100
    Displays the list of tasks in todo

 ## 3.17. Strike off tasks and track the progress of the week (Coming in v2.0)    
_To help users ensure they are on schedule._

 ## 3.18. Mark task as important : markAs (Coming in v2.0)

_Add a keyword “Important !” beside the task as the specific INDEX_
### Format: 
- markAs INDEX

#### Example:
- markAs 2
      The 2nd task in the list will have a label (Important !) i.e. read book (Important !)


## 3.19. Track the task completion rate (Coming in v2.0)    
_To allow users to track their productivity._

## 3.20. Customise light and dark theme:  custom (Coming in v2.0)
_Changes the GUI between a light theme and dark theme._    
### Format: custom LIGHT_OR_DARK    
#### Example:        
-custom dark    
    This changes the theme of the GUI to a dark theme.

## 3.21. Save file encryption (Coming in v2.0)    
_A privacy choice to enable/disable encryption of data files._

## 3.22. Sync timetable with nusmods (Coming in v2.0)    
_Added option to sync timetable with nusmods to properly match school schedule._

## 3.23. Sync timetable with external calendars (e.g.Google calendar) (Coming in v2.0)    
_Added option to sync planned activities in external calendars to match with BetterDuke._  
    
## 3.24. Labeling task: label (Coming in v2.0)
_Tags Task with a label so all related tasks can be categorised    
### Format:
- label td/TASK_DESCRIPTION tag/TAG_NAME    


# 4.     FAQ    

        Q:Do i have to install the application?    
        A:No you can just run the [betterduke].jar file
    
        Q:If I am unsure about the commands, how do i get help?    
        A:Just enter “help” into the command line and BetterDuke will show you a comprehensive guide on how to use our app
    
        Q:How do I find specific tasks based on a keyword?    
        A: You can make use of the filter function to look for such tasks.
    
# 5.     Command Summary    
1.  Add:
- for event : add/e Mod_Code TASK_DESCRIPTION /at DATE(xx/xx/xxxx or week x day) /from TIME /to TIME
- for deadline :add/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
**e.g** 
- add/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
- add/d CS2101 assignment /by week 1 mon 1200

2. Done:
 -  event : done/e Mod_Code TASK_DESCRIPTION /at DATE  (xx/xx/xxxx or week x day) /from TIME to TIME
- for deadline :done/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
**e.g**
- done/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
- done/d CS2101 assignment /by week 1 mon 1200

3. Delete:
 - for event : delete/e Mod_Code TASK_DESCRIPTION /at DATE  (xx/xx/xxxx or week x day) /from TIME to TIME
- for deadline : delete/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
**e.g.**
- delete/e CS2101 assignment /at 12/08/2019 /from 1200 /to 1300
- delete/d CS2101 assignment /by week 1 mon 1200

4. Filter: 
- show/filter KEYWORD
**e.g.** 
- show/filter book
5. Remind: 
- Set Reminder: remind/set Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- Remove Reminder : remind/rm Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- Remind check : remind/check
**e.g.** 
- remind/set CS2100 project /by 1/11/2019 1200 /on week 11 fri 0800
- remind/rm CS2100 project /by 1/11/2019 1200 /on week 11 fri 0800

6. Show recommended workload: 
- /show workload

7. Recur:  
- Add recurring task weekly:  recur/weekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- Remove a weekly recurring task that was set: recur/rmweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- Add recurring bi-weekly task: recur/biweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- Remove a bi-weekly recurring task that was set: recur/rmbiweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME 
**e.g.**  
- recur/weekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
- recur/rmweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
- recur/biweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400
- recur/rmbiweekly CS2101 assignment /start 10/10/2019 /to 31/11/2019 /from 1200 /to 1400

8. Show previous:
- show/previous x , where ‘x’ is an integer OR show/previous <command type> 
**e.g**.
- show/previous 3 
- show/previous add/d

9. Retrieve previous:
- retrieve/previous x , where ‘x’ is an integer
**e.g.** 
- retrieve/previous 2

10. Find earliest free time:
- find ‘x’ hours , where ‘x’ is an integer between 1 to 16 
**e.g.** 
- find 5 hours

11. Retrieve earliest free time:
- retrieve/time ‘x’ , where ‘x’ is an integer between 1 to 5 
**e.g.** 
- retrieve/time 5  

12. Select the Week:
- show/week ‘x’,where ‘x’ is an integer between 1 to 13
**e.g.** 
- show/week 5

13. Help:
- help
 
14. Exit:
 - bye
