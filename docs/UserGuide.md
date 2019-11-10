# BetterDuke User Guide

_The purpose of this user guide is to give a brief outline on how to use the different
features of the application BetterDuke._


## Table of Contents

- [Introduction](#introduction)
- [Quick Start](#QuickStart)
- [Features](#Features)
- [FAQ](#FAQ)
- [Command Summary](#Command)


# 1. Introduction
    Betterduke is a desktop application for NUS students with poor time management. 
    Students, more often than not, are on their laptops, hence Betterduke
    will provide a convenient platform for students to toggle between checking their timetables
    and scheduling their tasks. More importantly, Betterduke is optimized for those who
    prefer to work with a Command Line Interface (CLI) while still having the benefits
    of a Graphical User Interface (GUI). If you can type fast, Betterduke can
    schedule your tasks faster than traditional GUI apps.  Enjoy!

# 2. QuickStart
Ensure you have Java ​ 11 ​or above installed in your Computer.
Installation guide:
1. Download the latest jar file here
   [JAR](https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/jar-release/v1.3.1.jar)
   
2. Copy the file to the folder you want to use as the home folder for your BetterDuke.
3. Double-click the file to start the app. The GUI should appear in a few seconds.  
3.1. Open Terminal  
3.2. Change the path to the directory the file is located. By using the command ‘cd’. E.g. Type ‘cd Desktop/’ this changes the path to Desktop if the folder exist.  
3.3. Next, type the command shown, ‘java -jar v1.4.jar‘. This will startup the application.  
3.4. Initially, the week view timetable for event is not populated, upon entering a command, the timetable will display the week based on the week label shown in the GUI.   
3.5 If you wish to navigate to other week please enter the command: show/week <x> where <x> is an integer between 1 and 13 inclusive, or ‘recess’, ‘reading’ or ‘exam’.
       
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI1.png" width = 800 length = 450>

4. The Command line at the bottom is where users will input their commands and the response box is where the system will return it’s output.
5. Type the command in the command box and press **Submit** to execute it.
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/Example%20UI2.png" width = 800 length = 450>
       
6. E.g. typing "show/help"  and pressing **Submit** will open the help window.
7. Some example commands you can try:  
a.     show/workload: show workload for following week’s timetable  
b.     show/filter CS2113T: show tasks which contains keyword “CS2113T”  
c.     bye: exits the app
        Refer to [Features](#Features) for details of each command.


# 3. Features
   ## DISCLAIMER : Things to note

- Dates entered must be within NUS academic calendar (12/08/2019 to 01/12/2019)
- Please note that any events or deadline added before or after this period will not be shown on the GUI.
- Please refrain from editing the text file manually. (Data loss may result from tempering of txt file). If, for any reason, text file is changed manually, do it with discretion.
- /by is used for all commands related to deadline
- /at is used for all commands related to event
- Multiple deadline can be added at the same date and time
- Multiple events cannot be added at the same date and time
- Please ensure date regional format for your computer is in United States format
- Ensure your date and time is in 12 hours format
- Our application is synchronised with your computer’s timing.
- Please ensure the date regional format for your computer is in United States 12- hour format before proceeding.
  
For Windows 10:
1. Click Start
2. Click Settings
3. Click Time and Language
4. Click Date, time & Regional formatting under Date & Time 
5. Click Change Data Format
6. Ensure your settings are as follows

<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/date_format_windows.png">
For Mac OS:  

1. Press command + space (opens spotlight search)  
2. Key in Date & Time  
3. Press enter  
4. Click unlock to set changes for the date and time. 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/date_format_MAC.png">
5. Set date and time automatically to Apple Asia time as shown in the figure below.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/date_format_MAC2.png">    
6. Next, click Open Language & Region… and do the following changes as shown in the figure below. 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/date_format_MAC3.png"> 

  ##  3.1. Adding task:  add
_Adds a task to the list_
- Allow users to add in events or deadline, specifying NUS module code,task description, date and time
- Clash detection added in the add function would inform user about an event happening at the same time and same day preventing clashes in schedules
- Module code checker to ensure Module code added is valid
   
 ### Format    
* for event : add/e Mod_Code TASK_DESCRIPTION /at DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
* for deadline : add/d Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME
         
### Example: Adding an event
 #### STEPS
1. User input command :add/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_E_INPUT.png">
2. System: Shows progress indicator
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_E_PI.png">
3. System: Shows response: Event will be added.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_E_RESPONSE.png">
4. System: Update Calendar: Event will be populated.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_E_CALENDAR.png">

### Example: Adding a deadline
 #### STEPS
1. User input command :  add/d CS2101 assignment /by week 13 sat 1200
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_D_INPUT.png">
2. System: Shows progress indicator
  <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_D_PI.png">
3. System: Shows response: Event will be added.
    <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_D_RESPONSE.png">
4. System: Update Calendar: Event will be populated.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/ADD/ADD_D_LIST.png">




##    3.2. Done task: done
 _Complete a task from the list_
### *Format:* 
- for event : done/e Mod_Code TASK_DESCRIPTION /at DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
- for deadline :done/d Mod_Code TASK_DESCRIPTION /by DATE(DD/MM/YYYY or week x day) TIME
### Example: Set an event as done
 #### STEPS
1. User input command : done/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_E_INPUT.png">
2.System: Shows progress indicator
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_E_PI.png">
3.System: Shows response: Task will be marked done.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_E_RESPONSE.png">
4.System: Update Calendar: Task will be striked out.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_E_CALENDAR.png">

### Example: Set an deadline as done
 #### STEPS
1. User input command :  done/d CS2101 assignment /by week 13 sat 1200
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_D_INPUT.png">
2.System: Shows progress indicator
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_D_PI.png">
3.System: Shows response :Task will be marked done.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DONE/DONE_D_RESPONSE.png">


## 3.3. Delete task: ​ delete
_Delete a task from the list_
### Format:    
- for event : delete/e Mod_Code TASK_DESCRIPTION /at DATE(DD/MM/YYYY or week x day) /from TIME /to TIME
- for deadline :​delete/d Mod_Code TASK_DESCRIPTION /byvDATE(DD/MM/YYYY or week x day) TIME
### Example: Deleting an event
#### STEPS
1. User input command :  delete/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_E_INPUT.png">
2. System: Shows progress indicator  
Progress bar would be removed if no tasks are in the module.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_E_PI.png">
3. System: Shows response : Task will be removed.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_E_RESPONSE.png">   
4. System: Update Calendar : Task is removed.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_E_CALENDAR.png">

### Example: Deleting a deadline
#### STEPS
1. User input command :  delete/d CS2101 assignment /by week 13 sat 1200
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_D_INPUT.png">
2. System: Shows progress indicator  
Progress bar would be removed if no tasks are in the module.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_D_PI.png">
3. System: Shows response  : Task will be removed.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/DELETE/DELETE_D_RESPONSE.png">


  ##  3.4. Search for specific taskings using keywords: show/filter  
 _Find matching taskings using given keywords._    
 ### Format :show/filter KEYWORD    
● Search is not case sensitive    
● Every tasking made by the user will be searched for  
● Only the full words will be matched e.g. ​books​ will not match​ book    
● List of all task matching the keyword will be displayed for the user    
### Example: Filtering the deadlines and events by keyword
 #### STEPS
1. User input command :show/filter tutorial
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FILTER/FILTER_INPUT.png">
2.System: Shows response  
Returns any task with tutorial in the description
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FILTER/FILTER_RESPONSE.png">

  ##  3.5. Set reminder for upcoming task: remind    
_DISCLAIMER: ONLY works for deadline task. It would NOT work for an event task. Reason being academic events such as lectures and tutorials are already fixed at a time period, which warrants the unavailability of reminder for events.
Setting a reminder:  Reminder can only be set for existing deadline tasks. After a reminder is set, a notification will pop-up when a set timing is met.
Removing a reminder: Only an existing reminder can be removed. When a reminder is removed, there is a message that will indicate that the existing reminder is removed.
Check reminder: To check if there are already reminders set to facilitate removal or checking of reminders._

### Format : 
- Set Reminder: remind/set Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- Remove Reminder : remind/rm Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- Check reminders: remind/check

### Example: Setting a reminder
 #### STEPS
 Assuming there is a deadline set with command : add/d CS2101 assignment /by week 13 sat 1200
 
1. User input command : remind/set CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_SET_INPUT.png">
2. System: Shows response  
Reminder will be added. Reminder must be after current date but before date of deadline.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_SET_RESPONSE.png">
3. System: Show reminder notification  
There will be a blank window that will open. Clicking on the reminder notification will close it, or it can manually be closed.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_SET_NOTIF.png">

### Example: Removing a reminder
 #### STEPS
_Assuming that there is a reminder that was set using the above command with existing deadline using remind/set CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200_
 
1. User input command : remind/rm CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_RM_INPUT.png">
2. System: Shows response   
Reminder will be removed. Reminder’s date must be after the current date but before date of deadline.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_RM_RESPONSE.png">

### Example: Checking a reminder
 #### STEPS
_Assuming that there is a reminder set using remind/set CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200_
1. User input command : remind/check
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_CHECK_INPUT.png">
2. System: Shows response  
A message will show what reminders have been set.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/REMIND/REMIND_CHECK_RESPONSE.png">

 ## 3.6. View the previous inputs: show/previous
_Displays previous inputs entered based on your choice_
### Format : 
- show/previous <x> where x is an integer Or show/previous <command type>
- Examples of command type: add/d, add/e, delete/d,      delete/e, done/e, done/d, show/help, show/filter, find/time, retrieve/time, show/previous, retrieve/previous, remind/set, remind/rm, remind/check, recur/weekly, recur/biweekly, recur/rmweekly, recur/rmbiweekly, show/week, show/workload, bye


### Example:
 #### STEPS
1.User input command : show/previous 3
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_PRE_INPUT.pngG"> 
2.System: Shows response  
This command will show the 3 previous input from the current input in the response box as shown below.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_PRE_RESPONSE.png"> 

## 3.7.Retrieve the previous input that users want to re-enter: retrieve/previous  
_DISCLAIMER: This command, retrieve/previous will only work if show/previous command is entered. 
Set the command line text to the chosen previous command retrieved_
### Format : 
- retrieve/previous <x> where x is an integer between 1 to the size of the show previous list, inclusive.

### Example: Show previous
 #### STEPS
1.For instance the show previous list is: 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_PRE_RESPONSE.png"> 
2.User input command : retrieve/previous 2
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/RETRIEVE_INPUT.png">
3.System response
The command retrieve/previous will set the text in the command line to be show/filter 1 so that edits can be made easily without having to re-type the whole command. 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/RETRIEVE_OUTPUT.png">

## 3.8. Finding earliest free time based on the given schedule    
_DISCLAIMER: Only periods within 7am to 12am will be shown.
E.g find/time 5 hours will not show (9.30pm to 12.30am)
Instead, it will show (7.00 am to 12.00 pm) the next day.

Finds 5 earliest free time block with a given duration in hours, the information found will be displayed in the ‘Response’ Box.
Note: Only periods within 7am to 12am will be shown.  
E.g find/time 5  hours will not show (9.30pm to 12.30am)  
Instead, it will show (7.00 am to 12.00 pm) the next day_


### Format:
- find/time <x> hour or find/time <x> hours
- where x is an integer between 1 - 16, inclusive
	
### Example: Find Time
 #### STEPS
1.User input command : find/time 5 hours
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND/FIND_TIME_INPUT.png">
2.System response :
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND/FIND_TIME_RESPONSE.png">


 ## 3.9. Retrieve earliest free time found based on the given schedule : retrieve/ft   
 Retrieves anything 1 of the 5 earliest free time block offered and populates into the command line

### Format:
- retrieve/time <x>
- where x is option selected in integer between 1 - 5, inclusive 

  * Can only be entered after a find/time command

### Example:
 #### STEPS
 

1.For instance the find/time 5 hours is:  
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND/FIND_TIME_RESPONSE.png">

2.User input command : retrieve/time 1
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND/RETRIEVE_TIME_INPUT.png">

3.System response :
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/FIND/RETRIEVE_TIME_OUTPUT.png">
     

## 3.10. Select the week :  week
_Generates all assignment entries in the week selected_
### Format:
- show/week x
where ‘x’ is an integer between 1 - 13 or ‘x’ is non-integer either 'recess', 'reading', or 'exam'.

### Example:
 #### STEPS
 1. User inputs command: show/week 1
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_WEEK_INPUT.png">
2. System response   
The Week and calendar display will be switched to week 1.
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_WEEK_RESPONSE.png">
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_WEEK_RESPONSE2.png">


## 3.11. Viewing help : help 
_Shows list of commands and correct format_
### Format:
- show/help
### Example: Help
 #### STEPS
 1. User inputs command: show/week 1
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_HELP.png">
2. System response: 
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_HELP_OUT.png">

## 3.12. Show recommended workload 
_This will show a recommended workload schedule for the immediate coming week to the user._

### Format:
- show/workload 

### Example:
 #### STEPS
 1. User inputs command: show/workload
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_Workload_input.png">
2. System response:
<img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/SHOW/SHOW_Workload_RESPONSE.png">

## 3.13. Add or remove recurring activities 
_This will help user add or remove event that is recurring over a period of time._
### Format: 
- **Add recurring task weekly**:  recur/weekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
- **Remove a weekly recurring task that was set**: recur/rmweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
-  **Add recurring bi-weekly task**: recur/biweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME
- **Remove a bi-weekly recurring task that was set**: recur/rmbiweekly Mod_Code TASK_DESCRIPTION /start Start_Date(DD/MM/YYYY or week x day) /to
 End_Date(DD/MM/YYYY or week x day) /from TIME /to TIME

### Example: Adding a weekly recurring event
 #### STEPS
 1. User inputs command: recur/weekly CS2101 assignment /start week 13 sat /to exam week sat /from 1200 /to 1400
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_WEEKLY_INPUT.png">
2. System response
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_WEEKLY_RESPONSE.png">
3. System: update calendar
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR_WEEKLY_OUT2.png">       

### Example: Adding a weekly recurring event
 #### STEPS

1. User inputs command: recur/weekly CS2101 assignment /start week 13 sat /to exam week sat /from 1200 /to 1400
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_RM_WEEKLY_INPUT.png">
2. System response
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_RM_WEEKLY_RESPONSE.png">
3. System: update calendar
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/RECUR/RECUR_RM_WEEKLY_OUT.png"> 



## 3.14. Exiting the program : bye    
_Exits the program._   
#### Format:
- bye

##### Example : Exit
#### STEPS
1. User inputs command: bye
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/bye.png"> 
2.System Response:
 <img src="https://github.com/AY1920S1-CS2113T-W12-4/main/blob/master/docs/images/UG%20images/bye_response.png"> 


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
    
    Q: Can this application be run on different OS?
    A: Yes, it can be run on Mac OS and Windows OS.

    Q: Do I have to edit the data files?
    A: No, please try to not modify the data files. However, if you choose to do so, do it at your own discretion but at a risk of losing your data.
    
    Q: Do I have to follow your disclaimer listed near the start of the User Guide?
    A: Yes, to gain a full experience out of BetterDuke, please follow the disclaimer points closely.

    Q: What happens if the program behaves weirdly, crashes and cannot be opened again?
    A: Reasons of this happening is usually due to corruption of saved file. Deleting the data folder and restarting the application usually resolves the situation, however there is a risk of losing data.
    
    Q: Can i rearrange the deadline view table?
    A: Yes, you may rearrange the deadline view table by description or by date, simply click on the respective header you wish to arrange by.

    Q: Can I use this application in future semesters?
    A: Our current version only supports AY2019/2020 Semester 1 (between 12/08/2019 to 01/12/2019), do support us if you wish to see more releases.
    
# 5.     Command Summary    

1. Add:
- for event : add/e Mod_Code TASK_DESCRIPTION /at DATE(xx/xx/xxxx or week x day) /from TIME /to TIME
- for deadline : add/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
e.g 
  - add/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
  - add/d CS2101 assignment /by week 13 sat 1200

2. Done:
- for event : done/e Mod_Code TASK_DESCRIPTION /at DATE  (xx/xx/xxxx or week x day) /from TIME to TIME
- for deadline : done/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
e.g
  - done/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
   - done/d CS2101 assignment /by week 13 sat 1200

3. Delete:
 - for event : delete/e Mod_Code TASK_DESCRIPTION /at DATE  (xx/xx/xxxx or week x day) /from TIME to TIME
- for deadline : delete/d Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME
e.g.
  - delete/e CS2101 meeting /at 25/11/2019 /from 1200 /to 1300
  - delete/d CS2101 assignment /by week 13 sat 1200

4. Filter:
- show/filter KEYWORD
e.g.
  - show/filter book

5. Remind: 
- set reminder: remind/set Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- remove reminder : remind/rm Mod_Code TASK_DESCRIPTION /by DATE(xx/xx/xxxx or week x day) TIME /on DATE(xx/xx/xxxx or week x day) TIME
- check remind : remind/check
e.g. 
   - remind/set CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200
   - remind/rm CS2101 assignment /by week 13 sat 1200 /on week 13 fri 2200
   - Show recommended workload: show/workload

6. Recur:  
- add recurring task weekly:  recur/weekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- remove a weekly recurring task that was set: recur/rmweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- add recurring biweekly task: recur/biweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME
- remove a biweekly recurring task that was set: recur/rmbiweekly Mod_Code TASK_DESCRIPTION /start Start_Date(xx/xx/xxxx or week x day) /to End_Date(xx/xx/xxxx or week x day) /from TIME /to TIME 
e.g.  
    - recur/weekly CS2101 assignment /start week 13 sat /to exam	week sat /from 1200 /to 1400

    - recur/rmweekly CS2101 assignment /start week 13 sat /to exam week sat /from 1200 /to 1400
    
    - recur/biweekly CS2101 assignment /start week 7 mon /to week 10 mon /from 1200 /to 1400
    
    - recur/rmbiweekly CS2101 assignment /start week 7 mon /to week 10 mon /from 1200 /to 1400

7. Show previous:
 - show/previous <x> , where x is an integer OR show/previous <command type> 
e.g.
    - show/previous 3 OR show/previous add/d

8. Retrieve previous:
- retrieve/previous <x> , where x is an integer between 1 to 3, inclusive.
e.g.
    - retrieve/time 3  

9. Select the Week: show/week <x>,where x is an integer between 1 to 13, inclusive.
e.g. 
    - show/week 5

10. Help:
- show/help

11. Exit:
- bye

