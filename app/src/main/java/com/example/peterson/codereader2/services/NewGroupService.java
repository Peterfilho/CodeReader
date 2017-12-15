package com.example.peterson.codereader2.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import com.example.peterson.codereader2.activities.MainActivity;
import com.example.peterson.codereader2.activities.ReadCodeActivity;

/**
 * Created by peterson on 13/12/17.
 */

public class NewGroupService extends IntentService{
    private boolean running = true;
    private PendingIntent resultPendingIntent;

public NewGroupService(){
    super("New group service");
}


    protected void onHandleIntent(Intent intent) {
        notificate();
    }

    protected void notificate() {
        int notificationId = 27;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(android.R.drawable.ic_menu_info_details);
        mBuilder.setContentTitle("Novo grupo criado!");
        mBuilder.setContentText("Adicione codigos nesse grupo!");
        mBuilder.setAutoCancel(true);

        Intent resultIntent = new Intent(this, ReadCodeActivity.class);
        resultPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resultIntent, 0);

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }


}
