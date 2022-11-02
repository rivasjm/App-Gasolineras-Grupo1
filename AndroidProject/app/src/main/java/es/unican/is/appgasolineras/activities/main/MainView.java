package es.unican.is.appgasolineras.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import es.unican.is.appgasolineras.R;

import es.unican.is.appgasolineras.activities.filtrar.FiltrarView;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;
import es.unican.is.appgasolineras.activities.detail.GasolineraDetailView;
import es.unican.is.appgasolineras.activities.info.InfoView;

public class MainView extends AppCompatActivity implements IMainContract.View {
    IPrefs prefs;
    private IMainContract.Presenter presenter;
    private FusedLocationProviderClient fusedLocationClient;



    /*
    Activity lifecycle methods
     */

    /**
     * This method is automatically called when the activity is created
     * It fills the activity with the widgets (buttons, lists, etc.)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Lista gasolineras");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = Prefs.from(this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            presenter = new MainPresenter(this, prefs, true);
        } else {
            presenter = new MainPresenter(this, prefs, false);
        }
        if (prefs.getString("ubicacion").equals("si")) {
            this.conseguirUbicacion();
        } else {
            presenter.init();
        }
        this.init();
    }

    /**
     * Create a menu in this activity (three dot menu on the top left)
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * This is the listener to the three-dot menu on the top left
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuInfo:
                presenter.onInfoClicked();
                return true;
            case R.id.menuRefresh:
                presenter.onRefreshClicked();
                return true;
            case android.R.id.home:
                presenter.onHomeClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    IMainContract.View methods
     */

    @Override
    public void init() {
        // init UI listeners
        ListView lvGasolineras = findViewById(R.id.lvGasolineras);
        lvGasolineras.setOnItemClickListener((parent, view, position, id) ->
                presenter.onGasolineraClicked(position)
        );
        Button precio = findViewById(R.id.btnFiltroPrecio);
        precio.setOnClickListener(view ->
                presenter.onPrecioClicked()
        );
        ImageButton resetFiltroPrecio = findViewById(R.id.btnResetearFiltros);
        resetFiltroPrecio.setOnClickListener(view ->
                presenter.onResetFiltroPrecioClicked()
        );
    }

    @Override
    public IGasolinerasRepository getGasolineraRepository() {
        return new GasolinerasRepository(this);
    }

    @Override
    public void showGasolineras(List<Gasolinera> gasolineras) {
        GasolinerasArrayAdapter adapter = new GasolinerasArrayAdapter(this, gasolineras, prefs);
        ListView list = findViewById(R.id.lvGasolineras);
        list.setAdapter(adapter);
    }

    @Override
    public void showLoadCorrect(int gasolinerasCount) {
        if (gasolinerasCount == 0) {
            String text = getResources().getString(R.string.no_gasolineras_con_filtros);
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        } else {
            String text = getResources().getString(R.string.loadCorrect);
            Toast.makeText(this, String.format(text, gasolinerasCount), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadErrorRed() {
        String text = getResources().getString(R.string.loadErrorRed);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadErrorServidor() {
        String text = getResources().getString(R.string.loadErrorServidor);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void openGasolineraDetails(Gasolinera gasolinera) {
        Intent intent = new Intent(this, GasolineraDetailView.class);
        intent.putExtra(GasolineraDetailView.INTENT_GASOLINERA, gasolinera);
        startActivity(intent);
    }

    @Override
    public void openInfoView() {
        Intent intent = new Intent(this, InfoView.class);
        startActivity(intent);
    }

    @Override
    public void openFiltroPrecio() {
        Intent intent = new Intent(this, FiltrarView.class);
        intent.putExtra("max", presenter.getMaximoEntreTodas());
        startActivity(intent);
    }

    @Override
    public void openMenuPrincipal() {
        Intent intent = new Intent(this, MenuPrincipalView.class);
        prefs.putString("maxPrecio","");
        prefs.putString("marca", "");
        startActivity(intent);
    }

    private void conseguirUbicacion() {
        if (androidx.core.content.ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && androidx.core.content.ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            presenter.init();
            // alerta
        } else {
            fusedLocationClient.getCurrentLocation(100, null)
                    .addOnSuccessListener(this, new OnSuccessListener<>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                String lat = String.valueOf(location.getLatitude());
                                String lon = String.valueOf(location.getLongitude());
                                prefs.putString("latitud", lat);
                                prefs.putString("longitud", lon);
                            } else {
                                prefs.putString("latitud", "");
                                prefs.putString("longitud", "");
                            }
                            presenter.init();
                        }
                    });
        }
    }

}
