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
    5. [Storage Component](#storage)
    6. [Common Classes](#common)
4. [**Implementation**](#implementation)
    1. [Overview](#overview)
        1. [Code Design Considerations](#code-des-cons)
        2. [Feature Design Considerations](#feat-des-cons)
    2. [Module Manager](#module-manager)
    3. [Lesson Manager](#lesson-manager)
    4. [Task Manager](#task-manager)
    5. [Contact Manager](#contact-manager)
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

**TrackIt@NUS** is an all-in-one solution for busy NUS students. The information that can be managed by **TrackIt@NUS** includes:

* Modules
* Lessons (for each module)
* Tasks 
* Contacts

By combining these 4 core functions into a single app, we are able to deliver a unique user experience tailored to
 university students. In addition to the standard CRUD operations, students using TrackIt@NUS will be able to:
 
* View all upcoming tasks
* View all module-specific tasks
* View all upcoming lessons
* View all module-specific lessons
* View all contacts
* View all module-specific contacts (i.e. Professors, TAs, friends in the same module)

Any help on the development of TrackIt@NUS would be greatly appreciated, and there are several ways to do so:

* Contribute to the codebase of TrackIt@NUS by expanding the current set of features
* Write new test cases to improve coverage
* Propose and implement improvements for our existing features

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

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https
://github.com/AY2021S1-CS2103T-W13-4/tp/tree/master/docs/diagrams) folder. Refer to the [_PlantUML Tutorial_ at se
-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-W13-4/tp/blob/master/src/main/java/trackitnus/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-W13-4/tp/blob/master/src/main/java/trackitnus/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor & the logic
  interface providing APIs for the UI to retrieve necessary data.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{COMPONENT_NAME}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues
 the command `T delete 1`.

<img src="images/ArchitectureDeleteTaskSequenceDiagram.png" width="587" />

Another *Sequence Diagram* below shows how the components interact with each other for the scenario where the user
 clicks on a module tab.

<img src="images/ArchitectureModuleTabSequenceDiagram.png" width="686" />

The sections below give more details of each component.


### **UI Component** <a name="ui"></a>

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

The *Class Diagram* below shows how the components in the `Upcoming Tab` interact with each other.
![UiUpcomingTabClassDiagram](images/UiUpcomingTabClassDiagram.png)

:information_source: All the `ListPanels` and `Cards` inherit from the abstract `UiPart` class.

The UI contains 4 types of `Tabs`:
1. The `Upcoming Tab`
2. `Module Tabs`
3. The `Contacts Tab`
4. The `Help Tab`

Each of these tabs consists of one or more List Panels and its respective Card. In each List Panel, the `Graphics
` component of each of the List Cells is defined by the respective Card.
The `Contacts Tab` and `Help Tab` follow the same structure as the *Class Diagram* above.

#### **Module Tab** <a name="module-tab"></a>

![UiModuleTabClassDiagram.png](images/UiModuleTabClassDiagram.png)

The Class Diagram below shows how `Module Tab` components interact with each other.

This module tab consist of three panels (`LessonListPanel`, `TaskListPanel`, `ContactListPanel`) and their
 corresponding cards (`LessonCard`, `TaskCard`, `ContactCard`). In all of the panels, the graphics of each of the
  `ListCell` is defined by the respective Cards.
  
  The `ContactTab` and `HelpTab` both follow a similar structure as the above class diagram, except that they each
   consist of 1 single panel.
   
#### **Contact Tab** <a name="contact-tab"></a>

### **Logic Component** <a name="logic"></a>

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-W13-4/tp/blob/master/src/main/java/trackitnus/logic/Logic.java)

1. `Logic` uses the `TrackIterParser` class to parse the user command.
1. This results in a `Command` object which is executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/LogicDeleteTaskSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: The lifeline for `DeleteTaskCommandParser
` should end at the destroy marker (X) but due to a limitation in PlantUML, the lifeline continues to the end of the diagram
</div>

### **Model Component** <a name="model"></a>

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/AY2021S1-CS2103T-W13-4/tp/blob/master/src/main/java/trackitnus/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the TrackIt@NUS data.
* exposes 4 unmodifiable `ObservableList<>` objects:
    * `filteredModuleList`, which contains all the `Modules` in the TrackIt@NUS
    * `filteredLessonList`, which contains all the `Lessons` in the TrackIt@NUS
    * `filteredTaskList`, which contains all the `Tasks` in the TrackIt@NUS
    * `filteredContactList`, which contains all the `Contacts` in the TrackIt@NUS
* These lists can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change
* Does not depend on any of the other three components.

### **Storage Component** <a name="storage"></a>

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-W13-4/tp/blob/master/src/main/java/trackitnus/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the app's data in json format and read it back.

### **Common Classes** <a name="common"></a>

The `commons` package contains classes used by multiple other components in the `trackitnus.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation** <a name="implementation"></a>

This section describes some noteworthy details on how certain features are implemented.

### **Overview** <a name="overview"></a>

#### **Code Design Considerations** <a name="code-des-cons"></a>

All commands in TrackIt@NUS follow a similar execution flow.

![Command Activity Diagram](images/CommandActivityDiagram.png)

We have done this to improve maintainability. Making our code more uniform will:

* Make testing easier 
* Allow us to track down bugs faster
* Allow us to better use the Single Level of Abstraction (SLAP) principle in our code

In addition, this will make our code base much more organised, and hence make it easier for new developers to quickly
 learn and contribute.
 
Another design challenge was how to manage our predicates. TrackIt@NUS makes use of many different predicates to
 allow users to view specific tasks, lessons, and contacts. For exmaple, users can view module-specific tasks
 , contacts, and lessons. They can also view overdue tasks and future tasks (tasks where the deadline is more than a
  week away). To manage all these predicates, we had 2 options:

|  | Pros | Cons |
| ---- | ----- | ------- |
| **Option 1 (current choice):** Extract each the predicates into their own unique class | Increases code maintainability and testability. Now as a developer you exactly where to find each predicate. Makes use of the DRY principle and improves abstraction because you no longer need to interact with the actual lambda or test function, simply call the predicate. | Makes code more verbose as each predicate can simply be declared using a single lambda. |
| **Option2:** Declare each predicate using a single lambda in the ModelManager class. No predicate will have a class. | Makes code shorter and simpler to read. No need to create a class when you can simply declare a predicate with a lambda. | Need to duplicate such code when using ModelStubs for testing. This will violate the DRY principle. |

#### **Feature Design Considerations** <a name="feat-des-cons"></a>

### **Module Manager** <a name="module-manager"></a>

### **Lesson Manager** <a name="lesson-manager"></a>

### **Task Manager** <a name="task-manager"></a>

TrackIt@NUS allows users to keep track of his/her tasks. The task manager is one of TrackIt@NUS's basic components.

The common commands for the task manager include:

* `add` - Creates a new task
* `edit` - Modifies an existing task
* `delete` - Deletes an existing task

TrackIt@NUS also gives users a better understanding of their tasks by allowing users to view tasks in certain
 categories. Users can view overdue tasks, tasks on a specific day, future tasks (tasks that have deadlines more than
  a week away), and specific module tasks.
 
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

In this section, we will outline the key operations of the Task Manager, namely:
* `AddTaskCommand`
* `DeleteTaskCommand`
* `EditTaskCommand`

We will also elaborate on one more key operation that is used in the module tabs, namely `getModuleTasks
`, `getOverdueTasks`, `getDayUpcomingTasks`, `getFutureTasks`.
 
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

`getOverdueTasks`, `getDayUpcomingTasks`, and `getFutureTasks` are all implemented in very similar ways. In fact, the
 only differences are the predicates used.

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
