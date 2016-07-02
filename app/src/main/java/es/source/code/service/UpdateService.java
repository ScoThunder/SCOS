package es.source.code.service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import es.source.code.R;
import es.source.code.activity.FoodDetailed;

/**
 * Created by Hander on 16/7/2.
 * <p/>
 * Email : hander_wei@163.com
 */
public class UpdateService extends IntentService {

    public UpdateService() {
        super("UpdateService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //TODO Emulate check
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logo);
        builder.setContentTitle("新品上架：番茄炒蛋");
        builder.setContentText("价格：10.0￥；类型：热菜");

        Intent resultIntent = new Intent(this, FoodDetailed.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(FoodDetailed.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(100, builder.build());
    }

}
