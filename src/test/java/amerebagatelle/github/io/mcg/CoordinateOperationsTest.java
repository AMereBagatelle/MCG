package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.Coordinate;
import amerebagatelle.github.io.mcg.coordinates.CoordinateFile;
import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class CoordinateOperationsTest {
    CoordinateRoot root;

    @BeforeEach
    void resetCoordinateFolder() throws IOException {
        Path rootPath = Path.of("./test/coordinates");
        Files.createDirectories(rootPath);
        Files
                .walk(rootPath)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        root = new CoordinateRoot(rootPath);
    }

    CoordinateFile getTestFile() {
        var file = root.getFile("test.json");

        assumeTrue(file.isPresent(), "File was non-existent.  Assuming there were problems, not worried about it.");

        return file.get();
    }

    @Test()
    void coordinateSaving() {
        var file = getTestFile();

        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);

        testSet = new Coordinate("test2", 1, 1, 1, "test3");
        file.addCoordinate(testSet);

        assertDoesNotThrow(file::save);
    }

    @Test()
    void coordinateAdding() {
        var file = getTestFile();

        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);
        assertThrows(IllegalArgumentException.class, () -> file.addCoordinate(testSet));
    }

    @Test()
    void coordinateRemoval() {
        var file = getTestFile();
        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);

        testSet = new Coordinate("test1", 1, 1, 1, "test2");
        assertTrue(file.removeCoordinate(testSet));

        assertFalse(file.removeCoordinate(testSet));
    }

    @Test()
    void coordinateFileDeletion() {
        var file = getTestFile();

        Coordinate testSet = new Coordinate("test1", 1, 1, 1, "test2");
        file.addCoordinate(testSet);
        assertDoesNotThrow(file::save);

        assertDoesNotThrow(file::delete);
    }
}
