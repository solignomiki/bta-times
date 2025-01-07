package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.SeasonManager;
import org.spongepowered.asm.mixin.*;

@Mixin(value = World.class)
public abstract class WorldMixin implements solignomiki.times.interfaces.World {
	@Shadow
	public SeasonManager seasonManager;

	@Unique
	public void setSeasonManager(SeasonManager seasonManager) {
		this.seasonManager = seasonManager;
	}
}
