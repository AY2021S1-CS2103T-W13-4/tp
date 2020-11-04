# TrackIt@NUS - User Guide

By: `Team W13-4` Since: `Aug 2020` License: `MIT`

## Table of Contents
1. [Introduction](#introduction)
2. [Quick Start](#quick-start)
3. [About](#about)
    1. [Common Symbols](#common-symbols)
    2. [Command Format](#command-format)
    3. [Commands and their Purposes](#command-purpose)
4. [Layout](#layout)
    1. [Upcoming Tab](#upcomingtab)
    2. [Contacts Tab](#contactstab)
    3. [Module Tabs](#moduletabs)
5. [Features](#features)
    1. [Module](#module)
    2. [Lesson](#lesson)
    3. [Task](#task)
    4. [Contact](#contact)
    5. [Help](#help)
    6. [Exit](#exit)
6. [FAQ](#faq)
7. [Glossary](#glossary)
7. [Command Summary](#command-summary)
    1. [Module Commands](#module-commands)
    2. [Lesson Commands](#lesson-commands)
    3. [Task Commands](#task-commands)
    4. [Contact Commands](#contact-commands)
    5. [General Commands](#general-commands)


--------------------------------------------------------------------------------------------------------------------

## Introduction <a name="introduction"></a>

Welcome to the **TrackIt@NUS** user-guide!

Do you want to balance schoolwork and having a social life, but dislike having to use 3 different
 apps to keep track of everything? Then look no further! TrackIt@NUS is a one-stop solution for busy students like you
  and I.

TrackIt@NUS is a desktop app for managing modules, lessons, tasks, and contacts, tailored to the needs of NUS students and
  optimized for use via a [**Command Line Interface (CLI)**](#glossary) while still having the benefits of a
   [**Graphical User Interface (GUI)**](#glossary). If you can type fast, you will be able to manage your academic
    and social commitments much more efficiently than by using other traditional GUI apps.

What are you waiting for? Head on to [Section 2, **Quick Start**](#quick-start) to learn more!

## Quick start <a name="quick-start"></a>

1. Ensure you have **Java 11** or above on your Computer.

1. Download the latest version of TrackIt@NUS from [here](https://github.com/AY2021S1-CS2103T-W13-4/tp/releases).

1. Copy the file to the folder you want to use as the home folder for TrackIt@NUS.

1. Double-click the file to start the app. The GUI shown below should appear in a few seconds.

![Ui](images/Ui.png)

You are now on the home page of TrackIt@NUS.

At the bottom of the screen, type your command in the Command Box (see [Features](#features) for more info) and
 press `Enter` on your keyboard to execute it.

## About <a name="about"></a>

There are many things you can use TrackIt@NUS for. We have structured this document so it is easy for you to find
 what you need. In the [Common Symbols](#common-symbols), [Command Format](#command-format), and [Commands and their
  Purposes](#command-purpose) sections, you will find useful tips on reading this document. It is
  then followed by the [Features](#features) section, where the main features of TrackIt@NUS are documented.

### Common Symbols <a name="common-symbols"></a>

| Symbol | Description |
| --- | ----------- |
| :information_source: | Something important to take note of |
| :bulb: | A tip is being mentioned |
| :warning: | Something to be careful of |

### Command Format <a name="command-format"></a>

| **Format** | **Meaning** | **Example** |
| ----- | -------- | -------------- |
| `lower_case/` | These are **prefixes** and are used to separate the different parameters of a command | `n/`, `d/`, `t/` |
| `UPPER_CASE` | These are **parameters** and need to be supplied to complete certain commands | `C add n/John ...` |
| `[UPPER_CASE]` |  These are **optional parameters** | `C add n/John ...` or `C add n/John t/Friend ...` both work |
| `…`​ | These are parameters that can be used **multiple times** or **none at all** | `C add n/John t/Brother t/Colleague t/Friend ...` |

:information_source: Parameters can be in any order. e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

:warning: **Except** for parameters that can be used multiple times, we will only register the last shown instance of
 all other parameters (i.e. `C add n/Jon n/Tim n/James ...` will add a contact with the name James).

### Commands and their Purposes <a name="command-purpose"></a>

| **Command** | ** Purpose** |
| -------- | --------- |
| **Add** | Add a module/lesson/task/contact to TrackIt@NUS. Relevant parameters must be present |
| **Edit** | Edit a module/lesson/task/contact. Relevant parameters must be present. All parameters that are not specified will **remain unchanged** |
| **Delete** | Delete a module/lesson/task/contact. Relevant parameters must be present |

:warning: For lessons, tasks, and contacts, the **Edit** and **Delete** commands requires user to input an `INDEX` to
 specify the lesson/task/contact they want to edit/delete. The specified `INDEX` must be **within the range** of indexes
  seen in the **current window**.

--------------------------------------------------------------------------------------------------------------------
## Layout <a name="layout"></a>

This section gives you a brief overview of the layout of TrackIt@NUS.
TrackIt@NUS is divided into three main types of tabs:
* The **Upcoming** Tab
* The **Contacts** Tab
* The various **Module** Tabs

When you switch to a tab, the tab text will be highlighted in **blue**.

### Upcoming Tab <a name="upcomingtab"></a>
This is the main tab of the application, and is the default page when the app starts. It features a calendar view of the
 days for the next week, as well as the **Tasks** and **Lessons** you have added under the respective days they fall under. 

1. The first section labelled **"Overdue"** in red text, shows the Tasks that are past their deadline, and you have yet to complete. These **Tasks** will remain there until you delete them.

2. The second section is the weekly calendar view, with your **Lessons** and **Tasks** shown under each day.

3. The last section is labelled **Future**, and shows any **Tasks** that you may have added with a deadline falling after the date 7 days from today.

![UpcomingPanelUi](images/UpcomingPanelUi.png)

### Contacts Tab <a name="contactstab"></a>
The contacts tab shows you all the **contacts** you have within the app. It will show you information for each contact such as:
* **NAME**: contact name

* **PHONE NUMBER**: contact's phone number

* **EMAIL**: contact's email

* **TAG**: any additional information you have about the contact, such as which module they are associated with (e.g. CS1010S), or their role (e.g. friend, TA)

### Module Tabs <a name="moduletabs"></a>
![ModuleTab](images/ModuleTab.png)

Here under each module's tab you can find all relevant **Lessons**, **Tasks** and **Contacts** that are related to the
 module. Very convenient right? This information will be displayed in 3 sections:
 
1. **Lessons**: Here you can find the info for all weekly lessons, including its time, locations/Zoom links so
 say goodbye to missing classes (intentional skip not included😉)

2. **Tasks**: All the tasks for the module that you haven't done, listed in **chronological** order so that you know
 what
 to prioritize! Tips: Don't leave tutorials or assignments until the last day, chances are you not gonna finish it.

3. **Contacts**: Having difficulties with your assignment? Need TA's emails to ask? Look no further, this section
 will list all contacts that are tagged with this module code! 

--------------------------------------------------------------------------------------------------------------------
## Features <a name="features"></a>

This section contains all the information you need to know about the features of TrackIt@NUS. To use each feature or
 sub-feature, you will need to enter the command into the Command Box.

## Module <a name="module"></a>
Module is the first thing you need to create when you start using the app, because each module will be the house for 
all of its lessons, tasks and related contacts. So, let's find out how to create a new module! 

### Add

Create a new house for your data (Or shall I say it is to create a new category of things to worry about?)

Format: `M add m/MODULE_CODE n/NAME`

Examples: `M add m/CS1231S n/Discrete Structures`
 
### Edit
Because modules are displayed on the sidebar, it doesn't come with index. That's why to edit and delete a module, its
 code will be used! Let's see how the syntax of a module edit command:

Format: `M edit MODULE_CODE [m/NEW_MODULE_CODE] [n/NAME]`

Examples: `M edit CS1231S m/CS1101S n/Programming Methodology`

Remark: The `MODULE_CODE` provided must be present in the Module list.

### Delete
And what if you realized you accidentally added in a wrong module, or have finished one? Just delete that module! The
 command's syntax is as follows: 

Format: `M delete MODULE_CODE`

Examples: `M delete CS2100`

:information_source: When you delete a module, the contacts associated with that module will **not** be deleted. The
 tag will also **not** be edited in any way.

:warning: When you delete a module, the app will delete all of its related tasks & lessons, so check
 twice before you delete them! 

## Lesson <a name="lesson"></a>
Lectures, tutorials, labs, lectures, lectures, recitations, lectures...
Sometimes, just looking at the timetable makes you question your life choices,
probably until the next class starts. 

While TrackIt@NUS can't do much to ease your pain, it can, at the very least,
make sure you won't miss any of those beloved (or dreaded) classes!

To view all your lessons (classes) for the next week, 
simply click on the upcoming tab as shown below.

![Ui](images/UpcomingLessons.png)

This sorts all your lessons by date.
To view the lessons of a specific module, click on any of the module tabs as shown below.

![Ui](images/ModuleLessons.png)

:information_source: As of v1.3, only weekly lessons are supported.
Biweekly and monthly lessons may be added in subsequent versions.

You can also perform commands on the lessons as explained below.

### Add
The command that makes you groan every time you have to use it. 
Use this command to add a lesson to a module.

Format: `L add m/MODULE_CODE t/TYPE d/DATE a/ADDRESS`

Examples:
* `L add m/GET1020 t/lecture d/Mon 8:00-10:00 a/E-learning`
* `L add m/CS3233 t/tutorial d/MON 17:30-20:00 a/COM1-0215`

:warning: The `TYPE` must be one of the following:
`lec`/`lecture`, `tut`/`tutorial`,
`lab`/`laboratory`, `rec`/`recitation`, or `sec`/`sectional`.

:warning: The `DATE` provided must be in the format `ddd H:mm-H:mm`;
times are in 24-hour format.

:warning: The time slots of the lessons _can_ overlap. It is a situation
that no one wants to be in, but, unfortunately, does sometimes happen.

### Edit

After adding a lesson, you might still want to change its details. 
This is the command to do so.

Format: `L edit INDEX [m/MODULE_CODE] [n/TYPE] [d/DATE] [a/ADDRESS]`

Examples:
* `L edit 1 m/CS2103T n/tutorial d/Mon 14:00-15:00`
* `L edit 4 m/CS3233 a/COM2`

:information_source: The parameters `m/MODULE_CODE`, `n/TYPE`, and `d/DATE` are optional;
however, at least one parameter must be provided (i.e. `L edit 1` is not a valid command).

:information_source: Here, `INDEX` refers to the index of the lesson you want to edit, as shown in the current tab. 
The `INDEX` provided must be one of the lesson indexes shown in the current window.

### Delete

Sometimes, you might want to delete a particular lesson, probably since you've added
it by mistake, or you hate it so much you want to forget its existence,
or simply because the module has finished (hooray!). This is the command to do so.

Format: `L delete INDEX`

Examples:
* `L delete 1`

:information_source: Here, `INDEX` refers to the index of the lesson you want to delete, as shown in the current tab.
The `INDEX` provided must be one of the lesson indexes shown in the current window.

## Task <a name="task"></a>

Managing all your tasks can be so stressful! TrackIt@NUS helps alleviate that stress with our built-in Task Manager
! To view all your tasks, simply click on the upcoming tab as shown below.

![Upcoming](images/Upcoming.png)

This sorts all your tasks by date. To view module-specific tasks, click on any of the module tabs as shown below.

![ModuleTasks](images/ModuleTasks.png)

:information_source: All module-specific tasks are shown in the upcoming tab, but not vice versa.

You can also perform commands on the tasks as explained below.

### Add

After a day of school you realise that you now have a few more tasks to complete and wish to keep track of them. This is
 the command for adding a new task to TrackIt@NUS.

Format: `T add n/NAME d/DATE [m/MODULE_CODE] [r/REMARK]`

Examples:

* `T add n/Assignment 1 m/CS2100 d/20/11/2020 r/Focus on Chapters 1-3`
* `T add n/Plan for Bob's birthday d/12/11/2020`
* `T add n/Buy mooncakes for Mum d/28/10/2020 r/Remember to get Durian-flavoured ones`

:information_source: The parameters `m/MODULE_CODE` and `r/REMARK` are optional.

:warning: The `DATE` provided must be in the format `dd/MM/yyyy`.

:bulb: To give users greater freedom when adding tasks, you **can** add tasks with deadlines in the past.

### Edit
After adding a task to TrackIt@NUS, you may want to change it, perhaps extend the deadline or change it's remark. The
 edit command is what you should use to do so.

Format: `T edit INDEX [n/NAME] [d/DATE] [m/MODULE_CODE] [r/REMARK]`

Examples:

![EditTask](images/EditTask.png)

* `T edit 2 n/Work on midterm report` changes the name of the Task
* `T edit 2 r/min 1000 words` changes the remark of the Task
* `T edit 2 m/CS2100` changes the module code of the Task

:information_source: Here, `INDEX` refers to the index of the task you want to edit, as shown in the current tab. The
 `INDEX` provided must be one of the task indexes shown in the current window.

:warning: At least 1 field must be provided for editing (i.e. `T edit 1` is not allowed).

:bulb: Write `T edit 1 m/` to remove the module code from the task. Now, the task does not belong to any module. You
 can also use `T edit 1 r/` to remove the remark from the task.
 
:bulb: To give users greater freedom when adding tasks, you **can** add tasks with deadlines in the past.

### Delete
Once you have completed a task, you can delete it. This is the command to do so.

Format: `T delete INDEX`

Examples: `T delete 2`

:information_source: Here, `INDEX` refers to the index of the task you want to delete, as shown in the current tab. The
 `INDEX` provided must one of the task indexes shown in the current window.

## Contact <a name="contact"></a>
Don't you hate it when you need help for one of your modules but you just can't remember who else is taking the module with you? 

Luckily for you, TrackIt@NUS allows you to tag your contacts with the modules you have in common, and lets you see those contacts all at once! Simply click on a module tab and you will find the list of contacts associated with that module, as you can see below.

![ModuleTabContacts](images/ModuleTabContacts.png)

To view all contacts from all modules, as well as any contacts that are not associated with any module, click on the **Contacts** tab in the side panel.

### Add
A new semester has started and you want to keep track of your new classmates. This is the command to add a new contact.

Format: `C add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]...`

Examples:

* `C add n/John Doe p/98765432 e/jon@jon.com t/CS2020 t/TA`
* `C add n/Rajesh Kumar p/98762342 e/raj@law.com t/Friend t/Colleague`

:information_source: The `p/PHONE_NUMBER` and `e/EMAIL` tabs are optional. They can either be used once or not at all.

:information_source: The `t/TAG` parameter is optional. You can use it as many times as you want (i.e. `C add n/John
 Doe p/98765432 e/jon@jon.com t/CS2020 t/Brother` will add 2 tags to the contact instead of just 1).
 
:information_source: To add a contact to a module, simply enter the module code as a tag. If the provided module code
 does not exist, the tag will just be treated as another ordinary tag. No error will be thrown. 

:information_source: You can add a contact using an email that already exists in TrackIt@NUS. It is fine for 2
 contacts to have the same email.
 
:information_source: Phone numbers can must consist only digits, and can be of any length.

### Edit
After adding a contact to TrackIt@NUS, maybe you realised you spelt their name wrong, or you want to update the contact fields. The edit command is what you should use to do so.

Format: `C edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]...`

Examples:
* `C edit 1 n/Johnny Depp` changes the name of the Contact.
* `C edit 2 n/Sam e/sam@gmail.com t/CS2100 t/TA` changes the name, email and tags of the Contact.

:information_source: Here, the `INDEX` refers to the index of the contact you want to edit. The `INDEX` provided must be
 one of the contact indexes shown in the current window.

:information_source: You can edit a contact to have an email that already exists in TrackIt@NUS. It is fine for 2
contacts to have the same email.

:information_source: Phone numbers must consist only digits, and can be of any length.

:warning: At least 1 field must be provided for editing (i.e. `C edit 1` is not allowed).

:warning: When editing tags, existing tags of contact will be removed (i.e. adding tags is not cumulative).

:bulb: Can remove all tags by typing t/ without any tags following it.

### Delete
You have broken up with your girlfriend and want to forget their existence, starting by deleting their contact in TrackIt@NUS. This is the command to do so.

Format: `C delete INDEX`

Examples: `C delete 1`

:information_source: Here, the `INDEX` refers to the index of the contact you want to delete. The `INDEX` provided
 must be one of the contact indexes shown in the current window.
## Getting help <a name="help"></a>

For: Getting help from the program.

Format: `help`

Examples: `help`

## Exiting the app <a name="exit"></a>

For: Exit the program.

Format: `exit`

Examples: `exit`

## Saving the data

TrackIt@NUS data is saved in the hard disk automatically after any command that changes the data. There is no need to
 save manually.

--------------------------------------------------------------------------------------------------------------------

## FAQ <a name="faq"></a>

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
 the data of your previous TrackIt@NUS home folder.

## Glossary <a name="glossary"></a>

**Command Line Interface**: Known in short as **CLI**, the command line interface is a **text-based** user interface to
 view and manage computer files. Click [here](https://en.wikipedia.org/wiki/Command-line_interface) for more
  information!
 
**Graphical User Interface**: Known in short as **GUI**, the graphical user interface is a system of interactive
 visual components for computer software. Click [here](https://en.wikipedia.org/wiki/Graphical_user_interface) for
  more information!

## Command Summary <a name="command-summary"></a>

### Module <a name="module-commands"></a>

| Command | Example |
| ---- | ------ |
| **Add:** `M add m/MODULE_CODE n/NAME` | `M add m/CS2100 n/Computer Organisation` |
| **Edit:** `M edit MODULE_CODE [m/NEW_MODULE_CODE] [n/NAME]` | `M edit n/Discrete Mathematics` |
| **Delete:** `M delete MODULE_CODE` | `M delete CS1101S` |

### Lesson <a name="lesson-commands"></a>

| Command | Example |
| ---- | ------ |
| **Add:** `L add m/MODULE_CODE t/TYPE d/DATE a/ADDRESS` | `L add m/CS2103T t/tutorial d/Wed 14:00-15:00 a/COM1` |
| **Edit:** `L edit INDEX [m/MODULE_CODE] [t/TYPE] [d/DATE] [a/ADDRESS]` | `L edit 1 a/LT17` |
| **Delete:** `L delete INDEX` | `L delete 1` |

### Task <a name="task-commands"></a>

| Command | Example |
| ---- | ------ |
| **Add:** `T add n/NAME d/DATE [m/MODULE_CODE] [r/REMARK]` | `T add n/Assignment 1 d/20/11/2020 r/Focus on Chapters 1-3` |
| **Edit:** `T edit INDEX [n/NAME] [d/DATE] [m/MODULE_CODE] [r/REMARK]` | `T edit 1 n/Finish Assignment` |
| **Delete:** `T delete INDEX` | `T delete 1` |

### Contact <a name="contact-commands"></a>

| Command | Example |
| ------ | ---------- |
| **Add:** `C add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]...` | `C add n/John Doe p/98765432 e/jon@jon.com t/Brother t/Friend` |
| **Edit:** `C edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]...` | `C edit 1 n/Johnny Depp p/98999899` |
| **Delete:** `C delete INDEX` | `C delete 1` |

### General <a name="general-commands"></a>

| Command | Example |
| ---- | ------ |
| **Help:** `help` | `help` |
| **Exit:** `exit` | `exit` |
