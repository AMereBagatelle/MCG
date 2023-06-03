package amerebagatelle.github.io.mcg.coordinates;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Path;

public class CoordinateRoot extends CoordinateFolder {
    public CoordinateRoot(Path folder) throws IOException {
        super(null, folder);
    }

    public String getRootRelativePath(CoordinateFolder folder) {
        return folder.folder.relativize(this.folder).toString();
    }

    @Override
    public CoordinateFolder getParent() {
        throw new RuntimeException("Root folders do not have parents!");
    }
}