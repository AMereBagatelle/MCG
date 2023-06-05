package amerebagatelle.github.io.mcg.coordinates;

import java.io.IOException;
import java.nio.file.Path;

public class CoordinateRoot extends CoordinateFolder {
    public CoordinateRoot(Path folder) throws IOException {
        super(null, folder);
    }

    public String getRootRelativePath(CoordinateFolder folder) {
        return this.folder.relativize(folder.folder).toString();
    }

    @Override
    public CoordinateFolder getParent() {
        throw new RuntimeException("Root folders do not have parents!");
    }
}