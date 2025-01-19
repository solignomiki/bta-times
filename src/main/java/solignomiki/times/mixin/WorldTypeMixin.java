package solignomiki.times.mixin;

import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldType.class)
public abstract class WorldTypeMixin {


	@Final
	@Mutable
	@Shadow
	private SeasonConfig seasonConfig;

	@Final
	@Mutable
	@Shadow
	private int dayNightCycleTicks;

	@Unique
	public void setSeasonConfig(SeasonConfig seasonConfig) {
		this.seasonConfig = seasonConfig;
	}

	@Inject(
		method = "<init>(Lnet/minecraft/core/world/type/WorldType$Properties;)V",
		at = @At("TAIL")
	)
	public void modifyDayLength(WorldType.Properties properties, CallbackInfo ci) {
		properties.dayNightCycleTicks(24000);
		this.dayNightCycleTicks = 24000;
	}
}
