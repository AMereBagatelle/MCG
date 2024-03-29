package amerebagatelle.github.io.mcg.coordinates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CoordinateFolder {
    protected final CoordinateFolder parent;
    protected final Path folder;
    private final Logger logger = LogManager.getLogger();

    public CoordinateFolder(CoordinateFolder parent, Path folder) throws IOException {
        Files.createDirectories(folder);

        this.parent = parent;
        this.folder = folder;
    }

    /**
     * This is only valid once on a given object.  After its usage, the object becomes INVALID and cannot be used.
     *
     * @throws IOException If deleting the file failed.
     */
    public void delete() throws IOException {
        Files.createDirectories(folder);
        try (var files = Files.walk(folder)) {
            files.sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    public Optional<CoordinateFolder> getFolder(String folderName) {
        try {
            return Optional.of(new CoordinateFolder(this, folder.resolve(folderName)));
        } catch (IOException e) {
            logger.error("Failed to create a coordinate folder.");
            return Optional.empty();
        }
    }

    public Optional<CoordinateFile> getFile(String fileName) {
        try {
            return Optional.of(new CoordinateFile(folder.resolve(fileName)));
        } catch (IOException e) {
            logger.error("Failed to create a coordinate file.");
            return Optional.empty();
        }
    }

    public List<CoordinateFile> listFiles() {
        try (var pathStream = Files.list(folder)) {
            return pathStream.filter(Files::isRegularFile).map((path) -> {
                try {
                    return new CoordinateFile(path);
                } catch (IOException ignored) {
                    throw new RuntimeException("AAAAAAAAAAAAA");
                }
            }).toList();
        } catch (IOException e) {
            logger.error("Could not read files in folder " + folder);
            return List.of();
        }
    }

    public List<CoordinateFolder> listFolders() {
        try (var pathStream = Files.list(folder).filter((other) -> !other.equals(folder))) {
            return pathStream.filter(Files::isDirectory).map((path) -> {
                try {
                    return new CoordinateFolder(this, path);
                } catch (IOException ignored) {
                    throw new RuntimeException("AAAAAAAAAAAAA");
                }
            }).toList();
        } catch (IOException e) {
            logger.error("Could not read files in folder " + folder);
            return List.of();
        }
    }

    public CoordinateFolder getParent() {
        return this.parent;
    }

    public String getName() {
        return folder.getFileName().toString();
    }
}
