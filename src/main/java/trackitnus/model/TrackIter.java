package trackitnus.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import trackitnus.model.contact.Contact;
import trackitnus.model.contact.UniqueContactList;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.lesson.LessonComparator;
import trackitnus.model.lesson.UniqueLessonList;
import trackitnus.model.module.Module;
import trackitnus.model.module.UniqueModuleList;
import trackitnus.model.task.Task;
import trackitnus.model.task.TaskComparator;
import trackitnus.model.task.UniqueTaskList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact comparison)
 */
public final class TrackIter implements ReadOnlyTrackIter {

    private final UniqueContactList contacts;
    private final UniqueModuleList modules;
    private final UniqueTaskList tasks;
    private final UniqueLessonList lessons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */ {
        contacts = new UniqueContactList();
        modules = new UniqueModuleList();
        tasks = new UniqueTaskList();
        lessons = new UniqueLessonList();
    }

    public TrackIter() {
    }

    /**
     * Creates an TrackIter using the Contacts in the {@code toBeCopied}
     */
    public TrackIter(ReadOnlyTrackIter toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Replaces the contents of the task list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks.setTasks(tasks);
    }

    /**
     * Replaces the contents of the lessons list with {@code lessons}.
     * {@code lessons} must not contain duplicate lessons.
     */
    public void setLessons(List<Lesson> lessons) {
        this.lessons.setLessons(lessons);
    }

    /**
     * Resets the existing data of this {@code TrackIter} with {@code newData}.
     */
    public void resetData(ReadOnlyTrackIter newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
        setModules(newData.getModuleList());
        setTasks(newData.getTaskList());
        setLessons(newData.getLessonList());
    }


    //=========== Module ================================================================================

    /**
     * Returns true if a module with the same identity as {@code module} exists in the app.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the app.
     * The module must not already exist in the app.
     */
    public void addModule(Module p) {
        modules.add(p);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the app.
     * The module identity of {@code editedModule} must not
     * be the same as another existing module in the app.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code TrackIter}.
     * {@code key} must exist in the app.
     */
    public void removeModule(Module key) {
        modules.remove(key);
    }

    //=========== Contact ================================================================================

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the app.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Adds a module to the app.
     * The module must not already exist in the app.
     */
    public void addContact(Contact p) {
        contacts.add(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the app.
     * The contact identity of {@code editedContact} must not
     * be the same as another existing contact in the app.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code TrackIter}.
     * {@code key} must exist in the app.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }


    //=========== Task ================================================================================

    /**
     * Returns true if a task with the same identity as {@code task} exists in the app.
     */
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }

    /**
     * Adds a task to the app.
     * The task must not already exist in the app.
     */
    public void addTask(Task p) {
        tasks.add(p);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedTask}.
     * {@code target} must exist in the app.
     * The task identity of {@code editedTask} must not
     * be the same as another existing task in the app.
     */
    public void setTask(Task target, Task editedTask) {
        requireNonNull(editedTask);

        tasks.setTask(target, editedTask);
    }

    /**
     * Removes {@code key} from this {@code TrackIter}.
     * {@code key} must exist in the app.
     */
    public void removeTask(Task key) {
        tasks.remove(key);
    }

    //=========== Lesson ================================================================================

    /**
     * Returns true if a lesson with the same identity as {@code lesson} exists in the app.
     */
    public boolean hasLesson(Lesson lesson) {
        requireNonNull(lesson);
        return lessons.contains(lesson);
    }

    /**
     * Adds a lesson to the app.
     * The lesson must not already exist in the app.
     */
    public void addLesson(Lesson p) {
        lessons.add(p);
    }

    /**
     * Replaces the given lesson {@code target} in the list with {@code editedLesson}.
     * {@code target} must exist in the app.
     * The lesson identity of {@code editedLesson} must not
     * be the same as another existing lesson in the app.
     */
    public void setLesson(Lesson target, Lesson editedLesson) {
        requireNonNull(editedLesson);

        lessons.setLesson(target, editedLesson);
    }

    /**
     * Removes {@code key} from this {@code TrackIter}.
     * {@code key} must exist in the app.
     */
    public void removeLesson(Lesson key) {
        lessons.remove(key);
    }

    //// util methods

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Task> getTaskList() {
        tasks.sort(new TaskComparator());
        return tasks.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Lesson> getLessonList() {
        lessons.sort(new LessonComparator());
        return lessons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof TrackIter)) {
            return false;
        }
        // Two TrackIter will be considered equal if they have the SAME SET of contacts, modules, tasks & lessons
        // Since we sometimes sort tasks & lessons, before comparing equality it's necessary to sort as well
        TrackIter casted = (TrackIter) other;
        sortAll();
        casted.sortAll();
        return contacts.equals(casted.contacts)
            && modules.equals(casted.modules)
            && tasks.equals(casted.tasks)
            && lessons.equals(casted.lessons);
    }

    private void sortAll() {
        tasks.sort(new TaskComparator());
        lessons.sort(new LessonComparator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(contacts, modules, tasks, lessons);
    }

    public void sortLesson() {
        lessons.sort(new LessonComparator());
    }

    @Override
    public String toString() {
        return "TrackIter{" +
            "contacts=" + contacts +
            ", modules=" + modules +
            ", tasks=" + tasks +
            ", lessons=" + lessons +
            '}';
    }
}
