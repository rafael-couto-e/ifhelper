package ifrs.canoas.lib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;//Ver de importar esse Handler o an

public class RecadosService extends Service {

	private Timer timerAtual = new Timer();
	private TimerTask task;
	private final Handler handler = new Handler();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.v("TAG_IFHELPER","SERVICE - onCreate()");
		super.onCreate();
	}
	

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId){
		Log.i("TAG_IFHELPER", "onStartCommand()");

        //Wtf?
		task = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
                        Context ct = getApplicationContext();
						ct.sendBroadcast(new Intent(ct, NotificationTrigger.class));//Explicito
					}
				});
			}};

		timerAtual.schedule(task, 300, 300);


		return(super.onStartCommand(intent, flags, startId));//Continua ciclo de vida do meu serviço
	}

}
