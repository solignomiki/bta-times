package solignomiki.times.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import solignomiki.times.Times;
import turniplabs.halplibe.HalpLibe;

public class SeasonsConfig {
	@Environment(EnvType.SERVER)
	public static SeasonConfigBuilder forWorldType(SeasonConfigBuilder seasonConfigBuilder, Season season, int length) {

		if (Config.MODE.equalsIgnoreCase(Times.Mode.LENGTH.name())) {
			if (season == Seasons.OVERWORLD_SPRING) {
				return seasonConfigBuilder.withSeasonInCycle(season, Config.SPRING_LENGTH);
			} else if (season == Seasons.OVERWORLD_SUMMER) {
				return seasonConfigBuilder.withSeasonInCycle(season, Config.SUMMER_LENGTH);
			} else if (season == Seasons.OVERWORLD_FALL) {
				return seasonConfigBuilder.withSeasonInCycle(season, Config.FALL_LENGTH);
			} else if (season == Seasons.OVERWORLD_WINTER) {
				return seasonConfigBuilder.withSeasonInCycle(season, Config.WINTER_LENGTH);
			} else {
				Times.LOGGER.error("WTF is this season? Season length will be standart");
				return seasonConfigBuilder.withSeasonInCycle(season, length);
			}
		} else if (Config.MODE.equalsIgnoreCase(Times.Mode.REALTIME.name())) {
			if (Config.MODE == null) {
				throw new IllegalStateException("Mode is not set. Go to config/times.cfg and set it to LENGTH or REALTIME");
			}
			if (Config.HEMISPHERE == null) {
				throw new IllegalStateException("Hemisphere is not set. Go to config/times.cfg and set it to NORTHERN or SOUTHERN");
			}
			if (Config.HEMISPHERE.equalsIgnoreCase(Times.Hemisphere.NORTHERN.name())) {
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
			} else if (Config.HEMISPHERE.equalsIgnoreCase(Times.Hemisphere.SOUTHERN.name())) {
				if (season == Seasons.OVERWORLD_SPRING) {
					return seasonConfigBuilder.withSeasonInCycle(Seasons.OVERWORLD_FALL, Times.SEASONS_CALCULATOR.fallDays);
				} else if (season == Seasons.OVERWORLD_SUMMER) {
					return seasonConfigBuilder.withSeasonInCycle(Seasons.OVERWORLD_WINTER, Times.SEASONS_CALCULATOR.winterDays);
				} else if (season == Seasons.OVERWORLD_FALL) {
					return seasonConfigBuilder.withSeasonInCycle(Seasons.OVERWORLD_SPRING, Times.SEASONS_CALCULATOR.springDays);
				} else if (season == Seasons.OVERWORLD_WINTER) {
					return seasonConfigBuilder.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, Times.SEASONS_CALCULATOR.summerDays);
				} else {
					Times.LOGGER.error("WTF is this season? Season length will be standart");
					return seasonConfigBuilder.withSeasonInCycle(season, length);
				}
			} else {
				Times.LOGGER.error("WTF is this Hemisphere? Season length will be standart");
				return seasonConfigBuilder.withSeasonInCycle(season, length);
			}

		} else {
			Times.LOGGER.error("The mode specified in config is wrong. Season length will be standart");
			return seasonConfigBuilder.withSeasonInCycle(season, length);
		}
	}
	static public SeasonConfig forClient(int[] seasonsData) {
		if (seasonsData[4] == 1) {
			return SeasonConfig
				.builder()
				.withSeasonInCycle(Seasons.OVERWORLD_SPRING, seasonsData[0])
				.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, seasonsData[1])
				.withSeasonInCycle(Seasons.OVERWORLD_FALL, seasonsData[2])
				.withSeasonInCycle(Seasons.OVERWORLD_WINTER, seasonsData[3])
				.build();
		} else {
			return SeasonConfig
				.builder()
				.withSeasonInCycle(Seasons.OVERWORLD_FALL, seasonsData[0])
				.withSeasonInCycle(Seasons.OVERWORLD_WINTER, seasonsData[1])
				.withSeasonInCycle(Seasons.OVERWORLD_SPRING, seasonsData[2])
				.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, seasonsData[3])
				.build();
		}
	}
}
