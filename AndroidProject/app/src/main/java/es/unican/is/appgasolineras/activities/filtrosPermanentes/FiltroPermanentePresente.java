package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;

import es.unican.is.appgasolineras.R;

public class FiltroPermanentePresente extends AppCompatActivity {

    Spinner spn;
    Spinner spn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_permanente);

        ArrayList<String> tiposGasolina = new ArrayList<String>();
        tiposGasolina.add("Diesel");
        tiposGasolina.add("Gasolina");

        ArrayList<String> comunidades = new ArrayList<String>();
        comunidades.add("Cantabria");
        comunidades.add("Asturias");

        spn = findViewById(R.id.spinner1);
        spn2 =  findViewById(R.id.spinner2);

       spn.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,tiposGasolina));
       spn2.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,tiposGasolina));
    }
}