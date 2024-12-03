package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.NetClientHandler;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.net.packet.Packet250CustomPayload;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.config.season.SeasonConfigCycle;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.Seasons;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.Times;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Mixin(value = NetClientHandler.class)
public abstract class NetClientHandlerMixin extends NetHandler {
	@Final
	@Mutable
	@Shadow
	private Minecraft mc;

	@Shadow
	private WorldClient worldClient;

	@Inject(method = "handleCustomPayload(Lnet/minecraft/core/net/packet/Packet250CustomPayload;)V", remap = false, at = @At("TAIL"))
	public void handleCustomPayload(Packet250CustomPayload packet, CallbackInfo ci) {
		int[] seasonsLengths = new int[4];
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(packet.data);

			DataInputStream dis = new DataInputStream(bais);

			for (int i = 0; i < 4; i++) {
				seasonsLengths[i] = dis.readInt();
			}
			dis.close();
		} catch (IOException exception) {
			Times.LOGGER.error(exception.getMessage());
		}

		SeasonConfig seasonConfig = SeasonConfig
			.builder()
			.withSeasonInCycle(Seasons.OVERWORLD_SPRING, seasonsLengths[0])
			.withSeasonInCycle(Seasons.OVERWORLD_SUMMER, seasonsLengths[1])
			.withSeasonInCycle(Seasons.OVERWORLD_FALL, seasonsLengths[2])
			.withSeasonInCycle(Seasons.OVERWORLD_WINTER, seasonsLengths[3])
			.build();

		((WorldType) this.worldClient.worldType).setDefaultSeasonConfig(seasonConfig);

//		System.out.println(((SeasonConfigCycle) this.mc.theWorld.worldType.getDefaultSeasonConfig()).getSeasons());
//		System.out.println(((SeasonConfigCycle) this.worldClient.worldType.getDefaultSeasonConfig()).getSeasons());

		((World) this.worldClient).setSeasonManager(SeasonManager.fromConfig(this.worldClient, this.worldClient.worldType.getDefaultSeasonConfig()));

//		System.out.println(this.worldClient.getSeasonManager().getSeasons());
	}

}
