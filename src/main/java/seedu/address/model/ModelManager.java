package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.module.Module;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final TrackIter trackIter;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Module> filteredModules;
    private final FilteredList<Task> filteredTasks;
    private final FilteredList<Lesson> filteredLessons;

    /**
     * Initializes a ModelManager with the given trackIter and userPrefs.
     */
    public ModelManager(ReadOnlyTrackIter trackIter, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(trackIter, userPrefs);

        logger.fine("Initializing with address book: " + trackIter + " and user prefs " + userPrefs);

        this.trackIter = new TrackIter(trackIter);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.trackIter.getContactList());
        filteredModules = new FilteredList<>(this.trackIter.getModuleList());
        filteredTasks = new FilteredList<>(this.trackIter.getTaskList());
        filteredLessons = new FilteredList<>(this.trackIter.getLessonList());
    }

    public ModelManager() {
        this(new TrackIter(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getTrackIterFilePath() {
        return userPrefs.getTrackIterFilePath();
    }

    @Override
    public void setTrackIterFilePath(Path trackIterFilePath) {
        requireNonNull(trackIterFilePath);
        userPrefs.setTrackIterFilePath(trackIterFilePath);
    }

    //=========== TrackIter ================================================================================

    @Override
    public ReadOnlyTrackIter getTrackIter() {
        return trackIter;
    }

    @Override
    public void setTrackIter(ReadOnlyTrackIter trackIt) {
        this.trackIter.resetData(trackIt);
    }

    //=========== Contact ================================================================================
    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return trackIter.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        trackIter.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        trackIter.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        trackIter.setContact(target, editedContact);
    }

    //=========== Filtered Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedTrackIter}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) throws NullPointerException {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    //=========== Module ================================================================================

    @Override
    public boolean hasModule(Module task) {
        requireNonNull(task);
        return trackIter.hasModule(task);
    }

    @Override
    public void deleteModule(Module target) {
        trackIter.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        trackIter.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedTask) {
        requireAllNonNull(target, editedTask);

        trackIter.setModule(target, editedTask);
    }

    //=========== Filtered Module List Accessors =============================================================

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) throws NullPointerException {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Task ================================================================================

    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return trackIter.hasTask(task);
    }

    @Override
    public void deleteTask(Task target) {
        trackIter.removeTask(target);
    }

    @Override
    public void addTask(Task task) {
        trackIter.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        trackIter.setTask(target, editedTask);
    }

    //=========== Filtered Task List Accessors =============================================================

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return filteredTasks;
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }


    //=========== Lesson ================================================================================

    @Override
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return trackIter.hasLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson target) {
        trackIter.removeLesson(target);
    }

    @Override
    public void addLesson(Lesson module) {
        trackIter.addLesson(module);
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireAllNonNull(target, editedLesson);

        trackIter.setLesson(target, editedLesson);
    }

    //=========== Filtered Lesson List Accessors =============================================================

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        requireNonNull(predicate);
        filteredLessons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return trackIter.equals(other.trackIter)
            && userPrefs.equals(other.userPrefs)
            && filteredContacts.equals(other.filteredContacts)
            && filteredModules.equals(other.filteredModules)
            && filteredTasks.equals(other.filteredTasks)
            && filteredLessons.equals(other.filteredLessons);
    }

}
