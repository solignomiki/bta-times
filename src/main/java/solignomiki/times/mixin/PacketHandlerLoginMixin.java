package solignomiki.times.mixin;

import net.minecraft.core.net.handler.PacketHandler;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.server.net.handler.PacketHandlerLogin;
import net.minecraft.server.net.handler.PacketHandlerServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.Times;
import solignomiki.times.utils.Config;
import solignomiki.times.utils.SeasonsConfig;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Mixin(value = PacketHandlerLogin.class)
public abstract class PacketHandlerLoginMixin extends PacketHandler {

	private PacketHandlerServer packetHandlerServer;

	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/PacketLogin;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "packetHandlerServer")
	private PacketHandlerServer capturePacketHandlerServer(PacketHandlerServer packetHandlerServer) {
		this.packetHandlerServer = packetHandlerServer;
		return packetHandlerServer;
	}

	@Inject(
		method = "doLogin(Lnet/minecraft/core/net/packet/PacketLogin;)V",
		remap = false,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/net/handler/PacketHandlerServer;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	private void sendSeasonsLengthPacket(CallbackInfo ci) {
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
			packetHandlerServer.sendPacket((Packet) new PacketCustomPayload("Times|SeasonsLength", data));
		} catch (IOException exception) {
			Times.LOGGER.error(exception.getMessage());
		}
	}

}
