package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltroPermanenteView extends AppCompatActivity implements IPermanenteContract.View {
    Spinner spnCombustible;
    Spinner spnCCAA;

    IPrefs prefs;

    IPermanenteContract.Presenter presenter;
    FiltroPermanenteMapper mapper;

    CheckBox checkSi;
    CheckBox checkNo;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_permanentes);

        prefs = Prefs.from(this);
        mapper = new FiltroPermanenteMapper();

        //MainPresenter mainPresenter = new MainPresenter(this, p);

        getSupportActionBar().setTitle("Filtros permanentes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FiltroPermanentePresenter(prefs);
        presenter.init();
        this.init();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void init() {
        spnCCAA = findViewById(R.id.spinner_CCAA);
        //Configuracion del spinner de las provincias
        ArrayAdapter<CharSequence> adapterCCAA = ArrayAdapter.createFromResource(this,
                R.array.comunidadesArray, android.R.layout.simple_spinner_item);
        adapterCCAA.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCCAA.setAdapter(adapterCCAA);
        int comunidadGuardada = mapper.getCCAAIndex(prefs.getString("idComunidadName"));
        spnCCAA.setSelection(comunidadGuardada);

        spnCombustible = findViewById(R.id.spinner_combustible);
        //Configuracion del spinner de los combustibles
        ArrayAdapter<CharSequence> adapterCombustibles = ArrayAdapter.createFromResource(this,
                R.array.combustiblesArray, android.R.layout.simple_spinner_item);
        adapterCombustibles.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCombustible.setAdapter(adapterCombustibles);
        int combustibleGuardado = mapper.getCombustibleIndex(prefs.getString("tipoGasolina"));
        spnCombustible.setSelection(combustibleGuardado);

        Button btnGuardarPermanentes = findViewById(R.id.btnGuardarPermanentes);
        btnGuardarPermanentes.setOnClickListener(view -> {
            presenter.guardaFiltroPermanente(spnCCAA.getSelectedItemPosition(), spnCombustible.getSelectedItemPosition());
            openMainView();
        });

        Button btnResetPermanentes = findViewById(R.id.btnResetearPermanentes);
        btnResetPermanentes.setOnClickListener(view -> {
            presenter.reseteaFiltroPermanente();
            this.init();
        });

        checkSi = findViewById(R.id.checkBoxSi);
        checkSi.setOnClickListener(view -> {
            if (checkNo.isChecked()) {
                checkNo.setChecked(false);
            }
            checkSi.setChecked(true);
            /**
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // alerta
            } else {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                String lat = String.valueOf(location.getLatitude());
                                String lon = String.valueOf(location.getLongitude());
                                prefs.putString("latitud", lat);
                                prefs.putString("longitud", lon);
                            }
                        });
            }
             */
        });

        checkNo = findViewById(R.id.checkBoxNo);
        checkNo.setChecked(true);
        checkNo.setOnClickListener(view -> {
            if (checkSi.isChecked()) {
                checkSi.setChecked(false);
            }
            checkNo.setChecked(true);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openMainView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainView() {
        Intent myIntent = new Intent(this, MenuPrincipalView.class);
        startActivity(myIntent);
        finish();
    }

    public void onCheckboxClicked(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // alerta
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            String lat = String.valueOf(location.getLatitude());
                            String lon = String.valueOf(location.getLongitude());
                            prefs.putString("latitud", lat);
                            prefs.putString("longitud", lon);
                        }
                    });
        }


    }

}

