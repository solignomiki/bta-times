package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.EntityClientPlayerMP;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.net.command.ServerPlayerCommandSender;
import net.minecraft.core.world.World;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigCycle;
import net.minecraft.core.world.season.*;
import net.minecraft.core.world.type.WorldTypeOverworldExtended;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = WorldClient.class)
public abstract class WorldClientMixin extends World implements solignomiki.times.interfaces.World {
	@Final
	@Mutable
	@Shadow
	private Minecraft mc;

	@Inject(method = "tick()V", remap = false, at = @At("TAIL"))
	private void onWorldClientInit(CallbackInfo ci) {
//		System.out.println("IM ON CLIENT");
//		SeasonConfig seasonConfig = this.mc.theWorld.worldType.getDefaultSeasonConfig().builder().withSeasonInCycle(Seasons.OVERWORLD_WINTER, 1).build();
////		WorldType worldType = (WorldType) this.mc.theWorld.worldType;
////		worldType.setDefaultSeasonConfig(seasonConfig);
//
//
//
//		((WorldType) this.worldType).setDefaultSeasonConfig(seasonConfig);
//		System.out.println( ( (SeasonConfigCycle) this.mc.theWorld.worldType.getDefaultSeasonConfig()).getSeasons());
//		System.out.println(((SeasonConfigCycle) this.worldType.getDefaultSeasonConfig()).getSeasons());
//		this.setSeasonManager(SeasonManager.fromConfig(this, this.worldType.getDefaultSeasonConfig()));
//		System.out.println(((SeasonManagerCycle) this.getSeasonManager()).getSeasons());
	}
}
