package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTrackIter;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TrackIter data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final TrackIterStorage trackIterStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code TrackIterStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(TrackIterStorage trackIterStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.trackIterStorage = trackIterStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TrackIter methods ==============================

    @Override
    public Path getTrackIterFilePath() {
        return trackIterStorage.getTrackIterFilePath();
    }

    @Override
    public Optional<ReadOnlyTrackIter> readTrackIter() throws DataConversionException, IOException {
        return readTrackIter(trackIterStorage.getTrackIterFilePath());
    }

    @Override
    public Optional<ReadOnlyTrackIter> readTrackIter(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return trackIterStorage.readTrackIter(filePath);
    }

    @Override
    public void saveTrackIter(ReadOnlyTrackIter trackIter) throws IOException {
        saveTrackIter(trackIter, trackIterStorage.getTrackIterFilePath());
    }

    @Override
    public void saveTrackIter(ReadOnlyTrackIter trackIter, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        trackIterStorage.saveTrackIter(trackIter, filePath);
    }

}
