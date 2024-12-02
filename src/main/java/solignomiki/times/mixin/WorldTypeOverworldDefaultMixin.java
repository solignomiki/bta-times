package solignomiki.times.mixin;

import net.minecraft.core.net.packet.Packet4UpdateTime;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.type.WorldTypeOverworldDefault;
import net.minecraft.core.world.type.WorldTypeOverworldExtended;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import net.minecraft.server.MinecraftServer;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = WorldTypeOverworldDefault.class)
public abstract class WorldTypeOverworldDefaultMixin extends WorldTypeOverworld implements WorldType {
	public WorldTypeOverworldDefaultMixin(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
		super(languageKey, defaultWeather, windManager, defaultSeasonConfig);
	}
}
