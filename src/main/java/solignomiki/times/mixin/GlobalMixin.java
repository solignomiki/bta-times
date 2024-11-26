package solignomiki.times.mixin;

import net.minecraft.core.Global;
import net.minecraft.core.util.helper.Time;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Global.class)
public class GlobalMixin {
	@Final()
	@Mutable()
	@Shadow()
	public static int DAY_LENGTH_TICKS;
//
//	@Inject(method = "<clinit>", at = @At("TAIL"))
//	private static void injected(CallbackInfo ci) {
//		DAY_LENGTH_TICKS = 1;
//	}
}
