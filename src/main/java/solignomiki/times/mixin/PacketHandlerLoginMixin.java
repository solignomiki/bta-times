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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

			packetHandlerServer.sendPacket((Packet) new PacketCustomPayload("Times|SeasonsLength", data));
		} catch (IOException exception) {
			Times.LOGGER.error(exception.getMessage());
		}
	}

}
