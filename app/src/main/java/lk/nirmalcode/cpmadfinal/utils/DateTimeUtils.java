package lk.nirmalcode.cpmadfinal.utils;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String getRelativeDateTime(Context context, Long dateTime){
        String time = DateUtils.getRelativeDateTimeString(context, dateTime, DateUtils.MINUTE_IN_MILLIS, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE).toString();
        String[] times = time.split(",");
        if(time.contains("ago")){
            if (times[0].equals("0 min. ago"))
                time = "Just now";
            else {
                time = times[0];
            }
        }
        else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                Date timedate = sdf.parse(times[1].trim());
                SimpleDateFormat newsdf = new SimpleDateFormat("K:mm a");
                time = times[0] + ", " + newsdf.format(timedate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return time;
    }
}
