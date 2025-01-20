package solignomiki.times.mixin;


import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.core.util.helper.DyeColor;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.Times;
import solignomiki.times.interfaces.World;
import solignomiki.times.interfaces.WorldType;
import solignomiki.times.utils.Config;
import solignomiki.times.utils.SeasonsConfig;

import java.io.*;

@Mixin(value = PlayerList.class)
public abstract class PlayerListMixin {

	@Inject(
		method = "sendPlayerToOtherDimension(Lnet/minecraft/server/entity/player/PlayerServer;ILnet/minecraft/core/util/helper/DyeColor;Z)V",
		remap = false,
		at = @At(value = "TAIL")
	)
	public void sendPlayerToOtherDimension(PlayerServer playerServer, int targetDim, DyeColor portalColor, boolean generatePortal, CallbackInfo ci) {
		if (targetDim == 0) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);

				if (Config.MODE.equalsIgnoreCase(Times.Mode.LENGTH.name())) {
					dos.writeInt(Config.SPRING_LENGTH);
					dos.writeInt(Config.SUMMER_LENGTH);
					dos.writeInt(Config.FALL_LENGTH);
					dos.writeInt(Config.WINTER_LENGTH);
					dos.writeInt(1);
				} else if (Config.MODE.equalsIgnoreCase(Times.Mode.REALTIME.name())) {
					dos.writeInt(Times.SEASONS_CALCULATOR.springDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.summerDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.fallDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.winterDays);
					dos.writeInt(
						Config.HEMISPHERE
							.equalsIgnoreCase(Times.Hemisphere.NORTHERN.name())
							? 1 : 0
					);
				} else {
					Times.LOGGER.error("The mode specified in config is wrong. Season length will be standart");
					dos.writeInt(14);
					dos.writeInt(14);
					dos.writeInt(14);
					dos.writeInt(14);
					dos.writeInt(1);
				}

				dos.close();
				byte[] data = bos.toByteArray();

				playerServer.playerNetServerHandler.sendPacket((Packet) new PacketCustomPayload("Times|SeasonsLength", data));
			} catch (IOException exception) {
				Times.LOGGER.error(exception.getMessage());
			}
		}
	}
}
