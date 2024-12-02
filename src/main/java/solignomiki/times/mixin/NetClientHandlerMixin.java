package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigBuilder;
import net.minecraft.core.world.config.season.SeasonConfigCycle;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.SeasonManagerCycle;
import net.minecraft.core.world.season.Seasons;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;

@Mixin(value = NetClientHandler.class)
public abstract class NetClientHandlerMixin {
	@Final
	@Mutable
	@Shadow
	private Minecraft mc;

	@Shadow
	private WorldClient worldClient;

	@Inject(method = "handleLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At("TAIL"))
	public void handleLogin(Packet1Login packet1login, CallbackInfo ci) {
		System.out.println("IM ON CLIENT");

		solignomiki.times.interfaces.Packet1Login packet = ((solignomiki.times.interfaces.Packet1Login) packet1login);

		SeasonConfig seasonConfig = SeasonConfig
			.builder()
			.withSeasonInCycle(Seasons.OVERWORLD_WINTER, packet.getWinterLength())
			.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, packet.getSummerLength())
			.build();
		((WorldType) this.worldClient.worldType).setDefaultSeasonConfig(seasonConfig);

		System.out.println( ( (SeasonConfigCycle) this.mc.theWorld.worldType.getDefaultSeasonConfig()).getSeasons());
		System.out.println(((SeasonConfigCycle) this.worldClient.worldType.getDefaultSeasonConfig()).getSeasons());

		((World) this.worldClient).setSeasonManager(SeasonManager.fromConfig(this.worldClient, this.worldClient.worldType.getDefaultSeasonConfig()));

		System.out.println(this.worldClient.getSeasonManager().getSeasons());
	}
}
