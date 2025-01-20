package solignomiki.times;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solignomiki.times.utils.Config;
import solignomiki.times.utils.SeasonsCalculator;
import solignomiki.times.utils.SeasonsConfig;
import turniplabs.halplibe.HalpLibe;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

// Real-life day is 72 Minecraft days

public class Times implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "times";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TomlConfigHandler CONFIG;
	public static final SeasonsCalculator SEASONS_CALCULATOR;

	public enum Mode {
		LENGTH,
		REALTIME
	}

	public enum Hemisphere {
		NORTHERN,
		SOUTHERN
	}

    @Override
    public void onInitialize() {
		LOGGER.info("Times initialized.");
    }

	@Override
	public void beforeGameStart() {
	}

	@Override
	public void afterGameStart() {
		if (!HalpLibe.isClient) {
			if (Config.MODE.equalsIgnoreCase(Times.Mode.REALTIME.name())) {
				WorldServer world = MinecraftServer.getInstance().getDimensionWorld(Dimension.OVERWORLD.id);
				world.setWorldTime(SEASONS_CALCULATOR.getAllignedTime(
					world.getWorldTime()
				));
			}
		}
	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {
	}

	static {
		if (HalpLibe.isClient) {
			Toml toml = new Toml();
			CONFIG = new TomlConfigHandler(MOD_ID, toml);
		} else {
			Toml toml = new Toml();
			toml.addEntry("Mode", "Set it to LENGTH if you want to change amount of days in season. Set it to REALTIME if you want seasons to match real ones", "LENGTH");
			toml.addEntry("TurnOffSleep", "false");
			toml.addEntry("MinecraftDayLength", "[NOT IMPLEMENTED RIGHT NOW] Evaluates in minecraft ticks (20 ticks in second). Default is 24000", 24000);
			toml.addCategory( "Settings for LENGTH mode", "LENGTH")
				.addEntry("SpringLength",28)
				.addEntry("SummerLength",28)
				.addEntry("FallLength",28)
				.addEntry("WinterLength",28);
			toml.addCategory("Settings for REALTIME mode", "REALTIME")
				.addEntry("Hemisphere",  "Affects order of seasons. Can be NORTHERN or SOUTHERN. Default is NORTHERN", "NORTHERN");
			CONFIG = new TomlConfigHandler(MOD_ID, toml);
		}
		SEASONS_CALCULATOR = new SeasonsCalculator();
	}

}

