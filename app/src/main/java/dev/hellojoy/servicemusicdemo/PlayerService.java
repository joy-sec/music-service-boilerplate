package dev.hellojoy.servicemusicdemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;

public class PlayerService extends Service {

    private static final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final String TAG = "PlayerService";

    private MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String title = intent.getStringExtra("name");
        String uriString = intent.getStringExtra("uri");

        Uri uri = Uri.parse(uriString);

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Player")
                .setContentText(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .build();


        if (player.isPlaying()){
            player.stop();
            player.reset();
        }

        try {
            player.setDataSource(this,uri);
            player.prepare();
            player.start();


        } catch (IOException e) {
            e.printStackTrace();
        }


        startForeground(1, notification);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player !=null){
            player.stop();
            player.release();
        }
        Log.d(TAG, "onDestroy: player service stopped" );
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }



}
