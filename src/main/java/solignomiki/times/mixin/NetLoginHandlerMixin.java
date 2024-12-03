package solignomiki.times.mixin;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet250CustomPayload;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetLoginHandler;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import solignomiki.times.Times;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//Lnet/minecraft/server/net/handler/NetServerHandler.sendPacket (Lnet/minecraft/core/net/packet/Packet;)V
@Mixin(value = NetLoginHandler.class)
public abstract class NetLoginHandlerMixin extends NetHandler {

	private NetServerHandler netserverhandler;
//	private EntityPlayerMP entityplayermp;
//	private WorldServer worldserver;

	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "netserverhandler")
	private NetServerHandler captureNetServerHandler(NetServerHandler netserverhandler) {
		this.netserverhandler = netserverhandler;
		return netserverhandler;
	}

//	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "entityplayermp")
//	private EntityPlayerMP captureEntityPlayerMP(EntityPlayerMP entityplayermp) {
//		this.entityplayermp = entityplayermp;
//		return entityplayermp;
//	}
//
//	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "worldserver")
//	private WorldServer captureWorldServer(WorldServer worldserver) {
//		this.worldserver = worldserver;
//		return worldserver;
//	}

	@Inject(
		method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V",
		remap = false,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/net/handler/NetServerHandler;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V",
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

			netserverhandler.sendPacket((Packet) new Packet250CustomPayload("Times|SeasonsLength", data));
		} catch (IOException exception) {
			Times.LOGGER.error(exception.getMessage());
		}
	}
//	@Redirect(
//		method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V",
//		remap = false,
//		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/handler/NetServerHandler;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V", ordinal = 0)
//	)
//	private void sendSeasonsLengthPacket(NetServerHandler netserverhandler, Packet packet1login) {
//
//	}
}
