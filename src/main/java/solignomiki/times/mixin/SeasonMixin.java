package solignomiki.times.mixin;


import net.minecraft.core.world.World;
import net.minecraft.core.world.season.Season;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = Season.class)
public abstract class SeasonMixin {
	/*
	L8
    LINENUMBER 21 L8
    ALOAD 0
    LDC 0.5
    PUTFIELD net/minecraft/core/world/season/Season.dayLength : F
	 */
	@Redirect(method = "<init>(Ljava/lang/String;)V", remap = false, at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/season/Season;dayLength:F", opcode = Opcodes.PUTFIELD))
	private void redirectDayLength(Season season, float original) {
		System.out.println(season.dayLength);
	}

	@Redirect(method = "setDayLength(F)Lnet/minecraft/core/world/season/Season;", remap = false, at = @At(value = "FIELD", target = "Lnet/minecraft/core/world/season/Season;dayLength:F", opcode = Opcodes.PUTFIELD))
	private void redirectDayLength2(Season season, float original) {
		System.out.println(season.dayLength);
	}
}
