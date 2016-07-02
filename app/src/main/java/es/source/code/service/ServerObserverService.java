package es.source.code.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Hander on 16/7/1.
 * <p/>
 * Email : hander_wei@163.com
 */
public class ServerObserverService extends Service {

    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();

    public Handler cMessageHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //关闭模拟接收服务器传回菜品库存信息的多线程
                    mThreadPool.shutdownNow();
                    break;
                case 1:
                    //启动 多线程模拟接收服务器传回菜品库存信息（菜名称，库存量），每次接收
                    //到新数据，判断 SCOS app 进程是否在运行状态，如果为运行状态则向
                    //SCOS app 进程发送 Message，其 what 值为 10，并在该 Message 中携带
                    //收到的库存信息（菜名称，库存量），多线程休眠频率为 300 ms
                    if (isCurrentProcessRunning()) {
                        while (true) {
                            mThreadPool.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    msg.what = 10;
                                    msg.obj = "番茄炒蛋";   //模拟菜名称
                                    msg.arg1 = 10;  //模拟库存量
                                    cMessageHandler.sendMessage(msg);
                                }
                            });
                            try {
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            }
        }
    };

    /**
     * 判断当前App进程是否正在运行
     *
     * @return
     */
    private boolean isCurrentProcessRunning() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.processName.equals("es.source.code")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("1")) {
            Message msg = new Message();
            msg.what = 1;
            cMessageHandler.sendMessage(msg);
        } else if (intent.getStringExtra(Intent.EXTRA_TEXT).equals("0")) {
            Message msg = new Message();
            msg.what = 0;
            cMessageHandler.sendMessage(msg);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
