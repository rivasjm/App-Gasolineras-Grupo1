package es.unican.is.appgasolineras.activities.info;

import androidx.appcompat.app.AppCompatActivity;
import es.unican.is.appgasolineras.R;
import android.os.Bundle;
import android.widget.TextView;

public class InfoView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);

        getSupportActionBar().setTitle("Información GasFinder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = findViewById(R.id.tvInfoMessage);
        tv.setText("Aplicación creada para el Proyecto Integrado 2022");
    }
}