package solignomiki.times.mixin;

import net.minecraft.core.net.handler.NetHandler;
import net.minecraft.core.util.helper.Time;
import net.minecraft.core.world.World;
import net.minecraft.server.net.handler.NetServerHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Time.class)
public abstract class TimeMixin {
//	@Inject(
//		method = "tick()V",
//		at = @At("HEAD"),
//		remap = false
//	)
//	private static void tick(CallbackInfo ci) {
//		System.out.println("time should stop");
//
//		return;
//	}
//	@Redirect(
//		method = "tick()V",
//		at = @At(value = "FIELD", target = "Lnet/minecraft/core/util/helper/Time;lastTick:J", ordinal = 1),
//		remap = false
//	)
//	private static void redirectLastTick(long original) {
//		// Do nothing, effectively preventing the lastTick from being updated
//	}
}
