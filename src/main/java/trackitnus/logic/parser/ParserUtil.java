package trackitnus.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.commons.util.StringUtil;
import trackitnus.logic.parser.exceptions.InvalidIndexException;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.commons.Address;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.contact.Email;
import trackitnus.model.contact.Phone;
import trackitnus.model.lesson.DayOfWeek;
import trackitnus.model.lesson.LessonDateTime;
import trackitnus.model.lesson.Type;
import trackitnus.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws InvalidIndexException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isDigitSequence(trimmedIndex)) {
            throw new InvalidIndexException(Messages.MESSAGE_NOT_DIGIT_SEQUENCE);
        }
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new InvalidIndexException(Messages.MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses an {@code Optional<String> phone} into an {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parseOptionalPhone(Optional<String> phone) throws ParseException {
        if (phone.isEmpty()) {
            return null;
        }
        String trimmedPhone = phone.get().trim();
        if (trimmedPhone.isBlank()) {
            return null;
        }
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseOptionalEmail(Optional<String> email) throws ParseException {
        if (email.isEmpty()) {
            return null;
        }
        String trimmedEmail = email.get().trim();
        if (trimmedEmail.isBlank()) {
            return null;
        }
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String code} into an {@code Code}.
     *
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static Code parseCode(String code) throws ParseException {
        requireNonNull(code);
        String trimmedCode = code.trim();
        if (!Code.isValidCode(trimmedCode)) {
            throw new ParseException(Code.MESSAGE_CONSTRAINTS);
        }
        return new Code(trimmedCode);
    }

    /**
     * Parses an {@code Optional<String> code} into an {@code Code}.
     *
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static Code parseOptionalCode(Optional<String> code) throws ParseException {
        if (code.isEmpty()) {
            return null;
        }
        String trimmedCode = code.get().trim();

        if (trimmedCode.isBlank()) {
            return null;
        }
        if (!Code.isValidCode(trimmedCode)) {
            throw new ParseException(Code.MESSAGE_CONSTRAINTS);
        }
        return new Code(trimmedCode);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate, DATE_PATTERN);
        } catch (DateTimeParseException e) {
            throw new ParseException(Messages.DATE_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a VALID {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     * Crash the program if the date cannot be parsed (it's the caller responsibility to make sure the date passed in
     * is valid!). The function is written so that the caller doesn't need to handle the possible exception since
     * it's expected that no exception will be thrown.
     */
    public static LocalDate parseValidDate(String date) {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate, DATE_PATTERN);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("An invalid date has been passed into parseValidDate");
        }
    }

    /**
     * Parses a {@code String remark} into a {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String parseRemark(Optional<String> remark) {
        if (remark.isEmpty()) {
            return "";
        }
        return remark.get().trim();
    }

    /**
     * Parses a {@code String type} into a {@code Type}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code type} is invalid.
     */
    public static Type parseType(String type) throws ParseException {
        requireNonNull(type);
        String rawType = type.trim().toLowerCase(Locale.ENGLISH);
        switch (rawType) {
        case "lecture":
        case "lec":
            return Type.LEC;
        case "tutorial":
        case "tut":
            return Type.TUT;
        case "lab":
        case "laboratory":
            return Type.LAB;
        case "recitation":
        case "rec":
            return Type.REC;
        case "sectional":
        case "sec":
            return Type.SEC;
        default:
            throw new ParseException(Type.TYPE_MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String weekday} into a {@code LessonWeekday}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code weekday} is invalid.
     */
    private static DayOfWeek parseLessonWeekday(String weekday) throws ParseException {
        requireNonNull(weekday);
        String raw = weekday.trim().toLowerCase(Locale.ENGLISH);
        switch (raw) {
        case "sun":
            return DayOfWeek.Sun;
        case "mon":
            return DayOfWeek.Mon;
        case "tue":
            return DayOfWeek.Tue;
        case "wed":
            return DayOfWeek.Wed;
        case "thu":
            return DayOfWeek.Thu;
        case "fri":
            return DayOfWeek.Fri;
        case "sat":
            return DayOfWeek.Sat;
        default:
            throw new ParseException(LessonDateTime.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses a {@code String date} into a {@code LessonDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LessonDateTime parseLessonDateTime(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            String[] tokens = trimmedDate.split(" ", 2);
            if (tokens.length != 2) {
                throw new ParseException(LessonDateTime.MESSAGE_CONSTRAINTS);
            }
            String[] startEndTime = tokens[1].split("-", 2);
            DayOfWeek weekday = parseLessonWeekday(tokens[0]);
            if (startEndTime.length != 2) {
                throw new ParseException(LessonDateTime.MESSAGE_CONSTRAINTS);
            }
            LocalTime startTime = LocalTime.parse(startEndTime[0], LessonDateTime.FORMATTER);
            LocalTime endTime = LocalTime.parse(startEndTime[1], LessonDateTime.FORMATTER);
            if (startTime.compareTo(endTime) >= 0) {
                throw new ParseException(LessonDateTime.MESSAGE_CONSTRAINTS);
            }
            return new LessonDateTime(weekday, startTime, endTime);
        } catch (DateTimeParseException e) {
            throw new ParseException(LessonDateTime.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
