package com.myLoan.br.tools.math;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间日期处理类
 */
public final class DateUtil {

    /**
     * default date format pattern
     */
    public final static String DATE_FORMAT1 = "yyyy-MM";
    public final static String DATE_FORMAT11 = "yyyyMM";
    public final static String DATE_FORMATD = "dd";
    public final static String DATE_FORMAT = "yyyy-MM-dd";
    public final static String DATE_FORMAT111 = "MM/yyyy";
    public final static String DATE_FORMAT1111 = "dd/MM/yyyy";
    public final static String DATE_FORMAT2 = "yyyy年MM月dd日";
    public final static String DATE_FORMAT3 = "yyyyMMdd";
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public final static String DATE_TIME_FORMAT4 = "dd日 HH小时mm分";
    public final static String FULL_DATE_TIME = "yyyy-MM-dd HH:mm:ss:SSS";
    public final static String TIME_FORMAT = "HH:mm";
    public final static String TIME_FORMAT2 = "HH:mm:ss";
    public final static String MONTH_DAY_HOUR_MINUTE_FORMAT = "MM-dd HH:mm";
    public final static String FULL_DATE_TIME_FORMAT_1 = "yyyy-MM-dd HH:mm:ss";
    public final static String FULL_DATE_TIME_FORMAT_2 = "yyyyMMddHHmmss";
    public final static String FULL_DATE_TIME_FORMAT_3 = "yyyy-MM-dd HH:mm";
    public final static String FULL_DATE_TIME_FORMAT_4 = "yyyyMMddHHmm";
    public final static String FULL_DATE_TIME_FORMAT_5 = "HHddmmss";
    public final static String FULL_DATE_TIME_FORMAT_6 = "HHmmss";
    public final static String FULL_DATE_TIME_FORMAT_7 = "yyyyMMdd_HHmmss";
    private static final int DAYS_OF_A_WEEK = 7;

    private DateUtil() {
    }

    /**
     * parse date with the default pattern
     *
     * @param date string date
     * @return the parsed date
     */
    public static Date parseDate(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT3);
        try {
            return format.parse((date.length() > 8 ? date.substring(0, 8)
                    : date));
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date parseDate1(String date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT11);
        try {
            return format.parse((date.length() > 6 ? date.substring(0, 6)
                    : date));
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 获取增加小时后的 Date
     *
     * @param date
     * @param i
     * @return squall add 20100225
     */
    public static Date addHour(Date date, int i) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, i);
        return calendar.getTime();
    }

    /**
     * format date with the default pattern
     *
     * @param date the date that want to format to string
     * @return the formated date
     */


    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(date);
    }

    public static String formatDate1(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT1);
        return format.format(date);
    }

    public static String formatTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
        return format.format(date);
    }

    /**
     * format date with the given pattern
     *
     * @param date    the date that want to format to string
     * @param pattern the formated pattern
     * @return the formated date
     */
    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * get current date
     *
     * @return the string of current date
     *//*
	public static String getCurrentDateFormat() {
		if (TextUtils.isEmpty(UserInfo.sysDate)
				|| UserInfo.sysDate.length() != 14) {
			return formatDate(new Date());
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			try {
				Date date = dateFormat.parse(UserInfo.sysDate);
				return formatDate(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return formatDate(new Date());
			}
		}
	}
*/

    /**
     * get number of days between the two given date
     *
     * @param fromDate the begin date
     * @param endDate  the end date
     * @return the number of days between the two date
     */
    public static int getDateNum(Date fromDate, Date endDate) {
        long days = (endDate.getTime() - fromDate.getTime())
                / (1000 * 60 * 60 * 24);
        return (int) days;
    }

    /**
     * add day to the date
     *
     * @param date   the added date
     * @param number the number to add to the date
     * @return the added date
     */
    public static Date addDate(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, number);
        return calendar.getTime();
    }

    // 增加月份后获得的date
    public static Date addMonth(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        return calendar.getTime();
    }

    // 增加年份后获得的date
    public static Date addYear(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, number);
        return calendar.getTime();
    }

    public static Date subMonth(Date date, int number) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, number);
        return calendar.getTime();
    }

    /**
     * get the default calendar
     *
     * @return the calendar instance
     */
    public static Calendar getDefaultCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar;
    }

    /**
     * format the date into string value
     *
     * @param calendar the formated calendar
     * @return the string date
     */
    public static String getStringDate(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + getNiceString(month) + "-" + getNiceString(day);
    }

    /**
     * according to the pattern yyyy-MM-dd
     *
     * @param value the value
     * @return the formated value
     */
    public static String getNiceString(int value) {
        String str = "00" + value;
        return str.substring(str.length() - 2, str.length());
    }

    /**
     * get calendar from date
     *
     * @param date the passing date
     * @return the calendar instance
     */
    public static Calendar getCalendarFromDate(Date date) {
        Calendar calendar = getDefaultCalendar();
        calendar.setTime(date);
        return calendar;
    }

    public static String getInterval(Date startDate, Date endDate) {
        long intervalTime = endDate.getTime() - startDate.getTime();
        return getInterval(intervalTime);
    }

    public static int getIntervalMinute(Date startDate, Date endDate) {
        long intervalTime = endDate.getTime() - startDate.getTime();
        return (int) (intervalTime / (1000 * 60));
    }

    public static int getIntervalHour(Date startDate, Date endDate) {
        long intervalTime = endDate.getTime() - startDate.getTime();
        return (int) ((intervalTime / (1000 * 60)) / 60);
    }

    public static String getInterval(long intervalTime) {
        int hour = (int) (intervalTime / (1000 * 60 * 60));
        int minute = (int) (intervalTime / (1000 * 60) - hour * 60);
        int second = (int) ((intervalTime / 1000) - hour * 60 * 60 - minute * 60);
        if (hour > 0) {
            return hour + "小时 " + minute + "分 " + second + "秒";
        } else if (minute > 0) {
            return minute + "分钟 " + second + "秒";
        } else {
            return second + "秒";
        }
    }

    public static String millisecond2dmy(Long milliSecond) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(milliSecond);
        int year = calendar2.get(Calendar.YEAR);
        int month = calendar2.get(Calendar.MONTH)+1;
        int day = calendar2.get(Calendar.DAY_OF_MONTH);
        return  day+ "/" + month + "/" + year;
    }

    public static int getIntervalDay(String sDateStr, String eDateStr) {
        int day = 0;
        try {
            Date sDate = DateUtil.parseDate(sDateStr, DateUtil.DATE_FORMAT);
            Date eDate = DateUtil.parseDate(eDateStr, DateUtil.DATE_FORMAT);
            long intervalTime = eDate.getTime() - sDate.getTime();
            day = (int) (intervalTime / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }

    public static String getDateStr(Date date) {
        return getYear(date) + "年" + getMonth(date) + "月" + getDayOfMonth(date)
                + "日";
    }

    public static int getYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getHour(Date now) {
        Calendar calendar = getCalendarFromDate(now);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static int getWeekOfYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

/*
	public static Date getCurrentDate() {
		Date date;
		if (TextUtils.isEmpty(UserInfo.sysDate)
				|| UserInfo.sysDate.length() != 14) {
			date = new Date();
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			try {
				date = dateFormat.parse(UserInfo.sysDate);
			} catch (ParseException e) {
				e.printStackTrace();
				date = new Date();
			}
		}
		Calendar calendar = getCalendarFromDate(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
*/

/*	public static Date getCurrentTime() {
		Date date;
		if (TextUtils.isEmpty(UserInfo.sysDate)
				|| UserInfo.sysDate.length() != 14) {
			date = new Date();
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
			try {
				date = dateFormat.parse(UserInfo.sysDate);
			} catch (ParseException e) {
				e.printStackTrace();
				date = new Date();
			}
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.HOUR);
		calendar.get(Calendar.MINUTE);
		calendar.get(Calendar.SECOND);
		return calendar.getTime();
	}*/

	/*public static Date getNextDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}*/

    /**
     * 一周的日期
     *
     * @param date
     * @return
     */
    public static List<Date> getWeekDayOfYear(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int year = calendar.get(Calendar.YEAR);

        List<Date> result = new ArrayList<Date>();
        result.add(getDateOfYearWeek(year, week, Calendar.MONDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.TUESDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.WEDNESDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.THURSDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.FRIDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.SATURDAY));
        result.add(getDateOfYearWeek(year, week, Calendar.SUNDAY));
        return result;
    }

    /**
     * 获取一年中某周,星期几的日期
     *
     * @param yearNum
     * @param weekNum
     * @param dayOfWeek
     * @return
     */
    private static Date getDateOfYearWeek(int yearNum, int weekNum,
                                          int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        cal.set(Calendar.YEAR, yearNum);
        cal.set(Calendar.WEEK_OF_YEAR, weekNum);
        /*
         * cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0);
         * cal.set(Calendar.SECOND, 0);
         */
        return cal.getTime();
    }

    /**
     * 获取一年几周
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public int getWeekOfYear(int year, int month, int day) {
        String format = year + "-" + month + "-" + day;
        Date date = parseDate(format, DateUtil.DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 获取月份起始日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMinMonthDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return format.format(calendar.getTime());
    }

    /**
     * 获取月份最后日期
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String getMaxMonthDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(format.parse(date));
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(calendar.getTime());
    }

    /**
     * 获取指定日期是一周的第几天,星期日是第一天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = getCalendarFromDate(date);
        calendar.setMinimalDaysInFirstWeek(DAYS_OF_A_WEEK);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public static boolean beyondDate(Date beginDate, Date endDate, int i) {
        long inter = endDate.getTime() - beginDate.getTime();
        return inter - (i * 1000 * 60 * 60 * 24) >= 0;
    }

    public static boolean beyondMinute(Date beginDate, Date endDate, int i) {
        long inter = endDate.getTime() - beginDate.getTime();
        return Math.abs(inter) - (i * 1000 * 60) >= 0;
    }

    public static boolean beyondSecond(Date beginDate, Date endDate, int i) {
        long inter = endDate.getTime() - beginDate.getTime();
        return Math.abs(inter) - (i * 1000) >= 0;
    }

    public static boolean isEndGtStart(Date StartDate, Date endDate) {
        long inter = endDate.getTime() - StartDate.getTime();
        return inter < 0;
    }

    // 判断是否同一天
    public static boolean isSameDay(Date dateOne, Date dateTwo) {
        boolean flag = true;
        if (getYear(dateOne) != getYear(dateTwo)) {
            flag = false;
        } else if (getMonth(dateOne) != getMonth(dateTwo)) {
            flag = false;
        } else if (getDayOfMonth(dateOne) != getDayOfMonth(dateTwo)) {
            flag = false;
        }
        return flag;
    }

    /**
     * yyyyMMdd日期格式转成yyyy-MM-dd格式
     *
     * @param dateStr
     * @return
     */
    public static String parseDateStr(String dateStr) {
        if (dateStr != null && dateStr.length() >= 8) {
            return formatDate(parseDate(dateStr));
        }
        return dateStr;
    }

    /**
     * yyyy-MM-dd 日期格式转成yyyyMMdd格式
     *
     * @param dateStr
     * @return
     */
    public static String parseDateStr3(String dateStr) {
        if (dateStr != null && dateStr.length() >= 10) {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            return format.format(parseDate(dateStr, DATE_FORMAT3));
        }
        return dateStr;
    }

    /**
     * 计算start和date相差天数
     *
     * @param end
     * @return
     */
    public static int getdays(String start, String end) {
        int year = Integer.parseInt(start.substring(0, 4));
        int month = Integer.parseInt(start.substring(4, 6));
        int day = Integer.parseInt(start.substring(6, 8));
        Calendar calendar = Calendar.getInstance(); // 初始化日历对象
        calendar.set(year, month - 1, day); // 将calendar的时间设置为year年month月day日
        long timeOne = calendar.getTimeInMillis(); // calendar表示的时间转换成毫秒。
        year = Integer.parseInt(end.substring(0, 4));
        month = Integer.parseInt(end.substring(4, 6));
        day = Integer.parseInt(end.substring(6, 8));
        calendar.set(year, month - 1, day); // 将calendar的时间设置为year年month月day日
        long timeTwo = calendar.getTimeInMillis(); // calendar表示的时间转换成毫秒。
        long days = ((timeTwo - timeOne) / (1000 * 60 * 60 * 24));// 计算两个日期相隔天数
        return (int) days;
    }

    /*	*//**
     * 利息试算，根据起始时间和存款类型计算结束日期
     *
     * @param startDate
     * @param bsnType
     * @return
     *//*
	public static String getEndDate(String startDate, String bsnType) {
		try {
			if (StringUtil.isNotBlank(startDate)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(df.parse(startDate));
				if (StringUtil.isNotBlank(bsnType)) {
					if (bsnType.equals("0103")) {
						gc.add(2, 3);
					} else if (bsnType.equals("0106")) {
						gc.add(2, 6);
					} else if (bsnType.equals("0112")) {
						gc.add(1, 1);
					} else if (bsnType.equals("0124")) {
						gc.add(1, 2);
					} else if (bsnType.equals("0136")) {
						gc.add(1, 3);
					} else if (bsnType.equals("0160")) {
						gc.add(1, 5);
					}
				}
				return df.format(gc.getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}*/

    /**
     * 检查日期是否为月末
     *
     * @return ture:月末 false:非月末
     */
    public static boolean checkDateIsME(String dateStr) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT3);
        Date date;
        Calendar cal = Calendar.getInstance();
        try {
            date = df.parse(dateStr);
            cal.setTime(date);
            int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int nowDay = cal.get(Calendar.DAY_OF_MONTH);
            if (nowDay == lastDayOfMonth) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断年份是否为闰年
     *
     * @param year
     * @return
     */
    public static boolean checkDateIsRN(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            return true;
        else
            return false;
    }

    /**
     * 时间比较
     *
     * @param time1
     * @param time2
     * @return 1：time1>time2,
     * -1:time1<time2,
     * 0:time1=time2
     * 2:异常情况
     */
    public static int compareTime(String time1, String time2) {
        try {
            int t1 = Integer.parseInt((time1));
            int t2 = Integer.parseInt((time2));
            if (t1 > t2) {
                return 1;
            } else if (t1 < t2) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 2;
    }

    /**
     * 将时间格式化成yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return
     */
    public static String formatTime(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FULL_DATE_TIME_FORMAT_1);
        try {
            Date date = parseDate(time, FULL_DATE_TIME_FORMAT_2);
            time = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 将时间格式化成yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @param firstDate String转Date
     * @param lastDate  日期最终格式
     * @return
     */
    public static String formatTime(String time, String firstDate, String lastDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(lastDate);
        try {
            Date date = parseDate(time, firstDate);
            time = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String formatTime2hms(String time) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT2);
        try {
            if (time.length() == 5) {
                time = "0" + time;
            }
            Date date = parseDate(time, FULL_DATE_TIME_FORMAT_6);
            time = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String formatTimehms(long ms) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT2);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(ms);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return dateFormat.format(gc.getTime());
    }

    public static String formatTimehms(long ms, String DATE_TIME_FORMAT) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(ms);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return dateFormat.format(gc.getTime());
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats, Locale locale) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = Long.parseLong(timestampString);
        //pt-BR 葡萄牙-🇧🇷
        String date = new SimpleDateFormat(formats, locale).format(new Date(timestamp));
        return date;
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}