package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackIter;
import seedu.address.model.TrackIter;

/**
 * Represents a storage for {@link TrackIter}.
 */
public interface TrackIterStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTrackIterFilePath();

    /**
     * Returns TrackIter data as a {@link ReadOnlyTrackIter}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTrackIter> readTrackIter() throws DataConversionException, IOException;

    /**
     * @see #getTrackIterFilePath()
     */
    Optional<ReadOnlyTrackIter> readTrackIter(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTrackIter} to the storage.
     *
     * @param trackIter cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTrackIter(ReadOnlyTrackIter trackIter) throws IOException;

    /**
     * @see #saveTrackIter(ReadOnlyTrackIter)
     */
    void saveTrackIter(ReadOnlyTrackIter trackIter, Path filePath) throws IOException;

}
