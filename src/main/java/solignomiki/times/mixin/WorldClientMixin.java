package solignomiki.times.mixin;


import net.minecraft.client.world.WorldClientMP;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;
import solignomiki.times.utils.SeasonsConfig;

@Mixin(value = WorldClientMP.class)
public abstract class WorldClientMixin extends net.minecraft.core.world.World implements solignomiki.times.interfaces.World {
	@Inject(
		method = "sendQuittingDisconnectingPacket()V",
		remap = false,
		at = @At("TAIL")
	)
	public void sendQuittingDisconnectingPacket(CallbackInfo ci) {
		if (this.worldType instanceof WorldTypeOverworld) {
			SeasonConfig seasonConfig = SeasonsConfig.forClient(new int[]{14, 14, 14, 14, 1});
			((WorldType) this.worldType).setSeasonConfig(seasonConfig);

	//		System.out.println( ( (SeasonConfigCycle) this.worldType.getDefaultSeasonConfig()).getSeasons());
	//		System.out.println(((SeasonConfigCycle) this.worldType.getDefaultSeasonConfig()).getSeasons());

			((World) this).setSeasonManager(SeasonManager.fromConfig(this, this.worldType.getSeasonConfig()));

	//		System.out.println(this.getSeasonManager().getSeasons());

		}
	}
}
