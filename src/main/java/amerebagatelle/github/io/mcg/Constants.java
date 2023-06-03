package amerebagatelle.github.io.mcg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Constants {
    public static final Gson GSON = new GsonBuilder().create();
    public static final Logger LOGGER = LogManager.getLogger();
}
