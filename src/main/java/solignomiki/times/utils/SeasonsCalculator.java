package solignomiki.times.utils;

import org.lwjgl.Sys;
import solignomiki.times.Times;
import turniplabs.halplibe.HalpLibe;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class SeasonsCalculator {
	public static final int minecraftDaysInRealDay = 72;
	public final int year;
	public final long ticksInYear;
	public final int daysInTheYear;
	public final ZonedDateTime startOfYear;
	public final long secondsSinceStartOfYear;
	public final long ticksSinceStartOfYear;

	public static long minecraftDayLength = 24000;

	public long realYearsMinecraftWorldLasted;
	public long alignedTime;

	public final int springDays;
	public final int summerDays;
	public final int fallDays;
	public final int winterDays;
	public final int realSpringDays;
	public final int realSummerDays;
	public final int realFallDays;
	public final int realWinterDays;

	private ZonedDateTime now;

	public SeasonsCalculator() {
		if (!HalpLibe.isClient) {
			//System.out.println("DAY: " + Times.CONFIG.getLong("MinecraftDayLength"));
			minecraftDayLength = Times.CONFIG.getLong("MinecraftDayLength");
		}
		this.now = ZonedDateTime.now(ZoneId.systemDefault());
		this.year = now.getYear();

		this.realSpringDays = getDaysInMonths(year, Month.MARCH, Month.APRIL, Month.MAY);
		this.realSummerDays = getDaysInMonths(year, Month.JUNE, Month.JULY, Month.AUGUST);
		this.realFallDays = getDaysInMonths(year, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER);
		this.realWinterDays = getDaysInMonths(year, Month.DECEMBER, Month.JANUARY, Month.FEBRUARY);

//		System.out.println("Весна: " + realSpringDays + " дней");
//		System.out.println("Лето: " + realSummerDays + " дней");
//		System.out.println("Осень: " + realFallDays + " дней");
//		System.out.println("Зима: " + realWinterDays + " дней");

		this.springDays = this.realSpringDays * minecraftDaysInRealDay;
		this.summerDays = this.realSummerDays * minecraftDaysInRealDay;
		this.fallDays = this.realFallDays * minecraftDaysInRealDay;
		this.winterDays = this.realWinterDays * minecraftDaysInRealDay;

//		System.out.println("Весна: " + springDays + " дней");
//		System.out.println("Лето: " + summerDays + " дней");
//		System.out.println("Осень: " + fallDays + " дней");
//		System.out.println("Зима: " + winterDays + " дней");


		this.daysInTheYear = Year.from(this.now).length();
		this.ticksInYear = this.daysInTheYear * minecraftDaysInRealDay * minecraftDayLength;

		// Start of the Minecraft year is March 1st
		ZonedDateTime startOfYear = this.now.withMonth(3).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
		if (
			this.now.getMonthValue() < 3 ||
				(
					this.now.getMonthValue() == 3 &&
						this.now.getDayOfMonth() == 1 &&
						this.now.getHour() == 0 &&
						this.now.getMinute() == 0 &&
						this.now.getSecond() == 0
				)
		) {
			startOfYear = startOfYear.minusYears(1);
		}
		this.startOfYear = startOfYear;

		this.secondsSinceStartOfYear = ChronoUnit.SECONDS.between(this.startOfYear, this.now);

		this.ticksSinceStartOfYear = this.secondsSinceStartOfYear * 20;
	}

	public long getAllignedTime(long currentTime) {
		this.realYearsMinecraftWorldLasted = (int) Math.floor((double) currentTime / this.ticksInYear);
		this.alignedTime = this.ticksSinceStartOfYear + this.realYearsMinecraftWorldLasted * this.ticksInYear;
//		System.out.println("ticksInYear");
//		System.out.println(this.ticksInYear);
//		System.out.println("realYearsMinecraftWorldLasted");
//		System.out.println(this.realYearsMinecraftWorldLasted);
//		System.out.println("currentTime");
//		System.out.println(currentTime);
//		System.out.println("secondsSinceStartOfYear: " + this.secondsSinceStartOfYear);
//		System.out.println("ticksSinceStartOfYear");
//		System.out.println(this.ticksSinceStartOfYear);
//		System.out.println("alignedTime");
//		System.out.println(this.alignedTime);
		return this.alignedTime;

	}

	public static int getDaysInMonths(int year, Month... months) {
		int totalDays = 0;
		for (Month month : months) {
			totalDays += YearMonth.of(year, month).lengthOfMonth();
		}
		return totalDays;
	}
}
