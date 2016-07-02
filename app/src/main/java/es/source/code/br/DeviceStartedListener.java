package es.source.code.br;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import es.source.code.service.UpdateService;

/**
 * Created by Hander on 16/7/2.
 * <p/>
 * Email : hander_wei@163.com
 */
public class DeviceStartedListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //1）当 onReceive()方法接收到设备开机广播时，启动 UpdateService 服务
        Intent startServiceIntent = new Intent(context, UpdateService.class);
        context.startService(startServiceIntent);
    }
}
