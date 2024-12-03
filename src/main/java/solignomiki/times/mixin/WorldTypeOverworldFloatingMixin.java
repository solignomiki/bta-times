package solignomiki.times.mixin;

import net.minecraft.core.net.packet.Packet4UpdateTime;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.type.WorldTypeOverworldFloating;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = WorldTypeOverworldFloating.class)
public abstract class WorldTypeOverworldFloatingMixin extends WorldTypeOverworld implements WorldType {
	public WorldTypeOverworldFloatingMixin(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
		super(languageKey, defaultWeather, windManager, defaultSeasonConfig);
	}

	@Redirect(
		method = "<init>(Ljava/lang/String;)V",
		remap = false,
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;withSeasonInCycle(Lnet/minecraft/core/world/season/Season;I)Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;")
	)
	private static SeasonConfigBuilder redirectSeasonCreation(SeasonConfigBuilder seasonConfigBuilder, Season season, int length) {
		if (MinecraftServer.getInstance() == null) {
			return seasonConfigBuilder.withSeasonInCycle(season, length);
		}

		if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.LENGTH.name())) {
			return seasonConfigBuilder.withSeasonInCycle(season, Times.CONFIG.getInt("SeasonLength"));
		} else if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.REALTIME.name())) {
			if (season == Seasons.OVERWORLD_SPRING) {
				return seasonConfigBuilder.withSeasonInCycle(season, Times.SEASONS_CALCULATOR.springDays);
			} else if (season == Seasons.OVERWORLD_SUMMER) {
				return seasonConfigBuilder.withSeasonInCycle(season, Times.SEASONS_CALCULATOR.summerDays);
			} else if (season == Seasons.OVERWORLD_FALL) {
				return seasonConfigBuilder.withSeasonInCycle(season, Times.SEASONS_CALCULATOR.fallDays);
			} else if (season == Seasons.OVERWORLD_WINTER) {
				return seasonConfigBuilder.withSeasonInCycle(season, Times.SEASONS_CALCULATOR.winterDays);
			} else {
				Times.LOGGER.error("WTF is this season? Season length will be standart");
				return seasonConfigBuilder.withSeasonInCycle(season, length);
			}
		} else {
			Times.LOGGER.error("The mode specified in config is wrong. Season length will be standart");
			return seasonConfigBuilder.withSeasonInCycle(season, length);
		}
	}
}
