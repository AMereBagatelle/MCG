package amerebagatelle.github.io.simplecoordinates;

import amerebagatelle.github.io.simplecoordinates.coordinates.CoordinatesManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class SimpleCoordinates implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger();
    public static final CoordinatesManager coordinatesManager = new CoordinatesManager();

    @Override
    public void onInitializeClient() {
        logger.info("Gathering your coordinates...");

        coordinatesManager.initCoordinatesFolder();
    }
}
