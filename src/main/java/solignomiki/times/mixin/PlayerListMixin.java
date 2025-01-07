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

import java.io.*;

@Mixin(value = PlayerList.class)
public abstract class PlayerListMixin {

	@Inject(
//		method = "sendPlayerToOtherDimension(Lnet/minecraft/server/entity/player/EntityPlayerMP;IZ)V",
		method = "sendPlayerToOtherDimension(Lnet/minecraft/server/entity/player/PlayerServer;ILnet/minecraft/core/util/helper/DyeColor;Z)V",
		remap = false,
		at = @At(value = "TAIL")
	)
	public void sendPlayerToOtherDimension(PlayerServer playerServer, int targetDim, DyeColor portalColor, boolean generatePortal, CallbackInfo ci) {
		if (targetDim == 0) {
			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);

				if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.LENGTH.name())) {
					dos.writeInt(Times.CONFIG.getInt("SpringLength"));
					dos.writeInt(Times.CONFIG.getInt("SummerLength"));
					dos.writeInt(Times.CONFIG.getInt("FallLength"));
					dos.writeInt(Times.CONFIG.getInt("WinterLength"));
				} else if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.REALTIME.name())) {
					dos.writeInt(Times.SEASONS_CALCULATOR.springDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.summerDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.fallDays);
					dos.writeInt(Times.SEASONS_CALCULATOR.winterDays);
				} else {
					Times.LOGGER.error("The mode specified in config is wrong. Season length will be standart");
					dos.writeInt(14);
					dos.writeInt(14);
					dos.writeInt(14);
					dos.writeInt(14);
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
