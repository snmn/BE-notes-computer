package project.m.medicareminorproject.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import project.m.medicareminorproject.database.DatabaseHelper;
import project.m.medicareminorproject.helper.MedicineHelper;

/**
 * Created by Lenovo on 7/23/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    // The app's AlarmManager, which provides access to the system alarm services.
    private AlarmManager alarmMgr;
    // The pending intent that is triggered when the alarm fires.
    private PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        //2. Broadcast Receiver- ani alarm manager le alarm ko time bhayexi yeslai call garxa
        // ani yesle chai service lai call garxa
        Intent service = new Intent(context, SchedulingService.class);
        context.startService(service);
    }

    /**
     * Sets a repeating alarm that runs once a day at approximately 7:00 p.m. When the
     * alarm fires, the app broadcasts an Intent to this WakefulBroadcastReceiver.
     *
     * @param context
     */
    public void setAlarm(Context context) throws ParseException {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList<MedicineHelper> medicineHelpers = databaseHelper.selectFromDatabase();


        //1. Alarm Manager- yesma alarm time set garne
        alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class); // alarm ko time bhayexi AlarmReceiver (mathi ko broadcast receiver
        //call garne ) exactly bhanda onReceive call garxa mathi ko

        String time = medicineHelpers.get(medicineHelpers.size() - 1).getAlarmTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        Date date = dateFormat.parse(time);  // time string hunxa so hour and minute seperate :D gareko

        Calendar calendar = Calendar.getInstance();

        Calendar now = calendar.getInstance(); // alarm kati baje bajaune bhanne yaha lekhna parxa

        calendar.set(Calendar.HOUR_OF_DAY, date.getHours()); // hour of day bhaneko 24 hr format
        calendar.set(Calendar.MINUTE, date.getMinutes()); // minute
        if (now.after(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); //aaile 9 bajo but maile 8 baje ko alarm set gare bhane bholi ko 8 baje puryaune
            // so tyo time ma 24 hr jodeko
        }

        alarmIntent = PendingIntent.getBroadcast(context, Math.round(Float.parseFloat(medicineHelpers.get(medicineHelpers.size() - 1).getId())), intent, PendingIntent.FLAG_CANCEL_CURRENT);

        //int RTC_WAKEUP
        // Alarm time in System.currentTimeMillis() (wall clock time in UTC), which will wake up the device when it goes off
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmMgr.setExact(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), alarmIntent);
        } else {
            alarmMgr.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), alarmIntent);
        }

        // ani set repeating bhanne le alarm repeat garxa ani  AlarmManager.INTERVAL_DAY le chai every day garna khojeko

        // Enable {@code SampleBootReceiver} to automatically restart the alarm when the
        // device is rebooted.
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
//                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmMgr != null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
//        ComponentName receiver = new ComponentName(context, BootReceiver.class);
//        PackageManager pm = context.getPackageManager();

//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);
    }


}
