package trackitnus.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import trackitnus.commons.core.GuiSettings;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.commons.Code;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;
    Predicate<Task> PREDICATE_SHOW_ALL_TASKS = unused -> true;
    Predicate<Lesson> PREDICATE_SHOW_ALL_LESSONS = unused -> true;
    Predicate<Contact> PREDICATE_SHOW_NO_CONTACTS = unused -> false;
    Predicate<Task> PREDICATE_SHOW_NO_TASKS = unused -> false;
    Predicate<Lesson> PREDICATE_SHOW_NO_LESSONS = unused -> false;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' TrackIter file path.
     */
    Path getTrackIterFilePath();

    /**
     * Sets the user prefs' TrackIter file path.
     */
    void setTrackIterFilePath(Path trackIterFilePath);

    /**
     * Returns the TrackIter
     */
    ReadOnlyTrackIter getTrackIter();

    /**
     * Replaces TrackIter data with the data in {@code trackIt}.
     */
    void setTrackIter(ReadOnlyTrackIter trackIt);

    //=========== Contact ================================================================================

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the TrackIter.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the TrackIter.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the TrackIter.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the TrackIter.
     * The contact identity of {@code editedContact} must not
     * be the same as another existing contact in the TrackIter.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * @return A list of all contacts
     */
    ObservableList<Contact> getAllContacts();

    /**
     * Returns an unmodifiable view of the filtered contact list
     */
    ObservableList<Contact> getFilteredContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate) throws NullPointerException;

    //=========== Module ================================================================================

    /**
     * Returns true if a module with the same identity as {@code module} exists in the TrackIter.
     */
    boolean hasModule(Module module);

    /**
     * another version of hasModule where only code need to be passed in
     */
    boolean hasModule(Code code);

    /**
     * Returns the Module with the provided code, wraps in an Optional
     */
    Optional<Module> getModule(Code code);

    /**
     * Deletes the given module.
     * The module must exist in the TrackIter.
     */
    void deleteModule(Module target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the TrackIter.
     */
    void addModule(Module module);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the TrackIter.
     * The module identity of {@code editedModule} must not
     * be the same as another existing module in the TrackIter.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Returns an unmodifiable view of the filtered module list
     */
    ObservableList<Module> getFilteredModuleList();

    //=========== Task ================================================================================

    /**
     * Returns true if a task with the same identity as {@code task} exists in the TrackIter.
     */
    boolean hasTask(Task task);

    /**
     * Deletes the given task.
     * The task must exist in the TrackIter.
     */
    void deleteTask(Task target);

    /**
     * Adds the given task.
     * {@code task} must not already exist in the TrackIter.
     */
    void addTask(Task task);

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the TrackIter.
     * The task identity of {@code editedTask} must not
     * be the same as another existing task in the TrackIter.
     */
    void setTask(Task target, Task editedTask);

    /**
     * Returns an unmodifiable view of the filtered task list
     */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Returns the Index of the task in the current last shown list.
     *
     * @param task The task to query.
     * @return an Index object representing the task object.
     * @throws CommandException If the index is invalid.
     */
    Index getTaskIndex(Task task) throws CommandException;

    //=========== Lesson ================================================================================

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the TrackIter.
     */
    boolean hasLesson(Lesson lesson);

    /**
     * Deletes the given lesson.
     * The lesson must exist in the TrackIter.
     */
    void deleteLesson(Lesson target);

    /**
     * Adds the given lesson.
     * {@code lesson} must not already exist in the TrackIter.
     */
    void addLesson(Lesson lesson);

    /**
     * Replaces the given lesson {@code target} with {@code editedLesson}.
     * {@code target} must exist in the TrackIter.
     * The lesson identity of {@code editedLesson} must not
     * be the same as another existing lesson in the TrackIter.
     */
    void setLesson(Lesson target, Lesson editedLesson);

    /**
     * Returns an unmodifiable view of the filtered lesson list
     */
    ObservableList<Lesson> getFilteredLessonList();

    //--------------------------------START of V1.3's new functions--------------------------------

    /**
     * @param date The date to query
     * @return all lessons happens on that date
     */
    ObservableList<Lesson> getDayUpcomingLessons(LocalDate date);

    /**
     * @param code The module code to query
     * @return the list of lesson for a specific module
     */
    ObservableList<Lesson> getModuleLessons(Code code);

    /**
     * @param lesson The lesson to get index of
     * @return The index of the lesson w.r.t the last shown Lesson list.
     */
    Index getLessonIndex(Lesson lesson) throws CommandException;

    /**
     * @param code The module code to get index of
     * @return The index of the module with the specified code in the list of all modules
     */
    Index getModuleIndex(Code code) throws CommandException;

    /**
     * @param code The module code to query
     * @return the list of contacts for a specific module
     */
    ObservableList<Contact> getModuleContacts(Code code);

    /**
     * @param code The module code to query
     * @return the list of task for a specific module
     */
    ObservableList<Task> getModuleTasks(Code code);

    /**
     * @return A list of tasks where each task's deadline is before today.
     */
    ObservableList<Task> getOverdueTasks();

    /**
     * @return A list of tasks where each task's deadline is more than 7 days away.
     */
    ObservableList<Task> getFutureTasks();

    /**
     * @param date The date to query
     * @return the list of all tasks that take place on the specified date
     */
    ObservableList<Task> getDayUpcomingTasks(LocalDate date);

    //--------------------------------END of V1.3's new functions--------------------------------

    /**
     * Updates the filter of the filtered lesson list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredLessonList(Predicate<Lesson> predicate);

    /**
     * set the predicates all all filtered list to be false
     */
    void clearAllList();

}
