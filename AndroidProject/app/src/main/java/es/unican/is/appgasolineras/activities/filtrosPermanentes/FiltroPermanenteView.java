package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.is.appgasolineras.R;

public class FiltroPermanenteView extends AppCompatActivity implements IPermanenteContract.view {
    Spinner spinner1;
    Spinner spinner2;

    IPermanenteContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_permanente);
        presenter = new FiltroPermanentePresenter(this, getApplicationContext());

        //Falta anhadir botones Aceptar y Reset
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
    }
}
