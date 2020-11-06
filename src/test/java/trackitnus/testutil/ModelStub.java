package trackitnus.testutil;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import trackitnus.commons.core.GuiSettings;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.ReadOnlyTrackIter;
import trackitnus.model.ReadOnlyUserPrefs;
import trackitnus.model.commons.Code;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getTrackIterFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTrackIterFilePath(Path trackIterFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addContact(Contact contact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyTrackIter getTrackIter() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTrackIter(ReadOnlyTrackIter trackIt) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasContact(Contact contact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteContact(Contact target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Contact> getAllContacts() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Module task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasModule(Code code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Optional<Module> getModule(Code code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteModule(Module target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addModule(Module module) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setModule(Module target, Module editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        throw new AssertionError("This method should not be called.");
    }

    private void updateFilteredModuleList(Predicate<Module> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteTask(Task target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addTask(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTask(Task target, Task editedTask) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteLesson(Lesson target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addLesson(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setLesson(Lesson target, Lesson editedLesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getFilteredLessonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getDayUpcomingLessons(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Lesson> getModuleLessons(Code code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Contact> getModuleContacts(Code code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getModuleTasks(Code code) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getOverdueTasks() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getFutureTasks() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Task> getDayUpcomingTasks(LocalDate date) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredLessonList(Predicate<Lesson> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void clearAllList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Index getTaskIndex(Task task) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Index getLessonIndex(Lesson lesson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Index getModuleIndex(Code code) throws CommandException {
        throw new AssertionError("This method should not be called.");
    }

}
