package solignomiki.times.mixin;

import net.minecraft.client.world.WorldClient;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.type.WorldType;
import org.spongepowered.asm.mixin.*;

@Mixin(value = WorldType.class)
public abstract class WorldTypeMixin {

	@Final
	@Mutable
	@Shadow
	private SeasonConfig defaultSeasonConfig;

	@Unique
	public void setDefaultSeasonConfig(SeasonConfig seasonConfig) {
		this.defaultSeasonConfig = seasonConfig;
	}
}
