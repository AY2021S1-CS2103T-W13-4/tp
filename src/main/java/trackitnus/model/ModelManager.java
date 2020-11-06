package trackitnus.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import trackitnus.commons.core.GuiSettings;
import trackitnus.commons.core.LogsCenter;
import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.commons.util.CollectionUtil;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.contact.Contact;
import trackitnus.model.contact.ContactHasTagPredicate;
import trackitnus.model.lesson.DayOfWeek;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.lesson.LessonHasCodePredicate;
import trackitnus.model.lesson.LessonOnWeekdayPredicate;
import trackitnus.model.module.Module;
import trackitnus.model.tag.Tag;
import trackitnus.model.task.Task;
import trackitnus.model.task.TaskAfterDatePredicate;
import trackitnus.model.task.TaskHasCodePredicate;
import trackitnus.model.task.TaskIsOverduePredicate;
import trackitnus.model.task.TaskOnDatePredicate;

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
        CollectionUtil.requireAllNonNull(trackIter, userPrefs);

        logger.fine("Initializing with TrackIter: " + trackIter + " and user prefs " + userPrefs);

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
        trackIter.resetData(trackIt);
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
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        CollectionUtil.requireAllNonNull(target, editedContact);

        trackIter.setContact(target, editedContact);
    }

    //=========== Filtered Contact List Accessors =============================================================

    @Override
    public ObservableList<Contact> getAllContacts() {
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return getFilteredContactList();
    }

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
    public boolean hasModule(Code code) {
        requireNonNull(code);
        return trackIter.hasModule(new Module(code, new Name("dummy")));
    }

    @Override
    public Optional<Module> getModule(Code code) {
        List<Module> allModules = trackIter.getModuleList();
        for (Module module : allModules) {
            if (module.getCode().equals(code)) {
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteModule(Module target) {
        trackIter.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        trackIter.addModule(module);
    }

    @Override
    public void setModule(Module target, Module editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);

        trackIter.setModule(target, editedTask);
    }

    //=========== Filtered Module List Accessors =============================================================

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
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
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        CollectionUtil.requireAllNonNull(target, editedTask);

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

    @Override
    public Index getTaskIndex(Task task) throws CommandException {
        ObservableList<Task> taskList = getFilteredTaskList();
        int index = taskList.indexOf(task);
        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_TASK_DOES_NOT_EXIST);
        }
        return Index.fromZeroBased(index);
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
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        CollectionUtil.requireAllNonNull(target, editedLesson);

        trackIter.setLesson(target, editedLesson);
    }

    private void sortLesson() {
        trackIter.sortLesson();
    }

    @Override
    public void clearAllList() {
        updateFilteredContactList(PREDICATE_SHOW_NO_CONTACTS);
        updateFilteredLessonList(PREDICATE_SHOW_NO_LESSONS);
        updateFilteredTaskList(PREDICATE_SHOW_NO_TASKS);
    }

    //=========== Filtered Lesson List Accessors =============================================================

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        return filteredLessons;
    }

    //--------------------------------START of V1.3's new functions--------------------------------

    @Override
    public ObservableList<Lesson> getDayUpcomingLessons(LocalDate date) {
        sortLesson();
        updateFilteredLessonList(PREDICATE_SHOW_ALL_LESSONS);
        DayOfWeek weekday = DayOfWeek.getLessonWeekDay(date);
        return getFilteredLessonList().filtered(new LessonOnWeekdayPredicate(weekday));
    }

    @Override
    public ObservableList<Lesson> getModuleLessons(Code code) {
        updateFilteredLessonList(new LessonHasCodePredicate(code));
        return getFilteredLessonList();
    }

    @Override
    public Index getLessonIndex(Lesson lesson) throws CommandException {
        ObservableList<Lesson> lessonList = getFilteredLessonList();
        int index = lessonList.indexOf(lesson);
        if (index == -1) {
            throw new CommandException(Messages.MESSAGE_LESSON_DOES_NOT_EXIST);
        }
        return Index.fromZeroBased(index);
    }

    @Override
    public Index getModuleIndex(Code code) throws CommandException {
        ObservableList<Module> moduleList = getFilteredModuleList();
        Module module = getModule(code).orElseThrow(() -> new CommandException(Messages.MESSAGE_MODULE_DOES_NOT_EXIST));
        int index = moduleList.indexOf(module);
        return Index.fromZeroBased(index);
    }

    @Override
    public ObservableList<Contact> getModuleContacts(Code code) {
        Tag target = new Tag(code.toString());
        updateFilteredContactList(new ContactHasTagPredicate(target));
        return getFilteredContactList();
    }

    @Override
    public ObservableList<Task> getModuleTasks(Code code) {
        updateFilteredTaskList(new TaskHasCodePredicate(code));
        return getFilteredTaskList();
    }

    @Override
    public ObservableList<Task> getOverdueTasks() {
        updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return getFilteredTaskList().filtered(new TaskIsOverduePredicate());
    }

    @Override
    public ObservableList<Task> getFutureTasks() {
        updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        LocalDate oneWeekLater = LocalDate.now().plusWeeks(1);
        return getFilteredTaskList().filtered(new TaskAfterDatePredicate(oneWeekLater));
    }

    @Override
    public ObservableList<Task> getDayUpcomingTasks(LocalDate date) {
        updateFilteredTaskList(Model.PREDICATE_SHOW_ALL_TASKS);
        return getFilteredTaskList().filtered(new TaskOnDatePredicate(date));
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
