package solignomiki.times.mixin;

import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet1Login;
import net.minecraft.server.net.handler.NetLoginHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Mixin(value = Packet1Login.class)
public abstract class Packet1LoginMixin extends Packet implements solignomiki.times.interfaces.Packet1Login {
	@Unique
	public int springLength;
	@Unique
	public int summerLength;
	@Unique
	public int fallLength;
	@Unique
	public int winterLength;

	@Unique
	public void setSpringLength(int length) {
		this.springLength = length;
	}
	@Unique
	public void setSummerLength(int length) {
		this.summerLength = length;
	}
	@Unique
	public void setFallLength(int length) {
		this.fallLength = length;
	}
	@Unique
	public void setWinterLength(int length) {
		this.winterLength = length;
	}

	@Unique
	public int getSpringLength() {
		return this.springLength;
	}
	@Unique
	public int getSummerLength() {
		return this.summerLength;
	}
	@Unique
	public int getFallLength() {
		return this.fallLength;
	}
	@Unique
	public int getWinterLength() {
		return this.winterLength;
	}

	@Inject(method = "readPacketData(Ljava/io/DataInputStream;)V", remap = false, at = @At("TAIL"))
	public void readPacketData(DataInputStream dis, CallbackInfo ci) throws IOException {
		this.springLength = dis.readInt();
		this.summerLength = dis.readInt();
		this.fallLength = dis.readInt();
		this.winterLength = dis.readInt();
	}

	@Inject(method = "writePacketData(Ljava/io/DataOutputStream;)V", remap = false, at = @At("TAIL"))
	public void writePacketData(DataOutputStream dos, CallbackInfo ci) throws IOException {
		dos.writeInt(this.springLength);
		dos.writeInt(this.summerLength);
		dos.writeInt(this.fallLength);
		dos.writeInt(this.winterLength);
	}
	@Inject(method = "getPacketSize()I", remap = false, at = @At("Return"), cancellable = true)
	public void plusPacketSize(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(cir.getReturnValueI() + 4 + 4 + 4 + 4);
	}
}
