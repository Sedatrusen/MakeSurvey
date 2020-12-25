package com.israf.makesurvey.Bildirim;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationHelper {
    public String CHANNEL_ID= "make_survey";
     public void  displayNotification(Context context,String title ,String body){
         Intent i = new Intent(context, MainActivity.class); // Bildirime basıldığında hangi aktiviteye gidilecekse
         i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

         PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

         NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                 .setAutoCancel(true) // Kullanıcı bildirime girdiğinde otomatik olarak silinsin. False derseniz bildirim kalıcı olur.
                 .setContentTitle(title) // Bildirim başlığı
                 .setContentText(body) // Bildirim mesajı
                 .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark) // Bildirim simgesi
                 .setContentIntent(pendingIntent);

         NotificationManagerCompat manager = NotificationManagerCompat.from(context);

         manager.notify(0,builder.build());

     }
}
