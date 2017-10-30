package ifrs.canoas.lib;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceInicialize extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		intent = new Intent(context, GeoLocationService.class);//explicita
		context.startService(intent);
	}
	
}
