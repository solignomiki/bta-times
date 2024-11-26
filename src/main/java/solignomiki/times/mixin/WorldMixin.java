package solignomiki.times.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(value = World.class)
public class WorldMixin {
	@Shadow
	public List<EntityPlayer> players;

	@Shadow
	protected LevelData levelData;

}
