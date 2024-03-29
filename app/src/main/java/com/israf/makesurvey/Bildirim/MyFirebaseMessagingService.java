package com.israf.makesurvey.Bildirim;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;
import com.israf.makesurvey.ui.main.Result;

import java.util.Objects;


public class MyFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(Objects.requireNonNull(remoteMessage.getNotification()).getBody()); // Mesaj içeriği alınıp bildirim gösteren metod çağırılıyor

    }

    private void showNotification(String message) {

        Intent i = new Intent(this, Result.class); // Bildirime basıldığında hangi aktiviteye gidilecekse
        i.putExtra("SurveyName",message);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setAutoCancel(true) // Kullanıcı bildirime girdiğinde otomatik olarak silinsin. False derseniz bildirim kalıcı olur.
                .setContentTitle("Make Survey") // Bildirim başlığı
                .setContentText("New entry on "+message) // Bildirim mesajı
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark) // Bildirim simgesi
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(0,builder.build());
    }
}
