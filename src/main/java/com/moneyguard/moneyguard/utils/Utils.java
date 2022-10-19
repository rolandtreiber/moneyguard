package com.moneyguard.moneyguard.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static int getDayNumber(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfMonth(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static long getDaysDiff(Date date1, Date date2)
    {
        long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String format(Date date)
    {
        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static List<Date> getDates(Date lastUpdated, Date startDate, Date endDate, Short frequencyType, Integer frequencyBasis)
    {
        long time = new Date().getTime();
        Date today = new Date((time - time % (24 * 60 * 60 * 1000)) - 1);
        List<Date> dates = new ArrayList<>();
        if (lastUpdated.compareTo(startDate) < 0 || lastUpdated.compareTo(endDate) > 0) { // last updated is before start or last updated is after end
            return dates;
        } else {
            if (frequencyType == 1) {
                frequencyBasis++;
                if (frequencyBasis == 8) frequencyBasis = 1;
            }
            Date rollingDate = (Date) lastUpdated.clone();
            Date previousDate = (Date) lastUpdated.clone();
            do {
                switch (frequencyType) {
                    case 1: // Same day each week
                        if (Utils.getDayNumber(rollingDate) == frequencyBasis) {
                            dates.add((Date) rollingDate.clone());
                            previousDate = (Date) rollingDate.clone();
                        }
                        break;
                    case 2: // Every nth day
                        if (Utils.getDaysDiff(previousDate, rollingDate) == frequencyBasis) {
                            dates.add((Date) rollingDate.clone());
                            previousDate = (Date) rollingDate.clone();
                        }
                        break;
                    case 3: // Every nth week
                        if (Utils.getDaysDiff(previousDate, rollingDate) == frequencyBasis * 7L) {
                            dates.add((Date) rollingDate.clone());
                            previousDate = (Date) rollingDate.clone();
                        }
                        break;
                    case 4: // Every nth day of each month
                        if (Utils.getDayOfMonth(rollingDate) == frequencyBasis) {
                            dates.add((Date) rollingDate.clone());
                            previousDate = (Date) rollingDate.clone();
                        }
                        break;
                }

                Calendar c = Calendar.getInstance();
                c.setTime(rollingDate);
                c.add(Calendar.DATE, 1);
                rollingDate = c.getTime();
            } while (rollingDate.compareTo(today) < 0);
        }
        return dates;
    }
}
