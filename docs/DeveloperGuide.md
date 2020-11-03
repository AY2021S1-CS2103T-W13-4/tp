# TrackIt@NUS - Developer Guide

By: `Team W13-4` Since: `Aug 2020` License: `MIT`

## Table of Contents
1. [**Introduction**](#introduction)
2. [**Setting up**](#setup)
3. [**Design**](#design)
    1. [Architecture](#architecture)
    2. [UI Component](#ui)
        1. [Upcoming Tab](#upcoming-tab)
        2. [Module Tab](#module-tab)
        3. [Contact Tab](#contact-tab)
    3. [Logic Component](#logic)
    4. [Model Component](#model)
    5. [Common Classes](#common)
4. [**Implementation**](#implementation)
    1. [Module Manager](#module-manager)
    2. [Lesson Manager](#lesson-manager)
    3. [Task Manager](#task-manager)
    4. [Contact Manager](#contact-manager)
    6. [Logging](#logging)
    7. [Configuration](#config)
5. [**Documentation**](#documentation)
6. [**Testing**](#testing)
7. [**Dev Ops**](#devops)<br>
[**Appendix A: Product Scope**](#appen-a) <br>
[**Appendix B: User Stories**](#appen-b) <br>
[**Appendix C: Use Cases**](#appen-c) <br>
[**Appendix D: Non-Functional Requirements**](#appen-d) <br>
[**Appendix E: Glossary**](#appen-e) <br>
[**Appendix F: Instructions for Manual Testing**](#appen-f) <br>
[**Appendix G: Effort**](#appen-g) <br>
--------------------------------------------------------------------------------------------------------------------

## **Introduction** <a name="introduction"></a>

**TrackIt@NUS** is a desktop application for managing modules, lessons, tasks, and contacts, tailored to the needs of
 NUS students. It focuses on the _Command Line Interface (CLI)_ while providing users with a simple and clean
  _Graphical User Interface (GUI)_. The main iteraction with **TrackIt@NUS** will be done via commands.

**TrackIt@NUS** is an all-in-one solution for busy NUS students. The information that can be managed by **TrackIt@NUS
** includes:

* Modules
* Lessons (for each module)
* Tasks
* Contacts

The purpose of this Developer Guide is to help you understand the design and implementation of **TrackIt@NUS** so
 that you can get started on your contributions to the app.

## **Setting up, getting started** <a name="setup"></a>

Refer to the guide [here](./SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design** <a name="design"></a>

In this section, you will learn about the general design and structure TrackIt@NUS. Subsequently, this section will
 also describe and explain how each component in TrackIt@NUS works individually. TrackIt@NUS is coded using the
  [_Object Oriented Programming_](#oop) paradigm and it follows the [_Facade Pattern_](#facade-p) and [_Command
   Pattern_](#command-p) in software design.

### **Architecture** <a name="architecture"></a>

We implemented most of our methods in a similar way to ensure that the logic is uniform. This makes our code less bug
-prone, and more maintainable.

### **UI Component** <a name="ui"></a>
(Contributed by Wei Hong)

The Class Diagram below shows how the `UI` components and sections interact with one another.

![UiClassDiagram.png](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g. `SidePanel`, `StatusBarFooter`, `CommandBox
` etc. All these, including the MainWindow, inherit from the abstract UiPart class. The UI also consist of 4 main
 components: `UpcomingTab`, `ModuleTab`, `ContactTab` and `HelpTab`. Each of these components consist of
  several other classes as well. More details of this will be provided in the [sub-sections](#upcoming-tab).

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching .fxml files that
 are in the `src/main/resources/view` folder. For example, the layout of the `MainWindow` is specified in
  `MainWindow.fxml`.

The UI component,
* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

#### **Upcoming Tab** <a name="upcoming-tab"></a>
(Contributed by Tiffany)

#### **Module Tab** <a name="module-tab"></a>

(Contributed by Wei Hong)

The Class Diagram below shows how `Module Tab` components interact with each other.

This module tab consist of three panels (`LessonListPanel`, `TaskListPanel`, `ContactListPanel`) and their
 corresponding cards (`LessonCard`, `TaskCard`, `ContactCard`). In all of the panels, the graphics of each of the
  `ListCell` is defined by the respective Cards.
  
  The `ContactTab` and `HelpTab` both follow a similar structure as the above class diagram, except that they each
   consist of 1 single panel.

### **Logic Component** <a name="logic"></a>

### **Model Component** <a name="model"></a>

### **Common Classes** <a name="common"></a>

The `commons` package contains classes used by multiple other components in the `trackitnus.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation** <a name="implementation"></a>

This section describes some noteworthy details on how certain features are implemented.

### **Module Manager** <a name="module-manager"></a>

### **Lesson Manager** <a name="lesson-manager"></a>

### **Task Manager** <a name="task-manager"></a>

TrackIt@NUS allows users to keep track of his/her tasks. The task manager is one of TrackIt@NUS's basic components.
 
 #### Rationale
 
 Tasks are an integral part of any student's day-to-day life. Hence, TrackIt@NUS includes a task manager for students to 
 keep track of all their tasks. To better support NUS students, a task can either belong to a module or not. When
  adding a task, users can choose to the include the `m/MODULE_CODE` parameter in order to add a task that belongs to
   a module. When users click into a specific module tab, they can see the tasks belonging to each module.
   
![ModuleTasks](images/ModuleTasks.png)
    
:information_source: A task does not have to belong a module. In this case, the module parameter of the task is
 simply treated as null and the task can only be viewed in the upcoming tab.
     
:warning: The module must exist (i.e. there must be a module with the specified `MODULE_CODE`) otherwise the add and
 edit commands will not work.
 
:bulb: To remove a task from a module, simply type `T edit INDEX m/` (use the `m/` prefix but leave the `MODULE_CODE` parameter empty).

#### Current Implementation

In this section, we will outline the key commands of the Task Manager, namely:
* `AddTaskCommand`
* `DeleteTaskCommand`
* `EditTaskCommand`

We will also elaborate on one more key function, namely `getModuleTasks`.

Generally, this is how the Task Manager handles a command.
 
 ![Add Task](images/AddTaskActivityDiagram.png)
 
The add, delete, and edit commands are all implemented in similar ways. When they are executed they will:
 * call on the relevant Model methods
 * update the `UniqueTaskList` depending on the command
 * Save the updated task list to `data/trackIter.json`
 * return the relevant CommandResult message
 
When `AddTaskCommand` is executed, it will first call the model's `hasTask` method to ensure that the task does not
 yet exist in the app. Following this, if the task is added with a non-null module code, it will call the model's
  `hasModule` method to ensure that the specified module exists. If both these checks pass, `AddTaskCommand` will
   call the model's `addTask` method.
   
The model will then call the `addTask` method of TrackIter, which calls the `add` method of UniqueTaskList and adds
 the task to the app.

The following shows the sequence diagram of the `AddTaskCommand`.

![Add Task Sequence Diagram](images/AddTaskSequenceDiagram.png)

When the `DeleteTaskCommand` is executed, it will first call the model's `getFilteredTaskList` method to
 determine the last shown list of tasks. Then, it will call the index's `getZeroBased` method to find the
 zero-based index of the task it must delete. Then, it will check if this index is within range. If it is, it
 calls the model's `deleteTask` method.
 
The model will then call the `removeTask` method of TrackIter, which calls the `remove` method of UniqueTaskList and
 deletes the task in question from the app.

The following shows the sequence diagram of the `DeleteTaskCommand`.

![Delete Task Sequence Diagram](images/DeleteTaskSequenceDiagram.png)

When the `EditTaskCommand` is executed, it will first call the model's `getFilteredTaskList` method to determine the
 last shown list of tasks. Then, it will call the index's `getZeroBased` method to find the zero-basd index of the
  task we must edit. It will then check if the index is within range. If it is, it calls the model's `setTask` method.
  
The model will then call the `setTask` method of TrackIter, which calls the `setTask` method of UniqueTaskList and
 replace the original task with the edited version in the app.

The follow shows the sequence diagram of the `EditTaskCommand`.

![Edit Task Sequence Diagram](images/EditTaskSequenceDiagram.png)

The `getModuleTasks` function takes in a Module Code and returns all tasks that belong to the specified module.
. When `getModuleTasks` is called, it uses the `TaskHasCodePredicate` to update the task list in the app to only show
 the tasks that belong the specified module code.

This is the sequence diagram of `getModuleTasks`.

![Get Module Tasks Sequence Diagram](images/GetModuleTasksSequenceDiagram.png)

#### Design Considerations

As mentioned, a task may or may not belong to a module. In the case it does not, we store the module code as
 null. A task also may or may not have a remark. In the case it does not, we store the remark as the empty
  string. These 2 optional fields made the `EditTaskCommand` more challenging to implement. We wanted the user to be
   able to remove an existing module code or remark simply by typing `T edit INDEX m/` and `T edit INDEX r/` respectively.
     
![Edit Task Activity Diagram](images/EditTaskActivityDiagram.png)
   
The original AB3 implementation of edit commands, which would default to the original field if the edited
 field was null, would not be sufficient. Hence, we added 2 additional boolean variables - `isRemarkChanged` and
  `isCodeChanged`, to know whether users wanted to remove the existing module code or remark.

### **Contact Manager** <a name="contact-manager"></a>

### **Logging** <a name="logging"></a>

* We are using `java.util.logging` package for logging.
* The `LogsCenter` class is used to manage the logging levels and logging destinations
*  The `Logger` for a class can be obtained using `LogsCenter.getLogger(Class)` which will log messages according to the specified logging level
*  Log messages are output through the console and to a `.log` file
*  The output logging level can be controlled using the `logLevel` setting in the configuration file (see
 [Configuration](#config) for more info)
* When choosing a level for a log message, follow the conventions stated below

#### Logging Levels

* `SEVERE`: A critical problem detected which may cause the termination of the application
* `WARNING`: Can continue, but with caution
* `INFO`: Information showing the noteworthy actions by the App
* `FINE`: Details that is not usually noteworthy but may be useful in debugging e.g. print the actual list instead of just its size

### **Configuration** <a name="config"></a>

Certain properties of the application can be controlled (e.g user preferences file location, logging level) through the configuration file (default: `config.json`).

--------------------------------------------------------------------------------------------------------------------

## **Documentation** <a name="documentation"></a>

Refer to the guide [here](./Documentation.md)

## **Testing** <a name="testing"></a>

Refer to the guide [here](./Testing.md)

## **Dev Ops** <a name="devops"></a>

Refer to the guide [here](./DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix A: Product Scope** <a name="appen-a"></a>

**Target user profile**:

* take multiple modules
* want to easily track their weekly calendar
* want to be notified about important academic dates coming up
* want to have their personal todos & events integrated with their academic calendar
* want to access related contacts when they browse a module
* want to keep track of modules' details (grading scheme, module's knowledge coverage)
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions
* is reasonably comfortable using CLI apps

**Value proposition**
* able to integrate personal todos/events into the weekly academic calendar.
* able to manage modules' information (module's knowledge coverage, grading scheme) & related contacts in one app

## **Appendix B: User stories** <a name="appen-b"></a>

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I want to …​                                                   | So that I can…​                                                        |
| -------- | -------------------- | ---------------------------------------------------------------------- | ---------------------------------------------------------------------- |
| `* * *`  | student              | have all modules' important dates in one place                         | never miss any event                                                   |
| `* * *`  | student              | have information (name, description, time) about any important dates   | always know what to prepare                                            |
| `* * *`  | student              | have all the tutorial/lab details (Zoom link, time) in one place       | easily find them when I need it                                        |
| `* * *`  | forgetful student    | find the grading structure of a module                                 | better revise for each assignment / mid-term / exam                    |
| `* *`    | student              | have all the module descriptions in one place                          | read them if I need to                                                 |
| `*`      | struggling student   | access the contact information of my TA/Prof                           | ask question or schedule a consultation                                |

## **Appendix C: Use cases** <a name="appen-c"></a>

(For all use cases below, the **System** is the `TrackIt@NUS` application and the **Actor** is the `user`, unless specified otherwise)

**Use case: Find the grading structure of a module**

**MSS**

1.  User requests to list the modules
2.  TrackIt@NUS shows a list of all modules
3.  User requests to find the grading structure of a module in the list
4.  TrackIt@NUS shows the requested grading structure

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index (or module name) is invalid.

    * 3a1. TrackIt@NUS shows an error message.

      Use case resumes at step 2.

## **Appendix D: Non-Functional Requirements** <a name="appen-d"></a>

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 1000 tasks/contacts/classes without a noticeable sluggishness in performance for
 typical usage.
3.  Users of the app find the app intuitive and easy to use
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should
 be able to accomplish most of the tasks faster using commands than using the mouse.
5. The UI of the app is attractive to the users
*{More to be added}*

## **Appendix E: Glossary** <a name="appen-e"></a>

* **Mainstream OS**: Windows, Linux, Unix, OS-X

* **Obect Oriented Programming** <a name="oop"></a>: A type of computer programming paradigm (software design) in which
 programmers define the data type of a data structure, and the types of operations (functions) that can be applied 
 to the data structure

* **Facade Pattern** <a name="facade-p"></a>: A structural design pattern that provides a simplified 
(but limited) interface to a complex system of classes, library or framework.
 While decreasing the overall complexity of the application, it also helps to move unwanted dependencies to one place

* **Command Pattern** <a name="command-p"></a>: A Design Pattern that lets you encapsulate actions within Java
 classes. Of which, each class has an `execute()` method which is declared in the Command interface the class implements

## **Appendix F: Instructions for Manual Testing** <a name="appen-f"></a>

## **Appendix G: Effort** <a name="appen-g"></a>
