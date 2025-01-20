package solignomiki.times.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;
import solignomiki.times.interfaces.WorldType;
import solignomiki.times.utils.SeasonsConfig;
import turniplabs.halplibe.HalpLibe;
@Mixin(value = WorldTypeOverworld.class)
public abstract class WorldTypeOverworldMixin extends net.minecraft.core.world.type.WorldType implements WorldType {

	public WorldTypeOverworldMixin(net.minecraft.core.world.type.WorldType.Properties properties) {
		super(properties);
	}

	@Environment(EnvType.SERVER)
	@Redirect(
		method = "defaultProperties(Ljava/lang/String;)Lnet/minecraft/core/world/type/WorldType$Properties",
		remap = false,
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;withSeasonInCycle(Lnet/minecraft/core/world/season/Season;I)Lnet/minecraft/core/world/config/season/SeasonConfigBuilder;")
	)
	private static SeasonConfigBuilder redirectSeasonCreation(SeasonConfigBuilder seasonConfigBuilder, Season season, int length) {
		return SeasonsConfig.forWorldType(seasonConfigBuilder, season, length);
	}
}
