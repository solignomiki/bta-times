package solignomiki.times.utils;

import solignomiki.times.Times;

public class Config {
	public static final String MODE = Times.CONFIG.getString("Mode");
	public static final String HEMISPHERE = Times.CONFIG.getString("REALTIME.Hemisphere");
	public static final boolean TURN_OFF_SLEEP = Times.CONFIG.getBoolean("TurnOffSleep");
	public static final int SPRING_LENGTH = Times.CONFIG.getInt("LENGTH.SpringLength");
	public static final int SUMMER_LENGTH = Times.CONFIG.getInt("LENGTH.SummerLength");
	public static final int FALL_LENGTH = Times.CONFIG.getInt("LENGTH.FallLength");
	public static final int WINTER_LENGTH = Times.CONFIG.getInt("LENGTH.WinterLength");
}
