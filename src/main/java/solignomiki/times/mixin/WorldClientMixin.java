package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.net.packet.Packet255KickDisconnect;
import net.minecraft.core.world.PortalHandler;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigCycle;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = WorldClient.class)
public abstract class WorldClientMixin extends net.minecraft.core.world.World implements solignomiki.times.interfaces.World {
	@Inject(
		method = "sendQuittingDisconnectingPacket()V",
		remap = false,
		at = @At("TAIL")
	)
	public void sendQuittingDisconnectingPacket(CallbackInfo ci) {
		if (this.worldType instanceof WorldTypeOverworld) {
			SeasonConfig seasonConfig = SeasonConfig
				.builder()
				.withSeasonInCycle(Seasons.OVERWORLD_SPRING, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_FALL, 14)
				.withSeasonInCycle(Seasons.OVERWORLD_WINTER, 14)
				.build();
			((WorldType) this.worldType).setDefaultSeasonConfig(seasonConfig);

	//		System.out.println( ( (SeasonConfigCycle) this.worldType.getDefaultSeasonConfig()).getSeasons());
	//		System.out.println(((SeasonConfigCycle) this.worldType.getDefaultSeasonConfig()).getSeasons());

			((World) this).setSeasonManager(SeasonManager.fromConfig(this, this.worldType.getDefaultSeasonConfig()));

	//		System.out.println(this.getSeasonManager().getSeasons());

		}
	}
}
