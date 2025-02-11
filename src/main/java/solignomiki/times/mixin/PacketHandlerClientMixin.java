package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.client.world.WorldClientMP;
import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.Times;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;
import solignomiki.times.utils.SeasonsConfig;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

@Mixin(value = PacketHandlerClient.class)
public abstract class PacketHandlerClientMixin extends PacketHandler {
	@Final
	@Mutable
	@Shadow
	private Minecraft mc;

	@Shadow
	private WorldClientMP worldClientMP;

	@Inject(
		method = "handleCustomPayload(Lnet/minecraft/core/net/packet/PacketCustomPayload;)V",
		remap = false,
		at = @At("TAIL")
	)
	public void handleCustomPayload(PacketCustomPayload packet, CallbackInfo ci) {
		if (this.worldClientMP.worldType instanceof WorldTypeOverworld) {
			int[] seasonsData = new int[5];
			try {
				ByteArrayInputStream bais = new ByteArrayInputStream(packet.data);

				DataInputStream dis = new DataInputStream(bais);

				for (int i = 0; i < 5; i++) {
					seasonsData[i] = dis.readInt();
				}
				dis.close();
			} catch (IOException exception) {
				Times.LOGGER.error(exception.getMessage());
			}

			SeasonConfig seasonConfig = SeasonsConfig.forClient(seasonsData);
			((WorldType) this.worldClientMP.worldType).setSeasonConfig(seasonConfig);

	//		System.out.println(((SeasonConfigCycle) this.mc.theWorld.worldType.getDefaultSeasonConfig()).getSeasons());
	//		System.out.println(((SeasonConfigCycle) this.worldClient.worldType.getDefaultSeasonConfig()).getSeasons());

			((World) this.worldClientMP).setSeasonManager(SeasonManager.fromConfig(this.worldClientMP, this.worldClientMP.worldType.getSeasonConfig()));

	//		System.out.println(this.worldClient.getSeasonManager().getSeasons());
		}
	}

}
