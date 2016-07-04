package cap10.android.k19.com.br.locationapi;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final String TAG = "MainActivity";

    private TextView latitudeText;
    private TextView longitudeText;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Obtém a referência dos TextView da activity.
        latitudeText = (TextView) findViewById(R.id.latitude_text);
        longitudeText = (TextView) findViewById(R.id.longitude_text);

        /*
            Obtém a instância de um objeto da classe LocationManager, responsável por obter a
            localização do dispositivo.
         */
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        Location location = null;

        try {
            location = locationManager.getLastKnownLocation(provider);
        } catch (SecurityException e) {
            throw new SecurityException();
        }

        if (location != null) {
            Log.d(TAG, "Provider " + provider + " foi selecionado.");
            onLocationChanged(location);
        } else {
            latitudeText.setText(R.string.location_not_available);
            longitudeText.setText(R.string.location_not_available);
        }
    }//obCreate()

    @Override
    protected void onResume() {
        super.onResume();
        try {
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        } catch (SecurityException e) {
            throw new SecurityException();
        }

    }//onResume()

    @Override
    protected void onPause() {
        super.onPause();
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            throw new SecurityException();
        }
    }//obPause()

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        latitudeText.setText(getString(R.string.point_label, lat));
        longitudeText.setText(getString(R.string.point_label, lng));
    }//onLocationChanged()

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }//onStatusChanged()

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Novo provider " + provider, Toast.LENGTH_SHORT).show();
    }//onProviderEnabled()

    @Override
    public void onProviderDisabled(String provider) {
       Toast.makeText(this, "Provider desabilitado " + provider, Toast.LENGTH_SHORT).show();
    }//onProviderDisabled()
}//class MainActivity
