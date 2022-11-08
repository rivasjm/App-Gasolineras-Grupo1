package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.List;

import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public interface IListaFavoritasContract {

    public interface Presenter {

        void init();

        void doSyncInitFavoritas();

        void onGasolineraClicked(int index);
    }

    public interface View {

        void init();

        void showGasolineras(List<Gasolinera> gasolineras);

        void openGasolineraDetails(Gasolinera gasolinera);

        IGasolinerasRepository getGasolineraRepository();

        void showLoadCorrect(int gasolinerasCount);

        void showLoadErrorServidor();

        void openMenuPrincipal();
    }
}
