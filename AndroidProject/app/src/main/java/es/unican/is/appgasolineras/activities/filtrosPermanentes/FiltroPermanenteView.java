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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_permanentes);

        prefs = Prefs.from(this);
        mapper = new FiltroPermanenteMapper();

        getSupportActionBar().setTitle("Filtros permanentes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FiltroPermanentePresenter(prefs);
        presenter.init();
        this.init();
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

        checkSi = findViewById(R.id.checkBoxSi);
        checkSi.setOnClickListener(view -> {
            if (checkNo.isChecked()) {
                checkNo.setChecked(false);
            }
            checkSi.setChecked(true);
        });

        checkNo = findViewById(R.id.checkBoxNo);
        checkNo.setOnClickListener(view -> {
            if (checkSi.isChecked()) {
                checkSi.setChecked(false);
            }
            checkNo.setChecked(true);
        });


        if (prefs.getString("ubicacion").equals("si")) {
            checkSi.setChecked(true);
            checkNo.setChecked(false);
        } else {
            checkNo.setChecked(true);
            checkSi.setChecked(false);
        }

        Button btnGuardarPermanentes = findViewById(R.id.btnGuardarPermanentes);
        btnGuardarPermanentes.setOnClickListener(view -> {
            if (checkSi.isChecked()) {
                presenter.guardaFiltroPermanente(spnCCAA.getSelectedItemPosition(), spnCombustible.getSelectedItemPosition(), true);
                openMainView();
            } else {
                presenter.guardaFiltroPermanente(spnCCAA.getSelectedItemPosition(), spnCombustible.getSelectedItemPosition(), false);
                openMainView();
            }
        });

        Button btnResetPermanentes = findViewById(R.id.btnResetearPermanentes);
        btnResetPermanentes.setOnClickListener(view -> {
            presenter.reseteaFiltroPermanente();
            this.init();
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

}

