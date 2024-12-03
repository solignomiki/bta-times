package solignomiki.times;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.world.Dimension;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.WorldServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import solignomiki.times.utils.SeasonsCalculator;
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

	public static enum Mode {
		LENGTH,
		REALTIME
	}

    @Override
    public void onInitialize() {
		//NetworkHelper.register(PacketSeasonsLength.class, false, true);
		LOGGER.info("Times initialized.");
    }

	@Override
	public void beforeGameStart() {
	}

	@Override
	public void afterGameStart() {
		if (MinecraftServer.getInstance() != null) {
			WorldServer world = MinecraftServer.getInstance().getDimensionWorld(Dimension.overworld.id);
			world.setWorldTime(SEASONS_CALCULATOR.getAllignedTime(
				world.getWorldTime()
			));
		}
	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {
	}

	static {
		Toml toml = new Toml();
		toml.addEntry("Mode", "Set it to LENGTH if you want to change amount of days in season. Set it to REALTIME if you want seasons to match real ones", "LENGTH");
		toml.addEntry("SpringLength","28");
		toml.addEntry("SummerLength","28");
		toml.addEntry("FallLength","28");
		toml.addEntry("WinterLength","28");
		CONFIG = new TomlConfigHandler(MOD_ID, toml);
		SEASONS_CALCULATOR = new SeasonsCalculator();
	}

}
