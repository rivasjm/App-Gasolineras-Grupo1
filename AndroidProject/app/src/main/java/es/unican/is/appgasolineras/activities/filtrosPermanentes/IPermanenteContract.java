package es.unican.is.appgasolineras.activities.filtrosPermanentes;

public interface IPermanenteContract {

    public interface presenter {
        void init();

        void guardaFiltroPermanente(int idComunidad, int tipoGasolina);

        void reseteaFiltroPermanente();

    }


    public interface view{
        void init();
    }

}
