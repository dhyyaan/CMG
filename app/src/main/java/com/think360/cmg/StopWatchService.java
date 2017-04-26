package com.think360.cmg;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.think360.cmg.view.acitivity.LoginActivity;
import com.think360.cmg.view.acitivity.TimeFormatUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by think360 on 18/04/17.
 */

public class StopWatchService extends Service {

    private int currentTime = 0;
    private int lapTime = 0;
    private int lapCounter = 0;
    private int mId = 1;
    private Timer timer;
    private NotificationCompat.Builder builder;
    private NotificationManager manager;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Other code goes here...

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                setUpNotification();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        currentTime += 1;
                        lapTime += 1;
                        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        // update notification text
                        builder.setContentText(TimeFormatUtil.toDisplayString(currentTime));
                        manager.notify(mId, builder.build());

                        // update ui
                        // textView.setText(TimeFormatUtil.toDisplayString(currentTime));

                    }
                }, 0, 100);
            }
        }).start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void setUpNotification() {
        builder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_edit)
                .setContentTitle("Time Started")
                .setContentText("00:00:00")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setOngoing(true);


        Intent resultIntent = new Intent(this, LoginActivity.class);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

    }
}
