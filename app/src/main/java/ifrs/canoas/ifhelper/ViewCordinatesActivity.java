package ifrs.canoas.ifhelper;


import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class ViewCordinatesActivity extends DefaultActivity {


    //TODO implementar isso em service e deixar uma opção de lembrete por localização.
    private GoogleApiClient googleApiClient;
    private TextView tvCoordinate;

    //tratador de callback implementada na mesma classe para facilitar os acessos aos elementos da activity
    // outra alternativa é implementar essa interface o que não curto muito
    private GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks(){

        @Override
        public void onConnected(Bundle bundle) {
            Location l = LocationServices
                    .FusedLocationApi
                    .getLastLocation(googleApiClient);

            if(l != null){
                Log.i("LOG", "latitude: "+l.getLatitude());
                Log.i("LOG", "longitude: "+l.getLongitude());
                tvCoordinate.setText(l.getLatitude()+" | "+l.getLongitude());
            }else{
                tvCoordinate.setText("Sem coordenadas");
            }
        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cordinates);
        setToolbar();
        tvCoordinate = (TextView) findViewById(R.id.coordinate);
        usarGoogleApi();
    }


    private synchronized void usarGoogleApi(){
        GoogleApiClient.OnConnectionFailedListener listenerFalha = new GoogleApiClient.OnConnectionFailedListener(){
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.d("PROBLEMA", "Falhou a conexão com a google api");
                //Você pode voltar as origens e usar o sistema da API Android.location https://stackoverflow.com/questions/28535703/best-way-to-get-user-gps-location-in-background-in-android
            }
        };

        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(listenerFalha)// Posso comentar isso mas como bom programador devo me preoucupar com algumas coisas
                .addConnectionCallbacks(connectionCallbacks)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }



    protected void onStop() {
        super.onStop();
        pararConexaoComGoogleApi();
    }

    public void pararConexaoComGoogleApi() {
        //Verificando se está conectado para então cancelar a conexão!
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

}
