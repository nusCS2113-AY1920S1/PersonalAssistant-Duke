# Project Portfolio - Ho Si Shi Annette
## 1. Project Overview
This portfolio describes the contributions that I made to my school project, [WalletCLi](https://github.com/AY1920S1-CS2113T-W17-2/main).  As a team with 3 other CS2113T Software Engineering students, our group implemented a Command Line Interface application, called **WalletCLi**. 

**WalletCLi** primarily caters to NUS students and staff who prefer to use a desktop application for managing their monthly expenditure. Users can store their records of their expenses and loans with WalletCLi. In addition, **WalletCLi** provide features for users to keep track of their monthly budget and analyse their expenditure through statistics. On top of these features, users can export and import data to and from **WalletCLi**. The application is written in Java and compiled into jar files for users to run and use.
### 1.1 About this Portfolio
## 2. Summary of Contributions
This section shows a summary of contributions which I made for **WalletCLi**.
### 2.1 Code Contributed
A collation of my code files and lines of codes I written can be viewed from this link:
[Code Contribution Report](https://nuscs2113-ay1920s1.github.io/dashboard/#=undefined&search=Xdecosee)
### 2.2 Features Implemented
* Contact Management
  * _What it Does_: For each loan entry in the application, users will be required to tag them with the contacts details of the person whom they borrow or lend money to. 
  * _How it Works_: Users can manage their contact list by adding, editing or deleting contacts. Each contact will require the name of the contact. Additionally, users can optionally provide the contact's phone number or other details.
* Import Data
  * _What it Does_: For importing data, users can transfer records of their loans or monthly expenses from CSV files to **WalletCLi**. 
  * _Justification_: For users who are not comfortable with a Command Line Interface, they can still utilise our statistics feature to have a quick analysis their monthly expenditure by importing their data into **WalletCLi**.
  * _How it Works_: User will have to let **WalletCLi** know what is the name of the file which they want to import expenses or loan records from. The file has to be within the same directory as the running application. 
  * _Resource Credit_: [OpenCSV](http://opencsv.sourceforge.net/)
* Export Data
  * _What it Does_: For exporting data, users can transfer records of their loans or monthly expenses from **WalletCLi** to CSV files.
  * _Justification_: With the current implementation, aside from personal expenses, users can also utilize **WalletCLi** to track group expenses that they are in charge of. Example of group expenses can be household expenses, one-off holiday expenses or event and project budgeting. In view of this, the export feature will be useful for user when they want to share the data with other people.
  * _How it Works_: For exporting expenses, users will have to specifiy the month and year in which they want to export expense records from. For exporting loan records, users can simply key in the 'export loans' command. The records will be written into CSV files and saved into the same directory as the running application.
  * _Resource Credit_: [OpenCSV](http://opencsv.sourceforge.net/)
* Help Section
### 2.3 Other Contributions
## 3. Documentation Contribution
### 3.1 User Guide
### 3.2 Developer Guide
