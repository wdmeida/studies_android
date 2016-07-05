package cap10_02.android.k19.com.br.mapsexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

/*
    Para implementar a visualização do mapa da api do google maps, é necessário que a classe implemente
    a classe OnMapReadyCallback. É necessário declarar no build.gradle o compile 'com.google.android.gms:play-services:9.2.0',
    para que a api possa ser referênciada sem erros. Não esquecer também de definir do AndroidManifest.xml as seguintes permissões:
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        try {
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        } catch (SecurityException e) {
            Log.d("Erro de localização: ", e.getMessage());
        }

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));
    }
}
