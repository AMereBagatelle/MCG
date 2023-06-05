package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.CoordinateRoot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateFolderTest {
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

    @Test()
    void testListFiles() throws IOException {
        assertTrue(root.listFiles().isEmpty());

        var file = root.getFile("test.json").orElseThrow();
        file.save();

        assertEquals(1, root.listFiles().size());

        var childFolder = root.getFolder("two/").orElseThrow();

        assertTrue(childFolder.listFiles().isEmpty());

        file = childFolder.getFile("test.json").orElseThrow();
        file.save();

        assertEquals(1, childFolder.listFiles().size());
        assertTrue(childFolder.listFolders().isEmpty());
    }
}
