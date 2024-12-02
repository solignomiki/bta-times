package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.net.packet.Packet4UpdateTime;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.core.world.type.WorldTypeOverworldWoods;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.wind.WindManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.net.handler.NetServerHandler;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = WorldTypeOverworldWoods.class)
public abstract class WorldTypeOverworldWoodsMixin extends WorldTypeOverworld implements WorldType {
	public WorldTypeOverworldWoodsMixin(String languageKey, Weather defaultWeather, WindManager windManager, SeasonConfig defaultSeasonConfig) {
		super(languageKey, defaultWeather, windManager, defaultSeasonConfig);
	}
}
