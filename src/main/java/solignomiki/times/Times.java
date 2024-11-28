package solignomiki.times;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.util.Properties;


public class Times implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "times";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final ConfigHandler config;

    @Override
    public void onInitialize() {
        LOGGER.info("Times initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}

	static {
		Properties props = new Properties();
		props.setProperty("SeasonLength","28");
		config = new ConfigHandler(MOD_ID, props);
	}
}
