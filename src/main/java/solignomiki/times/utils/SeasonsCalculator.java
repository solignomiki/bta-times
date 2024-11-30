package solignomiki.times.utils;

import org.lwjgl.Sys;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class SeasonsCalculator {
	public static final int minecraftDaysInRealDay = 72;
	public static final long minecraftDayLength = 24000;
	public final int year;
	public long ticksInMinecraftYear;
	public int daysInTheYear;
	public long lastYearStart;

	public int springDays;
	public int summerDays;
	public int fallDays;
	public int winterDays;
	public int realSpringDays;
	public int realSummerDays;
	public int realFallDays;
	public int realWinterDays;

	private ZonedDateTime now;

	public SeasonsCalculator() {
		this.now = ZonedDateTime.now(ZoneId.systemDefault());
		this.year = now.getYear();

		this.realSpringDays = getDaysInMonths(year, Month.MARCH, Month.APRIL, Month.MAY);
		this.realSummerDays = getDaysInMonths(year, Month.JUNE, Month.JULY, Month.AUGUST);
		this.realFallDays = getDaysInMonths(year, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER);
		this.realWinterDays = getDaysInMonths(year, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY);

		System.out.println("Весна: " + realSpringDays + " дней");
		System.out.println("Лето: " + realSummerDays + " дней");
		System.out.println("Осень: " + realFallDays + " дней");
		System.out.println("Зима: " + realWinterDays + " дней");

		this.springDays = this.realSpringDays * minecraftDaysInRealDay;
		this.summerDays = this.realSummerDays * minecraftDaysInRealDay;
		this.fallDays = this.realFallDays * minecraftDaysInRealDay;
		this.winterDays = this.realWinterDays * minecraftDaysInRealDay;

		System.out.println("Весна: " + springDays + " дней");
		System.out.println("Лето: " + summerDays + " дней");
		System.out.println("Осень: " + fallDays + " дней");
		System.out.println("Зима: " + winterDays + " дней");
	}

	public long getAllignedTime(long currentTime) {
		this.daysInTheYear = Year.from(this.now).length();
		this.ticksInMinecraftYear = this.daysInTheYear * minecraftDaysInRealDay * minecraftDayLength;
		this.lastYearStart = currentTime % ticksInMinecraftYear;

		// Start of the Minecraft year is March 1st
		ZonedDateTime startOfYear = now.withMonth(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		if (now.getMonthValue() < 3 || (now.getMonthValue() == 3 && now.getDayOfMonth() == 1 && now.getHour() == 0 && now.getMinute() == 0 && now.getSecond() == 0)) {
			startOfYear = startOfYear.minusYears(1);
		}

		long secondsSinceStartOfYear = ChronoUnit.SECONDS.between(startOfYear, now);
		long ticksSinceStartOfYear = secondsSinceStartOfYear * 20;

		System.out.println("ticksInMinecraftYear");
		System.out.println(ticksInMinecraftYear);
		System.out.println("ticksInRealYear");
		System.out.println(daysInTheYear * 72 * 24000);
		System.out.println("lastYearStart");
		System.out.println(this.lastYearStart);
		System.out.println("currentTime");
		System.out.println(currentTime);
		System.out.println("secondsSinceStartOfMinecraftYear: " + secondsSinceStartOfYear);
		System.out.println("ticksSinceStartOfMinecraftYear");
		System.out.println(ticksSinceStartOfYear);
		return ticksSinceStartOfYear;

	}

	public static int getDaysInMonths(int year, Month... months) {
		int totalDays = 0;
		for (Month month : months) {
			totalDays += YearMonth.of(year, month).lengthOfMonth();
		}
		return totalDays;
	}
}
