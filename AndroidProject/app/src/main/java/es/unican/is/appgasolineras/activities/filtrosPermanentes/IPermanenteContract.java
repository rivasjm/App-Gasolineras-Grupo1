package es.unican.is.appgasolineras.activities.filtrosPermanentes;

public interface IPermanenteContract {

    public interface presenter {
        void init();

        void guardaFiltro(String tipoGasolina, int idComunidad);

        void reseteaFiltroPermanente();

    }


    public interface view{

    }

}
