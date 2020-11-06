package trackitnus.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_INVALID_TYPE = "The type provided is invalid";
    public static final String MESSAGE_INVALID_TAB_VALUE = "Invalid tab values.";

    public static final String MESSAGE_LESSON_DOES_NOT_EXIST = "There is no such lesson";
    public static final String MESSAGE_MODULE_DOES_NOT_EXIST = "The module specified in the command doesn't exist.";
    public static final String MESSAGE_TASK_DOES_NOT_EXIST = "There is no such task";

    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists";
    public static final String MESSAGE_DUPLICATE_LESSON = "This lesson already exists";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_LESSON_UNCHANGED = "The edited lesson is the same as the original.";
    public static final String MESSAGE_CONTACT_UNCHANGED = "The edited contact is the same as the original.";
    public static final String MESSAGE_TASK_UNCHANGED = "The edited task is the same as the original.";
    public static final String MESSAGE_MODULE_UNCHANGED = "The edited module is the same as the original.";

    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_INVALID_TASK_DISPLAYED_INDEX = "The task index provided is invalid";
    public static final String MESSAGE_INVALID_LESSON_DISPLAYED_INDEX = "The lesson index provided is invalid";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_NOT_DIGIT_SEQUENCE = "Index is not a valid digit sequence.";
    public static final String DATE_MESSAGE_CONSTRAINTS = "Date should be in the format dd/MM/yyyy";

    public static final String MESSAGE_ADD_CONTACT_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";
    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_ADD_LESSON_SUCCESS = "New lesson added: %1$s";
    public static final String MESSAGE_DELETE_LESSON_SUCCESS = "Deleted Lesson: %1$s";
    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Lesson: %1$s";
    public static final String MESSAGE_ADD_MODULE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DELETE_MODULE_SUCCESS = "Deleted Module: %1$s";
    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Module: %1$s";
    public static final String MESSAGE_ADD_TASK_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Deleted Task: %1$s";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting the app as requested ...";
    public static final String MESSAGE_MODULE_LIMIT_REACHED = "It's not allowed to have more than 10 modules";
}
