package solignomiki.times.mixin;

import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.net.NetworkManager;
import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.core.util.helper.RSA;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.handler.NetLoginHandler;
import net.minecraft.server.net.handler.NetServerHandler;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;

//Lnet/minecraft/server/net/handler/NetServerHandler.sendPacket (Lnet/minecraft/core/net/packet/Packet;)V
@Mixin(value = NetLoginHandler.class)
public abstract class NetLoginHandlerMixin extends NetHandler {

	private EntityPlayerMP entityplayermp;
	private WorldServer worldserver;

	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "entityplayermp")
	private EntityPlayerMP captureEntityPlayerMP(EntityPlayerMP entityplayermp) {
		this.entityplayermp = entityplayermp;
		return entityplayermp;
	}

	@ModifyVariable(method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "worldserver")
	private WorldServer captureWorldServer(WorldServer worldserver) {
		this.worldserver = worldserver;
		return worldserver;
	}


	@Redirect(
		method = "doLogin(Lnet/minecraft/core/net/packet/Packet1Login;)V",
		remap = false,
		at = @At(value = "INVOKE", target = "Lnet/minecraft/server/net/handler/NetServerHandler;sendPacket(Lnet/minecraft/core/net/packet/Packet;)V", ordinal = 0)
	)
	private void sendLoginPacket(NetServerHandler netserverhandler, Packet packet1login) {
		solignomiki.times.interfaces.Packet1Login packet = (solignomiki.times.interfaces.Packet1Login) new Packet1Login(
			"",
			entityplayermp.id,
			worldserver.getRandomSeed(),
			(byte)worldserver.dimension.id,
			(byte) Registries.WORLD_TYPES.getNumericIdOfItem(worldserver.dimensionData.getWorldType()),
			NetworkManager.PACKET_DELAY, RSA.getPublicKey(RSA.RSAKeyChain.getPublic())
		);

		if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.LENGTH.name())) {
			packet.setSpringLength(Times.CONFIG.getInt("SpringLength"));
			packet.setSummerLength(Times.CONFIG.getInt("SummerLength"));
			packet.setFallLength(Times.CONFIG.getInt("FallLength"));
			packet.setWinterLength(Times.CONFIG.getInt("WinterLength"));
		} else if (Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.REALTIME.name())) {
			packet.setSpringLength(Times.SEASONS_CALCULATOR.springDays);
			packet.setSummerLength(Times.SEASONS_CALCULATOR.summerDays);
			packet.setFallLength(Times.SEASONS_CALCULATOR.fallDays);
			packet.setWinterLength(Times.SEASONS_CALCULATOR.winterDays);
		} else {
			Times.LOGGER.error("The mode specified in config is wrong. Season length will be standart");
			packet.setSpringLength(14);
			packet.setSummerLength(14);
			packet.setFallLength(14);
			packet.setWinterLength(14);
		}

		netserverhandler.sendPacket((Packet) packet);
	}
}
