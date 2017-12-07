package com.example.yug08.BM_SWDC_yslee.DBcontrol;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.yug08.BM_SWDC_yslee.MainAppActivity;
import com.example.yug08.BM_SWDC_yslee.R;

/**
 * Created by yug08 on 2017-11-15.
 */

public class BackgroundService extends Service {

    private Notification notification;
    private NotificationManager Notifi_M;
    public ServiceThread serviceThread;

    private int notiflag;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Notifi_M = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        serviceThread = new ServiceThread(handler,getApplicationContext());

        serviceThread.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceThread.stopThread();
        serviceThread = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    class myServiceHandler extends Handler {

        @Override
        public void handleMessage(android.os.Message msg) {
            Intent intent = new Intent(BackgroundService.this, MainAppActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(BackgroundService.this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

            notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("")
                    .setContentText(serviceThread.getmasage())
                    .setSmallIcon(R.mipmap.setiot_win)
                    .setTicker("알림!!!")
                    .setContentIntent(pendingIntent)
                    .build();

            //소리추가
            notification.defaults = Notification.DEFAULT_SOUND;

            //알림 소리를 한번만 내도록
            notification.flags = Notification.FLAG_ONLY_ALERT_ONCE;

            //확인하면 자동으로 알림이 제거 되도록
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            Notifi_M.notify( 777 , notification);

            Toast.makeText(BackgroundService.this, serviceThread.getmasage(), Toast.LENGTH_SHORT).show();
        }
    };


}
