package com.haoche.yltms.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static int differentDaysByMillisecond(Date date1,Date date2)
    {
        int a = (int) ((date2.getTime() - date1.getTime()) / (1000*3600*24));
        int b = (int) ((date2.getTime() - date1.getTime()) % (1000*3600*24));
        if(b>0){
            return a + 1;
        }
        return a;
    }

    public static void main(String[] args)
    {
        String dateStr = "2019-04-18 22:28:00";
        String dateStr2 = "2019-04-19 22:25:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            Date date2 = format.parse(dateStr2);
            Date date = format.parse(dateStr);

            System.out.println("两个日期的差距：" + differentDaysByMillisecond(date,date2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
