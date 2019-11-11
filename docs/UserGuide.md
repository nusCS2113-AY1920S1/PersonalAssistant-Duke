# User Guide for Duke++

<!--
We can rename him later.

Editing is currently owners only (since the link is public on github); if you want to edit it then create an account with some email and post that email on Whatsapp, and I'll change it.
-->

## 1. Table of Contents

<!--
[TOC] is not supported on github; create the final version manually with proper numbering. 
-->

[TOC]

<!--
## Editor Guide 

### Why This

I'm using this platform because not everyone is familiar with markdown, and Google Docs doesn't support `code` and code blocks:

```java
private static final const CODE_BLOCK_EXAMPLE = "like this";
// Although we probably won't use code blocks very much, 
// other than to simulate command line inputs
```

The alternatives are:
* Write in Google Docs without markdown formatting (so we would format it later, which is like an extra step where we would need to discuss how we wanted it formatted).
* Write it in some other format, which means we would need to decide how to highlight the parts of our user guide like `inputs` and `outputs`, and also means that we can't prominently display our user guide on github.
* Write it in our own editors and use github to merge conflicts.

So I think this is the best middle ground, where everyone gets to learn how to (kind of) write markdown, which is pretty much what all github "documents" are written in. 

### Editing

Here's a [cheatsheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet) with the markdown syntax that github supports. **Note that markdown syntax can vary from site to site**.

The buttons at the top of the editor are quite comprehensive and provide syntax that all work on github, so you can use them.

For newlines, use `space` `space` `\n` for a new line, and `\n` `\n` for a new paragraph. Most of the time you want a new paragraph. 

Please keep the heading levels consistent when editing (and don't use a single `#` ever, that's the document header, the title).

### Things to Discuss
-->

## 2. Introduction
Welcome to Duke++, a free, simple and fast budgeting program, designed specifically with NUS students in mind. Duke++ can keep track of your past expenses, and display them in a user friendly and clean graphical user interface.
Duke++ also can recommend to you a budget based on your preferences and lifestyle. 

This user guide will help you get started on using Duke++, and will give you tips and tricks to use Duke++ as efficiently as possible using some of its features.

### 2.1 Glossary
`Expense` an amount of money spent. 

`Budget` a upper limit goal of how much you would like to spend.

`Payment` a payment the user need to pay in future.

`GUI` Graphical User Interface, the user interface that the user interacts with.

`Chat bot` a software program that attempts acts like a human to hold a conversation with.

`Recurring expenses` are expenses that repeats  monthly.

`Tentative expenses` are expenses that have not been confirmed to be spent.

`Pane` A specific GUI for a given functionality. [Expense](#Expenses-management-in-`expense`)
has its own pane (*Figure 4.1*) for tracking expenses.



## 3. Quick Start
1. Ensure you have Java 11 or above on your computer. 
2. Download the latest release from our [releases](https://github.com/AY1920S1-CS2113T-T12-2/main/releases) page.
3. Copy the file to the folder you want to use as the root folder of Duke++.
4. Double click the jar file to run it. 
5. You will be prompted by the chatbot to start interacting with it. Just follow along the instructions to start using!



## 4. Features

### 4.1 Expenses management in `expense`
<img src = "https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Screenshots/expense.png?raw=true">

*Figure 4.1 GUI of Expense pane*

A list to keep track of expenses. 

Enter commands to sort, filter and change the scope of the expenses, further elaborated [here](#4.2-Expense-commands). 


### 4.2 Budget recommendations using `plan`

<img src = "https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Screenshots/planbot.png?raw=true">

*Figure 4.2.1 GUI of PlanBot*

Budget recommendations is done by a chat bot, known as PlanBot. 

PlanBot will ask you questions dynamically based on your responses, and then make recommendations to you based on your response. 

Simply reply to PlanBot's questions by typing in the user input and follow the instructions on screen!

An example of `PlanBot` asking questions *dynamically* is: if the answer to *Do you live on campus?* is *yes*, `PlanBot` will not ask you about how you travel to school.


There are three main types of 

* For questions that asks for a choice `e.g. <yes/no>`, reply with one of the choice. e.g `yes`

* For questions that asks for a monetary amount, enter the amount in dollars and cents. `e.g. 3.50`

* For questions that prompts you with a range of numbers, enter a whole number in the range. `e.g. questions asks for <0-3>, valid answers are 1, 2 and 3`

<img src = "https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Screenshots/planbotExport.png?raw=true">

*Figure 4.2.2 PlanBot's recommendations*


After answering all of PlanBot's questions, PlanBot will generate a list of recommendations.

An example of a recommendation is: if PlanBot detects that you are better off buying concessions pass for public transport then paying each fare, it will recommend you to do so. 

Type `export` when prompted to export the recommendations.


### 4.3 Payments Reminder in `payment`

<img src = "https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Screenshots/Payment_Reminder.png?raw=true">

*Figure 4.3 GUI of Payment Reminder*

A reminder of payments to pay in future is displayed via a filterable, sortable and searchable GUI list. It helps you manage and remind money you need to pay. 

### 4.4 Budget Management in `budget`
<img : src="https://github.com/AY1920S1-CS2113T-T12-2/main/blob/master/docs/images/Screenshots/Screenshot%20of%20BudgetPane.png?raw=true">

*Figure 4.4 GUI of Budget Management*
 
Allows you to track budgets for specific categories and helps you closely monitor your expenses for these categories.

<!--
### `Keyword` - Describe action

Describe action and its outcome.

#### Basic Usage

`keyword (optional main parameter)`

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| A table   | of the      |
| additional| parameters  |
-->

## 5. Commands

### 5.1 General commands and navigation

Here is a complete list of all commands for your convenience. 

Please note that arguments with a `#` prefix in this guide are arguments that you should specify.

#### 5.1.1 (Proposed - Coming in `v2.0`) `help` - Get Help with Commands

Get help with using Duke++.

#### 5.1.2 `goto` - Navigate between different GUIs of Duke++

##### Basic Usage

```
goto #pane
```
`#pane` can take the following values below.
##### Panes 

| Pane | Description |
| --------- | ----------- |
| `expense` | Main screen with the expense list |
| `plan` | Recommendation chat bot|
| `payment` | Pending payments reminder  |
| `budget` | Budget management  |

#### 5.1.3 Autofilling of commands
Press the `tab` button on your keyboard when entering a command to automatically complete the word of a command. E.g after typing in `add`, press `tab` and Duke++ will fill it up to `addExpense`. 

Pressing `tab` again will cycle through other options if there are multiple options. E.g. in the previous example, if we press `tab` again we will get `addIncome` or `addPayment`. 

When the command name is complete, pressing `tab` will produce or complete the paramter name. E.g. after typing in `addPayment` and a space , press `tab` and Duke++ will produce `/description`; or when parameter is incomplete as `/d`, press `tab` and Duke++ will fill it up to `/description`.

Pressing `tab` again will cycle through other options if there are multiple options. E.g in the previous example, if we press `tab` again we will get `/due`. 


This feature is useful in filling in commands and getting parameters' names.

`Tips`: 

1. The autoCompleter can only make modification at the end of input. i.e. It doesn't support complement on a word not at the end.

2. To complete a word, no space should be at the end of input; To produce a parameter name, a space must be at the end of input.

#### 5.1.4 Command history

Pressing the `UP` or `DOWN` cursor/arrow keys will bring back your previous inputs. 

This feature is useful for reusing or fixing previous commands.

### 5.2 Expense Commands (To be done in `expense` pane)

#### 5.2.1 `addExpense` - Add a New Expense

Adds a new expense to the current list of expenses. The properties of the new expense can be specified. 

##### Basic Usage

```
addExpense #amount
```

`#amount` - The expense amount in dollars and cents. (E.g 2.50 for two dollars and fifty cents) 

##### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/d #description` | The name or a short description of the expense. |
| `/time #time` | The time that the expense should take place. By default, the expense takes place at the time it is added. It should be in `hh:mm dd/mm/yyyy` format.|
| `/tag #tag` | The tags that should be assigned to the expense. |
| `/isRecurring` | Denotes that the expense is a recurring expense. |
| `/isTentative` | Denotes that the expense is a tentative expense. |
| `/tag #tag` | The tag that should be assigned to the expense. |


#### 5.2.2 `deleteExpense` - Deletes expense(s).


##### Basic Usage

```
deleteExpense #item(s)
```

`#items(s)` can take the following arguments:

| Parameter | Description |
| --------- | ----------- |
|`#index`   | The displayed number of the item we wish to delete from the list
| `#range` | the range of expenses we want to delete (e.g `deleteExpense 2-4`) deletes the indexes 2, 3 ,4 |
| `all` | deletes the entire list. Use with caution!|


#### 5.2.3 `confirm` - Confirms a tentative expense


##### Basic Usage

```
confirm #index
```

`#index` - The displayed number of the item we wish to confirm from the list

<!--

#### 5.2.4 `filterExpense` - Filter Displayed Expenses

Set a filter on what expenses are displayed. Using repeated filter commands will continue to narrow down the list, until the filter is reset with `/reset`.

##### Basic Usage

```
filter
```

##### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Filter for expenses with a word or phrase in their name. |
| `/before $time` | Filter for expenses before a certain time. |
| `/after $time` | Filter for expenses after a certain time. |
| `/contact $contact` | Filter for a payee or payer of the expense. If an index, the information will be obtained from the contact list. 
| `/place $place` | Filter for a location of the expense. If an index, the information will be obtained from the place list. 
| `/tag $tags` | Filter for expenses with at least one of the tags specified. |
| `/reset` | Reset the filter. |


<!--
##### Basic Usage

```
redo #times
```

`#times` - Optional. The number of times to redo. If not included, the latest undone command will be redone. If the number exceeds the number of redoable commands, the maximum number of redoable commands will be redone. 

#### Additional Parameters

None. 
-->


<!---
#### 4.24. `search` - Search for an Expense

Search for an expense matching certain properties. 

##### Basic Usage

```
search $query
```

`$query` - A phrase that the expenses found should contain, anywhere in their properties. 

##### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $query` | Search for expenses with a word or phrase in their names. |
| `/before $time` | Search for expenses before a certain time. |
| `/after $time` | Search for expenses after a certain time. |
| `/contact $contact` | Search by the payee or payer of the expense. If an index, the information will be obtained from the contact list. 
| `/place $place` | Search by the location of the expense. If an index, the information will be obtained from the place list. 
| `/tag $tags` | Search for expenses with all tags specified. |

-->
#### 5.2.4. `sortExpense` - Sort Displayed Expenses

Change the order that expenses are displayed. 

```
sortExpense #properties
```

`#properties` - can take the values:

* `time`: sorts the list by latest at the top
* `description`: sorts the list in alphabetical order
* `amount`: sorts the list by largest at the top




#### 5.2.5 `viewExpense` - Change the View Scope of Expenses

Displays expenses within the given time scope.

##### Basic Usage

```
viewExpense #timeScope
```

`#timeScope` - The time scope of displayed expenses. It can be one of `day`, `week`, `month`, `year` and `all`.

##### Additional Parameters

 | Parameter | Description |
 | --------- | ----------- |
 |`/previous #previous`| Number of days/weeks/months/years ago.         |

Warning: Remember to switch back to `all` after `/previous` is applied, as the number of `/previous` may be forgotten and then some expenses added later may be filterd out by it.

### 5.3 Payment Reminder Commands (To be done in `payment` pane)

#### 5.3.1 `addPayment` - Add a New Payment to Pay

Adds a new payment to the payments reminder. The properties of the new payment can be specified. 

##### Basic Usage

```
addPayment #amount /description #description /due #due
```

`#amount` - The payment amount in dollars and cents. (E.g 2.50 for two dollars and fifty cents) 

`#description` - The name or a short description of the payment.

`#due` - The due date of the payments, following the format dd/mm/yyyy. (E.g. 31 Oct 2019 is represented as 31/10/2019)

##### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/priority #priority`| The priority of the payments. `#priority` can be one of `High`, `Medium` or `Low`. It is `Medium` by default if not specified.|
| `/tag #tag` | The tag assigned to the payment. It is totally customized by user. |
| `/receiver #receiver` | The receiver of the payment. |

#### 5.3.2 `changePayment` - Change an Existing Payment

Changes properties of a payment.

##### Basic Usage

```
changePayment #index
```
`#index` - The index of the target payment to be changed.

Even though changePayment command does not require any compulsory parameters , but the command without parameters makes no sense as it does not change any property of the target payment.

##### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/description #description` | The new description of the payment. |
| `/due #due` | The new due date of the payment. It should follow the format dd/mm/yyyy.|
| `/tag #tag` | The new tag assigned to the payment. |
| `/priority #priority` | The new priority level of the payment. It should be one of `High`, `Medium` and `Low`. |
| `/receiver #receiver` | The new receiver of the payment. |
| `/amount #amount` | The new amount of money of the payment. |


#### 5.3.3 `deletePayment` - Delete an Existing Payment

Deletes a payment.

##### Basic Usage

```
deletePayment #index
```
`#index` - The index of the target payment to be deleted.

#### 5.3.4 `donePayment` - Finish an Existing Payment

Completes a payment by removing it from payment list and then records it to the Expense Tracker.

##### Basic Usage 

```
donePayment #index
```

`#index` - The index of the target payment.

#### 5.3.5 `viewPayment` - View Payments within a Specified Time Scope

Filters payments with the given time scope.

##### Basic Usage

```
viewPayment #timeScope
```

`#timeScope` - The timeScope used to filter payments. It can be one of `overdue`, `week`, `month` and `all`. 

The `overdue` represents payments not finished by due. The `week` and `month` represent current week and current month respectively. The `all` simply displays all payments.

#### 5.3.6 `sortPayment` - Sort Payments.

Sorts payments with the given sorting criteria.

##### Basic Usage

```
sortPayment #sortingCriteria
```

`#sortingCriteria` - The sorting criteria used to sort the payments. It can be one of `amount`, `time` and `priority`. 

Payments with higher amount, closer due or higher priority are to be placed at the top of the list.

#### 5.3.7 `searchPayment` - Search Payments.

Searches payments matching with the given keyword.



##### Basic Usage

```
searchPayment #keyword
```

`#keyword` - The keyword for searching, where the letter case is ignored.

### 5.4 Income Commands (To be done in `budget` pane)
#### 5.4.1 **`addIncome` - Add a New Income**
Adds a new income to the current list of incomes. The source and amount of the new income has to be specified.

*Basic Usage*
```
addIncome #amount /d #description
```
`#amount` - The income amount in dollars and cents. (e.g. `1000.50` for one thousand dollars and fifty cents)

`#description`- The description or source of income

Example: `addIncome 1000.50 /d Internship at Grab`

>Keying in the command:
>
>![](https://i.imgur.com/LfrPFGi.png)
>
>After registering the command:
>
>![](https://i.imgur.com/zSr7Blr.png)



#### 5.4.2 **`deleteIncome` - Delete Income(s)**

Delete specific income(s) from the list. 

*Basic Usage*
```
deleteIncome #index
```
`#index` - The displayed number of the income we wish to delete from the list

*Alternate Arguments*
|Parameter|Description|
|---------|-----------|
| `#range` | Range of incomes we wish to delete (e.g. `deleteIncome 2-4` deletes the incomes with indexes 2,3,4 as shown)|
|`all`| Deletes every income from the list. Use with caution!|

Example: `deleteIncome 1`

>Keying in the command:
>![](https://i.imgur.com/W1hFlTZ.png)
>
>After registering the command:
>
>![](https://i.imgur.com/hB70pjJ.png)

### 5.5 Budget Commands (To be done in `budget` pane)
#### 5.5.1 **`addBudget` - Add a New Budget**
Adds a new budget.

*Basic Usage*
```
addBudget #index
```
`#index` - The desired amount to be set as the budget

*Note: Not including additional parameters changes the monthly budget only*

*Alternate Arguments*
|Parameter   |Description|
|--------------| -----------|
| `/tag #tag`  | Name of desired category to add budget to |

Example: `addBudget 100 /tag Hall`

#### 5.5.2 **`viewBudget` - Track a Budget Category**
Adds a new budget category to monitor visually.

*Basic Usage*
```
viewBudget #view /tag #category 
```
`#view` - The view pane we wish to place the specified category which ranges from 1 to 6

`#category` - The category we wish to track

Example: `viewBudget 3 /tag Hall`

> Keying in the command:
>
>![](https://i.imgur.com/REttIvV.png)
>
> After registering the command:
>
>![](https://i.imgur.com/4LPAebd.png)





## 6. Overview of Commands


### Navigation

`#` prefix denotes an argument that should be replaced a valid value.


Command | Arguments | Optional
------- | --------- | --------
`goto`| `expense`/` plan`/`payment`/`budget`| |

#### 6.1 Expense 
Command | Arguments | Optional
------- | --------- | --------
`addExpense`| `#amount`| `/d /time /tag /isTentative /tag`|
`deleteExpense`|`#index`/`#range`/`all`|  
`confirm`|`#index`|
`sortExpense`|`time`/`description`/`amount` |
`viewExpense`|`day`/`week`/`month`/`year` /`all`|`/previous`

#### 6.2 PlanBot
Simply reply to PlanBot's questions by typing in the user input and follow the instructions on screen!

After answering all of PlanBot's questions, PlanBot will generate a list of recommendations.

Type `export` when prompted to export the recommendations.



#### 6.3 Payment

Command | Arguments | Optional
------- | --------- | --------
`addPayment`| `#amount` & `/description` & `/due` | `/tag /receiver /priority`|
`changePayment`| `#index` | `/amount /description /due /tag /receiver /priority` |
`deletePayment`|`#index`|  
`donePayment`|`#index`|
`sortPayment`|`time`/`priority`/`amount` |
`viewPayment`|`overdue`/`week`/`month` /`all`||
`searchPayment` | `#keyword` ||

#### 6.4 Income

| Command     | Arguments |
| ----------- | --------- |
| `addIncome` | `#amount` |
| `deleteIncome`| `#index`| 

#### 6.6 Budget
|Command|Arguments|Optional|
| ------------ | ------| ----- |
| `addBudget`| `#amount`| `/tag`|
| `viewBudget` | `#view` `/tag`|



<!--
## Deprecated Commands

Commands that were planned to be included, but ultimately removed due to changes in implementation, low usefulness, or other reasons. 

### `add` - Add Expenses

Adds a new expense to the list of expenses. 

#### Basic Usage

```
add $name
```
`$name` - The name or title of the expense to add.

#### Additional Parameters

| Parameter | Description |
| ---- | ----------- |
| `/clone $id` | Use a copy of another expense as the base of the new expense, specified by its id. |
| `/`
|

### `all` - View All Expenses

Use `all` to show all expenses. 

#### Basic Usage

```
all
```

#### Additional Parameters

None

#### Example

```
> all contacts

Here are your contacts!

  Id  | Name      | Contact No.   | Email           |
  --- | --------- | ------------- | --------------- |
   1. | Jon Dough | 98765432      | jdough@mail.com |
   2. | John Dohe | 89876543      | None            |
```

### `delete` - Delete Items from a List

Delete one or more items from a list. 

### `done` - Complete a Payment or Loan

Complete a payment or loan so that the change is reflected in your balance. 

### `edit` - Edit Items in a List

Edit one or more items in a list.

### `export` - Export a List

Export a list into `.csv` format, so that it can be viewed and edited by external programs. 

### `find` - Find Items in a List

Find items matching a query in a list. If nothing was queried, the entire list will be shown.

#### Basic Usage

```
find expenses|contacts|places|notes
```

#### Additional Parameters

| Parameter | Description |
| ---- | ----------- |
| `/contains $phrases` | Look for items containing one or more phrases. |
| `/after $time` | Look for items that occur after a certain time. Items that do not occur at a certain time are not filtered out. |
| `/before $time` | Look for items that occur before a certain time. Items that do not occur at a certain time are not filtered out. |
| `/tag $tags` | Look for items that are tagged with one or more tags. |

#### Example

```
> find contacts /contains John

I've found 1 contact matching your query!

  Id  | Name      | Contact No.   | Email           |
  --- | --------- | ------------- | --------------- |
   1. | John Dohe | 89876543      | None            |
```

### `gui` - Open/Close a List on the Desktop

Use `gui` to open or close a window displaying one of your lists on the Desktop.

#### Basic Usage

```
gui expenses|contacts|places|notes
```

#### Additional Parameters

| Parameter | Description |
| ---- | ----------- |
| `/pie` | Only usable with expenses. Opens a pie chart to visualize your expenses. |
| `/bar` | Only usable with expenses. Opens a bar chart to visualize your expenses. |

#### Example

```
> gui expenses /bar
```

A new window will open with your expenses in bar chart form. 

### `sort` - Sort Items in a List

Sort items in a list by one or more of their properties. If no properties were provided, the list will be left unchanged. 

#### Basic Usage

```
sort expenses|contacts|places|notes $properties
```

`$properties` - The properties to sort by, in the order that they were provided.

#### Additional Parameters

| Parameter | Description |
| ---- | ----------- |
| `/reverse` | Reverse the sorting order from the default, which is first to last (alphabetically, numerically, temporally).

#### Example

```
> sort places name

I've sorted your places by name!

  Id  | Name        | Address        | Contact No.   | Email           |
  --- | ----------- | -------------- | ------------- | --------------- |
   1. | Jon's Dough | Blk A 2 St     | 61234567      | jdough@mail.com |
   2. | Mary's Lamb | Blk 23 B St    | None          | None            |
```



### 4.3. `contact` - Manage Contacts

The command references for the subcommands of `contact` are split into their own sections for clarity. The `contact` command will display help for the subcommands. 

#### Basic Usage

```
contact $subcommand
```

`$subcommand` - Optional. The subcommand to execute. If not provided, the available subcommands will be listed. 

#### Additional Parameters

None.

### 4.4. `contact add` - Add Contacts

Adds a new contact to the current list of contacts. The properties of the new contact can be specified. 

#### Basic Usage

```
contact add $name
```

`$name` - The name of the contact.

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/phone $number` | The contact's phone number. |
| `/email $address` | The contact's email address. |
| `/tag $tags` | The tags that should be assigned to the contact. |

### 4.5. `contact delete` - Delete Contacts

Deletes a contact from the list. 

#### Basic Usage

```
delete $indexes
```

`$indexes` - The index(es) of the contact(s) to delete. 

#### Additional Parameters

None. 

### 4.6. `contact edit` - Edit Contacts

Edit information about an existing contact.

#### Basic Usage

```
contact edit $index
```

`$index` - The index of the contact.

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/phone $number` | The contact's new phone number. |
| `/email $address` | The contact's new email address. |
| `/tag $tags` | The new tags that should be assigned to the contact. Tags already assigned, but specified again, will be removed. |

### 4.7. `contact filter` - Filter Displayed Contacts

Set a filter on what contacts are displayed. Using repeated filter commands will continue to narrow down the list, until the filter is reset with `/reset`.

#### Basic Usage

```
contact filter
```

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Filter for contacts with a word or phrase in their name. |
| `/phone $number` | Filter for contacts with a word or phrase in their phone number. |
| `/email $address` | Filter for contacts with a word or phrase in their email address. |
| `/tag $tags` | Filter for contacts with at least one of the tags specified. |
| `/reset` | Reset the filter. |

### 4.8. `contact search` - Search Contacts

Search for a contact matching certain properties. 

#### Basic Usage

```
contact search $query
```

`$query` - A phrase that the contacts found should contain, anywhere in their properties. 

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Search for contacts with a word or phrase in their name. |
| `/phone $number` | Search for contacts with a word or phrase in their phone number. |
| `/email $address` | Search for contacts with a word or phrase in their email address. |
| `/tag $tags` | Search for contacts with all the tags specified. |

### 4.9. `contact sort` - Sort Displayed Contacts

Change the order that contacts are displayed. 

#### Basic Usage

```
contact sort $properties
```

`$properties` - The properties to sort by, from highest to lowest priority. 

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/reverse` | Use this to reverse the sorting order. |

### 4.10. `contact view` - Change How Contacts are Displayed

Change how contacts are divided into pages. 

#### Basic Usage

`contact view all|tags`

#### Additional Parameters

None.

### 4.11. `delete` - Delete Expenses

Deletes an expense from the list. 

#### Basic Usage

```
delete $indexes
```

`$indexes` - The index(es) of the expense(s) to delete. 

#### Additional Parameters

None. 

### 4.12. `edit` - Edit Expenses

Edit the properties of one or more expenses. 

#### Basic Usage

```
edit $indexes
```

`$indexes` - The index(es) of the expense(s) to edit. 

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/cost $cost` | The new cost of the expense. |
| `/name $name` | The new name or a short description of the expense. |
| `/at $time` | The new time that the expense should take place. |
| `/every $duration` | The new interval of the expense's repetition, with the first occurence at the time specified by `/at`. |
| `/till $time` | The new time after which the expense should no longer repeat. |
| `/contact $contact` | The new payee or payer of the expense. If an index, the information will be obtained from the contact list. 
| `/place $place` | The new location of the expense. If an index, the information will be obtained from the place list. 
| `/tag $tags` | The new tags that should be assigned to the expense. Existing tags that are specified again will be removed. |

### 4.13. `filter` - Filter Displayed Expenses

Set a filter on what expenses are displayed. Using repeated filter commands will continue to narrow down the list, until the filter is reset with `/reset`.

#### Basic Usage

```
filter
```

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Filter for expenses with a word or phrase in their name. |
| `/before $time` | Filter for expenses before a certain time. |
| `/after $time` | Filter for expenses after a certain time. |
| `/contact $contact` | Filter for a payee or payer of the expense. If an index, the information will be obtained from the contact list. 
| `/place $place` | Filter for a location of the expense. If an index, the information will be obtained from the place list. 
| `/tag $tags` | Filter for expenses with at least one of the tags specified. |
| `/reset` | Reset the filter. |

### 4.14. `help` - Get Help with Commands

Get help with using Duke++.

#### Basic Usage

```
help $command
```

`$commmand` - Optional. The command to get help for. If not included, `help` will provide a list of all available commands instead. 

#### Additional Parameters

Additional parameters can be specified to view help for a specific parameter of the command that you are getting help with. 

### 4.15. `place` - Manage places

The command references for the subcommands of `place` are split into their own sections for clarity. The `place` command will display help for the subcommands. 

#### Basic Usage

```
place $subcommand
```

`$subcommand` - Optional. The subcommand to execute. If not provided, the available subcommands will be listed. 

#### Additional Parameters

None.

### 4.16. `place add` - Add places

Adds a new place to the current list of places. The properties of the new place can be specified. 

#### Basic Usage

```
place add $name
```

`$name` - The name of the place.

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/address $address` | The place's address. |
| `/tag $tags` | The tags that should be assigned to the place. |

### 4.17. `place delete` - Delete places

Deletes a place from the list. 

#### Basic Usage

```
delete $indexes
```

`$indexes` - The index(es) of the place(s) to delete. 

#### Additional Parameters

None. 

### 4.18. `place edit` - Edit places

Edit information about an existing place.

#### Basic Usage

```
place edit $index
```

`$index` - The index of the place.

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/address $address` | The place's new address. |
| `/tag $tags` | The new tags that should be assigned to the place. Tags already assigned, but specified again, will be removed. |

### 4.19. `place filter` - Filter Displayed places

Set a filter on what places are displayed. Using repeated filter commands will continue to narrow down the list, until the filter is reset with `/reset`.

#### Basic Usage

```
place filter
```

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Filter for places with a word or phrase in their name. |
| `/address $address` | Filter for places with a word or phrase in their address. |
| `/tag $tags` | Filter for places with at least one of the tags specified. |
| `/reset` | Reset the filter. |

### 4.20. `place search` - Search places

Search for a place matching certain properties. 

#### Basic Usage

```
place search $query
```

`$query` - A phrase that the places found should contain, anywhere in their properties. 

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/name $phrase` | Search for places with a word or phrase in their name. |
| `/address $address` | Search for places with a word or phrase in their address. |
| `/tag $tags` | Search for places with all the tags specified. |

### 4.21. `place sort` - Sort Displayed places

Change the order that places are displayed. 

#### Basic Usage

```
place sort $properties
```

`$properties` - The properties to sort by, from highest to lowest priority. 

#### Additional Parameters

| Parameter | Description |
| --------- | ----------- |
| `/reverse` | Use this to reverse the sorting order. |

### 4.22. `place view` - Change How places are Displayed

Change how places are divided into pages. 

#### Basic Usage

`place view all|tags`

#### Additional Parameters

None.

### 4.23. `redo` - Redo the Last Undone Command

Redo the changes that were undone by the last `undo` command. Cannot be used if there is nothing to redo, or if another change was made after the last `undo` command. 

#### Basic Usage

```
redo $times
```

`$times` - Optional. The number of times to redo. If not included, the latest undone command will be redone. If the number exceeds the number of redoable commands, the maximum number of redoable commands will be redone. 

#### Additional Parameters

None. 

-->
