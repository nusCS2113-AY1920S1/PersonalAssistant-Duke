
# COMPal - Develepor Guide

Welcome to **COMpal** Developer Guide! This Developer guide is still being work on for now!

**Appendix A**
 - User Stories
 - Use Case
 - Non-Functional Requirements


# User Stories


| As a ...|      I want to...     |  So that i can ... | Priority |
|:----------|:-------------:|:------:|:----------|
| forgetful student|  track my assignment tasks and deadlines |  better manage my schedule |
| unorganized student |   organise my contact of friends   |   |
| forgetful student |securely save my passwords in one location |   remember all of them or store them securely |
| sociable student | check on the timetables of my friends  |     sync our schedules and organise study sessions together |
| hardworking student | organise my group project’s progress |     |
|  considerate student| check on the timetable of my group mates |  organise team meetings that do not clash with our schedules   |
| time-management expert | efficiently manage my project timeline | remind my group mates of deadlines to meet |
| organised student| sort out my contacts according to my multiple group projects |    better coordinate group work |
| registered user | required to log in I |  can access the list of passwords stored in the system to retrieved stored password |
| lazy student | store & retrieve my CAP | I don’t bother to go to the website to verify |
| friendly student | share my contact with new friends i’ve met in class |    make more friends |
| hardworking student | track my progress of each module |   achieve a good CAP |
| self-professed foodie | link up with my friends for lunch breaks whenever they are free |  |
| busy student | keep everything academic related in a single app |   organise my life |
| CCA Oriented student  | store CCA related activities and notes for me |     organize my external school life |
| computing student | type for long periods of time on my keyboard  |   to listen to the therapeutic sounds of keyboard clicking |
| Student who is in charge of events | to organize my events |  not have conflicting schedule |
| programming beginner | keep track of common commands and lines of code | improve faster |
| studious student | keep notes using command line|  |
| new student | come up with a study plan |  optimize my school timetable |
|  unorganised student | prioritise my tasks better according to the deadlines and importance |   |
| struggling student | prioritise more important lessons |  I do not lag too far behind  |
| overloading student | maintain a perfect schedule |  get high CAP while overloading  |
| sociable student | use the application to keep in contact with my friends by sending email |    |
| new user | view the user guide easily  |  learn how to use the app  |
| Software Engineer Student | store my user stories  |  check them regularly   |
| student new to computing | to use more command based tools  |  to get used to it  |
| forgetful student | keep track of my school results such as for labs or midterms |tabulate my own results |
|hardworking student | optimise my timetable|graduate as early as possible|
| spendthrift student| manage my finances | |



# Use Case

System: NUSPal

**Actors: Student**
**Persons that can play this role** : undergraduate student, graduate student, a staff member doing a part-time course, exchange student

**Use case 1:** Track a task

 1. User enters a task into the command prompt
 2. NUSPal prompts user for more details about the task
    	
     - List item
     -  Task Type
     -  Importance
     - Deadline date
    - Description
     - Module Code
 3.   User enters the relevant information
    
 4.  NUSPal provides an acknowledgement that the task has been added

**Use case 2: Store Timetable**

1.  User enters command to store timetable    
2.  NUSPal prompts you for the timetable details
    - Number of mods
    - Session type(enum lab lecture tutorial sectional)
    - Day, Period and Duration
    - Priority
3.  User enters in time table manually
    
4.  NUSPal displays the timetable


**Use case 3: Store contacts**

1.  User enters command to store contacts.
    
2.  NUSPal prompts user for name and email address.
    
3.  NUSPal prompt category to store
	- Prof
	- Students
	- TA
	- Tags(Optional)

**Use case 4: Store Notes for modules**

1.  User enter command to store notes
    
2.  NUSPal prompt user for title & desc/module code
    
3.  User enters in title & desc/module code
    
4.  NUSPal prompt user for note content
    
5.  User enters content 


**Use case 5: Store Passwords Securely**  

1.  User enter commands to store password  
      
2.  NUSPal prompts for master password
    
3.  User enters master password
    
4.  User enter Description & Password
    
5.  NUSPal prompt for re-password for confirmation  


**Use case 6: Access Passwords**

1.  User enter commands to access password list
    
2.  NUSPal request master password
    
3.  User search by description

# Non-Functional Requirements

Should work on any mainstream OS as long as it has Java 9 or higher installed.

Should be able to hold up to 1000 prints without a noticeable sluggishness in performance for typical usage.

A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
