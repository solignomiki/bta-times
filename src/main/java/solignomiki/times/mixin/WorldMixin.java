package solignomiki.times.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.SeasonManager;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = World.class)
public abstract class WorldMixin implements solignomiki.times.interfaces.World {
	@Shadow
	public SeasonManager seasonManager;

	@Unique
	public void setSeasonManager(SeasonManager seasonManager) {
		this.seasonManager = seasonManager;
	}

	@Environment(EnvType.SERVER)
	@ModifyConstant(
		method = "updateSeasonAndLight()V",
		remap = false,
		constant = @Constant(longValue = 24000L)
	)
	public long changeDayLength(long original){
		return 24000;
	}
}
