package solignomiki.times.mixin;

import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.type.WorldTypeOverworldExtended;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import net.minecraft.core.world.wind.WindManagerGeneric;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WorldTypeOverworldExtended.class)
public abstract class WorldTypeOverworldExtendedMixin extends WorldTypeOverworld {


	public WorldTypeOverworldExtendedMixin(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
		super(languageKey, defaultWeather, windManager, defaultSeasonConfig);
	}

//	@Redirect(
//		method = "Lnet/minecraft/core/world/type/WorldTypeOverworldExtended;<init>(Ljava/lang/String;)V",
//		//remap = false,
//		at = @At(
//			value = "INVOKE",
//			target = "Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;build()Lnet/minecraft/core/world/config/season/SeasonConfig;",
//			ordinal = 0
//		)
//	)
//	private SeasonConfig replaceSeasonConfigBuild(SeasonConfigBuilder builder) {
//		System.out.println("here");
//		return SeasonConfig.builder()
//			.withSeasonInCycle(Seasons.OVERWORLD_SPRING, 1)
//			.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, 1)
//			.withSeasonInCycle(Seasons.OVERWORLD_FALL, 1)
//			.withSeasonInCycle(Seasons.OVERWORLD_WINTER, 1)
//			.build();
//	}
	@Redirect(
		method = "<init>(Ljava/lang/String;)V",
		remap = false,
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;withSeasonInCycle(Lnet/minecraft/core/world/season/Season;I)Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;")
	)
	private static SeasonConfigBuilder redirectFirst(SeasonConfigBuilder seasonConfigBuilder, Season season, int length) {
		System.out.println("here");
		return seasonConfigBuilder.withSeasonInCycle(season, 6_552);
	}
}
