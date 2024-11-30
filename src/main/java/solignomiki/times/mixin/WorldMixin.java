package solignomiki.times.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.core.world.season.Season;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = World.class)
public class WorldMixin {
	@Shadow
	public List<EntityPlayer> players;

	@Shadow
	protected LevelData levelData;

	@Final
	@Mutable
	@Shadow
	private SeasonManager seasonManager;

	@Inject(
		method = "<init>(Lnet/minecraft/core/world/save/LevelStorage;Ljava/lang/String;JLnet/minecraft/core/world/Dimension;Lnet/minecraft/core/world/type/WorldType;)V",
		remap = false,
		at = @At(value = "TAIL")
	)
	public void init(CallbackInfo ci) {
		System.out.println("seasons:");
		System.out.println(seasonManager.getDayInSeason());
	}

}
