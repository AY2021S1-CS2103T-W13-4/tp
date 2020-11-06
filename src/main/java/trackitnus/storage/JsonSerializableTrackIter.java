package trackitnus.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import trackitnus.commons.exceptions.IllegalValueException;
import trackitnus.model.ReadOnlyTrackIter;
import trackitnus.model.TrackIter;
import trackitnus.model.contact.Contact;
import trackitnus.model.lesson.Lesson;
import trackitnus.model.module.Module;
import trackitnus.model.task.Task;

/**
 * An Immutable TrackIter that is serializable to JSON format.gi
 */
@JsonRootName(value = "trackIter")
class JsonSerializableTrackIter {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contact list contains duplicate contact(s).";
    public static final String MESSAGE_DUPLICATE_MODULE = "Module list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_TASK = "Task list contains duplicate task(s).";
    public static final String MESSAGE_DUPLICATE_LESSON = "Lesson list contains duplicate lesson(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedTask> tasks = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTrackIter} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableTrackIter(@JsonProperty("contacts") List<JsonAdaptedContact> contacts,
                                     @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                     @JsonProperty("tasks") List<JsonAdaptedTask> tasks,
                                     @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        this.contacts.addAll(contacts);
        this.modules.addAll(modules);
        this.tasks.addAll(tasks);
        this.lessons.addAll(lessons);
    }

    /**
     * Converts a given {@code ReadOnlyTrackIter} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTrackIter}.
     */
    public JsonSerializableTrackIter(ReadOnlyTrackIter source) {
        contacts.addAll(source.getContactList().stream().map(JsonAdaptedContact::new).collect(Collectors.toList()));
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
        tasks.addAll(source.getTaskList().stream().map(JsonAdaptedTask::new).collect(Collectors.toList()));
        lessons.addAll(source.getLessonList().stream().map(JsonAdaptedLesson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this TrackIter into the model's {@code TrackIter} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TrackIter toModelType() throws IllegalValueException {
        TrackIter trackIter = new TrackIter();
        for (JsonAdaptedContact jsonAdaptedContact : contacts) {
            Contact contact = jsonAdaptedContact.toModelType();
            if (trackIter.hasContact(contact)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            trackIter.addContact(contact);
        }
        for (JsonAdaptedTask jsonAdaptedTask : tasks) {
            Task task = jsonAdaptedTask.toModelType();
            if (trackIter.hasTask(task)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TASK);
            }
            trackIter.addTask(task);
        }
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (trackIter.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            trackIter.addModule(module);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType();
            if (trackIter.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            trackIter.addLesson(lesson);
        }
        return trackIter;
    }

}
