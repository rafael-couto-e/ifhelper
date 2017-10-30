package ifrs.canoas.lib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by marcio on 29/10/17.
 */

public class GeoLocationService extends Service {
    private GoogleApiClient googleApiClient;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void verifica(Location loc){

    }

    public void detectLocation(){


    }

}
