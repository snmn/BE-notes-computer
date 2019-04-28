package project.m.medicareminorproject.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Lenovo on 7/23/2016.
 */
public class SchedulingService extends Service { // broadcast receiver le yeslai call garxa
    @Nullable
    @Override
    public IBinder onBind(Intent intent) { // yo chai rakhnai parxa mlai ni taha xaina kna ho
        return null;
    }

    public static final String TAG = "Scheduling";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        sendNotification("It's 07:00 PM. Please tap to The Connect Plus TCP for updates. Thank You ! Admin");
        Intent dialogIntent = new Intent(this, AlarmActivity.class);
        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dialogIntent);
        return START_NOT_STICKY;
    }



}
