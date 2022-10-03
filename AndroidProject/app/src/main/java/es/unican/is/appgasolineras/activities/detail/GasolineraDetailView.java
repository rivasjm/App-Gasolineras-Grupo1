package es.unican.is.appgasolineras.activities.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.model.Gasolinera;

public class GasolineraDetailView extends AppCompatActivity {

    public static final String INTENT_GASOLINERA = "INTENT_GASOLINERA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolinera_detail_view);

        // Link to view elements
        ImageView ivRotulo = findViewById(R.id.ivRotulo);
        TextView tvRotulo = findViewById(R.id.tvRotulo);
        TextView tvMunicipio = findViewById(R.id.tvMunicipio);
        TextView tvHorario = findViewById(R.id.tvHorario);

        // Get Gas Station from the intent that triggered this activity
        Gasolinera gasolinera = getIntent().getExtras().getParcelable(INTENT_GASOLINERA);

        // Set logo

        String rotulo = gasolinera.getRotulo().toLowerCase();

        int imageID = getResources().getIdentifier(rotulo, "drawable", getPackageName());
        if (imageID == 0 || TextUtils.isDigitsOnly(rotulo)) {
            imageID = getResources()
                    .getIdentifier("generic", "drawable", getPackageName());
        }
        if (imageID != 0) {
            ivRotulo.setImageResource(imageID);
        }

        // Set Texts
        tvRotulo.setText(gasolinera.getRotulo());
        tvMunicipio.setText(gasolinera.getMunicipio());
        tvHorario.setText(gasolinera.getHorario());
    }
}