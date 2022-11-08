package es.unican.is.appgasolineras.activities.listaFavoritos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class ListaFavoritasView extends AppCompatActivity implements IListaFavoritasContract.View{

    private IListaFavoritasContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_favoritas);

        getSupportActionBar().setTitle("Lista gasolineras favoritas");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.init();
    }

    @Override
    public void init() {

    }

}