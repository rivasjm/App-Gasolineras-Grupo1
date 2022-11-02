package es.unican.is.appgasolineras.activities.filtrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.main.MainView;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltrarView extends AppCompatActivity implements  IFiltrarContract.View{

    ImageButton btnBajarPrecio;
    ImageButton btnSubirPrecio;
    Button btnResetear;
    Button btnMostrarResultados;
    EditText etPrecioLimite;
    Spinner spnMarca;
    FiltroMarcaMapper mapperMarca;

    IPrefs prefs;

    IFiltrarContract.Presenter presenter;

    String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_precio_view);
        prefs = Prefs.from(this);
        getSupportActionBar().setTitle(R.string.filtros);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapperMarca = new FiltroMarcaMapper();

        max = getIntent().getStringExtra("max");
        if(max.length() == 3) {
            max = (max + "0");
        }
        presenter = new FiltrarPresenter(this, prefs, max);
        presenter.init();
        this.init();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void init() {
        //Configuracion del spinner de los combustibles
        spnMarca = findViewById(R.id.spinner_marca);
        ArrayAdapter<CharSequence> adapterMarcas = ArrayAdapter.createFromResource(this,
                R.array.marcasArray, android.R.layout.simple_spinner_item);
        adapterMarcas.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnMarca.setAdapter(adapterMarcas);


        //Configuracion spinner de las marcas
        int indice = mapperMarca.getMarcaIndex(prefs.getString("marca"));
        spnMarca.setSelection(indice);


        etPrecioLimite = findViewById(R.id.etPrecioLimite);
        etPrecioLimite.setText(max.substring(0,4));

        btnBajarPrecio = findViewById(R.id.btnBajarPrecio);
        btnBajarPrecio.setOnClickListener(view ->
            etPrecioLimite.setText(presenter.bajaPrecio(etPrecioLimite.getText().toString()))
        );

        btnSubirPrecio = findViewById(R.id.btnSubirPrecio);
        btnSubirPrecio.setOnClickListener(view ->
                etPrecioLimite.setText(presenter.subePrecio(etPrecioLimite.getText().toString()))
        );

        btnResetear = findViewById(R.id.btnResetear);
        btnResetear.setOnClickListener(view -> {
            etPrecioLimite.setText(max.substring(0, 4));
            spnMarca.setSelection(0);
        });

        btnMostrarResultados = findViewById(R.id.btnMostrarResultados);
        btnMostrarResultados.setOnClickListener(view -> {
            presenter.estableceMarca(mapperMarca.getMarca(spnMarca.getSelectedItemPosition()));
            presenter.estableceRango(String.valueOf(etPrecioLimite.getText()));
        });
        etPrecioLimite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // -
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // -
            }
                // -
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String value = s.toString();
                    Double numericValue = Double.parseDouble(value);
                    if (numericValue > Double.parseDouble(max)) {
                        etPrecioLimite.setText(max.substring(0,4));
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainView(){
        Intent myIntent = new Intent(this, MainView.class);
        startActivity(myIntent);
        finish();
    }

}