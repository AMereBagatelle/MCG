package amerebagatelle.github.io.mcg;

import amerebagatelle.github.io.mcg.coordinates.CoordinatesManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class MCG implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger();
    public static final CoordinatesManager coordinatesManager = new CoordinatesManager();

    @Override
    public void onInitializeClient() {
        logger.info("Gathering your coordinates...");

        coordinatesManager.initCoordinatesFolder();
    }
}