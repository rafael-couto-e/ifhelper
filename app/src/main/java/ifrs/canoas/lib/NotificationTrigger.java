package ifrs.canoas.lib;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import ifrs.canoas.ifhelper.R;
import ifrs.canoas.ifhelper.geral.CalculaFaltasActivity;
import ifrs.canoas.ifhelper.portal.ListarMensagemActivity;

public class NotificationTrigger extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// Gets an instance of the NotificationManager service
				NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

				Intent resultIntent = new Intent(context, ListarMensagemActivity.class);

				PendingIntent resultPendingIntent = PendingIntent.getActivity( // atividade
						context, // Contexto que vem do receive "MUITO IMPORTANTE"
						0, // Parametro não usado
						resultIntent, // Intent que lancará
						PendingIntent.FLAG_UPDATE_CURRENT
						);
				NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
						context).setSmallIcon(R.drawable.ic_error_red_24dp)
						.setContentTitle("Notificação lançada com SERVICE e BROADCAST RECEIVER")
						.setContentText("...");

				mBuilder.setContentIntent(resultPendingIntent);// Seta a intent que vai
																// abrir

				Notification n = mBuilder.build();
				n.vibrate = new long[] { 150, 300, 150, 600, 10, 600 };//Vibrar
				n.flags = Notification.FLAG_AUTO_CANCEL;

				mNotifyMgr.notify(1, n);
				
				
				Uri som = Uri.parse("android.resource://ifrs.canoas.ifhelper/" + R.raw.msg);
				Ringtone toque = RingtoneManager.getRingtone(context, som);
				toque.play();
			

	}
	


}
