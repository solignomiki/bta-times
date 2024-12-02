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
//		EntityPlayerMP entityplayermp = this.mcServer.playerList.getPlayerForLogin(this, packet1login.username);
//		WorldServer worldserver = this.mcServer.getDimensionWorld(entityplayermp.dimension);
		Packet1Login packet = new Packet1Login("", entityplayermp.id, worldserver.getRandomSeed(), (byte)worldserver.dimension.id, (byte) Registries.WORLD_TYPES.getNumericIdOfItem(worldserver.dimensionData.getWorldType()), NetworkManager.PACKET_DELAY, RSA.getPublicKey(RSA.RSAKeyChain.getPublic()));
		((solignomiki.times.interfaces.Packet1Login) packet).setSpringLength(Times.SEASONS_CALCULATOR.springDays);
		((solignomiki.times.interfaces.Packet1Login) packet).setSummerLength(Times.SEASONS_CALCULATOR.summerDays);
		((solignomiki.times.interfaces.Packet1Login) packet).setFallLength(Times.SEASONS_CALCULATOR.fallDays);
		((solignomiki.times.interfaces.Packet1Login) packet).setWinterLength(Times.SEASONS_CALCULATOR.winterDays);
		netserverhandler.sendPacket(packet);
	}
}
