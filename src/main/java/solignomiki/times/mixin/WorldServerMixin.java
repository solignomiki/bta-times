package solignomiki.times.mixin;


import net.minecraft.core.world.World;
import net.minecraft.core.world.save.LevelData;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import solignomiki.times.Times;
import solignomiki.times.utils.Config;

@Mixin(value = WorldServer.class)
public abstract class WorldServerMixin extends World {

//	private long timePlusOneDay;
//
//	@ModifyVariable(method = "updateSleepingPlayers()V", remap = false, at = @At(value = "STORE"), ordinal = 0, name = "timePlusOneDay")
//	private long captureTimePlusOneDay(long timePlusOneDay) {
//		this.timePlusOneDay = timePlusOneDay;
//		return timePlusOneDay;
//	}

	@Redirect(
		method = "updateSleepingPlayers()V",
		remap = false,
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/world/save/LevelData;setWorldTime(J)V",
			ordinal = 0
		)
	)
	private void setWorldTime(LevelData levelData, long time) {
//		if (!(Times.CONFIG.getString("Mode").equalsIgnoreCase(Times.Mode.REALTIME.name()))) {
//			levelData.setWorldTime(timePlusOneDay - timePlusOneDay % 24000L + (long)this.worldType.getSunriseTick(this) + 1000L);
//		}
		if (!Config.TURN_OFF_SLEEP) {
			levelData.setWorldTime(time);
		}

		//System.out.println(time);
	}
}
