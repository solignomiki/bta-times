package solignomiki.times.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet250CustomPayload;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.SeasonManager;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldTypeOverworld;
import net.minecraft.server.entity.player.EntityPlayerMP;
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
		method = "sendPlayerToOtherDimension(Lnet/minecraft/server/entity/player/EntityPlayerMP;IZ)V",
		remap = false,
		at = @At(value = "TAIL")
	)
	public void sendPlayerToOtherDimension(EntityPlayerMP entityplayermp, int targetDim, boolean generatePortal, CallbackInfo ci) {
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

				entityplayermp.playerNetServerHandler.sendPacket((Packet) new Packet250CustomPayload("Times|SeasonsLength", data));
			} catch (IOException exception) {
				Times.LOGGER.error(exception.getMessage());
			}
		}
	}

}
